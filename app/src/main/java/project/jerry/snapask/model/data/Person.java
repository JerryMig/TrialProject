package project.jerry.snapask.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Migme_Jerry on 2017/5/6.
 */

public class Person extends BaseData {

    @SerializedName("id")
    private int mId;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("username")
    private String mUserName;

    @SerializedName("role")
    private Role mRole;

    @SerializedName("profile_pic_url")
    private String mProfilePic;

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setRole(Role role) {
        mRole = role;
    }

    public Role getRole() {
        return mRole;
    }

    public void setProfilePic(String profilePic) {
        mProfilePic = profilePic;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

}
