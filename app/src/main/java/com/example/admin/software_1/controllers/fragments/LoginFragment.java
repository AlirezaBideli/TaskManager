package com.example.admin.software_1.controllers.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.admin.software_1.models.User;
import com.example.admin.software_1.models.UserLab;


public class LoginFragment extends Fragment implements View.OnClickListener {


    //widgets variables
    private EditText mUserNameEdt;
    private EditText mPasswordEdt;
    private Button mLoginBtn;
    private TextView mNotRegisteredTxt;
    //simple variables
    private String mUserName;
    private String mPassword;


    public static Fragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initialization(view);
        mLoginBtn.setOnClickListener(this);
        mNotRegisteredTxt.setOnClickListener(this);
        mUserNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserName = s.toString();
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
                mPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }


    private void initialization(View view) {
        mUserNameEdt = view.findViewById(R.id.username_edt_loginFragment);
        mPasswordEdt = view.findViewById(R.id.password_edt_loginFragment);
        mLoginBtn = view.findViewById(R.id.login_btn_loginFragment);
        mNotRegisteredTxt = view.findViewById(R.id.notRegister_txt_loginFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_loginFragment:
                checkLoginOrNot();
                break;

            case R.id.notRegister_txt_loginFragment:
                goToRegisterFragment();
                break;
        }
    }

    private void checkLoginOrNot() {
        User unAuthorizedUser = new User();
        unAuthorizedUser.setUserName(mUserName);
        unAuthorizedUser.setPassword(mPassword);
        User user = UserLab.getInstance(getActivity()).login(unAuthorizedUser);
        String message;
        if (user != null)//when username and password are equal
        {
            Log.i("Tag", "firstName: "+user.getFirstName() + " lastName: " + user.getLastName()
                    + "userName: " + user.getUserName() + "password : " + user.getPassword()+"user_id: "+user.getUser_id());

            UserLab.getInstance(getActivity()).setCurrentUser(user);
            message = getResources().getString(R.string.welcome_message);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            gotToTaskManagerActivity();
        } else {
            message = getResources().getString(R.string.invalidLogin);
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }


    private void gotToTaskManagerActivity() {
        Intent intent = new TaskManagerActivity().newIntent(getActivity());
        startActivity(intent);
    }

    private void goToRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_root_userActivity, RegisterFragment.newInstance())
                .commit();
    }

}
