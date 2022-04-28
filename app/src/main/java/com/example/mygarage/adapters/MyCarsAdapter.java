package com.example.mygarage.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygarage.CarRUDActivity;
import com.example.mygarage.MyCarsActivity;
import com.example.mygarage.R;
import com.example.mygarage.Utils;
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

        holder.carImage.setImageBitmap(Utils.getImage(carModel.getCarImage()));
        holder.carName.setText(carModel.getMake() + " " + carModel.getModel());
        holder.carDescription.setText(carModel.getEngine_capacity() + "L, " + carModel.getHorse_power() + "CP\n" + carModel.getOdometer()+"km");
        holder.carDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CarRUDActivity.class);
                intent.putExtra("CAR_ID", carModel.getId());
                Toast.makeText(context.getApplicationContext(), "Mergi la pagina cu detaliile masinii", Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return listCars.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView carName, carDescription;
        ImageView carImage;
        LinearLayout carDetails; 

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.myCarName);
            carDescription = itemView.findViewById(R.id.myCarDescription);
            carImage = itemView.findViewById(R.id.myCarImage);
            carDetails = itemView.findViewById(R.id.goToCarDetails);

        }
    }
}
