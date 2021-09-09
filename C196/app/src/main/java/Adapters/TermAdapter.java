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

import Activities.TermDetail;
import Models.Term;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder>{
    Context context;
    List<Term> terms;
    public TermAdapter(Context context, List<Term> terms) {
        this.context = context;
        this.terms = terms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.term_row, viewGroup, false);
        return new TermAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Term term = terms.get(i);
        viewHolder.bind(term);
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView sDate;
        TextView eDate;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.termNameRowID);
            sDate = itemView.findViewById(R.id.termStartDateRowID);
            eDate = itemView.findViewById(R.id.termEndDateRowID);
            linearLayout = itemView.findViewById(R.id.termRowIDContainer);
        }

        public void bind(final Term term){
            name.setText(term.getTermTitle());
            sDate.setText(term.getStartDate());
            eDate.setText(term.getEndDate());



            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("selected", (Serializable) term);
                    context.startActivity(intent);
                }
            });
        }
    }
}

