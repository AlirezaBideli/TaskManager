package com.example.admin.software_1.controllers.fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends DialogFragment implements View.OnClickListener {


    //Widgets variables
    private EditText mTitle_EditText;
    private EditText mDescription_EditText;
    private CheckBox mTaskType_Checkbox;
    private Button mAddButton;
    private Button mSetTimeButton;
    private Button mSetDateButton;
    private Button mTakePhoto;
    private Button mChoosePhoto;

    private ImageView mTaskPicture_imageView;
    //simple variables

    private Task mTask;
    private static String defaultValue;
    private static String mTitle;
    private static String mDescription;
    private static String mTime;
    private static String mDate;
    private static boolean mTaskTypeChecked;
    private Task.TaskType mTaskType;
    private Long mUserId;
    File mTaskPhoto;
    private static boolean sIsOrientationChanged = false;
    public static final String DIALOG_TAG_TIME_PICKER = "timePicker_tag";
    public static final String DIALOG_TAG_DATE_PICKER = "datePicker_tag";
    private static final String DIALOG_TAG_DT = "time_date_picker_tag";
    public static final String AUTHORITY = "com.example.admin.software_1.FileProvider";

    public static final int REQ_TIME_PICKER = 0;
    public static final int REQ_DATE_PICKER = 1;
    private static final int REQ_CAMERA = 2;
    private static final int REQ_DEVICE_STORAGE = 3;


    private boolean isInputValid;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {

        Bundle args = new Bundle();

        AddFragment fragment = new AddFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = UserLab.getInstance().getCurrentUser().get_id();
        mTask = new Task();
        //UUID and userId must added because user wont add this properties
        mTask.setUuId(UUID.randomUUID());
        mTask.setUser_id(mUserId);
        mTaskPhoto = TaskLab.getInstance().getTaskPicture(getActivity(), mTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initilization(view);
        setListeners();

        return view;
    }

    private void setListeners() {

        mAddButton.setOnClickListener(this);
        mSetTimeButton.setOnClickListener(this);
        mSetDateButton.setOnClickListener(this);
        mTaskType_Checkbox.setOnClickListener(this);
        mTakePhoto.setOnClickListener(this);
        mChoosePhoto.setOnClickListener(this);
        mTitle_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDescription_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDescription = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void initilization(View view) {
        mTitle_EditText = view.findViewById(R.id.title_editText_Addfragment);
        mDescription_EditText = view.findViewById(R.id.description_editText_Addfragment);
        mTaskType_Checkbox = view.findViewById(R.id.taskType_checkbox_Addfragment);
        mAddButton = view.findViewById(R.id.add_button_Addfragment);
        mSetTimeButton = view.findViewById(R.id.setTime_btn_AddFragment);
        mSetDateButton = view.findViewById(R.id.setDate_btn_AddFragment);
        mTakePhoto = view.findViewById(R.id.take_image_btn_AddFragment);
        mChoosePhoto = view.findViewById(R.id.choose_image_btn_AddFragment);
        mTaskPicture_imageView = view.findViewById(R.id.chooseImage_img_AddFrgment);
    }

    private void AddDate() {
        //check if the input are empty ,show a message

        if (mTitle_EditText.getText().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.input_error_message),
                    Toast.LENGTH_LONG).show();
            isInputValid = false;
        } else {
            addTask();

        }

    }

    private void addTask() {


        mUserId = UserLab.getInstance().getCurrentUser().get_id();
        isInputValid = true;
        mTitle = mTitle_EditText.getText().toString();
        mDescription = mDescription_EditText.getText().toString();
        mTaskType = setTaskType(mTaskType_Checkbox.isChecked());

        mTask.setTitle(mTitle);
        mTask.setTaskType(mTaskType);
        if (mDate != null)
            mTask.setDate(mDate);
        if (mTime != null)
            mTask.setTime(mTime);
        if (mDescription != null)
            mTask.setDescription(mDescription);

        TaskLab.getInstance().addTask(mTask);
    }

    private void resetData() {
        defaultValue = getResources().getString(R.string.undefined);
        mTitle = "";
        mDescription = "";
        mTime = defaultValue;
        mDate = defaultValue;
        mTaskTypeChecked = false;
        sIsOrientationChanged = false;
        mTaskType = null;
    }

    private Task.TaskType setTaskType(boolean isChecked) {
        if (isChecked)
            return Task.TaskType.DONE;
        else
            return Task.TaskType.UNDONE;
    }


    private void fillTaskImage() {
        Uri uri = FileProvider.getUriForFile(getActivity(),
                AUTHORITY
                , mTaskPhoto);
        getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        PictureUtils.updatePhotoView(getActivity(), mTask, mTaskPicture_imageView);
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


    private void goToDeviceStorage() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQ_DEVICE_STORAGE);
    }


    private void goToCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(getActivity(), AUTHORITY
                , mTaskPhoto);
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


    private void goToDatePickerFragment() {
        TaskDatePickerFragment taskDatePickerFragment = TaskDatePickerFragment.newInstance(new Date());//Date
        taskDatePickerFragment.setTargetFragment(AddFragment.this, REQ_DATE_PICKER);
        taskDatePickerFragment.show(getFragmentManager(), DIALOG_TAG_DATE_PICKER);
    }

    private void goToTimePickerFragment() {
        TaskTimePickerFragment timePickerFragment = TaskTimePickerFragment.newInstance(new Date());//Time
        timePickerFragment.setTargetFragment(AddFragment.this, REQ_TIME_PICKER);
        timePickerFragment.show(getFragmentManager(), DIALOG_TAG_TIME_PICKER);
    }

    private void goToTaskManagerActivity() {
        Intent intent = new TaskManagerActivity().newIntent(getActivity());
        startActivity(intent);
    }

    private void fillUIwidgets() {

        PictureUtils.updatePhotoView(getActivity(), mTask, mTaskPicture_imageView);
        mTitle_EditText.setText(mTitle, TextView.BufferType.EDITABLE);
        mDescription_EditText.setText(mDescription, TextView.BufferType.EDITABLE);
        mTaskType_Checkbox.setChecked(mTaskTypeChecked);
        if (!Objects.equals(mDate, defaultValue)) {
            String dateMessage = getResources().getString(R.string.taskDate_button_add, mDate);
            mSetDateButton.setText(dateMessage);
        }
        if (!Objects.equals(mTime, defaultValue)) {
            String timeMessage = getResources().getString(R.string.taskTime_button_add, mTime);
            mSetTimeButton.setText(timeMessage);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (sIsOrientationChanged) {
            fillUIwidgets();
            sIsOrientationChanged = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        sIsOrientationChanged = true;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!sIsOrientationChanged)
            resetData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_button_Addfragment:
                AddDate();
                resetData();
                if (isInputValid)
                    goToTaskManagerActivity();
                break;

            case R.id.setTime_btn_AddFragment:
                goToTimePickerFragment();
                break;

            case R.id.setDate_btn_AddFragment:
                goToDatePickerFragment();
                break;

            case R.id.taskType_checkbox_Addfragment:
                mTaskType = setTaskType(true);
                mTaskTypeChecked = true;
                break;
            case R.id.choose_image_btn_AddFragment:
                goToCamera();
                break;
            case R.id.take_image_btn_AddFragment:
                goToDeviceStorage();
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != EditActivity.RESULT_OK)
            return;


        switch (requestCode) {
            case REQ_TIME_PICKER:
                mTime = data.getStringExtra(TaskTimePickerFragment.EXTRA_TIME);
                String timeMessage = getResources().getString(R.string.taskTime_button_add, mTime);
                mSetTimeButton.setText(timeMessage);
                break;
            case REQ_DATE_PICKER:
                mDate = data.getStringExtra(TaskDatePickerFragment.EXTRA_DATE);
                String dateMessage = getResources().getString(R.string.taskDate_button_add, mDate);
                mSetDateButton.setText(dateMessage);
                break;
            case REQ_CAMERA:
                fillTaskImage();
                break;
            case REQ_DEVICE_STORAGE:
                try {
                    getImageFromGallary(data);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }


    }

    private void getImageFromGallary(Intent data) throws FileNotFoundException {
        Uri imageUri = data.getData();
        InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        copyToImageLocations(selectedImage);
        mTaskPicture_imageView.setImageBitmap(selectedImage);
    }


}
