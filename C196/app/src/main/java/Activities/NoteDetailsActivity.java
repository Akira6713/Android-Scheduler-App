package Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c196.R;

import Models.Note;

public class NoteDetailsActivity extends AppCompatActivity {
    private TextView note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        this.note = findViewById(R.id.notesDetail);
        Button shareB = findViewById(R.id.shareButton);
        Note note = (Note) getIntent().getSerializableExtra(
                CourseDetail.COURSE_SELECTED);
        this.note.setText(note.getDesc());
        shareB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(intent.EXTRA_SUBJECT, "My Note");
                intent.putExtra(intent.EXTRA_TEXT, NoteDetailsActivity.this.note.getText().toString());
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });
    }
}
