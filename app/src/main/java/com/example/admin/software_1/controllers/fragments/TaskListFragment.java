package com.example.admin.software_1.controllers.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.UserLab;
import com.example.admin.software_1.utils.PictureUtils;

import java.io.File;
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
    private Long mUserId;
    private List<Task> mTaskLsit;
    public static final String TAG_SHOW_TASK_INFO = "tag_showTaskInfo";

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
        mUserId = UserLab.getInstance().getCurrentUser().get_id();
        mTaskLsit = TaskLab.getInstance().getTasks(mTaskType, mUserId);
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
    public void onResume() {
        super.onResume();
        if (mTaskLsit.size() > 0) {
            mNoTaskImageView.setVisibility(View.INVISIBLE);
            TaskAdapter adapter = new TaskAdapter(mTaskLsit);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);
        } else {
            mNoTaskImageView.setVisibility(View.VISIBLE);
        }
    }


    private void initialization(View view) {
        mRecyclerView = view.findViewById(R.id.tasklist_recy_TaskListfragment);
        mNoTaskImageView = view.findViewById(R.id.noTask_img_taskListFragment);
    }


    ///RecyclerView Classes


    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTitile_textView;
        private TextView mDateandHour_textView;
        private ImageView mTaskImageView;
        private Button share_Button;
        List<Task> mTasks = TaskLab.getInstance().getTasks(TaskListFragment.this.mTaskType, mUserId);

        public TaskHolder(View itemView) {
            super(itemView);
            mTitile_textView = itemView.findViewById(R.id.title_textView_TaskListfragment);
            mDateandHour_textView = itemView.findViewById(R.id.hourAndDate_textView_fragment);
            share_Button = itemView.findViewById(R.id.share_btn_taskListFragmnet);
            mTaskImageView = itemView.findViewById(R.id.taskImage_img_sample);


            share_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = mTaskLsit.get(getAdapterPosition());
                    String textMessage = getString(R.string.share_text, task.getTitle(), task.getDescription(), task.getDate(),
                            task.getTaskType());
                    // Create the text message with a string
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
                    sendIntent.setType("text/plain");

                    // Verify that the intent will resolvale to an activity
                    if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(sendIntent);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ShowTaskInfoFragmnet showTaskInfoFragmnet = ShowTaskInfoFragmnet.newInstance
                            (mTasks.get(getAdapterPosition()).getUuId());
                    showTaskInfoFragmnet.show(getFragmentManager(), TAG_SHOW_TASK_INFO);

                }
            });
        }


        public void bind(Task task, int position) {

            String date_time_format = getResources().getString(R.string.date_time_textView, task.getDate(), task.getTime());
            mTitile_textView.setText(task.getTitle());
            mDateandHour_textView.setText(date_time_format);


            PictureUtils.updatePhotoView(getActivity(),task,mTaskImageView);
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
