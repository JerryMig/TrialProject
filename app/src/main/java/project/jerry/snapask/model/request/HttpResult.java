package project.jerry.snapask.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public class HttpResult<T> {

    @SerializedName("status")
    private String mStatus;

    @SerializedName("data")
    private T mData;

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setData(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }

}
