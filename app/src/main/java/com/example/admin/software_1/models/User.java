package com.example.admin.software_1.models;

import java.util.UUID;

/**
 * Created by ADMIN on 1/9/2019.
 */

public class User {


    private int mUser_id;
    private String mFirstName;
    private String mLastName;
    private String mUserName;
    private String mPassword;


    public User() {
    }

    public User(int userId) {
        mUser_id = userId;
    }


    //getters and setters
    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int getUser_id() {
        return mUser_id;
    }

}
