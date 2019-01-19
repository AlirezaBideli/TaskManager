package com.example.admin.software_1.controllers.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;
import com.example.admin.software_1.controllers.activities.UserActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.UserLab;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends DialogFragment implements View.OnClickListener {


    //Widgets variables
    private EditText mTitle_EditText;
    private EditText mDescription_EditText;
    private CheckBox mTaskType_Checkbox;
    private Button mAddButton;
    private Button mSetTimeButton;
    private Button mSetDateButton;
    //simple variables
    private static String defaultValue;
    private static String mTitle;
    private static String mDescription;
    private static String mTime;
    private static String mDate;
    private static boolean mTaskTypeChecked;
    private Task.TaskType mTaskType;
    private Long mUserId;
    private static boolean sIsOrientationChanged = false;
    public static final String DIALOG_TAG_TIME_PICKER = "timePicker_tag";
    public static final String DIALOG_TAG_DATE_PICKER = "datePicker_tag";
    private static final String DIALOG_TAG_DT = "time_date_picker_tag";

    public static final int REQ_TIME_PICKER = 0;
    public static final int REQ_DATE_PICKER = 1;
    private boolean isInputValid;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {

        Bundle args = new Bundle();

        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initilization(view);
        setListeners();

        return view;
    }

    private void setListeners() {

        mAddButton.setOnClickListener(this);
        mSetTimeButton.setOnClickListener(this);
        mSetDateButton.setOnClickListener(this);
        mTaskType_Checkbox.setOnClickListener(this);

        mTitle_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDescription_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDescription = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void initilization(View view) {
        mTitle_EditText = view.findViewById(R.id.title_editText_Addfragment);
        mDescription_EditText = view.findViewById(R.id.description_editText_Addfragment);
        mTaskType_Checkbox = view.findViewById(R.id.taskType_checkbox_Addfragment);
        mAddButton = view.findViewById(R.id.add_button_Addfragment);
        mSetTimeButton = view.findViewById(R.id.setTime_btn_AddFragment);
        mSetDateButton = view.findViewById(R.id.setDate_btn_AddFragment);
    }

    private void AddDate() {
        //check if the input are empty ,show a message

        if (mTitle_EditText.getText().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.input_error_message),
                    Toast.LENGTH_LONG).show();
            isInputValid = false;
        } else {
            addTask();

        }

    }

    private void addTask() {
        mUserId = UserLab.getInstance().getCurrentUser().get_id();
        isInputValid = true;
        mTitle = mTitle_EditText.getText().toString();
        mDescription = mDescription_EditText.getText().toString();
        mTaskType = setTaskType(mTaskType_Checkbox.isChecked());
        Task temp_task = new Task();
        temp_task.setUuId(UUID.randomUUID());
        temp_task.setTitle(mTitle);
        temp_task.setTaskType(mTaskType);
        if (mDate != null)
            temp_task.setDate(mDate);
        if (mTime != null)
            temp_task.setTime(mTime);
        if (mDescription != null)
            temp_task.setDescription(mDescription);
        temp_task.setUser_id(mUserId);
        TaskLab.getInstance().addTask(temp_task);
    }

    private void resetData() {
        defaultValue = getResources().getString(R.string.undefined);
        mTitle = "";
        mDescription = "";
        mTime = defaultValue;
        mDate = defaultValue;
        mTaskTypeChecked = false;
        sIsOrientationChanged = false;
        mTaskType = null;
    }

    private Task.TaskType setTaskType(boolean isChecked) {
        if (isChecked)
            return Task.TaskType.DONE;
        else
            return Task.TaskType.UNDONE;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != EditActivity.RESULT_OK)
            return;
        if (requestCode == REQ_TIME_PICKER) {
            mTime = data.getStringExtra(TaskTimePickerFragment.EXTRA_TIME);
            String timeMessage = getResources().getString(R.string.taskTime_button_add, mTime);
            mSetTimeButton.setText(timeMessage);
        }


        if (requestCode == REQ_DATE_PICKER) {
            mDate = data.getStringExtra(TaskDatePickerFragment.EXTRA_DATE);
            String dateMessage = getResources().getString(R.string.taskDate_button_add, mDate);
            mSetDateButton.setText(dateMessage);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_button_Addfragment:
                AddDate();
                resetData();
                if (isInputValid)
                    goToTaskManagerActivity();
                break;

            case R.id.setTime_btn_AddFragment:
                goToTimePickerFragment();
                break;

            case R.id.setDate_btn_AddFragment:
                goToDatePickerFragment();
                break;

            case R.id.taskType_checkbox_Addfragment:
                mTaskType = setTaskType(true);
                mTaskTypeChecked = true;
                break;


        }
    }


    private void goToDatePickerFragment() {
        TaskDatePickerFragment taskDatePickerFragment = TaskDatePickerFragment.newInstance(new Date());//Date
        taskDatePickerFragment.setTargetFragment(AddFragment.this, REQ_DATE_PICKER);
        taskDatePickerFragment.show(getFragmentManager(), DIALOG_TAG_DATE_PICKER);
    }

    private void goToTimePickerFragment() {
        TaskTimePickerFragment timePickerFragment = TaskTimePickerFragment.newInstance(new Date());//Time
        timePickerFragment.setTargetFragment(AddFragment.this, REQ_TIME_PICKER);
        timePickerFragment.show(getFragmentManager(), DIALOG_TAG_TIME_PICKER);
    }

    private void goToTaskManagerActivity() {
        Intent intent = new TaskManagerActivity().newIntent(getActivity());
        startActivity(intent);
    }

    private void fillUIwidgets() {

        mTitle_EditText.setText(mTitle, TextView.BufferType.EDITABLE);
        mDescription_EditText.setText(mDescription, TextView.BufferType.EDITABLE);
        mTaskType_Checkbox.setChecked(mTaskTypeChecked);
        if (!Objects.equals(mDate, defaultValue)) {
            String dateMessage = getResources().getString(R.string.taskDate_button_add, mDate);
            mSetDateButton.setText(dateMessage);
        }
        if (!Objects.equals(mTime, defaultValue)) {
            String timeMessage = getResources().getString(R.string.taskTime_button_add, mTime);
            mSetTimeButton.setText(timeMessage);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (sIsOrientationChanged) {
            fillUIwidgets();
            sIsOrientationChanged = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        sIsOrientationChanged = true;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!sIsOrientationChanged)
            resetData();
    }
}
