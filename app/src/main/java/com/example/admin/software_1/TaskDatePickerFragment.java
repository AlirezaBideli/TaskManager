package com.example.admin.software_1;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDatePickerFragment extends DialogFragment {


    public static final String FORMAT_DATE = "yyyy/MM/dd";
    public static final String EXTRA_DATE = "com.example.admin.software_1_extra_date";
    public static final String ARG_DATE = "args_date";
    private DatePicker mDatePicker;
    private Date mDate;

    public static TaskDatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();

        args.putSerializable(ARG_DATE, date);
        TaskDatePickerFragment fragment = new TaskDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDatePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker, null, false);


        initilization(view);
        datePickerInitialization();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.datePicker_string))
                .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String date = getDate();
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_DATE, date);
                        getTargetFragment().onActivityResult(AddFragment.REQ_DATE_PICKER, Activity.RESULT_OK, intent);

                    }
                })
                .setView(view)
                .create();


        return alertDialog;
    }

    private void datePickerInitialization() {
        mDate = (Date) getArguments().getSerializable(ARG_DATE);

        if (mDate!=null) {//it means that if the mDate is equal to Undefined
            int[] intDate = dateToInteger(mDate, FORMAT_DATE);//0: Year, 1: Month, 2: day
            int year = intDate[0];
            int month = intDate[1];
            int day = intDate[2];
            mDatePicker.init(year, month, day, null);
        }
    }

    private String getDate() {
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year = mDatePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        String dateString = sdf.format(date);

        return dateString;
    }


    private void initilization(View view) {
        mDatePicker = view.findViewById(R.id.taskDate_dap_TaskDatePickerFragment);
    }


    private int[] dateToInteger(Date date, String aFormat) {

        if (date == null) return null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        String stringDate = simpledateformat.format(date);
        String[] strDate = stringDate.split("/");
        int[] intDate = strToIntArray(strDate);//0: Year, 1: Month, 2: day
        return intDate;
    }


    private int[] strToIntArray(String[] strArray) {
        int[] temp = new int[strArray.length];
        for (byte i = 0; i < strArray.length; i++) {
            temp[i] = Integer.parseInt(strArray[i]);
        }

        return temp;
    }


}
