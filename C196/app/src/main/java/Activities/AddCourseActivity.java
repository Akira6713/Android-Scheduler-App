package Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import Models.Course;
import Models.MyDataBaseHandler;
import Models.Term;
import UtilityClassess.DateComparator;

public class AddCourseActivity extends AppCompatActivity {


    private EditText courseName;
    private TextView courseStartDate;
    private TextView courseEndDate;
    private Spinner courseStatus;
    private Spinner termList;
    private Button saveCourseButton;
    private Button cancelCourseButton;
    private Button startDateNotificationButton;
    private Button endDateNotificationButton;
    private NotificationManager notificationManager;
    private int year;
    private int month;
    private int dateOfMonth;
    private int isStart;
    static final int DATE_DIALOG_ID = 2222;
    MyDataBaseHandler myDataBaseHandler;
    AlarmManager alarmManager;
    Intent intent;
    PendingIntent broadcast;
    ArrayList<String> dates;
    String aDate;
    DateComparator dateComparator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        dateComparator = new DateComparator();
        courseName = findViewById(R.id.CourseNameValueID);
        courseStartDate = findViewById(R.id.CourseStartDateValueID);
        courseEndDate = findViewById(R.id.CourseEndDateValueID);
        courseStatus = findViewById(R.id.course_status_spinner);
        saveCourseButton = findViewById(R.id.courseSaveButtonID);
        cancelCourseButton = findViewById(R.id.courseCancelButtonID);
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dateOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        myDataBaseHandler = new MyDataBaseHandler(this);
        dates = new ArrayList<>();
        dateClickListener();
        startDateNotificationButton = findViewById(R.id.setAlarmStartDate);
        endDateNotificationButton = findViewById(R.id.setAlarmEndDate);
        termList = findViewById(R.id.course_term_spinner);
        ArrayList<Term> terms = myDataBaseHandler.getAllTerms();
        ArrayList<String> termNames = new ArrayList<>();
        for(int i =0; i< terms.size(); i++){
            termNames.add(terms.get(i).getTermTitle());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, termNames);
        termList.setAdapter(dataAdapter);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        startDateNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = courseStartDate.getText().toString();
                if(!value.equals("")){
                    try {
                        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date1);
                        if(value.equals(aDate)){
                            createAlarm(cal);
                        }else {
                            dates.add(value);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        endDateNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = courseEndDate.getText().toString();
                if(!value.equals("")){
                    try {
                        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date1);
                        if(value.equals(aDate)){
                            createAlarm(cal);
                        }else {
                            dates.add(value);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseNameEntered = courseName.getText().toString();
                String courseStartDateEntered = courseStartDate.getText().toString();
                String courseEndDateEntered = courseEndDate.getText().toString();
                String courseStatusSelected = courseStatus.getSelectedItem().toString();
                String termSelected = termList.getSelectedItem().toString();
                if(courseNameEntered.equals("")){
                    courseName.setError("Please fill the blank");
                }else if(courseStartDateEntered.equals("")){
                    courseStartDate.setError("Please fill the blank");
                }else if(courseEndDateEntered.equals("")){
                    courseEndDate.setError("Please fill the blank");
                }else if(!dateComparator.compareDates(courseEndDateEntered, courseStartDateEntered)){
                    courseStartDate.setError("Start date cannot be before end date");
                }
                else if(courseStatusSelected.equals("")){
                    courseStatus.setFocusable(true);
                }else if(!courseNameEntered.equals("") && !courseStartDateEntered.equals("") &&
                        !courseEndDateEntered.equals("") && !courseStatusSelected.equals("") ){
                    Course course = new Course(courseNameEntered, courseStartDateEntered, courseEndDateEntered, courseStatusSelected, termSelected);
                    myDataBaseHandler.addCourse(course);
                    myDataBaseHandler.close();
                    if(dates.size() > 0){
                        SharedPreferences settings = getApplicationContext().getSharedPreferences("COURSE",
                                MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        Set<String> set = new HashSet<>();
                        set.addAll(dates);
                        editor.putStringSet("courses", set);
                        editor.apply();
                    }
                    Toast.makeText(AddCourseActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddCourseActivity.this, "Error in saving data. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void updateDate(int year, int month, int dateOfMonth){
        String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        if(isStart == 1){
            courseStartDate.setText(aDate);
        }else {
            courseEndDate.setText(aDate);
        }
    }

    public void dateClickListener(){
        courseStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isStart = 1;
                createdDateDialog(DATE_DIALOG_ID).show();
            }
        });
        courseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = 0;
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
            updateDate(year, month, dateOfMonth);
        }
    };
    protected Dialog createdDateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year, month, dateOfMonth);
        }
        return null;
    }
    private void createAlarm(Calendar calendar) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(getApplicationContext(),MyNotificationPublisher.class);
        broadcast = PendingIntent.getBroadcast(this,100, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,now.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+4, broadcast);
    }

}