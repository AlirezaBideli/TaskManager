package com.example.admin.software_1.models;

import android.app.AlertDialog;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ADMIN on 12/23/2018.
 */


public class Task implements Serializable {


    //Change optional variables to "Undefined" when these variables are "null"
    private UUID mId;
    private String mTitle;
    private String mDescription = "Undefined";
    private String mDate = "Undefined";
    private String mTime = "Undefined";
    private TaskType mTaskType;
    private int userId;



    public Task()
    {
        mId=UUID.randomUUID();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }


    public String getTime() {
        return mTime;
    }

    public void setTime(String hour) {
        mTime = hour;
    }


    public TaskType getTaskType() {
        return mTaskType;
    }

    public void setTaskType(TaskType taskType) {
        mTaskType = taskType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }




    public enum TaskType {
        ALL(0),
        DONE(1),
        UNDONE(2);

        TaskType(int value) {
            this.value = value;
        }


        private int value;

        public int getValue() {

            return value;
        }

        public static TaskType getTaskType(int value) {
            switch (value) {
                case 0:
                    return Task.TaskType.ALL;
                case 1:
                    return Task.TaskType.DONE;
                default:
                    return Task.TaskType.UNDONE;
            }
        }


    }


}
