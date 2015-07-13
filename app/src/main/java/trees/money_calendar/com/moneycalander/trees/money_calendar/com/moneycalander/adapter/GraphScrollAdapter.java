package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.Incoming;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.OutGoing;

/**
 * Created by trees on 7/9/15.
 */
public class GraphScrollAdapter extends FragmentStatePagerAdapter {

    public GraphScrollAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if(position==0)  return new OutGoing();
        if(position==1)  return new Incoming();
        return null;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0) return "OUT";
        if(position==1) return "IN";
        return "";

    }
}
