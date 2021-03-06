package com.example.admin.software_1.controllers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.software_1.R;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveDialogFragment extends DialogFragment implements View.OnClickListener {

    public static String ARGS_TASK_ID = "task_id";
    private UUID mTaskId;//this is used for the get this fragment args
    private Button mYesButton;
    private Button mNoButton;
    private CallBacks mCallBacks;

    public RemoveDialogFragment() {
        // Required empty public constructor
    }

    public static RemoveDialogFragment newInstance(UUID taskId) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID, taskId);
        RemoveDialogFragment fragment = new RemoveDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallBacks)
            mCallBacks = (CallBacks) context;

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_remove_dialog_fragmnet, container, false);
        initilization(view);
        getArgs();
        setListeners();


        return view;
    }

    private void setListeners() {
        mYesButton.setOnClickListener(this);
        mNoButton.setOnClickListener(this);
    }


    private void initilization(View view) {
        mYesButton = view.findViewById(R.id.yes_btn_RemoveDialogFragment);
        mNoButton = view.findViewById(R.id.no_btn_RemoveDialogFragment);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yes_btn_RemoveDialogFragment:
                Context context = getActivity();
                mCallBacks.onDeletedTask(mTaskId);
                dismiss();
                break;
            case R.id.no_btn_RemoveDialogFragment:
                dismiss();
                break;
        }
    }


    private void getArgs() {
        mTaskId = (UUID) getArguments().getSerializable(ARGS_TASK_ID);
    }


    public interface CallBacks {
        void onDeletedTask(UUID taskId);
    }
}
