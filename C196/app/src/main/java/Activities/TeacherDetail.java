package Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.c196.R;
import Models.Teacher;

public class TeacherDetail extends AppCompatActivity {
    private TextView teacherName;
    private TextView teacherEmail;
    private TextView teacherPhone;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        teacherName = findViewById(R.id.teacherName);
        teacherEmail = findViewById(R.id.teacherEmail);
        teacherPhone = findViewById(R.id.teacherPhoneNumber);
        teacher = (Teacher) getIntent().getSerializableExtra(
                CourseDetail.COURSE_SELECTED);
        teacherName.setText(teacher.getTeacherPersonName());
        teacherEmail.setText(teacher.getEmailAddress());
        teacherPhone.setText(teacher.getPhoneNumber());
    }
}
