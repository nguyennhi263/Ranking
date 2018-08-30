package com.example.asto_nhi.ranking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends ArrayAdapter<MyDate> {
    private Activity contex;
    private ArrayList<MyDate> dayList;

    public CalendarAdapter(Activity contex, ArrayList<MyDate> dayList){
        super (contex , R.layout.custom_calendar_day,dayList);
        this.contex=contex;
        this.dayList = dayList;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = contex.getLayoutInflater();
        // day in question
        MyDate curDate = getItem(position);

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.custom_calendar_day, parent, false);

        TextView dayNum = (TextView) view.findViewById(R.id.dayNum);
        ImageView iconWeather = (ImageView) view.findViewById(R.id.iconWeather);
        TextView  rokyoDay= (TextView) view.findViewById(R.id.rokyoDay);

        if (curDate.getDateNum()== 0){
            // previous month
            dayNum.setVisibility(View.INVISIBLE);
            rokyoDay.setVisibility(View.INVISIBLE);
        }
        else {
            // set text
            dayNum.setText(String.valueOf(curDate.getDateNum()));
            rokyoDay.setText(curDate.getRokyoDay());
            // if empty weather
            if (curDate.getWeatherCode() != 0) {


                String iconName = curDate.getWeatherIcon();

                if (!iconName.isEmpty()){
                    // change weather icon
                    iconWeather.setVisibility(View.VISIBLE);
                    String uri = "@drawable/"+iconName;  //
                    int imageResource = contex.getResources().getIdentifier(uri, null, contex.getPackageName());
                    Drawable res = contex.getResources().getDrawable(imageResource);
                    iconWeather.setImageDrawable(res);
                }
            }

        }


        return view;
    }
}
