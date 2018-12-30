package com.example.admin.software_1;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EditActivity extends AppCompatActivity {


    private int msavedState;
    public static final int addButtonClicked = 0;
    public static final int itemTaskListClicked = 1;


    public  Intent newIntent() {
        Intent intent = new Intent(EditActivity.this, TaskManagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        msavedState = getIntent().getIntExtra(TaskManagerActivity.Tag_state, 1);


        Fragment fragment = setFragment(msavedState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_frame_EditFragment, fragment)
                .commit();


    }

    private Fragment setFragment(int msavedState) {
        switch (msavedState) {
            case addButtonClicked://when user clicked on the fab button in TaskManager Activity
                return AddFragment.newInstance();

            case itemTaskListClicked://when user clicked on  recycler view items in TaskListFragment

                int taskPostion = getIntent().getIntExtra(TaskListFragment.EXTRA_TASK_POSITION, -1);
                Task.TaskType taskType = (Task.TaskType) getIntent().getSerializableExtra(TaskListFragment.EXTRA_TASK_TYPE);
                return ShowTaskInfoFragmnet.newInstance(taskPostion, taskType);
        }
        return new Fragment();
    }
}
