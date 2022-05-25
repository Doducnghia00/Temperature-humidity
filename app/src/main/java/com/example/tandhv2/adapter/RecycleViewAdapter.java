package com.example.tandhv2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tandhv2.R;
import com.example.tandhv2.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context context;
    private ItemListener itemListener;

    private List<Sensor> sensors;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter(Context context, List<Sensor> sensors) {
        this.context = context;
        this.sensors = sensors;
    }

    public RecycleViewAdapter(Context context) {
        this.context = context;
        sensors = new ArrayList<>();

    }

    public void setSensors(List<Sensor> sensors) {

        this.sensors = sensors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sensor sensor = sensors.get(position);
        holder.temperature.setText(sensor.getTemperature() + "°C");
        holder.humidity.setText(sensor.getHumidity() +"%");
        holder.date.setText(sensor.getDate());
        holder.time.setText(sensor.getTime());
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView temperature,humidity,date,time;
        public ViewHolder(@NonNull View view) {
            super(view);
            temperature = view.findViewById(R.id.temperature);
            humidity = view.findViewById(R.id.humidity);
            date = view.findViewById(R.id.date);
            time = view.findViewById(R.id.time);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }


    }
    public interface ItemListener{
        void onItemClick(View view,int position);

    }
}
