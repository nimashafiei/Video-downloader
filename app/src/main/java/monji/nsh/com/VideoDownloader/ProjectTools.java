package monji.nsh.com.VideoDownloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nima_S_H on 2/23/2017.
 */
public class ProjectTools {

    private Context mContext;

    public ProjectTools(Context context){
        this.mContext = context;
    }

    public static Typeface getIranSansFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/IRANSansWeb.ttf");
    }

    public static Typeface getNazaninFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/BNazanin.ttf");
    }

    public static Typeface getRoyaFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "font/IRRoyaBold.ttf");
    }


}
