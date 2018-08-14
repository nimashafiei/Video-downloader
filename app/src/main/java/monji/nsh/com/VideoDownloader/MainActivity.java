package monji.nsh.com.VideoDownloader;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SocketStatus {

    VideoView videoView;

    private String path;

    private static String TAG = "MonjiApp";

    String currentVideo = "", vidTitle = "";

    WebSocket webSocket;

    int lastMinute = 0;
    int currentMinute = 0;

    boolean status = false;

    TextView txt;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        DownloadVideo downloadVideo = new DownloadVideo();

        Log.e(TAG, "*********");

        if(vidTitle == null){

            Log.e(TAG, "VidTitle is null");
            videoView.setVideoPath(path + "1.mp4");
            videoView.start();

            downloadVideo.fetchItems(MainActivity.this);
        }
        else{

            Log.e(TAG, "VidTitle is not null");

            videoView.setVideoPath(path + vidTitle);
            videoView.start();
        }

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                play();
            }
        });

    }

    private void init(){

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        pref = getApplicationContext().getSharedPreferences(getString(R.string.SharePreferences), 0);
        SharedPreferences.Editor editor = pref.edit();



        path = Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getPackageName() + "/Videos/";

        vidTitle = pref.getString("VideoTitle", null);
//        Log.e(TAG, vidTitle);

        videoView = (VideoView) findViewById(R.id.videoView);

    }

    private void play(){

        String str = pref.getString("VideoTitle", null);

        if(str == null){
            Log.e(TAG, "str is null");
        }
        else
            if (!vidTitle.equals(str)) {
                vidTitle = str;
                videoView.setVideoPath(path + vidTitle);
            }

//        if(currentVideo.equals("1.mp4")){
//            if(file2.exists()) {
//                videoView.setVideoPath(path + "2.mp4");
//                file1.delete();
//            }
//            else
//                videoView.setVideoPath(path + "1.mp4");
//        }
//        else{
//            if(file1.exists()) {
//                videoView.setVideoPath(path + "1.mp4");
//                file2.delete();
//            }
//            else
//                videoView.setVideoPath(path + "2.mp4");
//        }
        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        webSocket = new WebSocket(MainActivity.this);

        if(!videoView.isPlaying())
            videoView.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(videoView!= null)
            videoView.pause();
    }

    //
//
    @Override
    public void onBackPressed() {

    }


    @Override
    public void onClose() {


        status = false;

        Thread thread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                lastMinute = currentMinute;
                while (true)
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());

                    currentMinute = calendar.get(Calendar.MINUTE);


                    if (currentMinute != lastMinute){

                        Log.e("Current", currentMinute + "");
                        Log.e("Last", lastMinute + "");

                        lastMinute = currentMinute;



                        if(!status) {
                            Log.e("LOG", status + "");
                            webSocket = new WebSocket(MainActivity.this);
                            status = true;
                            break;
                        }


                    }
                }
            }
        });
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        thread.run();





    }
}
