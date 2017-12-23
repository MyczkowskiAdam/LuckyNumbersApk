package com.luckynumbers.mycax.luckynumbers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.Myholder> {
    List<DataModel> dataModelArrayList;
    Context context;
    ResultsCallBack resultsCallBack;

    public DatabaseAdapter(List<DataModel> dataModelArrayList, Context context) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    class Myholder extends RecyclerView.ViewHolder implements CardView.OnLongClickListener {
        TextView name, result;
        String id;


        public Myholder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.card_name);
            result = (TextView) itemView.findViewById(R.id.card_result);

            CardView cardView = (CardView) itemView.findViewById(R.id.card_view);
            if (PreferenceManager.getDefaultSharedPreferences(context)
                    .getBoolean("app_enable_grid",false)) {
                cardView.setLayoutParams(new RelativeLayout.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, 200));
            }
            cardView.setOnLongClickListener(this);
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
                            DataBHelper database = new DataBHelper(context);
                            database.deleteEntry(Long.parseLong(id));
                            dataModelArrayList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            resultsCallBack.getEmpty();
                            database.close();
                            Snackbar.make(v, "Successfully deleted!", Snackbar.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);
        holder.name.setText(dataModel.getName());
        holder.result.setText(dataModel.getResult());
        holder.id = dataModel.getId();
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public interface ResultsCallBack {
        void getEmpty();
    }

    public void setEmptyDrawableListener(ResultsCallBack callBack) {
        this.resultsCallBack = callBack;
    }
}



