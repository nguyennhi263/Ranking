package com.example.asto_nhi.ranking;

import android.icu.util.ChineseCalendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Calendar;

public class RokuyoCalendar {
    String mRokuyo[] = {"大安","赤口","先勝","友引","先負","仏滅"};
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String convertRokuyoDay(int day, int month, int year){
        int mLunarDay = 1;
        int mLunarMonth = 1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){

                // If device api >= api 24
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month-1,day);
            ChineseCalendar chineseCalendar = new ChineseCalendar( calendar.getTime());
            //
            mLunarDay = chineseCalendar.get(chineseCalendar.DATE);
            mLunarMonth = chineseCalendar.get(chineseCalendar.MONTH)+1;

            /*
            *  Japanese Lunar Calendar
            * */
            if (mLunarDay==1&&mLunarMonth==10){
                mLunarDay=30;
                mLunarMonth=9;
            }
            if (mLunarMonth==10){
                mLunarDay --;

            }
        }
        else{
            // If device api < api 24
            mLunarDay = (new ChineseCalendarConvert().getLunarDay(day,month,year));
            mLunarMonth = (new ChineseCalendarConvert().getLunarMonth(day,month,year));
        }

        return  mRokuyo[(mLunarMonth + mLunarDay) % 6];
    }
}
