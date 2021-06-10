package com.example.taskmaster;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskModel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskModel extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "body";
    private static final String ARG_PARAM3 = "state";
    private static final String ARG_PARAM4 = "image";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mBody;
    private String mState;
    private String mImage;

    public TaskModel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param body Parameter 2.
     * @param state Parameter 3.
     * @param image Parameter 4.
     * @return A new instance of fragment TaskModel.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskModel newInstance(String title, String body,String state,String image) {
        TaskModel fragment = new TaskModel();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, body);
        args.putString(ARG_PARAM3, state);
        args.putString(ARG_PARAM4, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            mBody = getArguments().getString(ARG_PARAM2);
            mState = getArguments().getString(ARG_PARAM3);
            mImage = getArguments().getString(ARG_PARAM4);

            // Event listener action

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_model, container, false);
    }
}