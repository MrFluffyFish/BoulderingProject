package com.dansoft.bouldering;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

//used to populate ViewUsers recycleView with analytic_rv_items
public class AnalyticRVAdapter extends RecyclerView.Adapter<AnalyticRVAdapter.ViewHolder> {
    private ArrayList<Analytic> analyticsArray;
    private Context context;

    // constructor
    public AnalyticRVAdapter(ArrayList<Analytic> analyticsArray, Context context) {
        this.analyticsArray = analyticsArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analytic_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //for each analytic record in the database
        //an analytic_rv_item is created and personalised to that record
        Analytic analytic = analyticsArray.get(position);
        holder.userIDTV.setText(String.valueOf(analytic.getUserID()));
        holder.boulderIDTV.setText(String.valueOf(analytic.getBoulderID()));
        holder.voteTV.setText(analytic.getVote());

        //upon clicking on an analytic_rv_item
        //starts UpdateAnalytic activity and sends relevant information
        holder.itemView.setOnClickListener(v -> {

            Intent i = new Intent(context, UpdateAnalytic.class);

            i.putExtra("userID", String.valueOf(analytic.getUserID()));
            i.putExtra("boulderID", String.valueOf(analytic.getBoulderID()));
            i.putExtra("vote", analytic.getVote());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return analyticsArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userIDTV, boulderIDTV, voteTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userIDTV = itemView.findViewById(R.id.idTVUserID);
            boulderIDTV = itemView.findViewById(R.id.idTVBoulderID);
            voteTV = itemView.findViewById(R.id.idTVVote);
        }
    }
}