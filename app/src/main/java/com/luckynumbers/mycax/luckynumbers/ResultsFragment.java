package com.luckynumbers.mycax.luckynumbers;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class ResultsFragment extends Fragment implements ListView.OnItemLongClickListener {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results,
                container, false);
        listView = (ListView) view.findViewById(R.id.contentlist);
        DataB database = new DataB(getActivity());
        database.ReadDB(getActivity(), listView);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        listView.setEmptyView(imageView);
        listView.setOnItemLongClickListener(this);
        return view;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, final long id) {
        new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setTitle("Delete result")
                .setMessage("Are you sure you want delete this result?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataB database = new DataB(getActivity());
                        database.deleteEntry(id);
                        database.ReadDB(getActivity(), listView);
                    }

                })
                .setNegativeButton("No", null)
                .show();
        return true;
    }

}