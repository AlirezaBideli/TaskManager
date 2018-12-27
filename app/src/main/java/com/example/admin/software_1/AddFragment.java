package com.example.admin.software_1;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class AddFragment extends Fragment {

    //Widgets variables
    private TextView mTitle_textView;
    private TextView mDescription_textView;
    private CheckBox mTaskType_checkbox;
    private Button mAddButton;
    private Button mSetTimeButton;
    //simple variables
    private String mTiltle;
    private String mDescription;
    private String mHour;
    private Task.TaskType mTaskType;
    public static final String mDatePattern="yyyy-MM-dd";
    public static final String DIALOG_TAG ="timePicker_tag";
    public static final int REQ_TIME_PICKER=0;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initilization(view);
        mAddButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getDatasFromUI();
                getActivity().finish();
                Toast.makeText(getActivity(),mTaskType.toString(),Toast.LENGTH_LONG).show();

            }
        });
        mSetTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerFragment  timePickerFragment=TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(AddFragment.this,REQ_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), DIALOG_TAG);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!= EditActivity.RESULT_OK)
            return;
        if(requestCode==REQ_TIME_PICKER)
        {
            mHour=data.getStringExtra(TimePickerFragment.EXTRA_DATE_TIME);
            mSetTimeButton.setText(mHour);

        }
    }

    private void initilization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_Addfragment);
        mDescription_textView = view.findViewById(R.id.description_textView_Addfragment);
        mTaskType_checkbox = view.findViewById(R.id.taskType_checkbox_Addfragment);
        mAddButton=view.findViewById(R.id.add_button_Addfragment);
        mSetTimeButton=view.findViewById(R.id.setTime_btn_AddFragment);
    }

    private void getDatasFromUI()
    {
        //check if the input are empty ,show a message
        if((mTitle_textView.getText().length()==0 ) || (mDescription_textView.getText().length()==0))
            Toast.makeText(getActivity(),"لطفا در ورود اطلاعات دقت فرمایید" ,Toast.LENGTH_LONG).show();
        else {
            mTiltle = mTitle_textView.getText().toString();
            mDescription = mDescription_textView.getText().toString();
            if (mTaskType_checkbox.isChecked())
                mTaskType = Task.TaskType.DONE;
            else
                mTaskType = Task.TaskType.UNDONE;


            Task temp_task=new Task();
            temp_task.setTitle(mTiltle);
            temp_task.setDescription(mDescription);
            temp_task.setTaskType(mTaskType);
            temp_task.setDate(makeDate_Time(mDatePattern));
            temp_task.setHour(mHour);
            TaskLab.getInstance().addTask(mTaskType,temp_task);

        }

    }
    private String makeDate_Time(String pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String result = simpleDateFormat.format(new Date());
        return result;
    }



}
