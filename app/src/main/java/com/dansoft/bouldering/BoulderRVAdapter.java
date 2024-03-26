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

//used to populate ViewBoulders recycleView with boulder_rv_items
public class BoulderRVAdapter extends RecyclerView.Adapter<BoulderRVAdapter.ViewHolder> {
    private ArrayList<Boulder> BoulderArray;
    private Context context;

    public BoulderRVAdapter(ArrayList<Boulder> BoulderArray, Context context) {
        this.BoulderArray = BoulderArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boulder_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //for each boulder record in the database
        //a boulder_rv_item is created and personalised to that record
        Boulder boulder = BoulderArray.get(position);
        holder.boulderIDTV.setText(String.valueOf(boulder.getBoulderID()));
        holder.gradeTV.setText(boulder.getGrade());
        holder.posXTV.setText(String.valueOf(boulder.getX()));
        holder.posYTV.setText(String.valueOf(boulder.getY()));
        holder.colourTV.setText(boulder.getColour());

        ArrayList<String> styleDescriptions = MainActivity.dbHandler.fetchStyleDescriptions(boulder.getStyleIDs());
        holder.styleIDsTV.setText(styleDescriptions.toString());

        //upon clicking on a boulder_rv_item
        //starts UpdateBoulder activity and sends relevant information
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, UpdateBoulder.class);

            i.putExtra("boulderID", String.valueOf(boulder.getBoulderID()));
            i.putExtra("grade", boulder.getGrade());
            i.putExtra("posX", String.valueOf(boulder.getX()));
            i.putExtra("posY", String.valueOf(boulder.getY()));
            i.putExtra("colour", boulder.getColour());
            i.putIntegerArrayListExtra("styleIDs", boulder.getStyleIDs());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return BoulderArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView boulderIDTV, gradeTV, posXTV, posYTV, colourTV, styleIDsTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            boulderIDTV = itemView.findViewById(R.id.idTVBoulderID);
            gradeTV = itemView.findViewById(R.id.idTVGrade);
            posXTV = itemView.findViewById(R.id.idTVPosX);
            posYTV = itemView.findViewById(R.id.idTVPosY);
            colourTV = itemView.findViewById(R.id.idTVColour);
            styleIDsTV = itemView.findViewById(R.id.idTVStyles);
        }
    }
}