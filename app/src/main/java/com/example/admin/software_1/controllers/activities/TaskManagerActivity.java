package com.example.admin.software_1.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.fragments.AddFragment;
import com.example.admin.software_1.controllers.fragments.DetailFragment;
import com.example.admin.software_1.controllers.fragments.NoLoginDialog;
import com.example.admin.software_1.controllers.fragments.RemoveDialogFragment;
import com.example.admin.software_1.controllers.fragments.SearchDialog;
import com.example.admin.software_1.controllers.fragments.TaskListFragment;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.UserLab;

import java.util.UUID;

public class TaskManagerActivity extends AppCompatActivity implements DetailFragment.CallBacks,
        AddFragment.CallBacks, RemoveDialogFragment.CallBacks {

    //simple variables
    public static final String EXTRA_TASK_TYPE = "com.example.admin.software_1_taskType";
    public static final String TAG_ADD_FRAGMENT = "tag_add fragment";
    public static final String Tag = "TaskManagerActivity_Tag";
    private static final String DIALOG_TAG = "noLogin_task";
    //Widget variables
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mAdd_fab;
    private Long mUserId;
    private Task.TaskType mTaskType;
    private String Tag_SEARCH = "tag_search";
    private TaskListFragment.TaskAdapter mTaskAdapter;


    public Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskManagerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        mUserId = UserLab.getInstance().getCurrentUser().get_id();
        initialization();//initialize Widgets ids

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {


            @Override
            public Fragment getItem(int position) {

                Log.i(Tag, "fragment " + position);
                mTaskType = getTaskType(position);

                return TaskListFragment.newInstance(mTaskType);
            }


            @Override
            public int getCount() {
                return 3;//number of tabs are 3 ALL,DONE,UnDone
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String result = "";
                switch (position) {
                    case 0:
                        result = getResources().getString(R.string.tab_name_All);
                        break;
                    case 1:
                        result = getResources().getString(R.string.tab_name_Done);
                        break;
                    case 2:
                        result = getResources().getString(R.string.tab_name_UnDone);
                        break;
                }
                return result;
            }
            public int getItemPosition(Object object){
                return POSITION_NONE;
            }
        });


        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setSaveEnabled(false);

        mAdd_fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                switch (v.getId()) {
                    case R.id.add_fab_TaskManagerActivity:


                        AddFragment addFragment = AddFragment.newInstance();
                        addFragment.show(getSupportFragmentManager(), TAG_ADD_FRAGMENT);


                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(TaskManagerActivity.this);
        inflater.inflate(R.menu.fragment_task_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_deleteAll:

                Long userId = UserLab.getInstance()
                        .getCurrentUser().get_id();
                TaskLab.getInstance().removeAllTasks(userId);
                int tabPostion = mViewPager.getCurrentItem();
                Task.TaskType taskType = getTaskType(tabPostion);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.container_viewPager_TaskManagerActivity,
                                TaskListFragment.newInstance(taskType))
                        .commit();

                break;
            case R.id.app_bar_search:

                SearchDialog searchDialog = SearchDialog.newInstance();
                searchDialog.show(getSupportFragmentManager(), Tag_SEARCH);
                break;
        }
        return true;
    }

    private Task.TaskType getTaskType(int position) {
        return Task.TaskType.getTaskType(position);
    }


    private void initialization() {
        mViewPager = findViewById(R.id.container_viewPager_TaskManagerActivity);
        mTabLayout = findViewById(R.id.tasktab_tab_Activity);
        mAdd_fab = findViewById(R.id.add_fab_TaskManagerActivity);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Long userId = UserLab.getInstance().getCurrentUser().get_id();
        int size = TaskLab.getInstance().getTasks(Task.TaskType.ALL, userId).size();
        if (keyCode == KeyEvent.KEYCODE_BACK
                && mUserId == UserActivity.USER_NEEDS_REGISTER && size > 0) {

            NoLoginDialog noLoginDialog = new NoLoginDialog();
            noLoginDialog.show(getSupportFragmentManager(), DIALOG_TAG);

        } else if (keyCode == KeyEvent.KEYCODE_BACK)
            super.onBackPressed();

        return true;

    }


    @Override
    public void onTaskUpdated() {


        TaskListFragment fragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.container_viewPager_TaskManagerActivity);
        fragment.updateUI();
        fragment.showNoTaskImg();



    }

    @Override
    public void onTaskAdded(Task task) {
        TaskLab.getInstance().addTask(task);

        TaskListFragment fragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.container_viewPager_TaskManagerActivity);

        fragment.updateUI();


    }


    @Override
    public void onDeletedTask(UUID taskId) {
        TaskLab.getInstance().removeTask(taskId);
        TaskListFragment fragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.container_viewPager_TaskManagerActivity);
        fragment.updateUI();
    }
}
