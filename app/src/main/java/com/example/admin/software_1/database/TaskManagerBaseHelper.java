package com.example.admin.software_1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ADMIN on 1/2/2019.
 */

public class TaskManagerBaseHelper extends SQLiteOpenHelper {

    public TaskManagerBaseHelper(Context context) {
        super(context, TaskManagerDbSchema.NAME, null, TaskManagerDbSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTaskTable(db);
        createUserTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void createTaskTable(SQLiteDatabase db) {
        db.execSQL("Create Table " + TaskManagerDbSchema.TaskTable.NAME + "(" +
                TaskManagerDbSchema.TaskTable.Cols._id + " integer primary key autoincrement," +
                TaskManagerDbSchema.TaskTable.Cols.UUID + "," +
                TaskManagerDbSchema.TaskTable.Cols.TITLE + "," +
                TaskManagerDbSchema.TaskTable.Cols.DESCRIPTION + "," +
                TaskManagerDbSchema.TaskTable.Cols.DATE + "," +
                TaskManagerDbSchema.TaskTable.Cols.TIME + "," +
                TaskManagerDbSchema.TaskTable.Cols.TASK_TYPE + "," +
                TaskManagerDbSchema.TaskTable.Cols.USER_ID +
                ");"
        );
    }


    private void createUserTable(SQLiteDatabase db) {
        db.execSQL("Create Table " + TaskManagerDbSchema.UserTable.NAME + "(" +
                TaskManagerDbSchema.UserTable.Cols._id + " integer primary key autoincrement," +
                TaskManagerDbSchema.UserTable.Cols.FIRST_NAME + "," +
                TaskManagerDbSchema.UserTable.Cols.LAST_NAME + "," +
                TaskManagerDbSchema.UserTable.Cols.USERNAME + "," +
                TaskManagerDbSchema.UserTable.Cols.PASSWORD +


                ");"
        );
    }
}

