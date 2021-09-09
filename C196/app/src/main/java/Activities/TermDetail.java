package Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;

import java.util.ArrayList;

import Models.Course;
import Models.MyDataBaseHandler;
import Models.Term;

public class TermDetail extends AppCompatActivity {

    private TextView name;
    private TextView startDate;
    private TextView endDate;
    private Button listOfCourses;
    private Button deleteButton;
    MyDataBaseHandler myDataBaseHandler;
    private Term term;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        name = findViewById(R.id.DTermNameValueID);
        startDate = findViewById(R.id.DTermStartDateValueID);
        endDate = findViewById(R.id.DTermEndDateValueID);
        listOfCourses = findViewById(R.id.listOFCourses);
        deleteButton = findViewById(R.id.termDeleteButton);
        Intent intent = getIntent();
        myDataBaseHandler = new MyDataBaseHandler(this);

        term = (Term) intent.getSerializableExtra(

                CourseDetail.COURSE_SELECTED);
        name.setText(term.getTermTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());

        listOfCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCoursesDialog(term.getTermTitle());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = myDataBaseHandler.getCoursesCountForATerm(term.getTermTitle());
                if(number != 0){
                    Toast.makeText(TermDetail.this, "Error! There is a course attached to the semester.", Toast.LENGTH_SHORT).show();

                }else{
                    myDataBaseHandler.deleteTerm(term);
                    myDataBaseHandler.close();
                    Toast.makeText(TermDetail.this, "The term has been deleted.", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(TermDetail.this, MainActivity.class);
                    startActivity(intent1);
                }
            }
        });
    }

    private void showCoursesDialog(String termTitle) {
        LinearLayout linearLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (20 * scale + 0.5f);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        final ListView listView = new ListView(this);
        linearLayout.addView(listView);
        ArrayList<Course> courseList = myDataBaseHandler.getCoursesForATerm(termTitle);
        ArrayList<String> courseNames = new ArrayList<>();
        for(int i =0; i< courseList.size(); i++){
            courseNames.add(courseList.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseNames);
        listView.setAdapter(dataAdapter);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Courses");
        dialog.setCancelable(true);
        AlertDialog alertDialog = dialog.create();
        alertDialog.setView(linearLayout);
        alertDialog.show();
    }
}
