package com.example.admin.software_1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TaskManagerActivity extends AppCompatActivity  {

//Widget variables
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mAdd_fab;
    public static final String Tag_state="om.example.admin.software_1_tag_tabPosition";


    public  Intent newIntent(int state)
    {
        Intent intent=new Intent(TaskManagerActivity.this,EditActivity.class);
        intent.putExtra(Tag_state,state);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        initialization();//initialize Widgets ids


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Task.TaskType taskType=getTaskType(position);


                return  TaskListFragment.newInstance(taskType);
            }

            @Override
            public int getCount() {
                return 3;//number of tabs are 3 ALL,DONE,UnDone
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String result="";
                switch (position)
                {
                    case 0:
                        result= getResources().getString(R.string.tab_name_All);
                        break;
                    case 1:
                        result= getResources().getString(R.string.tab_name_Done);
                    break;
                    case 2:
                        result= getResources().getString(R.string.tab_name_UnDone);
                    break;
                }
                return result;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);



        mAdd_fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=newIntent(0);
                startActivity(intent);
            }
        });




    }

private Task.TaskType getTaskType(int position)
{

    switch (position)
    {
        case 0:
            return Task.TaskType.ALL;
        case 1:
            return Task.TaskType.DONE;
        case 2:
            return Task.TaskType.UNDONE;
    }
    return null;
}


    private void initialization()
    {
        mViewPager=findViewById(R.id.container_viewPager_TaskManagerActivity);
        mTabLayout=findViewById(R.id.tasktab_tab_Activity);
        mAdd_fab=findViewById(R.id.add_fab_TaskManagerActivity);
    }


}
