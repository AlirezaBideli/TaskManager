package com.example.admin.software_1;



import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDatePickerFragment extends DialogFragment {


    public static final String Date_FORMAT = "yyyy/MM/dd";
    public static final String EXTRA_DATE ="com.example.admin.software_1_extra_date" ;
    private DatePicker mDatePicker;
    public static TaskDatePickerFragment newInstance() {

        Bundle args = new Bundle();

        TaskDatePickerFragment fragment = new TaskDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public TaskDatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker,null,false);
        initilization(view);
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.datePicker_string))
                .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String date=getDate();
                        Intent intent=new Intent();
                        intent.putExtra(EXTRA_DATE,date);
                        getTargetFragment().onActivityResult(AddFragment.REQ_DATE_PICKER, Activity.RESULT_OK,intent);

                    }
                })
                .setView(view)
                .create();
        return alertDialog;
    }

    private String getDate() {
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year =  mDatePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date=calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(Date_FORMAT);
        String dateString = sdf.format(date);

        return dateString;
    }


    private void initilization(View view)
    {
        mDatePicker=view.findViewById(R.id.taskDate_dap_TaskDatePickerFragment);
    }

}
