package com.example.finalprojectandroid1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    private Context context;
    private List<RestaurantModelClass> data;

    private RecyclerViewClickListener listener;

    public Adapter(Context context, List<RestaurantModelClass> data, RecyclerViewClickListener listener) {
        this.context = context;
        this.data = data;

        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater =  LayoutInflater.from(context);
        view = inflater.inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.address.setText(data.get(position).getAddress());
        holder.foodType.setText(data.get(position).getFoodType());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView address;
        TextView foodType;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.address);
            foodType = itemView.findViewById(R.id.food_type);


            itemView.setOnClickListener(this);
        }
//--------------------------------------
        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }



//--------------------------------------
    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }


}
