package com.example.admin.software_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/23/2018.
 */


//Singleton Class
public class TaskLab {


    private static final TaskLab mInstance=new TaskLab();

    private List<Task> mAll_tasks=new ArrayList<>();//the list of All tasks
    private List<Task> mDone_tasks=new ArrayList<>();//the list of Done tasks
    private List<Task> mUndone_tasks=new ArrayList<>();//the list of UnDone tasks

    private TaskLab(){}

    public static TaskLab getInstance()
    {
        return mInstance;
    }
    public void addTask(Task.TaskType taskType,Task task)
    {

        mAll_tasks.add(task);

        switch (taskType)
        {
            case Done:
                mDone_tasks.add(task);
                break;
            case UNDONE:
                mUndone_tasks.add(task);
                break;
        }
    }

    private List<Task> getTasksList(Task.TaskType taskType)
    {

        switch (taskType)
        {
            case ALL:
                return mAll_tasks;
            case Done:
                return mDone_tasks;
            case UNDONE:
                return mUndone_tasks;
        }
        return null;
    }





}
