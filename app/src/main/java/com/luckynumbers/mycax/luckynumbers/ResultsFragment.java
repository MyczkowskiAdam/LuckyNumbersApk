package com.luckynumbers.mycax.luckynumbers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ResultsFragment extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results,
                container, false);
        listView = (ListView) view.findViewById(R.id.contentlist);
        DataB database = new DataB(getActivity());
        database.ReadDB(getActivity(), listView);
        return view;
    }

}