package com.example.admin.software_1.controllers.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.UserLab;

import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ImageView mNoTaskImageView;

    //simple Variables
    private Task.TaskType mTaskType;
    private int mUserId;

    public TaskListFragment() {
        // Required empty public constructor
    }


    public static TaskListFragment newInstance(Task.TaskType taskType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TaskManagerActivity.EXTRA_TASK_TYPE, taskType);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskType = (Task.TaskType) getArguments().getSerializable(TaskManagerActivity.EXTRA_TASK_TYPE);
        mUserId= UserLab.getInstance(getActivity()).getCurrentUser().getUser_id();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        initialization(view);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();

        List<Task> taskList = TaskLab.getInstance(getActivity()).getTasks(mTaskType, mUserId);


        if (taskList.size() > 0) {
            mNoTaskImageView.setVisibility(View.INVISIBLE);
            TaskAdapter adapter = new TaskAdapter(taskList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        } else {
            mNoTaskImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void initialization(View view) {
        mRecyclerView = view.findViewById(R.id.tasklist_recy_TaskListfragment);
        mNoTaskImageView = view.findViewById(R.id.noTask_img_taskListFragment);
    }


    ///RecyclerView Classes


    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitile_textView;
        private TextView mDateandHour_textView;
        private TextView mTitleFirstLetter_textView;
        List<Task> mTasks = TaskLab.getInstance(getActivity()).getTasks(TaskListFragment.this.mTaskType,mUserId);

        public TaskHolder(View itemView) {
            super(itemView);
            mTitile_textView = itemView.findViewById(R.id.title_textView_TaskListfragment);
            mDateandHour_textView = itemView.findViewById(R.id.hourAndDate_textView_fragment);
            mTitleFirstLetter_textView = itemView.findViewById(R.id.firstLetter_textView_fragment);


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new EditActivity().newIntent(getActivity(),
                            EditActivity.itemTaskListClicked//state : 1
                            , mTasks.get(getAdapterPosition()).getId());
                    startActivity(intent);
                }
            });
        }


        public void bind(Task task, int position) {
            StringBuilder sb = new StringBuilder();
            sb.append(task.getTitle().charAt(0));
            String date_time_format = getResources().getString(R.string.date_time_textView, task.getDate(), task.getTime());
            mTitile_textView.setText(task.getTitle());
            mDateandHour_textView.setText(date_time_format);
            mTitleFirstLetter_textView.setText(sb.toString());
        }
    }


    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.sample_task_list, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {

            holder.bind(mTasks.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }


}
