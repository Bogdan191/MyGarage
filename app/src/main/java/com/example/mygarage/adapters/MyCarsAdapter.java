package com.example.mygarage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygarage.R;
import com.example.mygarage.models.CarModel;

import java.util.ArrayList;

public class MyCarsAdapter extends RecyclerView.Adapter<MyCarsAdapter.MyViewHolder> {

    Context context;

    ArrayList<CarModel> listCars;

    public MyCarsAdapter(Context context, ArrayList<CarModel> listCars) {
        this.context = context;
        this.listCars = listCars;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CarModel carModel = listCars.get(position);

        holder.carName.setText(carModel.getMake() + " " + carModel.getModel());
        holder.carDescription.setText(carModel.getEngine_capacity() + "L, " + carModel.getHorse_power() + "CP\n" + carModel.getOdometer()+"km");

    }

    @Override
    public int getItemCount() {
        return listCars.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView carName, carDescription;
        ImageView carImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.myCarName);
            carDescription = itemView.findViewById(R.id.myCarDescription);
            carImage = itemView.findViewById(R.id.myCarImage);
        }
    }
}
