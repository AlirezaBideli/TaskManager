package com.example.admin.software_1.controllers.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.software_1.R;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.utils.PictureUtils;

import java.io.File;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends DialogFragment implements View.OnClickListener {


    public static final String TAG_REMOVE = "tag_remove";
    public static final String TAG_EDIT = "tag_edit";
    public static final String TAG_ADD = "tag_add";
    public static final String EXTRA_TASK_ID = "com.example.admin.software_1_extra_task_id";
    public static final String ADD_FRAGMNET_TAG = "addFragment_tag";
    public static final String EDIT_FRAGMENT_TAG = "editFragment_tag";
    private Button mEdit_Button;
    private Button mRemove_Button;
    private TextView mTitle_textView;
    private TextView mDesc_textView;
    private TextView mDate_textView;
    private TextView mHour_textView;
    private CheckBox mTaskType_CheckBox;
    private ImageView mTask_ImageView;
    private UUID mTaskId;
    private File mTaskPicFile;
    private Task mTask;
    private CallBacks mCallBacks;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(UUID id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TASK_ID, id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallBacks)
            mCallBacks = (CallBacks) context;
        else throw new RuntimeException("Implementation Error");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBacks=null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_detail, null);
        initialization(view);
        mTaskId = (UUID) getArguments().getSerializable(EXTRA_TASK_ID);
        mTask = TaskLab.getInstance().getTask(mTaskId);
        mTaskPicFile = TaskLab.getInstance().getTaskPicture(getActivity(), mTask);
        fillUIWidgets(mTask);
        setListeners();

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        return alertDialog;
    }

    private void setListeners() {
        mRemove_Button.setOnClickListener(this);
        mEdit_Button.setOnClickListener(this);
        mTaskType_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    mTask.setTaskType(Task.TaskType.DONE);
                else
                    mTask.setTaskType(Task.TaskType.UNDONE);


            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        TaskLab.getInstance().updateTask(mTask);
        mCallBacks.onTaskUpdated();

    }

    private void initialization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_detailFragment);
        mDesc_textView = view.findViewById(R.id.description_textView_detailFragmnet);
        mDate_textView = view.findViewById(R.id.date_textView_detailFragment);
        mHour_textView = view.findViewById(R.id.hour_textView_detailFragment);
        mTaskType_CheckBox = view.findViewById(R.id.taskType_checkbox_detailFragment);
        mEdit_Button = view.findViewById(R.id.edit_button_detailFragment);
        mRemove_Button = view.findViewById(R.id.remove_button_detailFragment);
        mTask_ImageView = view.findViewById(R.id.chooseImage_img_detailFragment);

    }


    private void fillUIWidgets(Task task) {


        mTitle_textView.setText(task.getTitle());
        mDesc_textView.setText(task.getDescription());
        mDate_textView.setText(task.getDate());
        mHour_textView.setText(task.getTime());
        boolean isDoneTask = setChecked(task.getTaskType());
        mTaskType_CheckBox.setChecked(isDoneTask);
        PictureUtils.updatePhotoView(getActivity(), mTask, mTask_ImageView);
    }


    private boolean setChecked(Task.TaskType taskType) {

        if (taskType == Task.TaskType.DONE)
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.remove_button_detailFragment:
                showRemoveDialog();
                break;
            case R.id.edit_button_detailFragment:
                goToEditFragment();

                break;
        }
    }

    private void goToEditFragment() {


        EditFragment editFragment = EditFragment.newInstance(mTaskId);
        editFragment.show(getFragmentManager(), TAG_EDIT);


    }


    private void showRemoveDialog() {

        RemoveDialogFragment removeDialogFragmnet = RemoveDialogFragment.newInstance(mTaskId);
        removeDialogFragmnet.show(getFragmentManager(), TAG_REMOVE);
    }


    public interface CallBacks {
        void onTaskUpdated();
    }


}
