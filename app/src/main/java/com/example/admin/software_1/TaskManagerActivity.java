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
import android.view.View;

public class TaskManagerActivity extends AppCompatActivity implements GetDataFromActivity {

//Widget variables
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mAdd_fab;


    public  Intent newIntent(Context context)
    {
        Intent intent=new Intent(context,EditActivity.class);
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
                return  TaskListFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;//number of tabs are 3 ALL,DONE,UnDone
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position)
                {
                    case 0:

                        return getResources().getString(R.string.tab_name_All);
                    case 1:
                        return getResources().getString(R.string.tab_name_Done);
                    case 2:
                        return getResources().getString(R.string.tab_name_UnDone);
                }

                return "";
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);



        mAdd_fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=newIntent(TaskManagerActivity.this);
                startActivity(intent);
            }
        });

    }




    private void initialization()
    {
        mViewPager=findViewById(R.id.container_viewPager_TaskManagerActivity);
        mTabLayout=findViewById(R.id.tasktab_tab_Activity);
        mAdd_fab=findViewById(R.id.add_fab_TaskManagerActivity);
    }

    @Override
    public int getData() {

        return mTabLayout.getSelectedTabPosition();
    }
}
