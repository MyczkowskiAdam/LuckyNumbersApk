package com.luckynumbers.mycax.luckynumbers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luckynumbers.mycax.luckynumbers.datamodels.DataModelDB;
import com.luckynumbers.mycax.luckynumbers.datamodels.DataModelSelected;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.Myholder> {
    private final List<DataModelDB> dataModelDBArrayList;
    private final Context context;
    private ResultsCallBack resultsCallBack;
    private FABCallBack fabCallBack;
    private final List<DataModelSelected> selectedArrayList = new ArrayList<>();

    public DatabaseAdapter(List<DataModelDB> dataModelDBArrayList, Context context) {
        this.dataModelDBArrayList = dataModelDBArrayList;
        this.context = context;
    }

    class Myholder extends RecyclerView.ViewHolder implements CardView.OnLongClickListener {
        final TextView name;
        final TextView result;
        final FloatingActionButton fabChecked;
        private final Animation fab_open;
        private final Animation fab_close;
        String id;

        public boolean listContains(List<DataModelSelected> list, String adapterPosition ) {
            for (int i = 0; i < list.size();i++) {
                if (list.get(i).getCurrentAdapterPosition().equals(adapterPosition)) return true;
            }
            return false;
        }

        public int indexOfList(List<DataModelSelected> list, String adapterPosition) {
            for (int i = 0; i < list.size();i++) {
                if (list.get(i).getCurrentAdapterPosition().equals(adapterPosition)) return i;
            }
            return -1;
        }

        public Myholder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.card_name);
            result = itemView.findViewById(R.id.card_result);
            fabChecked = itemView.findViewById(R.id.fabChecked);
            fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(context, R.anim.fab_close);

            CardView cardView = itemView.findViewById(R.id.card_view);
            if (PreferenceManager.getDefaultSharedPreferences(context)
                    .getBoolean("app_enable_grid",false)) {
                cardView.setLayoutParams(new RelativeLayout.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, 200));
            }
            cardView.setOnLongClickListener(this);
            cardView.setOnClickListener(new CardView.OnClickListener() {
                public void onClick(View v) {
                    String adapterPositions = String.valueOf(getAdapterPosition());
                    if (listContains(selectedArrayList, adapterPositions)) {
                        selectedArrayList.remove(indexOfList(selectedArrayList, adapterPositions));
                        fabChecked.setVisibility(View.INVISIBLE);
                        fabChecked.startAnimation(fab_close);
                    }
                    else {
                        DataModelSelected dataModelSelected = new DataModelSelected();
                        dataModelSelected.setCurrentAdapterPosition(adapterPositions);
                        dataModelSelected.setSelectedId(id);
                        selectedArrayList.add(dataModelSelected);
                        fabChecked.setVisibility(View.VISIBLE);
                        fabChecked.startAnimation(fab_open);
                    }
                    fabCallBack.setFAB(selectedArrayList);
                }
            });
        }

        @Override
        public boolean onLongClick(final View v) {
            new AlertDialog.Builder(context)
                    .setIcon(!PreferenceManager.getDefaultSharedPreferences(context)
                            .getBoolean("app_dark_theme", false) ? R.drawable.ic_warning_black_24dp : R.drawable.ic_warning_white_24dp)
                    .setTitle("Delete result")
                    .setMessage("Are you sure you want delete this result?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeEntry(id, getAdapterPosition());
                            Toast.makeText(context, "Result successfully deleted", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        DataModelDB dataModelDB = dataModelDBArrayList.get(position);
        holder.name.setText(dataModelDB.getName());
        holder.result.setText(dataModelDB.getResult());
        holder.id = dataModelDB.getId();
    }

    @Override
    public int getItemCount() {
        return dataModelDBArrayList.size();
    }

    public interface ResultsCallBack {
        void getEmpty();
    }

    public void setEmptyDrawableListener(ResultsCallBack callBack) {
        this.resultsCallBack = callBack;
    }

    public interface FABCallBack {
        void setFAB(List<DataModelSelected> dataModelSelectedList);
    }

    public void setFABListener(FABCallBack callBack) {
        this.fabCallBack = callBack;
    }

    public void removeEntry( String id, int adapterPosition) {
        DataBHelper database = new DataBHelper(context);
        database.deleteEntry(Long.parseLong(id));
        dataModelDBArrayList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        resultsCallBack.getEmpty();
        database.close();
    }

    public void clearLists() {
        selectedArrayList.clear();
    }
}



