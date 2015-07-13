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
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.CategoryClass;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.SpinnerDataClass;
//import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.SpinnerDataClass;

/**
 * Created by trees on 6/8/15.
 */
public class CategoryAdapter extends ArrayAdapter
{

    Context context;
    private ArrayList<SpinnerDataClass> list;
    private String[] category;

    public CategoryAdapter(Context context, int textViewResourceId, String[] objects)
    {
        super(context, textViewResourceId, objects);
        this.context=context;
        category=objects;
        list=new ArrayList<SpinnerDataClass>();


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
        ViewHolderSpinner holder=null;

        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.category_row, parent, false);

            holder=new ViewHolderSpinner(row);
            row.setTag(holder);

        }
        else
        {
            holder=(ViewHolderSpinner)row.getTag();
        }
        holder.image.setImageResource(resolveIcon(position));

        holder.category.setText(category[position]);


        return row;
    }



    private int resolveIcon(int pos)
    {

        switch (pos)
        {
            case 0: return R.drawable.miscallenous100;
            case 1: return R.drawable.tarkari100;
            case 2: return R.drawable.food100;
            case 3: return R.drawable.shoping100;
            case 4: return R.drawable.education100;
            case 5: return R.drawable.personal100;
            case 6: return R.drawable.maintainance100;
            case 7: return R.drawable.entertain100;
            case 8: return R.drawable.health100;
            case 9: return R.drawable.travel100;
            case 10: return R.drawable.coins100;
            case 11: return R.drawable.home_expense100;

            default:
                return 0;
        }


    }

}

class ViewHolderSpinner
{
    ImageView image;
    TextView category;

    ViewHolderSpinner(View row)
    {
        image=(ImageView)row.findViewById(R.id.imageCategory);
        category=(TextView)row.findViewById(R.id.textCategoryName);
    }


}




