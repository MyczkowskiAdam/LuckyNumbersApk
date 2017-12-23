package com.luckynumbers.mycax.luckynumbers;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class ResultsFragment extends Fragment implements DatabaseAdapter.ResultsCallBack{

    DataBHelper database;
    RecyclerView recyclerView;
    DatabaseAdapter recycler;
    public List<DataModel> datamodel;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results,
                container, false);
        datamodel = new ArrayList<DataModel>();
        recyclerView = (RecyclerView) view.findViewById(R.id.app_recycle_view);
        database = new DataBHelper(getActivity());
        datamodel =  database.readDB();
        recycler = new DatabaseAdapter(datamodel, getActivity());
        recyclerView.setLayoutManager(PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean("app_enable_grid",false) ?
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) :
                new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new FadeInUpAnimator());
        ScaleInAnimationAdapter adapterAnim = new ScaleInAnimationAdapter(recycler);
        adapterAnim.setInterpolator(new OvershootInterpolator());
        adapterAnim.setFirstOnly(false);
        recyclerView.setAdapter(adapterAnim);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        recycler.setEmptyDrawableListener(this);
        getEmpty();
        database.close();
        return view;
    }

    @Override
    public void getEmpty() {
        if (datamodel.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
    }
}