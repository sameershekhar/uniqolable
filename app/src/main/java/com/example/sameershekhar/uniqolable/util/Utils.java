package com.example.sameershekhar.uniqolable.util;

import com.example.sameershekhar.uniqolable.R;

import java.sql.Timestamp;
import java.util.Date;

public class Utils {

    public static int getImage(String imageType){
        switch (imageType){
            case "Clear":
                return R.drawable.clear;
            case "Clouds":
                return R.drawable.storm;
            case "Sunny":
                return R.drawable.sunny;
            case "Rain":
                return R.drawable.rain;

        }
        return R.drawable.clear;
    }
    public static int getImageAdapter(String imageType){
        switch (imageType){
            case "Clear":
                return R.drawable.clear_white;
            case "Clouds":
                return R.drawable.storm_white;
            case "Sunny":
                return R.drawable.sunny_white;
            case "Rain":
                return R.drawable.rain_white;

        }
        return R.drawable.clear_white;
    }

    public static String getTemp(String temp){
        return temp+(char) 0x00B0+" c";
    }

    public static String getFormatedTime(long time){
        String[] strDays = new String[] { "Sunday", "Monday", "Tuesday","Wednesday", "Thursday","Friday", "Saturday" };
        // String[] weatherType = new String[] { "Clear","Clouds"};
        String dateformate="";
        if(time!=0) {
            Timestamp ts = new Timestamp(time);
            Date date = new Date(time * 1000);
            int day = date.getDay();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            return formatter.format(ts);
            dateformate += strDays[day];
        }
        return dateformate;
    }

}
