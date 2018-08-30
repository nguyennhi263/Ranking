package com.example.asto_nhi.ranking;

public class MyDate {
    int dateNum;
    int weatherCode;

    public MyDate(int dateNum, int weatherCode) {
        this.dateNum = dateNum;
        this.weatherCode = weatherCode;
    }

    public int getDateNum() {
        return dateNum;
    }

    public void setDateNum(int dateNum) {
        this.dateNum = dateNum;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }
    public String getWeatherIcon (){
        switch (this.weatherCode){
            case 11:
            case 12:
                return "rain_day_night";
            case 4:
                return "thundershowers_day_night";
            case 23:
            case 24:
                return "windy_day_night";
            case 27:
            case 28:
                return "mostly_cloudy_day_night";
            case 29:
            case 30:
                return "partly_cloudy_day";
            case 31:
            case 32:
                return "clear_day";
            case 33:
            case 34:
                return "fair_day";
            case 40:
            case 47:
                return "scattered_showers_day_night";
        }
    return "";
    }
}
