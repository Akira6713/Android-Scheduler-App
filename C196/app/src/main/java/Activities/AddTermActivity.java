package Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.c196.R;
import java.util.Calendar;
import Models.MyDataBaseHandler;
import Models.Term;
import UtilityClassess.DateComparator;

public class AddTermActivity extends AppCompatActivity {

    private EditText termNameTextValue;
    private TextView startDate;
    private TextView endDate;
    private Button saveButton;
    private Button cancelButton;
    private int year;
    private int month;
    private int dateOfMonth;
    private int isStart;
    static final int DATE_DIALOG_ID = 2222;

    MyDataBaseHandler myDataBaseHandler;
    DateComparator dateComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        dateComparator = new DateComparator();
        termNameTextValue = findViewById(R.id.TermNameValueID);
        startDate = findViewById(R.id.TermStartDateValueID);
        endDate = findViewById(R.id.TermEndDateValueID);
        saveButton = findViewById(R.id.TermSaveButtonID);
        cancelButton = findViewById(R.id.TermCancelButtonID);
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dateOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        myDataBaseHandler = new MyDataBaseHandler(this);
        dateClickListener();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termNameText = termNameTextValue.getText().toString();
                String dateText1 = startDate.getText().toString();
                String dateText2 = endDate.getText().toString();
                if(termNameText.equals("")){
                    termNameTextValue.setError("Please fill the blank.");
                }else if(dateText1.equals("")){
                    startDate.setError("Please fill the blank.");
                }else if(dateText2.equals("")){
                    endDate.setError("Please fill the blank");
                }else if(!dateComparator.compareDates(dateText2, dateText1)){
                    startDate.setError("Start date cannot be before end date.");
                }
                else if(!termNameText.equals("") && !dateText1.equals("") && !dateText2.equals("") ){
                    myDataBaseHandler.addTerm(new Term(termNameText, dateText1, dateText2));
                    myDataBaseHandler.close();
                    Toast.makeText(AddTermActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddTermActivity.this, "Error in saving Data! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





    private void updateDate(int year, int month, int dateOfMonth){
        String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        if(isStart == 1){
            startDate.setText(aDate);
        }else {
            endDate.setText(aDate);
        }
    }

    public void dateClickListener(){
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isStart = 1;
                createdDateDialog(DATE_DIALOG_ID).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
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

}
