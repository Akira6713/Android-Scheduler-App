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

import Activities.AssessmentDetail;
import Models.Assessment;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder>{
    Context context;
    List<Assessment> assessments;
    public AssessmentAdapter(Context context, List<Assessment> assessments) {
        this.context = context;
        this.assessments = assessments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.assessment_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Assessment assessment = assessments.get(i);
        viewHolder.bind(assessment);
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView assessmentDesc;
        TextView assessmentCategory;
        LinearLayout assessmentContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assessmentDesc = itemView.findViewById(R.id.assessRowID);
            assessmentCategory = itemView.findViewById(R.id.assessCategoryID);
            assessmentContainer = itemView.findViewById(R.id.assessmentContainerID);
        }

        public void bind(final Assessment assessment){
            assessmentDesc.setText(assessment.getTitle());
            assessmentCategory.setText(assessment.getCategory());
            assessmentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("selected", (Serializable) assessment);
                    context.startActivity(intent);
                }
            });
        }
    }


}


