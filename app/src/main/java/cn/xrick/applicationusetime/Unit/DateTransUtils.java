package cn.xrick.applicationusetime.Unit;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTransUtils {

    public static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    public static String stampToDate(long stamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(stamp);
        res = simpleDateFormat.format(date);
        return res;
    }


    //获取当日00:00:00的时间戳,东八区则为早上八点
    public static long getZeroClockTimestamp(long time){
        long currentStamp = time;
        currentStamp -= currentStamp % DAY_IN_MILLIS;
        Log.i("Wingbu"," DateTransUtils-getZeroClockTimestamp()  获取当日00:00:00的时间戳,东八区则为早上八点 :" + currentStamp);
        return currentStamp;
    }

    public static String milliseconds2hms(long time)
    {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time-hours*(1000 * 60 * 60 ))/(1000* 60);
        long second = (time-hours*1000 * 60 * 60-minutes*1000*60)/1000;
        String diffTime="";
        diffTime=hours+"小时"+minutes+"分"+second+"秒";
        return diffTime;
    }


}
