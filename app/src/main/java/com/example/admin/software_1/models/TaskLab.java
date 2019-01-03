package com.example.admin.software_1.models;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.LoginFilter;
import android.text.method.MultiTapKeyListener;
import android.util.Log;

import com.example.admin.software_1.database.TaskManagerBaseHelper;
import com.example.admin.software_1.database.TaskManagerCursorWrapper;
import com.example.admin.software_1.database.TaskManagerDbSchema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ADMIN on 12/23/2018.
 */


//Singleton Class
public class TaskLab {


    private static TaskLab mInstance;

    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;


    private TaskLab(Context context) {
        mContext = context.getApplicationContext();
        mSQLiteDatabase = new TaskManagerBaseHelper(mContext).getWritableDatabase();
    }

    public static TaskLab getInstance(Context context) {
        if (mInstance == null)
            mInstance = new TaskLab(context);

        return mInstance;
    }

    public void addTask(Task task) {
        ContentValues contentValues = getContentValues(task);
        mSQLiteDatabase.insert(TaskManagerDbSchema.TaskTable.NAME, null, contentValues);

    }


    public List<Task> getTasks(Task.TaskType taskType) {

        List<Task> tasks = new ArrayList<>();
        String taskId = taskType.getValue() + "";
        String whereClause = TaskManagerDbSchema.TaskTable.Cols.TASK_TYPE + "=" + taskId;
        if (taskType == Task.TaskType.ALL)
            whereClause = null;

        TaskManagerCursorWrapper cursorWrapper = queryTask(whereClause, null);


        cursorWrapper.moveToFirst();
        if (cursorWrapper.getCount() == 0)
            return tasks;
        try {
            while (!cursorWrapper.isAfterLast()) {
                tasks.add(cursorWrapper.getTask());
                cursorWrapper.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursorWrapper.close();
        }
        return tasks;
    }

    public Task getTask(UUID id) {

        Task task = new Task();
        String whereClause = TaskManagerDbSchema.TaskTable.Cols.UUID + "=" + "\'" + id + "\'";
        TaskManagerCursorWrapper cursorWrapper = queryTask(whereClause, null);


        cursorWrapper.moveToFirst();
        if (cursorWrapper.getCount() == 0)
            return null;
        try {
            task = cursorWrapper.getTask();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursorWrapper.close();
        }

        return task;
    }

    public void removeTask(Task task) {
        String whereClause = TaskManagerDbSchema.TaskTable.Cols.UUID + "=\'" + task.getId()+"\'";
        ContentValues contentValues = getContentValues(task);
        mSQLiteDatabase.delete(TaskManagerDbSchema.TaskTable.NAME,
                whereClause,
                null);

    }

    public void update(Task task) {
        String whereClause = TaskManagerDbSchema.TaskTable.Cols.UUID + "=\'" + task.getId()+"\'";
        ContentValues contentValues = getContentValues(task);
        mSQLiteDatabase.update(TaskManagerDbSchema.TaskTable.NAME,
                contentValues,
                whereClause,
                null);
    }

    private ContentValues getContentValues(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.UUID, task.getId().toString());
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.TITLE, task.getTitle());
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.DESCRIPTION, task.getDescription());
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.DATE, task.getDate());
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.TIME, task.getTime());
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.TASK_TYPE, task.getTaskType().getValue());
        contentValues.put(TaskManagerDbSchema.TaskTable.Cols.USER_ID, task.getUserId());

        return contentValues;
    }

    private TaskManagerCursorWrapper queryTask(String whereClause, String[] whereArgs) {

        Cursor cursor = mSQLiteDatabase.query(TaskManagerDbSchema.TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null);
        return new TaskManagerCursorWrapper(cursor);
    }
}
