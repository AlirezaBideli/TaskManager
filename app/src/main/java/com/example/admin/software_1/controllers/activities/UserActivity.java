package com.example.admin.software_1.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.fragments.LoginFragment;
import com.example.admin.software_1.controllers.fragments.RegisterFragment;

public class UserActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_root_userActivity, LoginFragment.newInstance())
                .commit();
    }
}
