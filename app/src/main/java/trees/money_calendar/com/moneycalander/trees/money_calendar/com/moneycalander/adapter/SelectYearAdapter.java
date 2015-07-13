package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.SpinnerDataClass;

/**
 * Created by trees on 7/9/15.
 */
public class SelectYearAdapter extends ArrayAdapter
{

    private Context context;
    private String[] yearList;

    public SelectYearAdapter(Context context, int textViewResourceId, String[] objects)
    {
        super(context, textViewResourceId, objects);
        this.context=context;
        yearList=objects;



    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        View row=convertView;


        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.year_sel_row, parent, false);

        }

        ((TextView)row.findViewById(R.id.selYearRow)).setText(yearList[position]);

        return row;

    }





}

