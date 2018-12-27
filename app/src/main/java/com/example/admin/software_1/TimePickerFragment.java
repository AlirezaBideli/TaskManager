package com.example.admin.software_1;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE_TIME = "com.example.admin.software_1_date_time";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance() {

        Bundle args = new Bundle();

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimePickerFragment() {
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
                        intent.putExtra(EXTRA_DATE_TIME, hour + ":" + minute);
                        getTargetFragment().onActivityResult(AddFragment.REQ_TIME_PICKER, Activity.RESULT_OK, intent);
                    }
                })
                .setView(view)
                .create();


        return alertDialog;
    }


    @Override
    public void onStart() {

        super.onStart();
    }

    private void initilization(View view) {
        mTimePicker = view.findViewById(R.id.taskTime_tmp_TimePickerFragment);
    }
}
