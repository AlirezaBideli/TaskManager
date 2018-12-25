package com.example.admin.software_1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    public static final String Tag_tabPosition="om.example.admin.software_1_tag_taskType";

    private int  mTabposition;
    private Task.TaskType mTaskType;
    public TaskListFragment() {
        // Required empty public constructor
    }



    public static Fragment newInstance(int tabPosition)
    {
        Bundle bundle=new Bundle();
        bundle.putInt(Tag_tabPosition,tabPosition);
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
        mTabposition=getArguments().getInt(Tag_tabPosition,0);
        setTaskType(mTabposition);

        List<Task>taskList=TaskLab.getInstance().getTasksList(mTaskType);
        TaskAdapter adapter=new  TaskAdapter(taskList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    private void initialization(View view)
    {
        mRecyclerView=view.findViewById(R.id.tasklist_recy_TaskListfragment);
    }



    private void setTaskType(int mTabposition)
    {
        switch (mTabposition)
        {
            case 0:
                mTaskType= Task.TaskType.ALL;
                break;
            case 1:
                mTaskType= Task.TaskType.DONE;
                break;
            case 2:
                mTaskType= Task.TaskType.UNDONE;
                break;

        }
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
        }


        public void bind(Task task)
        {
            StringBuilder sb=new StringBuilder();
            sb.append(task.getTitle().charAt(0));
            mTitile_textView.setText(task.getTitle());
            mDateandHour_textView.setText(task.getDate()+" "+task.getHour());
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

            holder.bind(mTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }



}
