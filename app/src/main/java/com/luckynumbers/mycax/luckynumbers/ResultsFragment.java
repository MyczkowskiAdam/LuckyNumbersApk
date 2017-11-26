package com.luckynumbers.mycax.luckynumbers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    DataBHelper database;
    RecyclerView recyclerView;
    DatabaseAdapter recycler;
    public List<DataModel> datamodel;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results,
                container, false);
        datamodel =new ArrayList<DataModel>();
        recyclerView = (RecyclerView) view.findViewById(R.id.app_recycle_view);
        database = new DataBHelper(getActivity());
        datamodel =  database.readDB();
        recycler = new DatabaseAdapter(datamodel, getActivity());
        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if (datamodel.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
        database.close();
        return view;
    }
}