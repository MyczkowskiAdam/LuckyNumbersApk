package com.luckynumbers.mycax.luckynumbers;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.luckynumbers.mycax.luckynumbers.datamodels.DataModelDB;
import com.luckynumbers.mycax.luckynumbers.datamodels.DataModelSelected;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class ResultsFragment extends Fragment implements DatabaseAdapter.ResultsCallBack, DatabaseAdapter.FABCallBack, FloatingActionButton.OnClickListener{

    private RecyclerView recyclerView;
    private List<DataModelDB> datamodel;
    private ImageView imageView;
    private DatabaseAdapter recycler;
    private boolean isOpen = false;
    private FloatingActionButton floatingActionButton;
    private Animation fab_open, fab_close;
    private List<DataModelSelected> selectedArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results,
                container, false);
        floatingActionButton = view.findViewById(R.id.fabDelete);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);

        datamodel = new ArrayList<>();
        recyclerView = view.findViewById(R.id.app_recycle_view);
        DataBHelper database = new DataBHelper(getActivity());
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

        imageView = view.findViewById(R.id.imageView);
        recycler.setEmptyDrawableListener(this);
        getEmpty();
        recycler.setFABListener(this);
        floatingActionButton.setOnClickListener(this);
        database.close();
        return view;
    }

    @Override
    public void onClick(final View view) {
        new AlertDialog.Builder(getActivity())
                .setIcon(!PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getBoolean("app_dark_theme", false) ? R.drawable.ic_warning_black_24dp : R.drawable.ic_warning_white_24dp)
                .setTitle("Delete result")
                .setMessage("Are you sure you want delete these results?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Collections.sort(selectedArrayList, new Comparator<DataModelSelected>() {
                            @Override
                            public int compare(DataModelSelected dataModelSelected, DataModelSelected t1) {
                                return dataModelSelected.getCurrentAdapterPosition().compareTo(t1.getCurrentAdapterPosition());
                            }
                        });
                        for (int i = 0; i < selectedArrayList.size();i++) {
                            recycler.removeEntry(selectedArrayList.get(i).getSelectedId(), Integer.parseInt(selectedArrayList.get(i).getCurrentAdapterPosition()));
                            for (int j = i+1; j < selectedArrayList.size();j++) {
                                if (Integer.parseInt(selectedArrayList.get(j).getCurrentAdapterPosition()) != 0) {
                                    selectedArrayList.get(j).setCurrentAdapterPosition(String.valueOf((Integer.parseInt(selectedArrayList.get(j).getCurrentAdapterPosition()))-1));
                                }
                            }
                        }
                        recycler.clearLists();
                        floatingActionButton.setVisibility(View.INVISIBLE);
                        floatingActionButton.startAnimation(fab_close);
                        Toast.makeText(getActivity(), "Results successfully deleted", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void getEmpty() {
        if (datamodel.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.startAnimation(fab_open);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setFAB(List<DataModelSelected> dataModelSelectedList) {
        this.selectedArrayList = dataModelSelectedList;

        if (isOpen && selectedArrayList.size() < 1) {
            floatingActionButton.setVisibility(View.INVISIBLE);
            floatingActionButton.startAnimation(fab_close);
            floatingActionButton.setClickable(false);
            isOpen = false;
        }
        else if (!isOpen && selectedArrayList.size() > 0) {
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.startAnimation(fab_open);
            floatingActionButton.setClickable(true);
            isOpen = true;
        }
    }
}