package monji.nsh.com.VideoDownloader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import junit.framework.TestCase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadVideo  {

    DownloadStatus mStatus;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String vidName = "";
    TextView txt;

    public void fetchItems(final Activity activity) {

        pref = activity.getApplicationContext().getSharedPreferences(activity.getString(R.string.SharePreferences), 0);
        editor = pref.edit();

        txt = (TextView)activity.findViewById(R.id.text);

        Map<String, String> data = new HashMap<>();
        data.put("mobile", pref.getString("mobile", null));
    
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(activity.getString(R.string.server_address))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Rest.api_Items service = retrofit.create(Rest.api_Items.class);

        Call<JsonItems> itemsCall = service.checkVideo(data);

        itemsCall.enqueue(new Callback<JsonItems>() {
            @Override
            public void onResponse(Call<JsonItems> call, Response<JsonItems> response) {

                String str = response.body().getUrl();
                Log.e("Message*********", str);
                if (!str.equals("empty")) {

                    if(!str.equals(pref.getString("VideoTitle", null))) {
                        vidName = str;
                        Log.e("VidNameeee", str);
                        new downloadFile().execute(activity.getString(R.string.preVideoAddress) + str,
                                Environment.getExternalStorageDirectory()
                                        + "/Android/data/"
                                        + activity.getPackageName()
                                        + "/Videos/");
                    }
                    else{
                        Log.e("Message", "Duplicate");
                    }
                } else {
                    Log.e("Message", "null");
                }
            }

            @Override
            public void onFailure(Call<JsonItems> call, Throwable throwable) {
                Log.e("Message", "Failed");
            }
        });
    }

    private class downloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.e("URL", "onPreExecute");
            txt.setVisibility(View.VISIBLE);
            txt.setText("Downloading....!!!");
            txt.bringToFront();
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                File outputFile = new File(urls[1]);

                if(!outputFile.exists())
                    outputFile.mkdirs();

                Log.e("VidName=========>", vidName);
                outputFile = new File(urls[1] + vidName);


                URL u = new URL(urls[0]);

                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

                DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
                fos.write(buffer);
                fos.flush();
                fos.close();

            } catch (FileNotFoundException e) {
                Log.e("FileNotFoundException", e.getMessage());

            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
            }

            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("URL", "onPostExecute" + vidName);
            editor.clear();
            editor.putString("VideoTitle", vidName);
            editor.commit();
            txt.setVisibility(View.INVISIBLE);
        }
    }


}