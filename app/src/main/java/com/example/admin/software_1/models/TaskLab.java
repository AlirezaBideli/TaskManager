package com.example.admin.software_1.models;


import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.example.admin.software_1.ORM.App;
import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.fragments.LoginFragment;

import org.greenrobot.greendao.query.WhereCondition;
import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ADMIN on 12/23/2018.
 */


//Singleton Class
public class TaskLab {


    private static TaskLab mInstance = new TaskLab();
    private TaskDao mTaskDao = (App.getApp()).getDaoSession().getTaskDao();

    private TaskLab() {
    }

    public static TaskLab getInstance() {
        return mInstance;
    }


    public File getTaskPicture(Context context, Task task) {

        return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), task.getPictureName());
    }


    //Remove  Task/s in mSQLiteDatabase
    public void removeTask(UUID taskId) {
        Task task = getTask(taskId);
        mTaskDao.delete(task);
    }

    public void removeAllTasks(Long userId) {


        List<Task> tasks = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.User_id.eq(userId))
                .list();
        mTaskDao.deleteInTx(tasks);

    }


    public void addTask(Task task) {
        //Set UUId for each os task just once they added
        mTaskDao.insert(task);

    }


    //Update specified Task in mSQLiteDatabase
    public void updateTask(Task task) {
        mTaskDao.update(task);
    }

    //Update tasks
    //this is user when we want to save the task of user that has not registered yet
    public void updateTasks(Long userId) {

        List<Task> tasks = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.User_id.eq(LoginFragment.DEFAULT_USER_ID))
                .list();
        for (Task task : tasks)
            task.setUser_id(userId);
        mTaskDao.updateInTx(tasks);

    }


    //Select  list of task from mSQLiteDatabase
    public List<Task> getTasks(Task.TaskType taskType, Long userId) {


        List<Task> tasks;

        if (taskType == Task.TaskType.ALL) {

            tasks = mTaskDao.queryBuilder().where(TaskDao.Properties.User_id.eq(userId))
                    .list();
        } else {
            tasks = mTaskDao.queryBuilder().where(TaskDao.Properties.TaskType.eq(taskType.getValue())
                    , TaskDao.Properties.User_id.eq(userId))
                    .list();
        }


        if (tasks.size() > 0)
            return tasks;

        else return new ArrayList<>();
    }

    public Task getTask(UUID taskId) {

        Task task = mTaskDao.queryBuilder()
                .where(TaskDao.Properties.UuId.eq(taskId.toString()))
                .uniqueOrThrow();

        return task;
    }


    //Search Tasks according to the Task Title and Task Descriptioin
    public List<Task> searchTask(String title, String description) {


        Long user_id = UserLab.getInstance().getCurrentUser().get_id();

        org.greenrobot.greendao.query.QueryBuilder qb=mTaskDao.queryBuilder();
        qb.where(TaskDao.Properties.User_id.eq(user_id),qb.
                or(TaskDao.Properties.Title.like(title),
                TaskDao.Properties.Description.like(description)));
        List<Task> tasks = qb.list();
        return tasks;
    }


    public void shareTask(Context context, Task task) {

        String textMessage = (context.getResources().getString(R.string.share_text,
                task.getTitle(), task.getDescription(), task.getDate(),
                task.getTaskType()));

        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
        sendIntent.setType("text/plain");

        // Verify that the intent will resolvale to an activity
        if (sendIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(sendIntent);
        }
    }

}
