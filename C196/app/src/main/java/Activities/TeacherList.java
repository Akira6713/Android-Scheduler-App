package Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.c196.R;

import java.util.ArrayList;

import Adapters.TeacherAdapter;
import Models.MyDataBaseHandler;
import Models.Teacher;

public class TeacherList extends AppCompatActivity {
    MyDataBaseHandler myDataBaseHandler;
    TeacherAdapter teacherAdapter;
    private RecyclerView listView;
    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        listView = findViewById(R.id.teacherListView);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myDataBaseHandler = new MyDataBaseHandler(this);
        courseID = getIntent().getIntExtra(CourseDetail.COURSE_ID, 0);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listView.getContext(), LinearLayout.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        loadTeacherList();
    }

    private void loadTeacherList() {
        ArrayList<Teacher> teacherList = myDataBaseHandler.getTeachersForACourse(courseID);
        teacherAdapter = new TeacherAdapter(this, teacherList);
        teacherAdapter.notifyDataSetChanged();
        listView.setAdapter(teacherAdapter);
    }
}
