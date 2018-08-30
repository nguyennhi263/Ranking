package com.example.asto_nhi.ranking;

public class MyWeather {
    int day,month,year,weatherCode;

    public MyWeather(int day, String month, int year, int weatherCode) {
        this.day = day;
        this.month = convertMonthToInt(month);
        this.year = year;
        this.weatherCode = weatherCode;
    }

    public MyWeather(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(int weatherCode) {
        this.weatherCode = weatherCode;
    }
    private int convertMonthToInt(String month){
      switch (month){
          case "Jan":
              return 1;
          case "Feb":
              return 2;
          case "Mar":
              return 3;
          case "Apr":
              return 4;
          case "May":
              return 5;
          case "Jun":
              return 6;
          case "Jul":
              return 7;
          case "Aug":
              return 8;
          case "Sep":
              return 9;
          case "Oct":
              return 10;
          case "Nov":
              return 11;
          case "Dec":
              return 12;
      }
      return 1;
      }

    @Override
    public boolean equals(Object obj) {
        return (this.day == (((MyWeather) obj).day)
                && this.month == (((MyWeather) obj).month) && this.year == (((MyWeather) obj).year));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
