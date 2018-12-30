package com.example.admin.software_1;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.example.admin.software_1_date_time";
    public static final String ARG_TIME = "arg_time";
    public static final String FORMAT_TIME = "hh:mm";

    private Date mTime;
    private TimePicker mTimePicker;

    public static TaskTimePickerFragment newInstance(Date time) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);
        TaskTimePickerFragment fragment = new TaskTimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskTimePickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null);
        initilization(view);
        timePickerInitialization();

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.timePicker_string))
                .setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        String time = getResources().getString(R.string.time_string, hour + "", minute + "");
                        intent.putExtra(EXTRA_TIME, time);
                        getTargetFragment().onActivityResult(AddFragment.REQ_TIME_PICKER, Activity.RESULT_OK, intent);
                    }
                })
                .setView(view)
                .create();


        return alertDialog;
    }

    private void timePickerInitialization() {
        mTime = (Date) getArguments().getSerializable(ARG_TIME);

        if(mTime!=null) {//it means that if the mTime is equal to Undefined
            int[] intTime = timeToInteger(mTime, FORMAT_TIME);
            int hour = intTime[0];
            int minute = intTime[1];
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        }
    }


    private int[] timeToInteger(Date time, String format) {

        if (time == null) return null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        String stringTime = simpledateformat.format(time);
        String[] strTime = stringTime.split(":");
        int[] intTime = strToIntArray(strTime);//0: Hour, 1: Minute
        return intTime;
    }


    private int[] strToIntArray(String[] strArray) {
        int[] temp = new int[strArray.length];
        for (byte i = 0; i < strArray.length; i++) {
            temp[i] = Integer.parseInt(strArray[i]);
        }

        return temp;
    }

    private void initilization(View view) {
        mTimePicker = view.findViewById(R.id.taskTime_tmp_TimePickerFragment);
    }
}
