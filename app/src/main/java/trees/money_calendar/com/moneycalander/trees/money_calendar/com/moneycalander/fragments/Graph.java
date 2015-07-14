package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.GraphScrollAdapter;

/**
 * Created by trees on 6/5/15.
 */
public class Graph extends Fragment{


    /*
 *This Fragment has two basic components
 * 1)SlideTabLayout
 * This view is used to define the layout of SlideTabs and all the definition is wrritten in SlideTabLayoutClass
 *
 *
 * 2)ViewPager
 * This View is used to host three fragmnet on it under its frame
 * It specially hosts ADD, TRANSICTION AND GRPAH Fragmnets on it
 *
 */





    private ViewPager viewPager;
    private PagerTitleStrip strip;
    private GraphScrollAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.graphs,container,false);
        initialize(v);
        return v;
    }




    private void initialize(View v)
    {

        strip=(PagerTitleStrip)v.findViewById(R.id.titleStrip);
        strip.setTextColor(getResources().getColor(R.color.materialWhite));
        adapter=new GraphScrollAdapter(getActivity().getSupportFragmentManager());
        viewPager=(ViewPager)v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

    }

//    public GraphScrollAdapter getViewPagerDai()
//    {
//        return adapter;
//    }




}
