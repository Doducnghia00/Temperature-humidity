package com.example.tandhv2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tandhv2.R;
import com.example.tandhv2.model.Sensor;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private LineChart mChart;
    private ArrayList<Entry> lineList;

    private LineDataSet lineDataSet;
    private LineData lineData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mChart = findViewById(R.id.lineChart);

        lineList = new ArrayList<>();
        lineList.add(new Entry(10f,100f));
        lineList.add(new Entry(20,120));
        lineList.add(new Entry(30,140));
        lineList.add(new Entry(40,160));
        lineList.add(new Entry(50,140));
        lineList.add(new Entry(60,150));

        lineDataSet = new LineDataSet(lineList,"Entries");
        lineData = new LineData(lineDataSet);
        //line_chart.data = lineData;
        mChart.setData(lineData);

//        lineDataSet.setValueTextColor(Color.BLUE);
//
//        lineDataSet.setValueTextSize(20f);
//        //lineDataSet.setDrawFilled(true);
//        lineDataSet.setDrawCircles(true);

        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setValueTextColor(Color.GREEN);




    }
}