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

import java.util.List;

import Activities.CourseDetail;
import Models.Course;
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    Context context;
    List<Course> courses;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }
    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder viewHolder, int i) {
        Course course = courses.get(i);
        viewHolder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView sDate;
        TextView eDate;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.courseNameRowID);
            sDate = itemView.findViewById(R.id.courseStartDateRowID);
            eDate = itemView.findViewById(R.id.courseEndDateRowID);
            linearLayout = itemView.findViewById(R.id.courseRowIDContainer);
        }

        public void bind(final Course course){
            name.setText(course.getName());
            sDate.setText(course.getStartDate());
            eDate.setText(course.getEndDate());

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("selected", course);
                    context.startActivity(intent);
                }
            });
        }

    }
}

