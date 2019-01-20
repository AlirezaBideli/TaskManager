package com.example.admin.software_1.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;

import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.User;
import com.example.admin.software_1.models.UserLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {


    //Widgets Variables
    private Button mRegisterBtn;
    private EditText mFirstNameEdt;
    private EditText mLastNameEdt;
    private EditText mUsernameEdt;
    private EditText mPasswordEdt;
    private EditText mConfirmPasswordEdt;
    private TextView mErrorTextView;
    //simple variables
    private String mFirstName;
    private String mLastName;
    private String mUserName;
    private String mPassword;
    private String mConfirmPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initilization(view);
        setListeners();


        return view;
    }

    private void setListeners() {
        mFirstNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFirstName = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLastNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLastName = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mUsernameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserName = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mConfirmPassword = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRegisterBtn.setOnClickListener(this);
    }


    private void initilization(View view) {
        mRegisterBtn = view.findViewById(R.id.register_btn_registerFragment);
        mUsernameEdt = view.findViewById(R.id.username_edt_registerFragment);
        mPasswordEdt = view.findViewById(R.id.password_edt_registerFragment);
        mConfirmPasswordEdt = view.findViewById(R.id.confirmPassword_edt_registerFragment);
        mErrorTextView = view.findViewById(R.id.error_txt_registerFragment);
        mFirstNameEdt = view.findViewById(R.id.firstName_edt_registerFragment);
        mLastNameEdt = view.findViewById(R.id.lastName_edt_registerFragment);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_btn_registerFragment:
                StringBuilder message = new StringBuilder();
                boolean isInputsValid = isInputsValid();


                if (!isInputsValid) {
                    message.append(getResources().getString(R.string.input_error_message) + "\n");
                } else {
                    String toastMessage;
                    boolean isPasswordTrue = isPasswordTrue();
                    if (isPasswordTrue) {
                        User user = makeUser();
                        try {
                            UserLab.getInstance().addUser(user);
                            updateTasks();
                            toastMessage= getResources().getString(R.string.registerMessage);
                            showToast(toastMessage);
                            goToTaskManagerActivity();
                        }
                        catch (Exception e)
                        {
                            toastMessage=getString(R.string.reapeatative_UserName_text);
                            showToast(toastMessage);
                        }


                    } else {
                        message.append(getResources().getString(R.string.notEqualPassword));
                    }
                }
                mErrorTextView.setText(message);

                break;
        }
    }

    private void updateTasks() {
        Long userId= UserLab.getInstance().getCurrentUser().get_id();
        TaskLab.getInstance().updateTasks(userId);
    }

    private boolean isInputsValid() {

        return
                (mFirstNameEdt.getText().length() != 0) &&
                        (mLastNameEdt.getText().length() != 0) &&
                        mUsernameEdt.getText().length() != 0 &&
                        (mPasswordEdt.getText().length() != 0) &&
                        (mConfirmPasswordEdt.getText().length() != 0);


    }


    private boolean isPasswordTrue() {
        return mPassword.equals(mConfirmPassword);
    }

    private User makeUser() {
        User user = new User();
        user.setFirstName(mFirstName);
        user.setLastName(mLastName);
        user.setUserName(mUserName);
        user.setPassword(mPassword);

        return user;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void goToTaskManagerActivity() {
        Intent intent = new TaskManagerActivity().newIntent(getActivity());
        startActivity(intent);
    }

}

