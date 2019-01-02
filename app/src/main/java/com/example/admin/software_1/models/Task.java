package com.example.admin.software_1.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ADMIN on 12/23/2018.
 */

public class Task implements Serializable {


    //Change optional variables to "Undefined" when these variables are "null"
    private int mPosition;
    private String mTitle;
    private String mDescription = "Undefined";
    private String mDate = "Undefined";
    private String mHour = "Undefined";
    private TaskType mTaskType;


    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }


    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        mHour = hour;
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

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }


    public enum TaskType {
        ALL(0),
        DONE(1),
        UNDONE(2);

        TaskType(int value){
            this.value = value;
        }


        private int value;

        public int getValue() {

            return value;
        }

        public static TaskType getTaskType(int value){
            switch (value)
            {
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
