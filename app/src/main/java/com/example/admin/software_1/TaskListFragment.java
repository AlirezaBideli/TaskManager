package com.example.admin.software_1;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    public static final String EXTRA_TASK_POSITION ="taskPosition";
    public static final String EXTRA_TASK_TYPE ="taskType";



    private Task.TaskType mTaskType;
    public TaskListFragment() {
        // Required empty public constructor
    }


    public Intent newIntent(int state,int position,Task.TaskType taskType)
    {
        Intent intent=new Intent(getActivity(),EditActivity.class);
        intent.putExtra(TaskManagerActivity.Tag_state,state);
        intent.putExtra(EXTRA_TASK_POSITION,position);
        intent.putExtra(EXTRA_TASK_TYPE,taskType);

        Log.i("Tag",state+" "+position+" "+taskType);
        return intent;
    }

    public static Fragment newInstance(Task.TaskType taskType)
    {
        Bundle bundle=new Bundle();
        bundle.putSerializable(EXTRA_TASK_TYPE,taskType);
        TaskListFragment fragment=new TaskListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_task_list, container, false);
        initialization(view);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mTaskType=(Task.TaskType)getArguments().getSerializable(EXTRA_TASK_TYPE);

        List<Task>taskList=TaskLab.getInstance().getTasksList(mTaskType);
        TaskAdapter adapter=new  TaskAdapter(taskList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    private void initialization(View view)
    {
        mRecyclerView=view.findViewById(R.id.tasklist_recy_TaskListfragment);
    }




    ///RecyclerView Classes


    private class TaskHolder extends RecyclerView.ViewHolder
    {

        private TextView mTitile_textView;
        private TextView mDateandHour_textView;
        private TextView mTitleFirstLetter_textView;
        public TaskHolder(View itemView) {
            super(itemView);
            mTitile_textView=itemView.findViewById(R.id.title_textView_TaskListfragment);
            mDateandHour_textView=itemView.findViewById(R.id.hourAndDate_textView_fragment);
            mTitleFirstLetter_textView=itemView.findViewById(R.id.firstLetter_textView_fragment);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent=newIntent(1,getAdapterPosition(),mTaskType);
                    startActivity(intent);
                }
            });
        }


        public void bind(Task task,int position)
        {
            StringBuilder sb=new StringBuilder();
            sb.append(task.getTitle().charAt(0));
            String date_time_format=getResources().getString(R.string.date_time_textView,task.getDate(),task.getHour());
            task.setPosition(position);
            mTitile_textView.setText(task.getTitle());
            mDateandHour_textView.setText(date_time_format);
            mTitleFirstLetter_textView.setText(sb.toString());
        }
    }


    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>
    {
        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks)
        {
            mTasks=tasks;
        }
        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.sample_task_list,parent,false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {

            holder.bind(mTasks.get(position),position);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }



}
