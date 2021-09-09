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

import Activities.TeacherDetail;
import Models.Teacher;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>{

    Context context;
    List<Teacher> teachers;

    public TeacherAdapter(Context context, List<Teacher> teachers) {
        this.context = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacher_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Teacher teacher = teachers.get(i);
        viewHolder.bind(teacher);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView teacherName;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.teacherRowID);
            linearLayout = itemView.findViewById(R.id.teacherRowContainerID);
        }

        public void bind(final Teacher teacher){
            teacherName.setText(teacher.getTeacherPersonName());

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeacherDetail.class);
                    intent.putExtra("selected", (Serializable) teacher);
                    context.startActivity(intent);
                }
            });
        }
    }

}
