package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database;



import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 07/06/15.
 */
public class DateAndTimeStamp
{
    public static long returnTimeStamp(String dateString)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
        long time=89;
        try
        {
            Date date=simpleDateFormat.parse(dateString);

            time=date.getTime()/1000L;
        }
        catch (ParseException e)
        {
            Log.i("EXCPEITON", " ASJDFLASKDJFLK JALKADSFJLKSADJFL KSAD LK");
            e.printStackTrace();

        }


        return time;
    }


    public static void returnDate(long timeStamp)
    {

        try
        {
            Timestamp stamp = new Timestamp(timeStamp);
            Date date = new Date(stamp.getTime());
            Log.i("time_stamp", date.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
//            return "0000-00-00";
        }

    }
}

