package Activities;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.example.c196.R;
import java.util.ArrayList;
import Adapters.AssessmentAdapter;
import Models.Assessment;
import Models.MyDataBaseHandler;

public class AssessmentList extends AppCompatActivity {

    private RecyclerView listView;
    MyDataBaseHandler myDataBaseHandler;
    private int courseID;
    AssessmentAdapter assessmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        listView = findViewById(R.id.assessListView);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myDataBaseHandler = new MyDataBaseHandler(this);
        courseID = getIntent().getIntExtra(CourseDetail.COURSE_ID, 0);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listView.getContext(), LinearLayout.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        loadAssessList();

    }

    private void loadAssessList() {
        if(getIntent().getIntExtra("updatedID", Integer.MAX_VALUE) != Integer.MAX_VALUE){
            courseID = getIntent().getIntExtra("updatedID", Integer.MAX_VALUE);
        }
        ArrayList<Assessment> assessList = myDataBaseHandler.getAssesment(courseID);
        assessmentAdapter = new AssessmentAdapter(this, assessList);
        assessmentAdapter.notifyDataSetChanged();
        listView.setAdapter(assessmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAssessList();
    }
}
