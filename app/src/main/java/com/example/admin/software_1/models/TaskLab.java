package com.example.admin.software_1.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ADMIN on 12/23/2018.
 */


//Singleton Class
public class TaskLab {


    private static final TaskLab mInstance = new TaskLab();

    private List<Task> mAll_tasks = new LinkedList<>();//the list of All tasks


    private TaskLab() {
    }

    public static TaskLab getInstance() {
        return mInstance;
    }

    public void addTask(Task.TaskType taskType, Task task) {
        mAll_tasks.add(task);
    }

    public List<Task> getTasksList(Task.TaskType taskType) {
        switch (taskType) {
            case ALL:
                return mAll_tasks;
            case DONE:
                return getDoneTaskList();

            case UNDONE:
                return getUnDoneTaskList();
        }
        return new ArrayList<>();
    }

    public Task getTaskByPosition(int position, Task.TaskType taskType) {
        List<Task> result;
        switch (taskType) {
            case ALL:
                return mAll_tasks.get(position);
            case DONE:
                result = getDoneTaskList();
                return result.get(position);

            case UNDONE:
                result = getUnDoneTaskList();
                return result.get(position);

        }
        return null;
    }


    private List<Task> getDoneTaskList() {
        List<Task> done_tasks = new ArrayList<>();

        for (int i = 0; i < mAll_tasks.size(); i++) {
            if (mAll_tasks.get(i).getTaskType() == Task.TaskType.DONE)
                done_tasks.add(mAll_tasks.get(i));
        }
        return done_tasks;
    }

    private List<Task> getUnDoneTaskList() {
        List<Task> Undone_tasks = new ArrayList<>();

        for (int i = 0; i < mAll_tasks.size(); i++) {
            if (mAll_tasks.get(i).getTaskType() == Task.TaskType.UNDONE)
                Undone_tasks.add(mAll_tasks.get(i));
        }
        return Undone_tasks;
    }


    public void removeTask(Task task) {
        mAll_tasks.remove(task);
    }


}
