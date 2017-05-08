package project.jerry.snapask.model.data;

import com.google.gson.annotations.SerializedName;

import project.jerry.snapask.model.data.BaseData;
import project.jerry.snapask.model.data.Person;
import project.jerry.snapask.model.data.Subject;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class ClassData extends BaseData {

    @SerializedName("id")
    private int mId;

    @SerializedName("asked_by")
    private Person mAskingPerson;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("user_id")
    private int mAskingUserId;

    @SerializedName("answer_tutor_id")
    private int mAnsweringUserId;

    @SerializedName("answered_by")
    private Person mAnsweringPerson;

    @SerializedName("created_at")
    private String mTime;

    @SerializedName("subject")
    private Subject mSubject;

    @SerializedName("picture_url")
    private String mClassPic;

    @SerializedName("user_rating")
    private int mRating;

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setAskingPerson(Person askingPerson) {
        mAskingPerson = askingPerson;
    }

    public Person getAskingPerson() {
        return mAskingPerson;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setAskingUserId(int askingUserId) {
        mAskingUserId = askingUserId;
    }

    public int getAskingUserId() {
        return mAskingUserId;
    }

    public void setAnsweringUserId(int answeringUserId) {
        mAnsweringUserId = answeringUserId;
    }

    public int getAnsweringUserId() {
        return mAnsweringUserId;
    }

    public void setAnsweringPerson(Person answeringPerson) {
        mAnsweringPerson = answeringPerson;
    }

    public Person getAnsweringPerson() {
        return mAnsweringPerson;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTime() {
        return mTime;
    }

    public void setSubject(Subject subject) {
        mSubject = subject;
    }

    public Subject getSubject() {
        return mSubject;
    }

    public void setClassPic(String classPic) {
        mClassPic = classPic;
    }

    public String getClassPic() {
        return mClassPic;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public int getRating() {
        return mRating;
    }
}
