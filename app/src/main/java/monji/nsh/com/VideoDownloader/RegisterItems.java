package monji.nsh.com.VideoDownloader;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterItems {

    @SerializedName("result")
    @Expose
    private String mResult;

    public String getResult() {
        return mResult;
    }

    @SerializedName("name")
    @Expose
    private String mName;

    public String getName() {
        return mName;
    }

    public void setResult(String mResult) {
        this.mName = mResult;
    }

}