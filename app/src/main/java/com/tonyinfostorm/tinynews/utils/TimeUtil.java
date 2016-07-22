package com.tonyinfostorm.tinynews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by higer on 2016/7/20.
 */
public class TimeUtil {
    public static String convertTimeToFormat(long timeStamp) {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        long curTime = System.currentTimeMillis() / (long) 1000;
        try {
            Date curDate = dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
            curTime = curDate.getTime() / (long) 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time = curTime - timeStamp / (long) 1000;
        long result = 0;
        if (time < 60 && time >= 0) {
            return "just now";
        } else if (time >= 60 && time < 3600) {
            result = time / 60;
            return result + " " + (result < 2 ? "minute ago" : "minutes ago");
        } else if (time >= 3600 && time < 3600 * 24) {
            result = time / 3600;
            return result + " " + (result < 2 ? "hour ago" : "hours ago");
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            result = time / 3600 / 24;
            return result + " " + (result < 2 ? "day ago" : "days ago");
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            result = time / 3600 / 24 / 30;
            return result + " " + (result < 2 ? "month ago" : "months ago");
        } else if (time >= 3600 * 24 * 30 * 12) {
            result = time / 3600 / 24 / 12;
            return result + " " + (result < 2 ? "year ago" : "years ago");
        } else {
            return "just now";
        }
    }
}
