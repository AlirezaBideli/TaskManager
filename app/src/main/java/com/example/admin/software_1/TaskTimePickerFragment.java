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


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.example.admin.software_1_date_time";
    private TimePicker mTimePicker;

    public static TaskTimePickerFragment newInstance() {

        Bundle args = new Bundle();

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

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.timePicker_string))
                .setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        intent.putExtra(EXTRA_TIME, hour + ":" + minute);
                        getTargetFragment().onActivityResult(AddFragment.REQ_TIME_PICKER, Activity.RESULT_OK, intent);
                    }
                })
                .setView(view)
                .create();


        return alertDialog;
    }


    private void initilization(View view) {
        mTimePicker = view.findViewById(R.id.taskTime_tmp_TimePickerFragment);
    }
}
