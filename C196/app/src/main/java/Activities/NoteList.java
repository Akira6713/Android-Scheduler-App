package Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.c196.R;
import java.util.ArrayList;
import Adapters.NoteAdapter;
import Models.Note;
import Models.MyDataBaseHandler;


public class NoteList extends AppCompatActivity {
    MyDataBaseHandler myDataBaseHandler;
    NoteAdapter noteAdapter;
    RecyclerView listView;
    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_notes);

        listView = findViewById(R.id.noteListView);
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myDataBaseHandler = new MyDataBaseHandler(this);
        courseID = getIntent().getIntExtra(CourseDetail.COURSE_ID, 0);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listView.getContext(), LinearLayout.VERTICAL);
        listView.addItemDecoration(dividerItemDecoration);
        loadNoteList();
    }

    private void loadNoteList() {
        ArrayList<Note> noteList = myDataBaseHandler.getNote(courseID);
        noteAdapter = new NoteAdapter(this, noteList);
        noteAdapter.notifyDataSetChanged();
        listView.setAdapter(noteAdapter);
    }
}
