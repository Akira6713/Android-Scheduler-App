package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.c196.R;

import java.util.ArrayList;

import Adapters.CourseAdapter;
import Models.Course;
import Models.MyDataBaseHandler;

public class CourseList extends Fragment {

    private RecyclerView listView;
    private CourseAdapter courseAdapter;
    private MyDataBaseHandler myDataBaseHandler;
    ArrayList<Course> courses;
    public CourseList() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);

        listView = rootView.findViewById(R.id.courseList);
        listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myDataBaseHandler = new MyDataBaseHandler(getContext());
        courses = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listView.getContext(), LinearLayout.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        loadCourseList();

        return rootView;
    }

    private void loadCourseList() {
        courses = myDataBaseHandler.getAllCourses();
        courseAdapter = new CourseAdapter(getContext(), courses);
        courseAdapter.notifyDataSetChanged();
        listView.setAdapter(courseAdapter);

    }


}
