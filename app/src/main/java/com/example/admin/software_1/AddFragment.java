package com.example.admin.software_1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener {

    //Widgets variables
    private TextView mTitle_textView;
    private TextView mDescription_textView;
    private CheckBox mTaskType_checkbox;
    private Button mAddButton;
    private Button mSetTimeButton;
    private Button mSetDateButton;
    //simple variables
    private String mTiltle;
    private String mDescription;
    private String mTime;
    private String mDate;
    private Task.TaskType mTaskType;
    public static final String DIALOG_TAG_TIME_PICKER = "timePicker_tag";
    public static final String DIALOG_TAG_DATE_PICKER = "datePicker_tag";
    public static final int REQ_TIME_PICKER = 0;
    public static final int REQ_DATE_PICKER = 1;


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


        mAddButton.setOnClickListener(this);
        mSetTimeButton.setOnClickListener(this);
        mSetDateButton.setOnClickListener(this);
        return view;
    }


    private void initilization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_Addfragment);
        mDescription_textView = view.findViewById(R.id.description_textView_Addfragment);
        mTaskType_checkbox = view.findViewById(R.id.taskType_checkbox_Addfragment);
        mAddButton = view.findViewById(R.id.add_button_Addfragment);
        mSetTimeButton = view.findViewById(R.id.setTime_btn_AddFragment);
        mSetDateButton = view.findViewById(R.id.setDate_btn_AddFragment);
    }

    private void getDatasFromUI() {
        //check if the input are empty ,show a message
        if (mTitle_textView.getText().length() == 0)
            Toast.makeText(getActivity(), getResources().getString(R.string.input_error_message),
                    Toast.LENGTH_LONG).show();
        else {
            mTiltle = mTitle_textView.getText().toString();
            mDescription = mDescription_textView.getText().toString();
            setTaskType();
            Task temp_task = new Task();
            temp_task.setTitle(mTiltle);
            temp_task.setTaskType(mTaskType);
            if (mDate != null)
                temp_task.setDate(mDate);
            if (mTime != null)
                temp_task.setHour(mTime);
            if (mDescription != null)
                temp_task.setDescription(mDescription);

            TaskLab.getInstance().addTask(mTaskType, temp_task);
            getActivity().finish();

        }

    }

    private void setTaskType() {
        if (mTaskType_checkbox.isChecked())
            mTaskType = Task.TaskType.DONE;
        else
            mTaskType = Task.TaskType.UNDONE;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != EditActivity.RESULT_OK)
            return;
        if (requestCode == REQ_TIME_PICKER) {
            mTime = data.getStringExtra(TaskTimePickerFragment.EXTRA_TIME);
            String[] strTime=mTime.split(TaskTimePickerFragment.SEPARATOR_TIME);
            int[] intTime=TaskTimePickerFragment.strToIntArray(strTime);//0: hour, 1: minute

            String message = getResources().getString(R.string.taskTime_button_add, mTime);
            mSetTimeButton.setText(message);
        }


        if (requestCode == REQ_DATE_PICKER) {
            mDate = data.getStringExtra(TaskDatePickerFragment.EXTRA_DATE);
            String message = getResources().getString(R.string.taskDate_button_add, mDate);
            mSetDateButton.setText(message);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_button_Addfragment:
                getDatasFromUI();
                break;

            case R.id.setTime_btn_AddFragment:
                goToTimePickerFragment();
                break;

            case R.id.setDate_btn_AddFragment:
                goToDatePickerFragment();
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



}
