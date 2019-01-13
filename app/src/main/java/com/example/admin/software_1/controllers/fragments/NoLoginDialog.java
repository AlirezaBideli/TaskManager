package com.example.admin.software_1.controllers.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.admin.software_1.R;
import com.example.admin.software_1.controllers.activities.EditActivity;
import com.example.admin.software_1.controllers.activities.TaskManagerActivity;
import com.example.admin.software_1.controllers.activities.UserActivity;
import com.example.admin.software_1.models.TaskLab;
import com.example.admin.software_1.models.UserLab;

/**
 * Created by ADMIN on 1/13/2019.
 */

public class NoLoginDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_message)
                .setMessage(R.string.without_login_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new UserActivity().newIntent(getActivity(),UserActivity.NOT_REGISTERED_USER);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int userId = UserLab.getInstance(getActivity()).getCurrentUser().getUser_id();
                        if (userId == LoginFragment.DEFAULT_USER_ID) {
                            TaskLab.getInstance(getActivity()).removeAllTasks(userId);
                            gotToLoginFragment();
                        }
                    }
                })
                .create();

        return alertDialog;

    }


    private void gotToLoginFragment()
    {
        Intent intent=new UserActivity().newIntent(getActivity(), UserActivity.NOT_REGISTERED_USER);
        startActivity(intent);
    }
}
