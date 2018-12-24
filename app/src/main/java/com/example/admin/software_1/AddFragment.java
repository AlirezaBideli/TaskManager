package com.example.admin.software_1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    //Widgets variables
    private TextView mTitle_textView;
    private TextView mDescription_textView;
    private CheckBox mTaskType_checkbox;
    private Button mAddButton;
    //simple variables
    private String mTiltle;
    private String mDescription;
    private Task.TaskType mTaskType;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        initilization(view);
        mAddButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getDatasFromUI();
            }
        });
        return view;
    }


    private void initilization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_Addfragment);
        mDescription_textView = view.findViewById(R.id.description_textView_Addfragment);
        mTaskType_checkbox = view.findViewById(R.id.taskType_checkbox_Addfragment);
        mAddButton=view.findViewById(R.id.add_button_Addfragment);
    }

    private void getDatasFromUI()
    {
        //check if the input are empty ,show a message
        if((mTitle_textView.getText().length()==0 ) || (mDescription_textView.getText().length()==0))
            Toast.makeText(getActivity(),"لطفا در ورود اطلاعات دقت فرمایید" ,Toast.LENGTH_LONG).show();
        else {
            mTiltle = mTitle_textView.getText().toString();
            mDescription = mDescription_textView.getText().toString();
            if (mTaskType_checkbox.isChecked())
                mTaskType = Task.TaskType.Done;
            else
                mTaskType = Task.TaskType.UNDONE;
        }

    }

}
