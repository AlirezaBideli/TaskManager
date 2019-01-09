package com.example.admin.software_1.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.fragments.AddFragment;
import com.example.admin.software_1.controllers.fragments.ShowTaskInfoFragmnet;
import com.example.admin.software_1.controllers.fragments.TaskListFragment;
import com.example.admin.software_1.models.Task;

import java.util.UUID;

public class EditActivity extends AppCompatActivity {
public static final int STATE_ADD = 0;

    private int mSavedState;
    private UUID mTaskId;
    public static final int addButtonClicked = 0;
    public static final int itemTaskListClicked = 1;
    public static final int nothingSelected = -1;
    private Fragment mContent;
    public static final String EXTRA_STATE = "com.example.admin.software_1_extra_state";
    private static final String EXTRA_TASK_ID ="com.example.admin.software_1_extra_task_id" ;




    public  Intent newIntent(Context context,int state,UUID taskId) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(EXTRA_STATE,state);
        intent.putExtra(EXTRA_TASK_ID,state);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        mSavedState = getIntent().getIntExtra(EXTRA_STATE,nothingSelected);
        mTaskId= (UUID) getIntent().getSerializableExtra(TaskManagerActivity.EXTRA_TASK_UUID);
        Fragment fragment = setFragment(mSavedState);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.container_frame_EditFragment)==null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container_frame_EditFragment, fragment)
                    .commit();
        }


    }

    private Fragment setFragment(int msavedState) {
        switch (msavedState) {
            case addButtonClicked://when user clicked on the fab button in TaskManager Activity
                return AddFragment.newInstance();

            case itemTaskListClicked://when user clicked on  recycler view items in TaskListFragment


                return ShowTaskInfoFragmnet.newInstance(mTaskId);
        }
        return new Fragment();
    }



}
