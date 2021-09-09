package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.c196.R;

import java.io.Serializable;
import java.util.List;

import Activities.NoteDetailsActivity;
import Models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    Context context;
    List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Note note = notes.get(i);
        viewHolder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteDesc;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteDesc = itemView.findViewById(R.id.noteRowID);
            linearLayout = itemView.findViewById(R.id.noteRowIDContainer);
        }

        public void bind(final Note note){
            noteDesc.setText(note.getDesc());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NoteDetailsActivity.class);
                    intent.putExtra("selected", (Serializable) note);
                    context.startActivity(intent);
                }
            });
        }
    }
}

