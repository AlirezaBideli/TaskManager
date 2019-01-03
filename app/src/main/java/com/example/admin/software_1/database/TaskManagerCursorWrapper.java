package com.example.admin.software_1.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.admin.software_1.models.Task;

import java.util.UUID;

/**
 * Created by ADMIN on 1/3/2019.
 */

public class TaskManagerCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public static final byte ALL_ID=0;
    public static final byte DONE_ID=1;
    public static final byte UNDOE_ID=2;




    public TaskManagerCursorWrapper(Cursor cursor) {
        super(cursor);


    }


    public Task getTask() {
        Task task = new Task();

        UUID id = UUID.fromString(getString(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.UUID)));
        String title = getString(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.TITLE));
        String description = getString(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.DESCRIPTION));
        String date = getString(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.DATE));
        String time = getString(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.TIME));
        int taskType = getInt(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.TASK_TYPE));
        int userId = getInt(getColumnIndex(TaskManagerDbSchema.TaskTable.Cols.USER_ID));


        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setDate(date);
        task.setTime(time);
        Task.TaskType original_TaskType=getTaskType(taskType);
        task.setTaskType(original_TaskType);
        task.setUserId(userId);

        Log.i("Tag",id+","+title+","+description+","+date+","+time+","+taskType+"="+original_TaskType+","+userId);
        return task;
    }

    private Task.TaskType getTaskType(int taskType_id)
    {

            return Task.TaskType.getTaskType(taskType_id);

    }
}
