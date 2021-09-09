package Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import Models.Assessment;
import Models.Course;
import Models.Note;
import Models.MyDataBaseHandler;
import Models.Teacher;
import Models.Term;

public class CourseDetail extends AppCompatActivity {

    private EditText courseName;
    private EditText courseStarteDate;
    private EditText courseEndDate;
    private EditText courseStatus;
    private EditText courseTerm;
    private Spinner courseStatusSpinner;
    private Spinner semesterSpinner;
    private Button cancelButton;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private Button addteacherButton;
    private Button addAssessmentButton;
    private Button addNoteButton;
    Course course;
    MyDataBaseHandler myDataBaseHandler;
    private NotificationManager notificationManager;
    int id;
    final static String COURSE_SELECTED = "selected";
    final static String COURSE_ID = "courseID";
    private int year;
    private int month;
    private int dateOfMonth;
    private int isStart;
    String aDate;
    static final int DATE_DIALOG_ID = 2222;
    static final int DATE_DIALOG_ID_INSIDE = 1111;
    EditText editText2;
    public static boolean COME_FROM = false;
    AlarmManager alarmManager;
    Intent intent;
    PendingIntent broadcast;
    ArrayList<String> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_course_view);

        courseName = findViewById(R.id.dCourseNameValueID);
        courseStarteDate = findViewById(R.id.dCourseStartDateValueID);
        courseEndDate = findViewById(R.id.dCourseEndDateValueID);
        courseStatus = findViewById(R.id.dCourseStatus);
        cancelButton = findViewById(R.id.cancelCourseDetailButtonID);
        editButton = findViewById(R.id.aeditButtonID);
        deleteButton = findViewById(R.id.deleteButtonID);
        courseStatusSpinner = findViewById(R.id.dcourse_status_spinner);
        semesterSpinner = findViewById(R.id.dcourse_term_spinner);
        saveButton = findViewById(R.id.saveCourseDetailButtonID);
        addteacherButton = findViewById(R.id.addTeacherInfoButtonID);
        addAssessmentButton = findViewById(R.id.addAssessmentInfoButtonID);
        courseTerm = findViewById(R.id.dCourseSemester);
        addNoteButton = findViewById(R.id.addNoteButtonID);
        cancelButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dateOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dates = new ArrayList<>();
        courseName.setEnabled(false);
        courseStarteDate.setEnabled(false);
        courseEndDate.setEnabled(false);
        courseStatus.setEnabled(false);
        courseTerm.setEnabled(false);
        aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        addNoteButton.setText("List of Notes");
        addAssessmentButton.setText("List of Assessments");
        addteacherButton.setText("List of Teachers");
        myDataBaseHandler = new MyDataBaseHandler(this);
        course = (Course) getIntent().getSerializableExtra(COURSE_SELECTED);
        id = course.getId();
        makeButtonsActive();
        courseName.setText(course.getName());
        courseStarteDate.setText(course.getStartDate());
        courseEndDate.setText(course.getEndDate());
        courseStatus.setText(course.getCourseStatus());
        courseTerm.setText(course.getCourseTerm());
        semesterSpinner.setVisibility(View.GONE);
        courseStatusSpinner.setVisibility(View.GONE);
        ArrayList<Term> terms = myDataBaseHandler.getAllTerms();
        ArrayList<String> termNames = new ArrayList<>();
        for(int i =0; i< terms.size(); i++){
            termNames.add(terms.get(i).getTermTitle());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, termNames);
        semesterSpinner.setAdapter(dataAdapter);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDataBaseHandler.deleteCourse(course);
                myDataBaseHandler.close();
                Toast.makeText(CourseDetail.this, "Course deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CourseDetail.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
                addNoteButton.setText("Add A Note");
                addNoteButton.setEnabled(true);
                addAssessmentButton.setText("Add An Assessment");
                addAssessmentButton.setEnabled(true);
                addteacherButton.setText("Add A Teacher");
                addteacherButton.setEnabled(true);
                makeViewsEditable(courseName);
                makeViewsEditable(courseStarteDate);
                makeViewsEditable(courseEndDate);
                courseStatus.setVisibility(View.GONE);
                courseTerm.setVisibility(View.GONE);
                courseStatusSpinner.setVisibility(View.VISIBLE);
                semesterSpinner.setVisibility(View.VISIBLE);
                dateClickListener();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
                addNoteButton.setText("List of Notes");
                addAssessmentButton.setText("List of Assessments");
                addteacherButton.setText("List of Teachers");
                String courseNameValue = courseName.getText().toString();
                String courseStartDateValue = courseStarteDate.getText().toString();
                String courseEndDateValue = courseEndDate.getText().toString();
                if(courseNameValue.equals("") || courseNameValue.equals(course.getName())){
                    courseName.setText(course.getName());
                }
                if(courseStartDateValue.equals("") || courseStartDateValue.equals(course.getStartDate())){
                    courseStarteDate.setText(course.getStartDate());
                }
                if(courseEndDateValue.equals("") || courseEndDateValue.equals(course.getEndDate())){
                    courseEndDate.setText(course.getEndDate());
                }
                makeButtonsActive();
                courseName.setEnabled(false);
                courseStarteDate.setEnabled(false);
                courseEndDate.setEnabled(false);
                courseStatus.setVisibility(View.VISIBLE);
                courseTerm.setVisibility(View.VISIBLE);
                semesterSpinner.setVisibility(View.GONE);
                courseStatusSpinner.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseNameValue = courseName.getText().toString();
                String courseStartDateValue = courseStarteDate.getText().toString();
                String courseEndDateValue = courseEndDate.getText().toString();
                if(courseNameValue.equals("")){
                    courseName.setError("Course name cannot be empty.");
                }else if(courseStartDateValue.equals("")){
                    courseStarteDate.setError("Course start date cannot be empty.");
                }else if(courseEndDateValue.equals("")){
                    courseEndDate.setError("Course end date cannot be empty.");
                }else if(!compareDates(courseEndDateValue, courseStartDateValue)){
                    courseStarteDate.setError("Start date cannot be before end date");
                }
                else{
                    Course course1 = new Course(courseNameValue, courseStartDateValue,
                            courseEndDateValue, courseStatus.getText().toString(), courseTerm.getText().toString());
                    myDataBaseHandler.updateCourse(id , course1);
                    myDataBaseHandler.close();
                    Toast.makeText(CourseDetail.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    cancelButton.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);
                    courseName.setEnabled(false);
                    courseStarteDate.setEnabled(false);
                    courseEndDate.setEnabled(false);
                    courseStatus.setVisibility(View.VISIBLE);
                    courseTerm.setVisibility(View.VISIBLE);
                    semesterSpinner.setVisibility(View.GONE);
                    courseStatusSpinner.setVisibility(View.GONE);
                    addAssessmentButton.setText("List of Assessments");
                    addteacherButton.setText("List of Teachers");
                    addNoteButton.setText("List of Notes");
                    makeButtonsActive();
                }

            }
        });

        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = addAssessmentButton.getText().toString();
                if(buttonText.equals("List of Assessments")){
                    Intent intent = new Intent(CourseDetail.this, AssessmentList.class);
                    intent.putExtra(COURSE_ID, course.getId());
                    startActivity(intent);
                }else{
                    showAssesmentInfoDialog();
                }

            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = addNoteButton.getText().toString();
                if(buttonText.equals("List of Notes")){
                    Intent intent = new Intent(CourseDetail.this, NoteList.class);
                    intent.putExtra(COURSE_ID, course.getId());
                    startActivity(intent);
                }else{
                    showAddNoteDialogDialog();
                }


            }
        });

        addteacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = addteacherButton.getText().toString();
                if(buttonText.equals("List of Teachers")){
                    Intent intent = new Intent(CourseDetail.this, TeacherList.class);
                    intent.putExtra(COURSE_ID, course.getId());
                    startActivity(intent);
                }else{
                    showTeacherInfoDialog();
                }
            }
        });

    }

    private void makeButtonsActive() {
        int teacherCount = myDataBaseHandler.getTeachersForACourseCount(course.getId());
        int assessmentCount = myDataBaseHandler.getAssesmentForACourseCount(course.getId());
        int noteCount = myDataBaseHandler.getNoteForACourseCount(course.getId());
        if(teacherCount == 0){
            addteacherButton.setEnabled(false);
        }
        if(assessmentCount == 0){
            addAssessmentButton.setEnabled(false);
        }
        if(noteCount == 0){
            addNoteButton.setEnabled(false);
        }
    }

    public void showAddNoteDialogDialog() {
        LinearLayout linearLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (20 * scale + 0.5f);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        final TextView textView1 = new TextView(this);
        textView1.setText("Note Description");
        linearLayout.addView(textView1);

        final EditText editText1 = new EditText(this);
        linearLayout.addView(editText1);


        AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle).setTitle("Course Assessment").setView(linearLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value =editText1.getText().toString();
                        if(value.equals("")){
                            editText1.setError("Please fill the blank");
                        }else if(!value.equals("")){
                            Note note = new Note(value, course.getId());
                            myDataBaseHandler.addNote(note);
                            myDataBaseHandler.close();
                            Toast.makeText(CourseDetail.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CourseDetail.this, "Error in saving data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    public void showAssesmentInfoDialog() {
        LinearLayout linearLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (20 * scale + 0.5f);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        final TextView textView = new TextView(this);
        textView.setText("Assessment Title");
        linearLayout.addView(textView);

        final EditText editText = new EditText(this);
        linearLayout.addView(editText);

        final TextView textView1 = new TextView(this);
        textView1.setText("Assessment Description");
        linearLayout.addView(textView1);

        final EditText editText1 = new EditText(this);
        linearLayout.addView(editText1);

        final TextView textView2 = new TextView(this);
        textView2.setText("Completion Date");
        linearLayout.addView(textView2);

        editText2 = new EditText(this);
        linearLayout.addView(editText2);

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDateDialog(DATE_DIALOG_ID_INSIDE).show();
                String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
                editText2.setText(aDate);
            }
        });
        final String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        editText2.setText(aDate);

        final Button notifButton = new Button(this);
        notifButton.setText("Set Notification");
        linearLayout.addView(notifButton);

        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText2.getText().toString();
                if(value != null){
                    Date date1 = null;
                    try {
                        date1 = new SimpleDateFormat("MM/dd/yyyy").parse(value);
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
                }else{
                    editText2.setError("Please choose a date first.");
                }
            }
        });

        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        final RadioButton radioButton1 = new RadioButton(this);
        radioButton1.setText("Objective");
        radioGroup.addView(radioButton1);

        final RadioButton radioButton2 = new RadioButton(this);
        radioButton2.setText("Performance");
        radioGroup.addView(radioButton2);
        linearLayout.addView(radioGroup);


        AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle).setTitle("Course Assessment").setView(linearLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value =editText1.getText().toString();
                        String title = editText.getText().toString();
                        String date = editText2.getText().toString();
                        if(value.equals("")){
                            editText1.setError("Please fill the blank");
                        }else if(!value.equals("")){
                            String selected;
                            if(radioButton1.isChecked()){
                                selected = "Objective";
                            }else{
                                selected = "Performance";
                            }
                            Assessment assesment = new Assessment(value, course.getId(), selected, title, date);
                            myDataBaseHandler.addAssesment(assesment);
                            myDataBaseHandler.close();
                            Toast.makeText(CourseDetail.this, "Assessment added.", Toast.LENGTH_SHORT).show();
                            if(dates.size() > 0){
                                SharedPreferences settings = getApplicationContext().getSharedPreferences("ASSESSMENT",
                                        MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                Set<String> set = new HashSet<>();
                                set.addAll(dates);
                                editor.putStringSet("assessment", set);
                                editor.apply();
                            }
                        }else{

                            Toast.makeText(CourseDetail.this, "Error in saving data. Please try again.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    public void showTeacherInfoDialog() {
        LinearLayout linearLayout = new LinearLayout(this);
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (20 * scale + 0.5f);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        final TextView textView1 = new TextView(this);
        textView1.setText("Teacher Name");
        linearLayout.addView(textView1);

        final EditText editText1 = new EditText(this);
        linearLayout.addView(editText1);

        final TextView textView2 = new TextView(this);
        textView2.setText("Phone Number");
        linearLayout.addView(textView2);

        final EditText editText2 = new EditText(this);
        linearLayout.addView(editText2);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);

        final TextView textView3 = new TextView(this);
        textView3.setText("Email Address");

        linearLayout.addView(textView3);

        final EditText editText3 = new EditText(this);
        linearLayout.addView(editText3);

        editText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle).setTitle("Course Assessment").setView(linearLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String namevalue = editText1.getText().toString();
                        String phoneValue = editText2.getText().toString();
                        String emailValue = editText3.getText().toString();
                        if (namevalue.equals("")) {
                            editText1.setError("Please fill the blank");
                        } else if (phoneValue.equals("")) {
                            editText2.setError("Please fill the blank");
                        } else if (emailValue.equals("")) {
                            editText3.setError("Please fill the blank");
                        } else if (!namevalue.equals("") && !phoneValue.equals("") && !emailValue.equals("")) {
                            Teacher teacher = new Teacher(namevalue, phoneValue, emailValue, course.getId());
                            myDataBaseHandler.addTeacher(teacher);
                            myDataBaseHandler.close();
                            Toast.makeText(CourseDetail.this, "Teacher added.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CourseDetail.this, "Error in saving data. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    private void makeViewsEditable(EditText editText) {
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
    }

    public void dateClickListener(){

        courseStarteDate.setOnClickListener(new View.OnClickListener(){
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

    private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int myear, int mmonth, int mmonthDay) {
            year = myear;
            month = mmonth;
            dateOfMonth = mmonthDay;
            String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
            editText2.setText(aDate);
        }
    };

    protected Dialog createdDateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year, month, dateOfMonth);
            case DATE_DIALOG_ID_INSIDE:
                return new DatePickerDialog(this, datePickerListener1, year, month, dateOfMonth);
        }
        return null;
    }

    private void updateDate(int year, int month, int dateOfMonth){
        String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        if(isStart == 1){
            courseStarteDate.setText(aDate);
        }else {
            courseEndDate.setText(aDate);
        }
    }



    private boolean compareDates(String endDate, String startDate) {
        String[] startDateSplitted = startDate.split("/");
        String[] endDateSplitted = endDate.split("/");
        int month1 = Integer.valueOf(startDateSplitted[0]);
        int month2 = Integer.valueOf(endDateSplitted[0]);
        int day1 = Integer.valueOf(startDateSplitted[1]);
        int day2 = Integer.valueOf(endDateSplitted[1]);
        int year1 = Integer.valueOf(startDateSplitted[2]);
        int year2 = Integer.valueOf(endDateSplitted[2]);
        if((year1 < year2) || (year1 == year2) && (month2 > month1) || (year1 == year2) && (month2 == month1) && (day2 > day1)){
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeButtonsActive();
    }


    private void createAlarm(Calendar calendar) {
        COME_FROM = true;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(getApplicationContext(),MyNotificationPublisher.class);
        broadcast = PendingIntent.getBroadcast(this,100, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,now.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+4, broadcast);
    }
}
