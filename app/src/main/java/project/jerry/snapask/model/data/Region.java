package project.jerry.snapask.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class Region extends BaseData {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("full_name")
    private String mFullName;

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getFullName() {
        return mFullName;
    }
}
