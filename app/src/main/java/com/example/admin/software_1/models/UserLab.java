package com.example.admin.software_1.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.software_1.database.TaskManagerBaseHelper;
import com.example.admin.software_1.database.TaskManagerCursorWrapper;
import com.example.admin.software_1.database.TaskManagerDbSchema;

/**
 * Created by ADMIN on 1/9/2019.
 */

public class UserLab {


    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private static UserLab ourInstance;
    private User mCurrentUser;

    public static UserLab getInstance(Context context) {

        if (ourInstance == null)
            ourInstance = new UserLab(context);
        return ourInstance;
    }

    private UserLab(Context context) {
        mContext = context.getApplicationContext();
        mSQLiteDatabase = new TaskManagerBaseHelper(mContext).getWritableDatabase();
    }


    public User getCurrentUser() {
        return mCurrentUser;
    }

    //get User from UserDataBase
    public User login(User unAuthorizedUser) {

        String whereClause = TaskManagerDbSchema.UserTable.Cols.USERNAME +
                "=? and " + TaskManagerDbSchema.UserTable.Cols.PASSWORD + "=?";
        String[] whereArgs = {unAuthorizedUser.getUserName(), unAuthorizedUser.getPassword()};

        TaskManagerCursorWrapper userCursorWrapper = queryUser(whereClause, whereArgs);
        if (userCursorWrapper.getCount() == 0)
            return null;


        User user = null;
        userCursorWrapper.moveToFirst();
        try {
            user = userCursorWrapper.getUser();
        } finally {
            userCursorWrapper.close();
        }

        return user;
    }

    private TaskManagerCursorWrapper queryUser(String whereCluase, String[] whereArgs) {
        Cursor cursor = mSQLiteDatabase.query(
                TaskManagerDbSchema.UserTable.NAME,
                null,
                whereCluase,
                whereArgs,
                null,
                null,
                null,
                null);

        return new TaskManagerCursorWrapper(cursor);
    }

    //Insert to  UserDatabase;
    public void addUser(User user) {
        ContentValues contentValues = getUserColumns(user);
        mSQLiteDatabase.insert(TaskManagerDbSchema.UserTable.NAME, null, contentValues);

        String whereClause = TaskManagerDbSchema.UserTable.Cols.USERNAME + "= \'" + user.getUserName() + "\'"
                + " and " + TaskManagerDbSchema.UserTable.Cols.PASSWORD + "=\'" + user.getPassword() + "\'";

        String[] whereArgs = {user.getUserName(), user.getPassword()};


        TaskManagerCursorWrapper cursorWrapper =
                userQuery(whereClause, whereArgs);

        setCurrentUser(cursorWrapper);

    }

    private void setCurrentUser(TaskManagerCursorWrapper cursorWrapper) {

        try {
            cursorWrapper.moveToFirst();
            mCurrentUser = cursorWrapper.getUser();
        } finally {
            cursorWrapper.close();
        }
        cursorWrapper.close();
    }

    public void setCurrentUser(User user) {

        mCurrentUser = user;
    }

    private TaskManagerCursorWrapper userQuery(String whereClause, String[] whereArgs) {
        Cursor cursor = mSQLiteDatabase.query(TaskManagerDbSchema.UserTable.NAME,
                null,
                whereClause,
                null,
                null,
                null,
                null,
                null);

        return new TaskManagerCursorWrapper(cursor);
    }

    private ContentValues getUserColumns(User user) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskManagerDbSchema.UserTable.Cols.FIRST_NAME, user.getFirstName());
        contentValues.put(TaskManagerDbSchema.UserTable.Cols.LAST_NAME, user.getLastName());
        contentValues.put(TaskManagerDbSchema.UserTable.Cols.USERNAME, user.getUserName());
        contentValues.put(TaskManagerDbSchema.UserTable.Cols.PASSWORD, user.getPassword());
        return contentValues;
    }


}
