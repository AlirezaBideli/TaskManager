package com.example.admin.software_1.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    //Widgets variables
    private EditText mTitle_EditText;
    private EditText mDescription_EditText;
    private CheckBox mTaskType_checkbox;
    private Button mDone_button;
    private Button mSetDate_Button;
    private Button mSetTime_Button;
    public static final String DIALOG_TAG_TIME_PICKER = "timePicker_tag";
    public static final String DIALOG_TAG_DATE_PICKER = "datePicker_tag";
    public static final int REQ_TIME_PICKER = 0;
    public static final int REQ_DATE_PICKER = 1;
    public static final String ARGS_TASK = "taskObject";
    private Task mTask;
    private String mTitle;
    private String mDescription;
    private String mTime;
    private String mDate;
    private Task.TaskType mTaskType;
    private String undefined;


    public static Fragment newInstance(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_TASK, task);
        EditFragment fragment = new EditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = getArgs();
        mDescription = getResources().getString(R.string.undefined);//set undefined because maybe the user delete it
        mDate = mTask.getDate();
        mTime = mTask.getTime();
        undefined = getResources().getString(R.string.undefined);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        initialization(view);

        fillUiWidgets(mTask);
        mDone_button.setOnClickListener(this);
        mSetDate_Button.setOnClickListener(this);
        mSetTime_Button.setOnClickListener(this);
        return view;
    }


    private void initialization(View view) {
        mTitle_EditText = view.findViewById(R.id.title_editText_EditFragment);
        mDescription_EditText = view.findViewById(R.id.description_editText_EditFragment);
        mTaskType_checkbox = view.findViewById(R.id.taskType_checkbox_EditFragment);
        mDone_button = view.findViewById(R.id.done_button_EditFragment);
        mSetDate_Button = view.findViewById(R.id.setDate_btn_EditFragment);
        mSetTime_Button = view.findViewById(R.id.setTime_btn_EditFragment);
    }

    private Task getArgs() {


        Task task = (Task) getArguments().getSerializable(ARGS_TASK);
        return task;
    }

    private void fillUiWidgets(Task task) {
        mTitle = task.getTitle();
        mDescription = task.getDescription();
        mTaskType = task.getTaskType();
        mTitle_EditText.setText(mTitle);
        mDescription_EditText.setText(mDescription);
        boolean reault = setTaskTypeChecked(mTaskType);
        mTaskType_checkbox.setChecked(reault);
        mSetDate_Button.setText(getResources().getString(R.string.taskDate_button_edit, task.getDate()));
        mSetTime_Button.setText(getResources().getString(R.string.taskTime_button_edit, task.getTime()));
    }

    private boolean setTaskTypeChecked(Task.TaskType taskType) {
        return taskType == Task.TaskType.DONE;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.done_button_EditFragment:
                if (mTitle_EditText.getText().length() == 0) {
                    String message = getResources().getString(R.string.input_error_message);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                } else
                    setSendedTask();
                break;

            case R.id.setDate_btn_EditFragment:
                goToDatePickerFragment();
                break;

            case R.id.setTime_btn_EditFragment:
                goToTimePickerFragment();
                break;
        }

    }


    private void setSendedTask() {

        mTitle = mTitle_EditText.getText().toString();
        mDescription = mDescription_EditText.getText().toString();
        mTaskType = getTaskType(mTaskType_checkbox.isChecked());
        Task.TaskType taskType = getTaskType(mTaskType_checkbox.isChecked());
        mTask.setTaskType(taskType);
        mTask.setTitle(mTitle);
        mTask.setDescription(mDescription);
        mTask.setTaskType(mTaskType);
        mTask.setDate(mDate);
        mTask.setTime(mTime);

        TaskLab.getInstance(getActivity()).update(mTask);
        Intent intent = new Intent(getActivity(), TaskManagerActivity.class);
        intent.putExtra(TaskManagerActivity.Tag_state, 2);
        startActivity(intent);

    }


    private void goToDatePickerFragment() {
        Date date = stringToDate(mDate);
        TaskDatePickerFragment taskDatePickerFragment = TaskDatePickerFragment.newInstance(date);
        taskDatePickerFragment.setTargetFragment(EditFragment.this, REQ_DATE_PICKER);
        taskDatePickerFragment.show(getFragmentManager(), DIALOG_TAG_DATE_PICKER);
    }

    private void goToTimePickerFragment() {
        Date time = stringToTime(mTime);
        TaskTimePickerFragment timePickerFragment = TaskTimePickerFragment.newInstance(time);
        timePickerFragment.setTargetFragment(EditFragment.this, REQ_TIME_PICKER);
        timePickerFragment.show(getFragmentManager(), DIALOG_TAG_TIME_PICKER);
    }


    private Task.TaskType getTaskType(boolean isChecked) {
        if (isChecked)
            return Task.TaskType.DONE;
        return Task.TaskType.UNDONE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != EditActivity.RESULT_OK)
            return;
        if (requestCode == REQ_TIME_PICKER) {
            mTime = data.getStringExtra(TaskTimePickerFragment.EXTRA_TIME);
            String message = getResources().getString(R.string.taskTime_button_add, mTime);
            mSetTime_Button.setText(message);
        }


        if (requestCode == REQ_DATE_PICKER) {
            mDate = data.getStringExtra(TaskDatePickerFragment.EXTRA_DATE);
            String message = getResources().getString(R.string.taskDate_button_add, mDate);
            mSetDate_Button.setText(message);

        }

    }

    private Date stringToDate(String strDate) {
        ParsePosition ps = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TaskDatePickerFragment.FORMAT_DATE);
        Date date = simpleDateFormat.parse(strDate, ps);
        return date;
    }

    private Date stringToTime(String strTime) {
        ParsePosition ps = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TaskTimePickerFragment.FORMAT_TIME);
        Date time = simpleDateFormat.parse(strTime, ps);
        return time;
    }


}
