package monji.nsh.com.VideoDownloader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityResult {

    @SerializedName("city_name")
    @Expose
    private String mCityName;

    @SerializedName("city_id")
    @Expose
    private String mCityID;

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public String getCityID() {
        return mCityID;
    }

    public void setCityID(String mCityID) {
        this.mCityID = mCityID;
    }


    public void add(String cityID, String name) {

        this.mCityID = cityID;
        this.mCityName = name;
    }
}

