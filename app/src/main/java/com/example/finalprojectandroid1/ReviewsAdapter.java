package com.example.finalprojectandroid1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder>{

    private List<ReviewModelClass> data;

    public ReviewsAdapter(List<ReviewModelClass> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //------------------------------to check
        String rev = data.get(position).getReview();
        String rat = data.get(position).getRating();
        String dateAndTime = data.get(position).getDateAndTime();

        holder.setData(rev, rat, dateAndTime);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView review;
        TextView rating;
        TextView dateAndTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            review = itemView.findViewById(R.id.review);
            rating = itemView.findViewById(R.id.rating);
            dateAndTime = itemView.findViewById(R.id.date_and_time);


        }

        public void setData(String rev, String rat, String date) {

            review.setText(rev);
            rating.setText(rat);
            dateAndTime.setText(date);


        }

    }
}
