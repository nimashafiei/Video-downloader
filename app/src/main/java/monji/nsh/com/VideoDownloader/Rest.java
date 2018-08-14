package monji.nsh.com.VideoDownloader;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public class Rest {

    public interface api_Items {

        @POST("/api")
        Call<JsonItems> getData(@QueryMap Map<String, String> option);
        @POST("/api/register")
        Call<RegisterItems> register(@QueryMap Map<String, String> option);
        @POST("/api/requestCode")
        Call<RegisterItems> login(@QueryMap Map<String, String> option);
        @POST("/api/sendCode")
        Call<RegisterItems> afterLogin(@QueryMap Map<String, String> option);
        @POST("/api/video/final")
        Call<JsonItems> checkVideo(@QueryMap Map<String, String> option);

    }
}