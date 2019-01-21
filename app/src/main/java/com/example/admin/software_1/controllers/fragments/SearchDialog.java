package com.example.admin.software_1.controllers.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.software_1.R;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.utils.PictureUtils;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import static com.example.admin.software_1.controllers.fragments.TaskListFragment.TAG_SHOW_TASK_INFO;

/**
 * Created by ADMIN on 1/21/2019.
 */

public class SearchDialog extends DialogFragment implements View.OnClickListener {

    //Widgets variables
    private Button mSearchButton;
    private EditText mTitleEdt;
    private EditText mDescriptionEdt;
    private RecyclerView mTaskRecyclerView;
    //simple variables
    private String mTitle;
    private String mDescription;
    private List<Task> mTaskList;

    public static SearchDialog newInstance() {

        Bundle args = new Bundle();

        SearchDialog fragment = new SearchDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_search, null);
        initialization(view);
        setListeners();

        String title = getString(R.string.search_message);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(title)
                .create();
        return alertDialog;
    }

    private void initialization(View view) {
        mSearchButton = view.findViewById(R.id.search_btn_searchDialog);
        mTitleEdt = view.findViewById(R.id.title_edt_searchDialog);
        mDescriptionEdt = view.findViewById(R.id.desc_edt_searchDialog);
        mTaskRecyclerView = view.findViewById(R.id.taskList_recy_searchDialog);
    }

    private void setListeners() {
        mSearchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.search_btn_searchDialog:

                if (isInputsValid()) {
                    search();
                } else {
                    Toast.makeText(getActivity(), R.string.input_error_message, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void search() {
        mTitle = mTitleEdt.getText().toString();
        mDescription = mDescriptionEdt.getText().toString();
        mTaskList =TaskLab.getInstance().searchTask(mTitle,mDescription);

        TaskAdapter adapter=new TaskAdapter(mTaskList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mTaskRecyclerView.setLayoutManager(linearLayoutManager);
        mTaskRecyclerView.setAdapter(adapter);
        mTaskRecyclerView.getAdapter().notifyDataSetChanged();

    }


    private boolean isInputsValid() {
        return (mTitleEdt.getText().length() != 0 ||
                mDescriptionEdt.getText().length() != 0);
    }


    private class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTitile_textView;
        private TextView mDateandHour_textView;
        private ImageView mTaskImageView;
        private Button share_Button;

        public TaskHolder(View itemView) {
            super(itemView);
            mTitile_textView = itemView.findViewById(R.id.title_textView_TaskListfragment);
            mDateandHour_textView = itemView.findViewById(R.id.hourAndDate_textView_fragment);
            share_Button = itemView.findViewById(R.id.share_btn_taskListFragmnet);
            mTaskImageView = itemView.findViewById(R.id.taskImage_img_sample);


            share_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = mTaskList.get(getAdapterPosition());
                    TaskLab.getInstance().shareTask(getActivity(),task);

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ShowTaskInfoFragmnet showTaskInfoFragmnet = ShowTaskInfoFragmnet.newInstance
                            (mTaskList.get(getAdapterPosition()).getUuId());
                    showTaskInfoFragmnet.show(getFragmentManager(), TAG_SHOW_TASK_INFO);

                }
            });
        }

        public void bind(Task task)
        {
            String date_time_format = getResources().getString(R.string.date_time_textView, task.getDate(), task.getTime());
            mTitile_textView.setText(task.getTitle());
            mDateandHour_textView.setText(date_time_format);
            PictureUtils.updatePhotoView(getActivity(),task,mTaskImageView);
        }


    }



    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        List<Task> mTasks;

        public TaskAdapter(List<Task> tasks)
        {
            this.mTasks=tasks;
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
