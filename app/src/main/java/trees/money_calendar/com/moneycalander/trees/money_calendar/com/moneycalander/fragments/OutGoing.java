package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.CategoryClass;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.SelectMonthAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.SelectYearAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.DateAndTimeStamp;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.Message;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;

/**
 * Created by trees on 6/5/15.
 */
public class OutGoing extends  Fragment  implements AdapterView.OnItemSelectedListener

{
    //MPANDROID CHART
    private Map<Integer, Integer> dataMap;
    private LinearLayout lineLl;
    private LineChart lineChart;
    private Context context;
    private LineDataSet dataSet;
    private ArrayList<Entry> entries;
    private LineData data;
    private ArrayList<String> labels;
    private Spinner spinnerYear, spinnerMonth;
    private int curntYea=Calendar.getInstance().get(Calendar.YEAR);
    private int currntMonth=Calendar.getInstance().get(Calendar.MONTH);
    private String[] yearList={curntYea-3+"",curntYea-2+"", curntYea-1+"", curntYea+""};
    private String year_month=curntYea+"/"+(currntMonth+1);
    private String graphStartDateInFormat=curntYea+"/"+(currntMonth+1)+"/1 00:00";
    private int year_index=3;
    private int month_index=currntMonth;
    private final static String GRAPH_LABLE="                      GRAPH OF  " ;

    // private LineChart lineChart;
    private Map<Integer, String> catMap;
    private int groceryPcnt,  foodPcnt,   shopPcnt, educationPcnt,
            maintainPcnt, personPct, healthPcnt, entertainPcnt,
            travelPcnt,    savePcnt,   homePcnt, miscelloPcnt;


    private static final int IN_OUT_FLAG=0;
    private int screenWidht=0;



        //########################### CATEGORY VIEWS #############################
        ImageView cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10, cat11, cat12;
        TextView pc1, pc2, pc3, pc4, pc5, pc6, pc7, pc8, pc9, pc10, pc11, pc12;
        TextView n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12;
        View l1 ,l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

        View v=inflater.inflate(R.layout.outgoing_layout, container, false);
        graphInitialize(v);
        return v;
        }



