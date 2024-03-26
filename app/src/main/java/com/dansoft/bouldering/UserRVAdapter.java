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

//used to populate ViewUsers recycleView with user_rv_items
public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> {
    private ArrayList<User> usersArray;
    private Context context;

    public UserRVAdapter(ArrayList<User> users, Context context) {
        this.usersArray = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //for each user record in the database
        //a user_rv_item is created and personalised to that record
        User user = usersArray.get(position);
        holder.userIDTV.setText(String.valueOf(user.getUserID()));
        holder.firstNameTV.setText(user.getFirstName());
        holder.surnameTV.setText(user.getSurname());
        holder.dateOfBirthTV.setText(user.getDateOfBirth());
        holder.emailTV.setText(user.getEmail());
        holder.mobileNumberTV.setText(user.getMobileNumber());
        holder.usernameTV.setText(user.getUsername());
        holder.passwordTV.setText(user.getPassword());

        //upon clicking on a user_rv_item
        //starts UpdateUser activity and sends relevant information
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, UpdateUser.class);

            i.putExtra("userID", String.valueOf(user.getUserID()));
            i.putExtra("firstName", user.getFirstName());
            i.putExtra("surname", user.getSurname());
            i.putExtra("dateOfBirth", user.getDateOfBirth());
            i.putExtra("email", user.getEmail());
            i.putExtra("mobileNumber", user.getMobileNumber());
            i.putExtra("username", user.getUsername());
            i.putExtra("password", user.getPassword());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return usersArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userIDTV, firstNameTV, surnameTV, dateOfBirthTV, emailTV, mobileNumberTV, usernameTV, passwordTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userIDTV = itemView.findViewById(R.id.idTVUserID);
            firstNameTV = itemView.findViewById(R.id.idTVFirstName);
            surnameTV = itemView.findViewById(R.id.idTVSurname);
            dateOfBirthTV = itemView.findViewById(R.id.idTVDateOfBirth);
            emailTV = itemView.findViewById(R.id.idTVEmail);
            mobileNumberTV = itemView.findViewById(R.id.idTVMobileNumber);
            usernameTV = itemView.findViewById(R.id.idTVUsername);
            passwordTV = itemView.findViewById(R.id.idTVPassword);
        }
    }
}