package com.example.admin.software_1.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.software_1.ORM.App;
import com.example.admin.software_1.database.TaskManagerBaseHelper;
import com.example.admin.software_1.database.TaskManagerCursorWrapper;
import com.example.admin.software_1.database.TaskManagerDbSchema;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;
import java.util.Properties;

/**
 * Created by ADMIN on 1/9/2019.
 */

public class UserLab {


    private static final UserLab ourInstance = new UserLab();
    private User mCurrentUser;
    private UserDao mUserDao = (App.getApp()).getDaoSession().getUserDao();

    public static UserLab getInstance() {
        return ourInstance;
    }

    private UserLab() {

    }


    public User getCurrentUser() {
        return mCurrentUser;
    }

    //get User from UserDataBase
    public boolean login(String userName, String password) {


        List<User> users = mUserDao.queryBuilder().where(UserDao.Properties.UserName.eq(userName),
                UserDao.Properties.Password.eq(password))
                .list();

        if (users.size()==1) {
            setCurrentUser(users.get(0));
            return true;
        }
        return false;
    }

    public void setCurrentUser(User user) {

        mCurrentUser = user;
    }

    //Insert to  UserDatabase;
    public void addUser(User user) {
        //insert user in database without id and get its id too.
        long id=mUserDao.insert(user);
        //after insert our user to database we set its id
        user.set_id(id);
        setCurrentUser(user);

    }


}
