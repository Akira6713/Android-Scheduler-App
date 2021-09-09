package Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.c196.R;
import java.util.Calendar;
import Models.Assessment;
import Models.MyDataBaseHandler;
import UtilityClassess.DateComparator;

public class AssessmentDetail extends AppCompatActivity {
    EditText title;
    EditText desc;
    EditText date;
    EditText category;
    Spinner categorySpinner;
    Assessment assessment;
    MyDataBaseHandler myDataBaseHandler;
    int id;
    private int year;
    private int month;
    private int dateOfMonth;
    static final int DATE_DIALOG_ID = 2222;
    DateComparator dateComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assesment_view);
        dateComparator = new DateComparator();
        final Button save = findViewById(R.id.saveButtonA);
        final Button cancel = findViewById(R.id.cancelButtonA);
        final Button delete = findViewById(R.id.deleteButtonA);
        final Button edit = findViewById(R.id.editButtonA);
        title = findViewById(R.id.dTitleIDText);
        desc = findViewById(R.id.dDescIDText);
        date = findViewById(R.id.dDateIDText);
        category = findViewById(R.id.dCategoryIDText);
        categorySpinner = findViewById(R.id.dassessment_category_spinner);
        assessment = (Assessment) getIntent().getSerializableExtra(CourseDetail.COURSE_SELECTED);
        id = assessment.getId();
        title.setText(assessment.getTitle());
        desc.setText(assessment.getDescription());
        date.setText(assessment.getDate());
        category.setText(assessment.getCategory());
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dateOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        title.setEnabled(false);
        desc.setEnabled(false);
        date.setEnabled(false);
        category.setEnabled(false);
        save.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        categorySpinner.setVisibility(View.GONE);
        myDataBaseHandler = new MyDataBaseHandler(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleValue = title.getText().toString();
                String descValue = desc.getText().toString();
                String dateValue = date.getText().toString();
                String categoryValue = category.getText().toString();
                if (titleValue.equals("")) {
                    title.setError("Assessment title cannot be empty.");
                } else if (descValue.equals("")) {
                    desc.setError("Assessment description cannot be empty.");
                } else if (dateValue.equals("")) {
                    date.setError("Date cannot be empty.");
                } else if (categoryValue.equals("")) {
                    date.setError("Category cannot be empty.");
                } else {
                    Assessment assessment1 = new Assessment(descValue, assessment.getAssessmentCourse(),
                            categoryValue, titleValue, dateValue);
                    Log.v("iddd", String.valueOf(id));
                    myDataBaseHandler.updateAssessment(id, assessment1);
                    myDataBaseHandler.close();
                    Toast.makeText(AssessmentDetail.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    cancel.setVisibility(View.GONE);
                    save.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    categorySpinner.setVisibility(View.GONE);
                    category.setVisibility(View.VISIBLE);
                    desc.setEnabled(false);
                    date.setEnabled(false);
                    category.setEnabled(false);
                    title.setEnabled(false);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                desc.setEnabled(false);
                date.setEnabled(false);
                category.setVisibility(View.VISIBLE);
                category.setEnabled(false);
                categorySpinner.setVisibility(View.GONE);
                title.setEnabled(false);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataBaseHandler.deleteAssessment(assessment);
                Log.v("number of Aass", String.valueOf(myDataBaseHandler.getAssesmentForACourseCount(assessment.getAssessmentCourse())));
                myDataBaseHandler.close();
                Toast.makeText(AssessmentDetail.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AssessmentDetail.this, AssessmentList.class);
                intent.putExtra("updatedID", assessment.getAssessmentCourse());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                desc.setEnabled(true);
                date.setEnabled(true);
                category.setVisibility(View.GONE);
                categorySpinner.setVisibility(View.VISIBLE);
                title.setEnabled(true);
                dateClickListener();
            }
        });
    }

    public void dateClickListener(){

        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                createdDateDialog(DATE_DIALOG_ID).show();
            }
        });

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int myear, int mmonth, int mmonthDay) {
            year = myear;
            month = mmonth;
            dateOfMonth = mmonthDay;
            date.setText(dateComparator.updateDate(year, month, dateOfMonth));
        }
    };



    protected Dialog createdDateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year, month, dateOfMonth);
        }
        return null;
    }



}
