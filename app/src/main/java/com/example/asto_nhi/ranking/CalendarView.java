package com.example.asto_nhi.ranking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarView extends LinearLayout
{
    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private Context context;
    ArrayList<MyDate> myDates = new ArrayList<>();
    public CalendarView(Context context)
    {
        super(context);
        //initControl(context);
        //this.context = context;
    }

    /**
     * Load component XML layout
     */
    private void initControl(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.custom_calendar, this);

        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }
    private void updateCalendar()
    {
        Date currentDate = new Date();
        int DAYS_COUNT = 28;
        int DAYS_WEEK = 1; // week start from Sunday ; Sun = 1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1); // set calendar start at first of month
        DAYS_COUNT = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        DAYS_WEEK = calendar.get(Calendar.DAY_OF_WEEK);

        // get day of previous month
        for (int i =0 ; i <= DAYS_WEEK-1;i++){
            MyDate myDate = new MyDate(0,0);
            myDates.add(myDate);
        }
        // get day of current month
        for (int i=1;i <= DAYS_COUNT;i++){
            MyDate myDate = new MyDate(i,0);
            myDates.add(myDate);
        }
        // update grid
        CalendarAdapter calendarAdapter = new CalendarAdapter((Activity) context.getApplicationContext(),myDates);
        Toast.makeText(getContext(),"test",Toast.LENGTH_SHORT).show();
        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        //txtDate.setText(sdf.format(currentDate.getTime()));
        txtDate.setText(DAYS_COUNT+"");
    }
    private void updateCalendar(int month, int year)
    {

        Date currentDate = new Date();
        int DAYS_COUNT = 28;
        int DAYS_WEEK = 1; // week start from Sunday ; Sun = 1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1); // set calendar start at first of month
        DAYS_COUNT = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        DAYS_WEEK = calendar.get(Calendar.DAY_OF_WEEK);

        // get day of previous month
        for (int i =0 ; i<=DAYS_WEEK-1;i++){
            MyDate myDate = new MyDate(0,0);
            myDates.add(myDate);
        }
        // get day of current month
        for (int i=1;i<=DAYS_COUNT;i++){
            MyDate myDate = new MyDate(i,0);
            myDates.add(myDate);
        }
        // update grid
        CalendarAdapter calendarAdapter = new CalendarAdapter((Activity) context.getApplicationContext(),myDates);

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        txtDate.setText(sdf.format(currentDate.getTime()));
    }
}