package com.example.mygarage.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mygarage.DBHelper;
import com.example.mygarage.R;
import com.example.mygarage.models.News;
import com.example.mygarage.models.ServiceHistoryModel;
import java.util.ArrayList;

public class ServiceInfoAdapter  extends RecyclerView.Adapter<ServiceInfoAdapter.MyViewHolder> {
    Context context;

    ArrayList<ServiceHistoryModel> listServiceInfo;

    public ServiceInfoAdapter(Context context, ArrayList<ServiceHistoryModel> listServiceInfo) {
        this.context = context;
        this.listServiceInfo = listServiceInfo;
    }

    @NonNull
    @Override
    public ServiceInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.service_info_item, parent, false);
        return new ServiceInfoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceInfoAdapter.MyViewHolder holder, int position) {

        ServiceHistoryModel serviceHistoryModel = listServiceInfo.get(position);

        holder.titleServiceInfo.setText("Tip interventie: " + serviceHistoryModel.getTypeOfIntervention());
        holder.serviceDescription.setText("Detalii: " + serviceHistoryModel.getDetails() + " \n\\nData: " + serviceHistoryModel.getService_made_date());


        holder.buttonServiceInfoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implement db logic for delete service info
                String serviceId = serviceHistoryModel.getId();
                DBHelper db = new DBHelper(context);
                db.DeleteServiceHistory(serviceId);

                Toast.makeText(context, "Istoriv service sters! Reincarcati pagina pentru a reimprospata lista!", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listServiceInfo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleServiceInfo, serviceDescription;
        ImageButton buttonServiceInfoDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleServiceInfo = itemView.findViewById(R.id.tvServiceInfoData);
            serviceDescription = itemView.findViewById(R.id.tvServiceInfoDetails);
            buttonServiceInfoDelete = itemView.findViewById(R.id.buttonServiceInfoDelete);
        }
    }

}
