package trees.money_calendar.com.moneycalander;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.SlidingTabLayout;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.ViewPagerAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.DateAndTimeStamp;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.Message;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.Graph;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.Incoming;

/*
 *This is the main activity of the application that has three basic components
 *
 * 1)APP BAR
 * app bar view is used instead of Action bar
 * the app bar view xml object is inflated and linked with app bar refrence
    eg appbar=(Toolbar)findViewById(R.id.app_bar);
 *
 * 2)SlideTabLayout
 * This view is used to define the layout of SlideTabs and all the definition is wrritten in SlideTabLayoutClass
 *
 *
 * 3)ViewPager
 * This View is used to host three fragmnet on it under its frame
 * It specially hosts ADD, TRANSICTION AND GRPAH Fragmnets on it
 *
 */




public class MainActivity extends ActionBarActivity implements View.OnClickListener
{

    private ViewPager pager;
    private ImageButton fabButton;
    private ViewPagerAdapter pagerAdapter;
    private SlidingTabLayout tabs;
    public static final CharSequence titles[]={"LIST","GRAPHS"};
    public static final int noOfTabs=2;
    private Toolbar appbar;   //This refrence refers to the tool_bar xml to be exact



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();                           //this activity intializes all the view
                                                //instances and increase readability

    }



    private void initialise()
    {

         /*1)TOOL BAAR
          #Linking the app bar layout file with the tool bar object
          #Tool bar is set as action bar for an activity using setSupportActionBar(ToolBar Instance)
        */
        appbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(appbar);


        /*
         2)VIEW PAGER INITILIZATION
         *
         * first the ViewPagerAdapter Class is instantiated
         * then pager is linked with the ViewPager View
         * at this point both ViewPagerAdapter and ViewPager object is instantiated
          * and the ViewPager Object is set with the ViewPager instance

         */

        pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), titles, noOfTabs);
        pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);


         /*
        *3)
        *
        *Here the SlidingTabsLayout refrence is initialized with corresponding xml view
        *then the tabs are set to distribute evenly
        * and tabs are integrated with custome color facility
        * and then tabs are integrated on ViewPager instance
        *
         */

        tabs=(SlidingTabLayout)findViewById(R.id.slidingTabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);


        /*
        *4)FAB BUTTONS
        * JUST INITIALIZING AND SETTING CLICK LISTENER
         */
        fabButton=(ImageButton)findViewById(R.id.fabButton);
        fabButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.fabButton)
        {


            Intent intent=new Intent(this, AddActivity.class);
            startActivity(intent);




        }

    }

//    public void getViewPager(int pos)
//    {
//        Graph graphFrag=(Graph)pagerAdapter.getItem(1);
//        Incoming inFrag=(Incoming)graphFrag.getViewPagerDai().getItem(1);
//        inFrag.inCome();
//    }

    public ImageButton getFab()
    {
        return fabButton;
    }



}