//#################################   GRPAH INITIALIZEER ########################################


    private void graphInitialize(View v)
        {

        context=getActivity();
        spinnerYear = (Spinner)v.findViewById(R.id.selectYearOut);
        spinnerYear.setAdapter(new SelectYearAdapter(getActivity(), R.layout.year_sel_row, yearList));
        spinnerYear.setOnItemSelectedListener(this);
        spinnerYear.setSelection(year_index);

        spinnerMonth = (Spinner)v.findViewById(R.id.selectMonthOut);
        spinnerMonth.setAdapter(new SelectMonthAdapter(getActivity(), R.layout.month_sel_row, CategoryClass.MONTHS));
        spinnerMonth.setOnItemSelectedListener(this);
        spinnerMonth.setSelection(month_index);

        initCategoryViews(v);
        screenWidht=getScreenWidth();
        dataMap=new HashMap<Integer, Integer>();
        SQLiteAdapter sqLiteAdapter=new SQLiteAdapter(getActivity());
        dataMap=sqLiteAdapter.getAllDataFromMonthlyTable(IN_OUT_FLAG, year_month);
        dataMap=normalizeMapFromDatabase(dataMap, graphStartDateInFormat);
//        Message.message(context, "sele="+CategoryClass.MONTHS[month_index]);


        setCatPercent(year_month);
        setCatView(v);

        SortedSet<Integer> sortedKeys=new TreeSet<Integer>(dataMap.keySet());
        entries=new ArrayList<Entry>();
        labels = new ArrayList<String>();

        fillAllCordinates(sortedKeys);
        //askdjflkasjflska

        lineChart=new LineChart(context);
        lineLl=(LinearLayout)v.findViewById(R.id.outComeLl);


        dataSet=new LineDataSet(entries, GRAPH_LABLE+year_month);
        dataSet.setColor(getResources().getColor(R.color.outgoingColor));
        data=new LineData(labels, dataSet);
        lineChart.setData(data);
        lineChart.animateY(3000, Easing.EasingOption.EaseInBounce);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setDrawGridLines(false);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setDrawTopYLabelEntry(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");
        lineChart.setBackgroundColor(getResources().getColor(R.color.materialWhite));

        lineLl.addView(lineChart);

        ////



        }

    private void initCategoryViews(View v)
        {
        cat1=(ImageView)v.findViewById(R.id.cat1Image);
        n1=(TextView)v.findViewById(R.id.cat1Name);
        l1=v.findViewById(R.id.cat1Line);
        pc1=(TextView)v.findViewById(R.id.cat1Percent);


        cat2=(ImageView)v.findViewById(R.id.cat2Image);
        n2=(TextView)v.findViewById(R.id.cat2Name);
        l2=v.findViewById(R.id.cat2Line);
        pc2=(TextView)v.findViewById(R.id.cat2Percent);


        cat3=(ImageView)v.findViewById(R.id.cat3Image);
        n3=(TextView)v.findViewById(R.id.cat3Name);
        l3=v.findViewById(R.id.cat3Line);
        pc3=(TextView)v.findViewById(R.id.cat3Percent);


        cat4=(ImageView)v.findViewById(R.id.cat4Image);
        n4=(TextView)v.findViewById(R.id.cat4Name);
        l4=v.findViewById(R.id.cat4Line);
        pc4=(TextView)v.findViewById(R.id.cat4Percent);


        cat5=(ImageView)v.findViewById(R.id.cat5Image);
        n5=(TextView)v.findViewById(R.id.cat5Name);
        l5=v.findViewById(R.id.cat5Line);
        pc5=(TextView)v.findViewById(R.id.cat5Percent);


        cat6=(ImageView)v.findViewById(R.id.cat6Image);
        n6=(TextView)v.findViewById(R.id.cat6Name);
        l6=v.findViewById(R.id.cat6Line);
        pc6=(TextView)v.findViewById(R.id.cat6Percent);


        cat7=(ImageView)v.findViewById(R.id.cat7Image);
        n7=(TextView)v.findViewById(R.id.cat7Name);
        l7=v.findViewById(R.id.cat7Line);
        pc7=(TextView)v.findViewById(R.id.cat7Percent);

        cat8=(ImageView)v.findViewById(R.id.cat8Image);
        n8=(TextView)v.findViewById(R.id.cat8Name);
        l8=v.findViewById(R.id.cat8Line);
        pc8=(TextView)v.findViewById(R.id.cat8Percent);


        cat9=(ImageView)v.findViewById(R.id.cat9Image);
        n9=(TextView)v.findViewById(R.id.cat9Name);
        l9=v.findViewById(R.id.cat9Line);
        pc9=(TextView)v.findViewById(R.id.cat9Percent);


        cat10=(ImageView)v.findViewById(R.id.cat10Image);
        n10=(TextView)v.findViewById(R.id.cat10Name);
        l10=v.findViewById(R.id.cat10Line);
        pc10=(TextView)v.findViewById(R.id.cat10Percent);


        cat11=(ImageView)v.findViewById(R.id.cat11Image);
        n11=(TextView)v.findViewById(R.id.cat11Name);
        l11=v.findViewById(R.id.cat11Line);
        pc11=(TextView)v.findViewById(R.id.cat11Percent);

        cat12=(ImageView)v.findViewById(R.id.cat12Image);
        n12=(TextView)v.findViewById(R.id.cat12Name);
        l12=v.findViewById(R.id.cat12Line);
        pc12=(TextView)v.findViewById(R.id.cat12Percent);



        }


    private void fillAllCordinates(SortedSet<Integer> sortedKeys)
        {

        int x=0, y=0;
        for(Integer timeStamps:sortedKeys)
        {

        entries.add(new Entry(y, x));
        y=dataMap.get(timeStamps);
        labels.add(""+x);
        x++;
        }
        }

///############################## RETURN SCREEN WIDTH ###############################################

    private int getScreenWidth()
        {

        Display display=getActivity().getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        return size.x-200;
        }

//###############################  SETTTING ALL CATEGORY PERCENTAGE ###################################

    private void setCatPercent(String year_month)
        {

        SQLiteAdapter adapter=new SQLiteAdapter(getActivity());
        groceryPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.GROC,IN_OUT_FLAG, year_month);
//        Message.message(context, "Percentage="+groceryPcnt);
        foodPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.FOOD, IN_OUT_FLAG, year_month);
        shopPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.SHOP, IN_OUT_FLAG, year_month);
        educationPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.EDU, IN_OUT_FLAG, year_month);
        maintainPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.MAINT, IN_OUT_FLAG, year_month);
        personPct=adapter.getAllCategoryFromDailyTable(CategoryClass.PERSO, IN_OUT_FLAG, year_month);
        healthPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.HEALTH, IN_OUT_FLAG, year_month);
        entertainPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.ENTERT,IN_OUT_FLAG, year_month);
        travelPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.TRAVEL, IN_OUT_FLAG, year_month);
        savePcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.SAVE, IN_OUT_FLAG, year_month);
        homePcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.HOME, IN_OUT_FLAG, year_month);
        miscelloPcnt=adapter.getAllCategoryFromDailyTable(CategoryClass.MISCAL, IN_OUT_FLAG, year_month);


        catMap=new HashMap<Integer, String >();
        catMap.put(groceryPcnt, CategoryClass.GROC);

        if(catMap.containsKey(foodPcnt)){ catMap.put(foodPcnt, catMap.get(foodPcnt)+":"+ CategoryClass.FOOD);}
        else{catMap.put(foodPcnt, CategoryClass.FOOD); }



        if(catMap.containsKey(shopPcnt)) {catMap.put(shopPcnt, catMap.get(shopPcnt)+":"+CategoryClass.SHOP);}
        else {catMap.put(shopPcnt, CategoryClass.SHOP);}

        if(catMap.containsKey(educationPcnt)) {catMap.put(educationPcnt,catMap.get(educationPcnt)+":"+CategoryClass.EDU);}
        else { catMap.put(educationPcnt, CategoryClass.EDU);}

        if(catMap.containsKey(maintainPcnt))  {catMap.put(maintainPcnt, catMap.get(maintainPcnt)+":"+CategoryClass.MAINT);}
        else {catMap.put(maintainPcnt, CategoryClass.MAINT);}

        if(catMap.containsKey(personPct)) {catMap.put(personPct, catMap.get(personPct)+":"+CategoryClass.PERSO);}
        else {catMap.put(personPct, CategoryClass.PERSO);}

        if(catMap.containsKey(healthPcnt)) {catMap.put(healthPcnt,catMap.get(healthPcnt)+":"+CategoryClass.HEALTH);}
        else catMap.put(healthPcnt, CategoryClass.HEALTH);

        if(catMap.containsKey(entertainPcnt)) catMap.put(entertainPcnt,catMap.get(entertainPcnt)+":"+CategoryClass.ENTERT);
        else catMap.put(entertainPcnt, CategoryClass.ENTERT);

        if(catMap.containsKey(travelPcnt)) catMap.put(travelPcnt, catMap.get(travelPcnt)+":"+CategoryClass.TRAVEL);
        else catMap.put(travelPcnt, CategoryClass.TRAVEL);

        if(catMap.containsKey(savePcnt)) catMap.put(savePcnt, catMap.get(savePcnt)+":"+CategoryClass.SAVE);
        else catMap.put(savePcnt, CategoryClass.SAVE);

        if(catMap.containsKey(homePcnt)) catMap.put(homePcnt,catMap.get(homePcnt)+":"+CategoryClass.HOME);
        else catMap.put(homePcnt, CategoryClass.HOME);

        if(catMap.containsKey(miscelloPcnt)) catMap.put(miscelloPcnt, catMap.get(miscelloPcnt)+":"+CategoryClass.MISCAL);
        else catMap.put(miscelloPcnt, CategoryClass.MISCAL);


        }


