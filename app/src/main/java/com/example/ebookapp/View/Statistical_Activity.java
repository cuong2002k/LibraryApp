package com.example.ebookapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ebookapp.DatabaseHandler.BorrowingHandler;
import com.example.ebookapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Statistical_Activity extends AppCompatActivity {

    private List<String> XLabel = Arrays.asList("1", "2","3","4","5",
            "6","7","8","9","10","11","12");
    private BorrowingHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        handler = new BorrowingHandler(Statistical_Activity.this);

        BarChart barChart = findViewById(R.id.chart);
        barChart.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> barEntries = handler.getStatistical();

        BarDataSet barDataSet = new BarDataSet(barEntries, "Thống Kê");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(XLabel));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


    }
}