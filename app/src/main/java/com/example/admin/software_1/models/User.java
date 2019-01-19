package com.example.admin.software_1.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ADMIN on 1/9/2019.
 */

@Entity()
public class User {


    @Id(autoincrement = true)
    private Long _id;

    private String firstName;
    private String lastName;
    @Unique
    private String userName;
    private String password;
    @Generated(hash = 1758850353)
    public User(Long _id, String firstName, String lastName, String userName,
            String password) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
  





}