////#################################  SETTING ALL THE VIEW OF #######################################


    private void setCatView(View v)
        {

        SortedSet<Integer> sortedKeys=new TreeSet<Integer>(catMap.keySet());
        List<String > extractedCat=new ArrayList<String >();
        List<Integer> extractedPcnt=new ArrayList<Integer>();

        for(int sortedPcnt:sortedKeys)
        {
        for(String splittdCatvalue:catMap.get(sortedPcnt).split(":"))
        {
        extractedPcnt.add(sortedPcnt);
        extractedCat.add(splittdCatvalue);
        }
        }
        int i=0;
        String text="";
        for(Integer sortedPcnt:extractedPcnt)
        {

        switch (i)
        {
        case 11:


        cat1.setImageResource(resolveIcon(extractedCat.get(i)));
        l1.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n1.setText(extractedCat.get(i));
        pc1.setText(sortedPcnt + "%");

        break;

        case 10:
        cat2.setImageResource(resolveIcon(extractedCat.get(i)));
        l2.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n2.setText(extractedCat.get(i));
        pc2.setText(sortedPcnt + "%");
        break;

        case 9:

        cat3.setImageResource(resolveIcon(extractedCat.get(i)));
        l3.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n3.setText(extractedCat.get(i));
        pc3.setText(sortedPcnt + "%");
        break;
        case 8:

        cat4.setImageResource(resolveIcon(extractedCat.get(i)));
        l4.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n4.setText(extractedCat.get(i));
        pc4.setText(sortedPcnt+"%");
        break;
        case 7:

        cat5.setImageResource(resolveIcon(extractedCat.get(i)));
        l5.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n5.setText(extractedCat.get(i));
        pc5.setText(sortedPcnt + "%");
        break;
        case 6:

        cat6.setImageResource(resolveIcon(extractedCat.get(i)));
        l6.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n6.setText(extractedCat.get(i));
        pc6.setText(sortedPcnt + "%");
        break;
        case 5:

        cat7.setImageResource(resolveIcon(extractedCat.get(i)));
        l7.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n7.setText(extractedCat.get(i));
        pc7.setText(sortedPcnt+"%");
        break;
        case 4:

        cat8.setImageResource(resolveIcon(extractedCat.get(i)));
        l8.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n8.setText(extractedCat.get(i));
        pc8.setText(sortedPcnt + "%");
        break;
        case 3:

        cat9.setImageResource(resolveIcon(extractedCat.get(i)));
        l9.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n9.setText(extractedCat.get(i));
        pc9.setText(sortedPcnt + "%");
        break;
        case 2:

        cat10.setImageResource(resolveIcon(extractedCat.get(i)));
        l10.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n10.setText(extractedCat.get(i));
        pc10.setText(sortedPcnt + "%");

        break;
        case 1:

        cat11.setImageResource(resolveIcon(extractedCat.get(i)));
        l11.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n11.setText(extractedCat.get(i));
        pc11.setText(sortedPcnt + "%");
        break;
        case 0:

        cat12.setImageResource(resolveIcon(extractedCat.get(i)));
        l12.getLayoutParams().width=screenWidht*sortedPcnt/100;
        n12.setText(extractedCat.get(i));
        pc12.setText(sortedPcnt+"%");
        break;




        }

        i++;
        }

        // Message.message(getActivity(), text);

        }



