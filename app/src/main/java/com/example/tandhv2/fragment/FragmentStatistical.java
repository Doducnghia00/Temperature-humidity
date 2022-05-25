package com.example.tandhv2.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tandhv2.R;
import com.example.tandhv2.activity.HttpsEndpointActivity;
import com.example.tandhv2.database.SensorDatabase;
import com.example.tandhv2.model.Sensor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

//Temperature and Humidity
public class FragmentStatistical extends Fragment  {
    //implements OnChartGestureListener, OnChartValueSelectedListener
    private LinearLayout btAdmin;
    private LineChart temperatureChart, humidityChart;
    private ArrayList<Entry> lineList;
    private List<Sensor> sensors;
    private LineDataSet lineDataSet;
    private LineData lineData;
    private Handler mHandler;
    private ImageView refresh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistical,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        this.mHandler = new Handler();

        m_Runnable.run();
        setUpLineChartT();
        setUpLineChartH();



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensors = SensorDatabase.getInstance(getActivity()).sensorDAO().getAll();
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                setUpLineChartT();
                setUpLineChartH();


            }
        });




        btAdmin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(getActivity(), "Long", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HttpsEndpointActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }



    private void setUpLineChartT() {
        temperatureChart.getDescription().setEnabled(false);
        temperatureChart.setBackgroundColor(Color.WHITE);
        temperatureChart.setDrawGridBackground(false);
        Log.e("LineChart", "================");
        lineList = new ArrayList<>();
        for (int i = 0; i < sensors.size(); i++) {
            String t = sensors.get(i).getTemperature();
            float f = Float.parseFloat(t);
            lineList.add(new Entry(i,f));
            Log.e("LineChart", "temperature : " + f);

        }
        for (int i = 0; i< lineList.size();i++) {
            Log.e("LineChart", "temperature : " + lineList.get(i).toString());
        }
        Log.e("LineChart", "================");
        lineDataSet = new LineDataSet(lineList,"Temperature");
        lineData = new LineData(lineDataSet);
        temperatureChart.setData(lineData);

        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setFillColor(Color.RED);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(8f);
        lineDataSet.setValueTextColor(Color.RED);

        temperatureChart.notifyDataSetChanged();
        temperatureChart.invalidate();

    }
    private void setUpLineChartH() {
        humidityChart.getDescription().setEnabled(false);
        humidityChart.setBackgroundColor(Color.WHITE);
        humidityChart.setDrawGridBackground(false);

        lineList = new ArrayList<>();
        for (int i = 0; i < sensors.size(); i++) {
            String t = sensors.get(i).getHumidity();
            float f = Float.parseFloat(t);
            lineList.add(new Entry(i,f));
        }
        lineDataSet = new LineDataSet(lineList,"Humidity");
        lineData = new LineData(lineDataSet);
        humidityChart.setData(lineData);

        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setFillColor(Color.BLUE);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(8f);
        lineDataSet.setValueTextColor(Color.BLUE);

        humidityChart.notifyDataSetChanged();
        humidityChart.invalidate();
    }


    private void initView(View view) {
        refresh = view.findViewById(R.id.refresh);
        refresh.setVisibility(View.GONE);
        sensors = SensorDatabase.getInstance(getActivity()).sensorDAO().getAll();
        //lineList = new ArrayList<>();
        btAdmin = view.findViewById(R.id.btAdmin);
        temperatureChart = view.findViewById(R.id.temperatureChart);
        humidityChart = view.findViewById(R.id.humidityChart);
    }

    private final Runnable m_Runnable = new Runnable(){
        public void run() {
            sensors = SensorDatabase.getInstance(getActivity()).sensorDAO().getAll();
            setUpLineChartT();
            setUpLineChartH();

            mHandler.postDelayed(m_Runnable, 2000);
        }

    };
}
