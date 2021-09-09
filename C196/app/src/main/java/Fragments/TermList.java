package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.c196.R;

import java.util.ArrayList;

import Adapters.TermAdapter;
import Models.MyDataBaseHandler;
import Models.Term;

public class TermList extends Fragment {

    private RecyclerView termList;
    MyDataBaseHandler myDataBaseHandler;
    TermAdapter termAdapter;
    ArrayList<Term> terms;

    public TermList() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_term_list, container, false);
        myDataBaseHandler = new MyDataBaseHandler(getContext());

        termList = rootView.findViewById(R.id.term_List_fragment);
        termList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        terms = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(termList.getContext(), LinearLayout.VERTICAL);
        termList.addItemDecoration(dividerItemDecoration);
        loadTermList();
        return rootView;
    }


    private void loadTermList() {
        terms = myDataBaseHandler.getAllTerms();
        termAdapter = new TermAdapter(getContext(), terms);
        termAdapter.notifyDataSetChanged();
        termList.setAdapter(termAdapter);

    }
}

