package com.dss.cortessanchezarturosanchezalbaabeljose.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dss.cortessanchezarturosanchezalbaabeljose.R;
import com.dss.cortessanchezarturosanchezalbaabeljose.models.Computer;

import java.util.List;

public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ComputerViewHolder>{

    Context activity;
    List<Computer> computerList;

    public ComputerAdapter(Context activity, List<Computer> computerList){
        this.activity = activity;
        this.computerList = computerList;
    }



    public static class ComputerViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, brand, price;

        public ComputerViewHolder(@NonNull View item) {
            super(item);
            image = item.findViewById(R.id.imageView);
            title = item.findViewById(R.id.txt_title);
            brand = item.findViewById(R.id.txt_brand);
            price = item.findViewById(R.id.txt_price);
        }
    }





    @Override
    public int getItemCount() {
        return computerList.size();
    }

    @NonNull
    @Override
    public ComputerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.computer_item, parent, false);
        return new ComputerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputerViewHolder holder, int position) {

        holder.title.setText(computerList.get(position).getName());
        holder.brand.setText(computerList.get(position).getBrand());
        holder.price.setText(computerList.get(position).getPrice());

        Glide.with(activity)
                .load("https://png.pngtree.com/png-vector/20191026/ourmid/pngtree-laptop-icon-png-image_1871597.jpg")
                .into(holder.image);
    }



}
