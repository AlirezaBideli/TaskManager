package com.example.admin.software_1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_task_list, container, false);
        initialization(view);

        List<Task> taskList=new ArrayList<>();
        Task task=new Task();
        task.setTitle("Eating dinner");
        task.setTaskType(Task.TaskType.ALL);
        task.setDate(new Date());
        task.setHour(new Date());
        task.setDescription("A good Night");
        TaskLab.getInstance().addTask(task.getTaskType(),task);
        Task task2=new Task();
        task2.setTitle("Eating BreakFast");
        task2.setTaskType(Task.TaskType.ALL);
        task2.setDate(new Date());
        task2.setHour(new Date());
        task2.setDescription("A good Morning");
        TaskLab.getInstance().addTask(task2.getTaskType(),task);
        taskList=TaskLab.getInstance().getTasksList(Task.TaskType.ALL);
        TaskAdapter adapter=new  TaskAdapter(taskList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        return view;
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
        List<Task> mTasks=new ArrayList<>();

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
