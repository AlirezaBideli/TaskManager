package com.example.admin.software_1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    //Widgets variables
    private TextView mTitle_textView;
    private TextView mDescription_textView;
    private CheckBox mTaskType_checkbox;
    private Button mRemove_button;
    private Button mEdit_button;
    //simple variables
    private String mTiltle;
    private String mDescription;
    private Task.TaskType mTaskType;

    public static final String Tag_tabPosition="taskPosition";
    public static final String Tag_taskType="taskPosition";


    public static Fragment newInstance(int tabPosition,Task.TaskType taskType)
    {
        Bundle bundle=new Bundle();
        bundle.putSerializable(Tag_taskType,taskType);
        bundle.putInt(Tag_tabPosition,tabPosition);
        TaskListFragment fragment=new TaskListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit, container, false);
        initialization(view);


        return view;
    }

    private void initialization(View view) {
        mTitle_textView = view.findViewById(R.id.title_textView_Editfragment);
        mDescription_textView = view.findViewById(R.id.description_textView_Editfragment);
        mTaskType_checkbox = view.findViewById(R.id.taskType_checkbox_Editfragment);
        mRemove_button = view.findViewById(R.id.remove_button_EditFragment);
        mEdit_button = view.findViewById(R.id.edit_container_TaskManagerActivity);

    }

}
