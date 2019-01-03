package com.example.admin.software_1.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;


/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveDialogFragment extends DialogFragment implements View.OnClickListener {

    public static  String ARGS_TASK="rgs_task";
    private Task mTask;//this is used for the get this fragment args
    private Button mYesButton;
    private Button mNoButton;

    public static RemoveDialogFragment newInstance(Task task) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK,task);
        RemoveDialogFragment fragment = new RemoveDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public RemoveDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_remove_dialog_fragmnet, container, false);
        initilization(view);
        getArgs();
        mYesButton.setOnClickListener(this);
        mNoButton.setOnClickListener(this);


        return view;
    }


    private void initilization(View view)
    {
        mYesButton=view.findViewById(R.id.yes_btn_RemoveDialogFragment);
        mNoButton=view.findViewById(R.id.no_btn_RemoveDialogFragment);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.yes_btn_RemoveDialogFragment:
                TaskLab.getInstance(getActivity()).removeTask(mTask);
                Intent intent=((EditActivity)getActivity()).newIntent();
                startActivity(intent);

                break;
            case R.id.no_btn_RemoveDialogFragment:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
                break;
        }
    }


    private void getArgs()
    {
        mTask=(Task) getArguments().getSerializable(ARGS_TASK);
    }
}
