package id.kiosku.tcx.drivers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by AMP on 21-Oct-15.
 */
public class DatetimeDriver {
    public static String get(String datetime,String format){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat(format, new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String get(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("EEEE, dd MMMM yyyy - HH:mm:ss", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String get(){
        try {
            return new SimpleDateFormat("EEEE, dd MMMM yyyy - HH:mm:ss", new Locale("in", "ID")).format(new Date());
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTime(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("HH:mm:ss", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getTime(){
        try {
            return new SimpleDateFormat("HH:mm:ss", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }


    public static String getDate(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getDate(){
        try {
            return new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }

    public static String getDayName(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("EEEE", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getDayName(){
        try {
            return new SimpleDateFormat("EEEE", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }

    public static String getDateWithDayName(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getDateWithDayName(){
        try {
            return new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }

    public static String getDay(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("dd", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getDay(){
        try {
            return new SimpleDateFormat("dd", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }

    public static String getMonth(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("MMMM", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getMonth(){
        try {
            return new SimpleDateFormat("MMMM", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }

    public static String getYear(String datetime){
        try {
            if(datetime!=null && !datetime.equals("0000-00-00 00:00:00")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
                return new SimpleDateFormat("yyyy", new Locale("in", "ID")).format(date);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
    public static String getYear(){
        try {
            return new SimpleDateFormat("yyyy", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }
    public static String getAsText(){
        try {
            return new SimpleDateFormat("yyyyMMddHHmmss", new Locale("in", "ID")).format(new Date());
        }catch (Exception e){
            return null;
        }
    }
}
