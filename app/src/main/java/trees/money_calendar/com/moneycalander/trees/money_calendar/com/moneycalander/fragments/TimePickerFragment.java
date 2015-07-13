package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import java.util.Calendar;

/**
 * Created by trees on 6/19/15.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
    private int hour, minute;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //USE current Time as the default values for time picker
        final Calendar c = Calendar.getInstance();
        hour=c.get(Calendar.HOUR);
        minute=c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(), this, hour, minute, false);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {

       // Message.message(getActivity(), "Hour:"+hourOfDay+"  Minute:"+minute);
        hour=hourOfDay;
        this.minute=minute;

    }

    public String getTime()
    {
        return " "+hour+":"+minute;
    }

    public void resetTime()
    {
        final Calendar c = Calendar.getInstance();
        hour=c.get(Calendar.HOUR);
        minute=c.get(Calendar.MINUTE);
    }
}
