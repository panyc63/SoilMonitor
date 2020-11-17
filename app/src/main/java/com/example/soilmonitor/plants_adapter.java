package com.example.soilmonitor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.soilmonitor.classes.PlantData;

import java.util.ArrayList;

public class plants_adapter extends RecyclerView.Adapter<plants_adapter.MyViewHolder> {

    Context context;
    ArrayList<PlantData> plantmoist;
    private OnItemClickListener listener;
    public static final String TAG = "abc";


    public plants_adapter(Context c, ArrayList<PlantData> p , OnItemClickListener onItemClickListener)
    {
        context = c;
        plantmoist = p;
        this.listener = onItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(view,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        int moist = plantmoist.get(position).getMoisture_Level();

        holder.PlantName.setText(plantmoist.get(position).getPlantName());
        holder.Moisture.setText(String.valueOf(moist));

        holder.img.setImageResource(R.drawable.plant);

    }

    @Override
    public int getItemCount() {
        return plantmoist.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView PlantName,Moisture;
        CardView cardView;
        OnItemClickListener listener;

        public MyViewHolder(View itemView,OnItemClickListener listener)
        {
            super(itemView);
            PlantName = (TextView) itemView.findViewById(R.id.plant_name_add);
            Moisture = (TextView) itemView.findViewById(R.id.plant_moisture_add);
            img = (ImageView) itemView.findViewById(R.id.plantpic);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int moist = plantmoist.get(position).getMoisture_Level();
            int humid = plantmoist.get(position).getHumidity();
            int temp = plantmoist.get(position).getTemperature();
            String pName = plantmoist.get(position).getPlantName();
            String Email = plantmoist.get(position).getOwner_Email();

            Intent(context,Plant_Setting.class,"S001",moist,humid,temp,pName,Email);

            /*
            try {
                listener.onItemClick(getAdapterPosition());
            }catch (Exception e)
            {
                Log.d(TAG, e.toString());
            }
            */
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int Position);

    }
    private void Intent(Context c, Class cl, String S_ID,int moist,int humid,int temp,String pName,String email)
    {
        android.content.Intent intent = new Intent(c,cl);
        intent.putExtra("SensorID",S_ID);
        intent.putExtra("Moist",0);
        intent.putExtra("Humid",0);
        intent.putExtra("Temp",0);
        intent.putExtra("PlantName",pName);
        intent .putExtra("Email",email);
        c.startActivity(intent);
    }

}
