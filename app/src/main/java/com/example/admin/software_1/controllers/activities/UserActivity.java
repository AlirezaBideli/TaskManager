package com.example.admin.software_1.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.fragments.LoginFragment;
import com.example.admin.software_1.controllers.fragments.RegisterFragment;

public class UserActivity extends AppCompatActivity {


    public static final int NOT_REGISTERED_USER=0;
    public static final int REGISTERED_USER=1;
    public static final String EXTRA_STATE="com.example.admin.software_1.controllers.activities_extra_state";

    public Intent newIntent(Context context,int state) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_STATE,state);
        return intent;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setCurrentFragment();
    }

    private void setCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment sourceFragmnet=null;
        int state=getIntent().getIntExtra(EXTRA_STATE,REGISTERED_USER);
        switch (state)
        {
            case NOT_REGISTERED_USER:
                sourceFragmnet= RegisterFragment.newInstance();
                break;
            case REGISTERED_USER:
                sourceFragmnet= LoginFragment.newInstance();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container_root_userActivity,sourceFragmnet)
                .commit();
    }



}