///########################## RESOLVE RESOURCES OF A CATEGORY ######################################

    private int resolveIcon(String category)
        {

        switch (category)
        {
        case CategoryClass.GROC: return R.drawable.tarkari100;
        case CategoryClass.FOOD: return R.drawable.food100;
        case CategoryClass.SHOP: return R.drawable.shoping100;
        case CategoryClass.EDU: return R.drawable.education100;
        case CategoryClass.MAINT: return R.drawable.maintainance100;
        case CategoryClass.PERSO: return R.drawable.personal100;
        case CategoryClass.MISCAL: return R.drawable.miscallenous100;
        case CategoryClass.ENTERT: return R.drawable.entertain100;
        case CategoryClass.HEALTH: return R.drawable.health100;
        case CategoryClass.TRAVEL: return R.drawable.travel100;
        case CategoryClass.SAVE: return R.drawable.coins100;
        case CategoryClass.HOME: return R.drawable.home_expense100;
        }

        return 0;
        }




//########################################## NORMALIZE THE ONE MONTH VALUE INTO 31 VALUES #######################################

    private Map<Integer, Integer> normalizeMapFromDatabase(Map<Integer, Integer> map, String graphStartDate)
        {
        Integer startTimeStamp=(int)(DateAndTimeStamp.returnTimeStamp(graphStartDate));

        for(int i=0; i<32;i++)
        {
        if(map.containsKey(startTimeStamp)){}
        else
        map.put(startTimeStamp, 0);
        startTimeStamp+=86400;

        }

        return map;
        }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {


        if(parent.getId()==R.id.selectYearOut)
        {
          year_index = position;
        }


        if(parent.getId()==R.id.selectMonthOut)
        {
        month_index = position;

        }

        refreshGraph();


}

    @Override
    public void onNothingSelected(AdapterView<?> parent)
        {
        month_index=currntMonth;
        year_index=0;

        }


    public void refreshGraph()
        {

        year_month=yearList[year_index]+"/"+(month_index+1);
        graphStartDateInFormat=year_month+"/1 00:00";

        dataMap=new HashMap<Integer, Integer>();
        SQLiteAdapter sqLiteAdapter=new SQLiteAdapter(getActivity());
        dataMap=sqLiteAdapter.getAllDataFromMonthlyTable(IN_OUT_FLAG, year_month);


        dataMap=normalizeMapFromDatabase(dataMap, graphStartDateInFormat);
        View view=getActivity().getLayoutInflater().inflate(R.layout.outgoing_layout, null);
        ((LinearLayout)view.findViewById(R.id.catIncludeOut)).removeAllViews();
        view=getActivity().getLayoutInflater().inflate(R.layout.outgoing_layout, null);
        setCatPercent(year_month);
        setCatView(view);


        SortedSet<Integer> sortedKeys=new TreeSet<Integer>(dataMap.keySet());
        entries=new ArrayList<Entry>();
        labels = new ArrayList<String>();

        fillAllCordinates(sortedKeys);
        //askdjflkasjflska
        lineLl.removeAllViews();
        dataSet=new LineDataSet(entries, GRAPH_LABLE+year_month);
        dataSet.setColor(getResources().getColor(R.color.outgoingColor));
        data=new LineData(labels, dataSet);
        lineChart.setData(data);
        lineChart.animateY(3000, Easing.EasingOption.EaseInBounce);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setDrawGridLines(false);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setDrawTopYLabelEntry(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");
        lineChart.setBackgroundColor(getResources().getColor(R.color.materialWhite));
        lineLl.removeAllViews();
        lineLl.addView(lineChart);


        }

    @Override
    public void onResume()
    {
        super.onResume();
        refreshGraph();
    }

}




