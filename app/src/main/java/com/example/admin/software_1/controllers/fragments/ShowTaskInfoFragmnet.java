package com.example.admin.software_1.controllers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.software_1.R;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowTaskInfoFragmnet extends Fragment implements View.OnClickListener {


    private Button mAdd_Button;
    private Button mEdit_Button;
    private Button mRemove_Button;
    private TextView mTitle_textView;
    private TextView mDesc_textView;
    private TextView mDate_textView;
    private TextView mHour_textView;
    private CheckBox mTaskType_CheckBox;

    private Task mTask;
    public static final String REMOVE_TAG = "remove_tag";
    public static final String ADD_FRAGMNET_TAG = "addFragment_tag";
    public static final String EDIT_FRAGMENT_TAG = "editFragment_tag";


    public ShowTaskInfoFragmnet() {
        // Required empty public constructor
    }


    public static ShowTaskInfoFragmnet newInstance(int taskPosition, Task.TaskType taskType) {
        Bundle bundle = new Bundle();
        bundle.putInt(TaskListFragment.EXTRA_TASK_POSITION, taskPosition);
        bundle.putSerializable(TaskListFragment.EXTRA_TASK_TYPE, taskType);
        ShowTaskInfoFragmnet fragment = new ShowTaskInfoFragmnet();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_task_info_fragmnet, container, false);
        initialization(view);
        mTask = getTaskFromArgs();
        fillUIWidgets(mTask);
        mAdd_Button.setOnClickListener(this);
        mRemove_Button.setOnClickListener(this);
        mEdit_Button.setOnClickListener(this);


        return view;
    }


    private void initialization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_ShowTaskInfoFragment);
        mDesc_textView = view.findViewById(R.id.description_textView_ShowTaskInfoFragment);
        mDate_textView = view.findViewById(R.id.date_textView_ShowTaskInfoFragment);
        mHour_textView = view.findViewById(R.id.hour_textView_ShowTaskInfoFragment);
        mTaskType_CheckBox = view.findViewById(R.id.taskType_checkbox_ShowTaskInfoFragment);

        mAdd_Button = view.findViewById(R.id.add_button_ShowTaskInfoFragment);
        mEdit_Button = view.findViewById(R.id.edit_button_ShowTaskInfoFragment);
        mRemove_Button = view.findViewById(R.id.remove_button_ShowTaskInfoFragment);
    }


    private Task getTaskFromArgs() {
        int taskPosition = getArguments().getInt(TaskListFragment.EXTRA_TASK_POSITION);
        Task.TaskType taskType = (Task.TaskType) getArguments().getSerializable(TaskListFragment.EXTRA_TASK_TYPE);
        Task task = TaskLab.getInstance().getTaskByPosition(taskPosition, taskType);
        return task;
    }

    private void fillUIWidgets(Task task) {


        mTitle_textView.setText(task.getTitle());
        mDesc_textView.setText(task.getDescription());
        mDate_textView.setText(task.getDate());
        mHour_textView.setText(task.getHour());
        boolean isDoneTask = setChecked(task.getTaskType());
        mTaskType_CheckBox.setChecked(isDoneTask);
    }


    private boolean setChecked(Task.TaskType taskType) {

        if (taskType == Task.TaskType.DONE)
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.add_button_ShowTaskInfoFragment:
                goToAddFragment();
                break;
            case R.id.remove_button_ShowTaskInfoFragment:
                showRemoveDialog();
                break;
            case R.id.edit_button_ShowTaskInfoFragment:
                goToEditFragment();

                break;
        }
    }

    private void goToEditFragment() {
        FragmentManager fragmentManager =  getFragmentManager();
        fragmentManager.popBackStack();

            fragmentManager.beginTransaction()
                    .replace(R.id.container_frame_EditFragment, EditFragment.newInstance(mTask))
                     .commit();



    }

    private void goToAddFragment() {
        FragmentManager fragmentManager =getFragmentManager();

        fragmentManager.popBackStack();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_frame_EditFragment, AddFragment.newInstance())
                    .commit();


    }

    private void showRemoveDialog() {

        RemoveDialogFragment removeDialogFragmnet = RemoveDialogFragment.newInstance(mTask);
        removeDialogFragmnet.show(getFragmentManager(), REMOVE_TAG);
    }


}
