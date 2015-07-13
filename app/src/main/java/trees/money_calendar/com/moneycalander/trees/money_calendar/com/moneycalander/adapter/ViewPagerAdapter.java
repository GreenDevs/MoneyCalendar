package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.Graph;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.TransictionList;


/**
 * Created by trees on 6/5/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter
{   /*
    *
    * This class servers Three fragment on it
    * that is why it has implemented FragmentStatePagerAdapter
    *
    * It contains
    *
    1)Constructor that takes FragmentManager refrence, CharSequence array, int variable
      fragmentmanager refrence is required because this ViewPager manages Fragment on it
    * CharSequence array holds the title of all the fragment
    * int variable holds the no of the fragments
    *
    *
    * 2)getItem() Method
    *
    * just returns the refrence of the instance of the Framgment corresponding
    * to the current position of viewpager
    *
    * 3)getCount() Method
    *
    * This returns the no of tabs for tab strips
    *
    * 4)getPageTitle() Method()
    *
    * This returns the title for the tab strips of the current fragment
     */

    CharSequence mTitles[];
    int noOfTabs;
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int noOfTabs)
    {
        super(fm);
        this.mTitles=mTitles;
        this.noOfTabs=noOfTabs;
    }

    @Override
    public Fragment getItem(int i)
    {

        if(i==0) return new TransictionList();
        if(i==1) return new Graph();

        return null;
    }

    @Override
    public int getCount()
    {
        return noOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitles[position];
    }
}
