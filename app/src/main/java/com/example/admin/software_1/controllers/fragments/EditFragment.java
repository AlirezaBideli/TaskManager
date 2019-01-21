package com.example.admin.software_1.controllers.fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;
import com.example.admin.software_1.models.Task;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.UserLab;
import com.example.admin.software_1.utils.PictureUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends DialogFragment implements View.OnClickListener {

    //Widgets variables
    private EditText mTitle_EditText;
    private EditText mDescription_EditText;
    private CheckBox mTaskType_checkbox;
    private Button mDone_button;
    private Button mSetDate_Button;
    private Button mSetTime_Button;
    private Button mChoose_button;
    private Button mTakePhoto_button;
    private ImageView mTask_Img;
    //Simple variables
    private UUID mTaskId;
    private Task mTask;
    private String mTitle;
    private String mDescription;
    private String mTime;
    private String mDate;
    private Task.TaskType mTaskType;
    private String undefined;
    public static final String DIALOG_TAG_TIME_PICKER = "timePicker_tag";
    public static final String DIALOG_TAG_DATE_PICKER = "datePicker_tag";
    public static final String AUTHORITY = "com.example.admin.software_1.FileProvider";
    private static final int REQ_DEVICE_STORAGE = 3;
    public static final int REQ_TIME_PICKER = 0;
    public static final int REQ_DATE_PICKER = 1;
    public static final String ARGS_TASK_Id = "taskId";
    private static final int REQ_CAMERA = 2;
    private File mTaskPicFile;


    public static EditFragment newInstance(UUID taskId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_TASK_Id, taskId);
        EditFragment fragment = new EditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
        setFields();

    }

    private void setFields() {
        mTask = TaskLab.getInstance().getTask(mTaskId);
        mDate = mTask.getDate();
        mTime = mTask.getTime();
        undefined = getResources().getString(R.string.undefined);
        mDescription = getResources().getString(R.string.undefined);//set undefined because maybe the user delete it
        mTaskPicFile=TaskLab.getInstance().getTaskPicture(getActivity(),mTask);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        initialization(view);

        fillUiWidgets();
        setListeners();
        return view;
    }

    private void setListeners() {
        mDone_button.setOnClickListener(this);
        mSetDate_Button.setOnClickListener(this);
        mSetTime_Button.setOnClickListener(this);
        mChoose_button.setOnClickListener(this);
        mTakePhoto_button.setOnClickListener(this);
    }


    private void initialization(View view) {
        mTitle_EditText = view.findViewById(R.id.title_editText_EditFragment);
        mDescription_EditText = view.findViewById(R.id.description_editText_EditFragment);
        mTaskType_checkbox = view.findViewById(R.id.taskType_checkbox_EditFragment);
        mDone_button = view.findViewById(R.id.done_button_EditFragment);
        mSetDate_Button = view.findViewById(R.id.setDate_btn_EditFragment);
        mSetTime_Button = view.findViewById(R.id.setTime_btn_EditFragment);
        mChoose_button = view.findViewById(R.id.choose_image_btn_EditFragment);
        mTakePhoto_button=view.findViewById(R.id.take_image_btn_EditFragment);
        mTask_Img = view.findViewById(R.id.chooseImage_img_EditFragment);
    }

    private void getArgs() {
        mTaskId = (UUID) getArguments().getSerializable(ARGS_TASK_Id);
    }

    private void fillUiWidgets() {
        mTitle = mTask.getTitle();
        mDescription = mTask.getDescription();
        mTaskType = mTask.getTaskType();
        mTitle_EditText.setText(mTitle);
        mDescription_EditText.setText(mDescription);
        boolean reault = setTaskTypeChecked(mTaskType);
        mTaskType_checkbox.setChecked(reault);
        mSetDate_Button.setText(getResources().getString(R.string.taskDate_button_edit, mTask.getDate()));
        mSetTime_Button.setText(getResources().getString(R.string.taskTime_button_edit, mTask.getTime()));
        PictureUtils.updatePhotoView(getActivity(),mTask,mTask_Img);
    }

    private boolean setTaskTypeChecked(Task.TaskType taskType) {
        return taskType == Task.TaskType.DONE;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.done_button_EditFragment:
                if (mTitle_EditText.getText().length() == 0) {
                    String message = getResources().getString(R.string.input_error_message);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                } else
                    setSendedTask();
                break;

            case R.id.setDate_btn_EditFragment:
                goToDatePickerFragment();
                break;

            case R.id.setTime_btn_EditFragment:
                goToTimePickerFragment();
                break;
            case R.id.choose_image_btn_EditFragment:
                goToDeviceStorage();
                break;
            case R.id.take_image_btn_EditFragment:
                goToCamera();
                break;
        }

    }

    private void setSendedTask() {

        Long userId = UserLab.getInstance().getCurrentUser().get_id();
        mTitle = mTitle_EditText.getText().toString();
        mDescription = mDescription_EditText.getText().toString();
        mTaskType = getTaskType(mTaskType_checkbox.isChecked());
        mTask.setTitle(mTitle);
        mTask.setDescription(mDescription);
        mTask.setTaskType(mTaskType);
        mTask.setDate(mDate);
        mTask.setTime(mTime);
        mTask.setUser_id(userId);
        mTask.setImagePath(mTaskPicFile.getPath());
        TaskLab.getInstance().updateTask(mTask);
        Intent intent = new TaskManagerActivity().newIntent(getActivity());
        startActivity(intent);

    }


    private void goToDatePickerFragment() {
        Date date = stringToDate(mDate);
        TaskDatePickerFragment taskDatePickerFragment = TaskDatePickerFragment.newInstance(date);
        taskDatePickerFragment.setTargetFragment(EditFragment.this, REQ_DATE_PICKER);
        taskDatePickerFragment.show(getFragmentManager(), DIALOG_TAG_DATE_PICKER);
    }

    private void goToTimePickerFragment() {
        Date time = stringToTime(mTime);
        TaskTimePickerFragment timePickerFragment = TaskTimePickerFragment.newInstance(time);
        timePickerFragment.setTargetFragment(EditFragment.this, REQ_TIME_PICKER);
        timePickerFragment.show(getFragmentManager(), DIALOG_TAG_TIME_PICKER);
    }


    private Task.TaskType getTaskType(boolean isChecked) {
        if (isChecked)
            return Task.TaskType.DONE;
        return Task.TaskType.UNDONE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != EditActivity.RESULT_OK)
            return;


        switch (requestCode) {
            case REQ_DATE_PICKER: {
                mDate = data.getStringExtra(TaskDatePickerFragment.EXTRA_DATE);
                String message = getResources().getString(R.string.taskDate_button_add, mDate);
                mSetDate_Button.setText(message);
            }
            break;
            case REQ_TIME_PICKER:
                mTime = data.getStringExtra(TaskTimePickerFragment.EXTRA_TIME);
                String message = getResources().getString(R.string.taskTime_button_add, mTime);
                mSetTime_Button.setText(message);
                break;
            case REQ_CAMERA:
                fillTaskImage();
                break;
            case REQ_DEVICE_STORAGE:
                try {
                    getImageFromGallary(data);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }



    }

    private void goToCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(getActivity(), AUTHORITY
                , mTaskPicFile);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);


        List<ResolveInfo> activities = getActivity().getPackageManager().queryIntentActivities(
                captureIntent,
                PackageManager.MATCH_DEFAULT_ONLY
        );
        for (ResolveInfo acticity : activities) {
            getActivity().grantUriPermission(
                    acticity.activityInfo.packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureIntent, REQ_CAMERA);
    }
    private void goToDeviceStorage() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQ_DEVICE_STORAGE);
    }
    private void getImageFromGallary(Intent data) throws FileNotFoundException {
        Uri imageUri = data.getData();
        InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        copyToImageLocations(selectedImage);
        mTask_Img.setImageBitmap(selectedImage);
    }
    private void fillTaskImage() {
        Uri uri = FileProvider.getUriForFile(getActivity(),
                AUTHORITY
                , mTaskPicFile);
        getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        PictureUtils.updatePhotoView(getActivity(),mTask,mTask_Img);
    }
    private void copyToImageLocations(Bitmap bitmap) {
        File path = TaskLab.getInstance().getTaskPicture(getActivity(), mTask);
        File gallaryImage = new File(path, mTask.getPictureName());


        FileOutputStream stream = null;
        try {

            ByteArrayOutputStream Bytestream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, Bytestream);
            byte[] byteArray = Bytestream.toByteArray();

            stream = new FileOutputStream(path);
            stream.write(byteArray);
            stream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    private Date stringToDate(String strDate) {
        ParsePosition ps = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TaskDatePickerFragment.FORMAT_DATE);
        Date date = simpleDateFormat.parse(strDate, ps);
        return date;
    }

    private Date stringToTime(String strTime) {
        ParsePosition ps = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TaskTimePickerFragment.FORMAT_TIME);
        Date time = simpleDateFormat.parse(strTime, ps);
        return time;
    }


}
