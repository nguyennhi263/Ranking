package com.example.asto_nhi.ranking;

import android.app.Activity;
import android.content.Context;
import android.icu.util.ChineseCalendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class TestCalendarActivity extends AppCompatActivity {
    // internal components
    private TextView test;
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    ArrayList<MyDate> myDates = new ArrayList<>();
    ArrayList<MyWeather> myWeatherArrayList = new ArrayList<>();
    int currentMonth;
    int currentYear;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_calendar);
        iniCalendar();
        // get current month
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear =  calendar.get(Calendar.YEAR);
        new GetWeatherAPI().execute();
        // set up default calendar
        //updateCalendar(currentMonth,currentYear);

        // previous month
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMonth(currentMonth-1);
                updateCalendar(currentMonth,currentYear);
            }
        });

        //next month
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMonth(currentMonth+1);
                updateCalendar(currentMonth,currentYear);
            }
        });
        /*
        //test
        Calendar cal = Calendar.getInstance();
        cal.set(2018,11-1,8);
        ChineseCalendar chineseCalendar = new ChineseCalendar( cal.getTime());
        int mLunarDay = chineseCalendar.get(chineseCalendar.DATE);
        int mLunarMonth = chineseCalendar.get(chineseCalendar.MONTH)+1;
        int myDay = new ChineseCalendarConvert().getLunarDay(8,11,2018);
        int myMonth = new ChineseCalendarConvert().getLunarMonth(8,11,2018);
        test.setText(mLunarDay+"."+mLunarMonth+" "+myDay+"."+myMonth);*/
    }
    private void iniCalendar(){
        test = (TextView) findViewById(R.id.test) ;
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }
    private void checkMonth(int month){
        if (month == 13){
            currentMonth =1;
            currentYear++;
        }
        else
        if (month == 0){
            currentMonth = 12;
            currentYear--;
        }
        else {
            currentMonth = month;
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateCalendar(int month, int year)
    {
        //clear data
        myDates.clear();
        // setting calendar lib
        int DAYS_COUNT = 28;
        int DAYS_WEEK = 1; // week start from Sunday ; Sun = 1
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1,1);  // set calendar start at first of month
        DAYS_COUNT = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        DAYS_WEEK = calendar.get(Calendar.DAY_OF_WEEK);

        // get day of previous month
        for (int i =0 ; i < DAYS_WEEK-1;i++){
            MyDate myDate = new MyDate(0,0);
            myDates.add(myDate);
        }
        // get day of current month
        for (int i=1; i<= DAYS_COUNT;i++){
            MyDate myDate = new MyDate(i,0);
            myDate.setRokyoDay(new RokuyoCalendar().convertRokuyoDay(i,month,year));
            //check weather forecast
            if (!myWeatherArrayList.isEmpty()){
                if(myWeatherArrayList.contains(new MyWeather(i,month,year))){
                    int index = myWeatherArrayList.indexOf(new MyWeather(i,month,year));
                    int weatherCode = myWeatherArrayList.get(index).getWeatherCode();
                    myDate.setWeatherCode(weatherCode);
                }
            }
            //
            myDates.add(myDate);
        }

        // get day of next month
        calendar.set(year,month - 1,DAYS_COUNT);  // set calendar start at end of month
        int LAST_DAY  = calendar.get(Calendar.DAY_OF_WEEK);
        if (LAST_DAY<8){
            for (int i = 7;i >LAST_DAY;i--){
                MyDate myDate = new MyDate(0,0);
                myDates.add(myDate);
            }
        }
        // update grid view
        CalendarAdapter calendarAdapter = new CalendarAdapter(this,myDates);
        grid.setAdapter(calendarAdapter);

        // update title
       txtDate.setText(year+"."+month);
    }

    private class GetWeatherAPI extends AsyncTask<String, Void, String> {

        String URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text=%22osaka%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                //Khởi tạo đối tượng client
                HttpClient client = new DefaultHttpClient();
                //Đối tượng chứa nội dung cần gửi
                HttpGet get = new HttpGet();
                URI uri = new URI(URL);
                get.setURI(uri);

                //Recive result
                HttpResponse response = client.execute(get);
                InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                String result  ="";
                while ((line = bufferedReader.readLine())!=null){
                    result +=line;
                }
                return result;

            }catch (Exception e){
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject json = new JSONObject(s);
                try {
                    JSONObject query = json.getJSONObject("query");
                    JSONObject results = query.getJSONObject("results");
                    JSONObject channel = results.getJSONObject("channel");
                    JSONObject item = channel.getJSONObject("item");
                    JSONArray forecast = item.getJSONArray("forecast");

                    //test.setText(forecast.toString());
                    myWeatherArrayList.clear();
                    for (int i= 0; i <forecast.length();  i++){
                        JSONObject cur = forecast.getJSONObject(i);
                        String date = cur.getString("date");
                        String code = cur.getString("code");

                        String[] day = date.split(" "); // split day , month, year
                        MyWeather curDate = new MyWeather(Integer.parseInt(day[0]),day[1],Integer.parseInt(day[2]),Integer.parseInt(code));

                        myWeatherArrayList.add(curDate);
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //test.setText(s.toString());
            updateCalendar(currentMonth,currentYear);
            //updateCalendar(1,2031);
            // end dialog bar

        }
    }
}
