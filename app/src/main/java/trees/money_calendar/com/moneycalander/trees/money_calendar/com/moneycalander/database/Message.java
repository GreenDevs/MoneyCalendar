package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by root on 08/06/15.
 */
public class Message
{
    public static void message(Context context,String message)
    {
       Toast toast= Toast.makeText(context,""+message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void message_longTime(Context context,String message)
    {
        Toast toast= Toast.makeText(context,""+message,Toast.LENGTH_LONG);
        toast.show();
    }
}
