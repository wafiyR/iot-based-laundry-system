package com.example.laundryrush;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AdminMainMenuActivity extends AppCompatActivity  {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        //BarChart chart = (BarChart) findViewById(R.id.chart);
        barChart = findViewById(R.id.chart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(110.000f, 0)); // Jan
        barEntries.add(new BarEntry(40.000f, 1)); // Feb
        barEntries.add(new BarEntry(60.000f, 2)); // Mar
        barEntries.add(new BarEntry(30.000f, 3)); // Apr
        barEntries.add(new BarEntry(100.000f, 4)); // May

        BarDataSet barDataSet = new BarDataSet(barEntries, "Profit");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("March");
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");


        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        /*BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        barEntries.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        barEntries.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        barEntries.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        barEntries.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        barEntries.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        barEntries.add(v1e6);*/

    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this).setMessage("Are you sure you want to logout?").setPositiveButton(R.string.yesdialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //sessionManagement.editor.clear().apply();
                startActivity(new Intent(AdminMainMenuActivity.this, LoginActivity.class));
                finish();

            }
        }).setNegativeButton(R.string.nodialog, null).show();
    }
}