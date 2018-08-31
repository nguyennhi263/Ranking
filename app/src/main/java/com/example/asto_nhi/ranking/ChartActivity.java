package com.example.asto_nhi.ranking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    CombinedChart combinedChart;
    protected String[] mDays = new String[] {
            "","6.10" , "6.11", "6.12", "6.13", "6.14"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        combinedChart = (CombinedChart)findViewById(R.id.combinedChart);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });

        //Set the Legends Orientation
        Legend l = combinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setEnabled(false);
        //Set Right Axis
        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setEnabled(false);
        //Set Left Axis
        YAxis leftAxis = combinedChart.getAxisLeft();

        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        // show negative value
        leftAxis.setStartAtZero(false);
        rightAxis.setDrawTopYLabelEntry(false);
        //Set X-Axis Lables
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setAxisMinimum(0f); // start at zero
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mDays[(int) value % mDays.length];
            }
        });

        //Create CombinedData Object
        CombinedData data = new CombinedData();

        data.setData( generateLineData());
        data.setData(generateBarData());

        // Set color
        combinedChart.setBackgroundColor(getResources().getColor(R.color.Black));
        combinedChart.setDrawGridBackground(false);
        combinedChart.setGridBackgroundColor(getResources().getColor(R.color.Black));
        leftAxis.setTextColor(getResources().getColor(R.color.While));
        xAxis.setTextColor(getResources().getColor(R.color.While));
        leftAxis.setGridColor(getResources().getColor(R.color.While));

        //Finally Create Chart
        leftAxis.setDrawZeroLine(true);
        combinedChart.setTouchEnabled(false);
        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        combinedChart.setData(data);
        combinedChart.invalidate();

    }
    private ArrayList<BarEntry> getStackBarEntriesData(ArrayList<BarEntry> entries){

        entries.add(new BarEntry(1,new float[]{-200,-350,-500}));
        entries.add(new BarEntry(2,new float[]{2000,300,400}));
        entries.add(new BarEntry(3,new float[]{200,3000,300}));
        entries.add(new BarEntry(4,new float[]{100,500,2000}));
        entries.add(new BarEntry(5,new float[]{400,2000,3000}));
        return entries;
    }
    private ArrayList<Entry> getLineEntriesData(ArrayList<Entry> entries){
        entries.add(new Entry(0, 1000));
        entries.add(new Entry(1, -1000));
        entries.add(new Entry(2, 2000));
        entries.add(new Entry(3, 3000));
        entries.add(new Entry(4, 2500));
        entries.add(new Entry(5, 5000));

        return entries;
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries = getLineEntriesData(entries);
        LineDataSet set = new LineDataSet(entries, "Line");
        set.setColor(getResources().getColor(R.color.While));
        set.setLineWidth(2.5f);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(false);
        set.enableDashedLine(10f, 10f, 0f);
        set.setDrawCircles(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries = getStackBarEntriesData(entries);

        BarDataSet set1 = new BarDataSet(entries, "Bar");
        set1.setColors(getColors());
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setDrawValues(false);

        float barWidth = 0.45f; // x2 dataset


        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

        return d;
    }
    private int[] getColors() {
        // set color for stacked bar chart
        int[] colors = new int[3];
        colors[0] =  getResources().getColor(R.color.colorAccent);
        colors[1] = getResources().getColor(R.color.colorPrimary);
        colors[2] = getResources().getColor(R.color.colorPrimaryDark);
        return colors;
    }
}
