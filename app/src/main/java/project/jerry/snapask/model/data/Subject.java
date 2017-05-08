package project.jerry.snapask.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class Subject extends BaseData {

    @SerializedName("id")
    private int mId;

    @SerializedName("abbr")
    private String mAbbreviation;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("region")
    private Region mRegion;

    public void setmId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setAbbreviation(String abbreviation) {
        mAbbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return mAbbreviation;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setRegion(Region region) {
        mRegion = region;
    }

    public Region getRegion() {
        return mRegion;
    }
}
