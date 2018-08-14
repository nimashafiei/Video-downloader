package monji.nsh.com.VideoDownloader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonItems {

    @SerializedName("startDate")
    @Expose
    private String mStartDate;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("url")
    @Expose
    private String mUrl;

    @SerializedName("expire")
    @Expose
    private String mExpaier;

    public String getmTitle() {return mTitle;}

    public void setmTitle(String mTitle) {this.mTitle = mTitle;}

    public String getUrl() {
        return mUrl;
    }

    public String getDate() {
        return mStartDate;
    }

    public void setDate(String mDate) {
        this.mStartDate = mDate;
    }

    public String getmxpaier() {
        return mExpaier;
    }

    public void setExpaier(String mExpaier) {
        this.mExpaier = mExpaier;
    }


}