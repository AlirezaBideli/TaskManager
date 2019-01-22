package com.example.admin.software_1.controllers.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.utils.PictureUtils;

import java.io.File;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowTaskInfoFragmnet extends DialogFragment implements View.OnClickListener {


    private Button mAdd_Button;
    private Button mEdit_Button;
    private Button mRemove_Button;
    private TextView mTitle_textView;
    private TextView mDesc_textView;
    private TextView mDate_textView;
    private TextView mHour_textView;
    private CheckBox mTaskType_CheckBox;
    private ImageView mTask_ImageView;

    private UUID mTaskId;
    public static final String TAG_REMOVE = "tag_remove";
    public static final String TAG_EDIT = "tag_edit";
    public static final String TAG_ADD = "tag_add";
    public static final String EXTRA_TASK_ID = "com.example.admin.software_1_extra_task_id";
    public static final String ADD_FRAGMNET_TAG = "addFragment_tag";
    public static final String EDIT_FRAGMENT_TAG = "editFragment_tag";
    private File mTaskPicFile;
    private Task mTask;


    public ShowTaskInfoFragmnet() {
        // Required empty public constructor
    }


    public static ShowTaskInfoFragmnet newInstance(UUID id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TASK_ID, id);
        ShowTaskInfoFragmnet fragment = new ShowTaskInfoFragmnet();
        fragment.setArguments(bundle);
        return fragment;
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_show_task_info_fragmnet, null);
        initialization(view);
        mTaskId = (UUID) getArguments().getSerializable(EXTRA_TASK_ID);
        mTask = TaskLab.getInstance().getTask(mTaskId);
        mTaskPicFile = TaskLab.getInstance().getTaskPicture(getActivity(), mTask);
        fillUIWidgets(mTask);
        setListeners();

        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setView(view).create();
        return alertDialog;
    }

    private void setListeners() {
        mAdd_Button.setOnClickListener(this);
        mRemove_Button.setOnClickListener(this);
        mEdit_Button.setOnClickListener(this);

    }


    private void initialization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_ShowTaskInfoFragment);
        mDesc_textView = view.findViewById(R.id.description_textView_ShowTaskInfoFragment);
        mDate_textView = view.findViewById(R.id.date_textView_ShowTaskInfoFragment);
        mHour_textView = view.findViewById(R.id.hour_textView_ShowTaskInfoFragment);
        mTaskType_CheckBox = view.findViewById(R.id.taskType_checkbox_ShowTaskInfoFragment);

        mAdd_Button = view.findViewById(R.id.add_button_ShowTaskInfoFragment);
        mEdit_Button = view.findViewById(R.id.edit_button_ShowTaskInfoFragment);
        mRemove_Button = view.findViewById(R.id.remove_button_ShowTaskInfoFragment);
        mTask_ImageView = view.findViewById(R.id.chooseImage_img_ShowTaskInfo);

    }


    private void fillUIWidgets(Task task) {


        mTitle_textView.setText(task.getTitle());
        mDesc_textView.setText(task.getDescription());
        mDate_textView.setText(task.getDate());
        mHour_textView.setText(task.getTime());
        boolean isDoneTask = setChecked(task.getTaskType());
        mTaskType_CheckBox.setChecked(isDoneTask);
        PictureUtils.updatePhotoView(getActivity(), mTask,mTask_ImageView);
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
            case R.id.add_button_ShowTaskInfoFragment:
                goToAddFragment();
                break;
            case R.id.remove_button_ShowTaskInfoFragment:
                showRemoveDialog();
                break;
            case R.id.edit_button_ShowTaskInfoFragment:
                goToEditFragment();

                break;
        }
    }

    private void goToEditFragment() {


        EditFragment editFragment = EditFragment.newInstance(mTaskId);
        editFragment.show(getFragmentManager(), TAG_EDIT);


    }

    private void goToAddFragment() {
        AddFragment editFragment = AddFragment.newInstance();
        editFragment.show(getFragmentManager(), TAG_ADD);


    }

    private void showRemoveDialog() {

        RemoveDialogFragment removeDialogFragmnet = RemoveDialogFragment.newInstance(mTaskId);
        removeDialogFragmnet.show(getFragmentManager(), TAG_REMOVE);
    }


}
