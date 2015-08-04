package trees.money_calendar.com.moneycalander;



import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.CategoryClass;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.CategoryAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.DateAndTimeStamp;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.Message;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.TimePickerFragment;


public class EditActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    /*
    *
    * THIS ACTIVITY IS USED TO EDIT THE ENTRY FROM  THE USER.. USER CAN EDIT FIELDS .
    * AMOUNT, CATEGORY, INFLOW OR OUTFLOW, DATE, TIME, DESCRIPTION ARE THE FIELDS
    *
    * THIS Activity has folowing methods
    *
    *
    * 1)initialization()
    * THIS METHOD DOES NOTHING BUT INCREASES THE READABLITY OF THE CLASS
    * INITIALIZATION OF ALL THE XML VIEW AND REQUIRED VARIABELS IS DONE HERE
    *
    *
    * 2)Validation()
    *
    * This method is used to validate all the fileds specified by the user and returns
    * true  -> If entered data is valid
    * false -> If entered data is invalid
    *
    * 3)OnClick Method
    *
    *
    * 4)resolveCategory()
    *
    * This method only returns a category name on selection of the spinner item
    * *
    * 5)initializeViewFromDatabase()
    * This method gets the intent from another activity. and the intent has the UID, TIME_STAMP and In_Or_Out.
     * which helps from taking the previous data of the item in list clicked to be editable
     */


    //VARIABLE INTRODUCTION
    /*
    **
    * -> radiobuttons: inflow and outflow are used to hold the incoming or outgoing money flag
    * ->spinner: it is used to show the category listing
    * ->editButton: for confirming edit option, desButton: to trigger the description option, timeButton: for time picker
    * ->EditText: year, month, days is used to make date:year/month/days, description: used to keep the description
    * ->categoryName: used to keep the selected category of spinner
    * ->appbar: this is used to put the app bar  in the app
    * ->timeFragment: it is used to refer the time picker and take the time value set by it
    * ->currentYr, curntMnth, curntDays, are used to store the current year month and current days
    * ->in_or_out is flag that stores incoming or outgoing value, previousInOrOut hold previous in our selection
    * ->uId is used to take the primary key of the selected row on the list
    * ->previousTimeStamp stores the time stamp of the selected list item
     * ->similar previous values are stored on previousDate, previousYearMonth, previousAmount, previousTime;
     */
    private RadioButton inFlowRadio, outFlowRadio;
    private Spinner spinner;
    private Button editButton, desButton, timeButton;
    private EditText days, month, year, amount, description;
    private String categoryName;
    private Toolbar appBar;
    private TimePickerFragment timeFragment;
    private Calendar c;
    private int curntYr,curntMnth, curntDays, in_or_out, previousInOut;
    private long uId=0;
    private long previousTimeStamp=0;
    private boolean watchClicked=false;
    private String previousDate="";
    private String previousYear_Month="";
    private String previousAmount="";
    private String previousTime="";



    //################# CATEGORIES ########################
    private String list[] = {CategoryClass.MISCAL,CategoryClass.GROC, CategoryClass.FOOD,CategoryClass.SHOP,CategoryClass.EDU,
            CategoryClass.PERSO,CategoryClass.MAINT, CategoryClass.ENTERT,CategoryClass.HEALTH, CategoryClass.TRAVEL,
            CategoryClass.SAVE,CategoryClass.HOME};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initialize();
        initializeViewFromDatabase();
    }




    private void initializeViewFromDatabase()
    {
        SQLiteAdapter adapter=new SQLiteAdapter(this);

        Bundle maalHaru=getIntent().getBundleExtra("maalkobundle");
        uId=maalHaru.getLong("uid");
        previousInOut=maalHaru.getByte("in_or_out", (byte) 0);
        previousTimeStamp=maalHaru.getLong("time_stamp");
        String[] obtainedTuple=adapter.getDataFromDailyTable(uId).split("//");

        if(obtainedTuple!=null)
        {
            previousAmount=obtainedTuple[0];
            amount.setText(previousAmount);
            previousDate=obtainedTuple[1];
            year.setText(previousDate.split("/")[0]);
            month.setText(previousDate.split("/")[1]);
            days.setText(previousDate.split("/")[2]);
            spinner.setSelection(CategoryClass.getCategoryPosition(obtainedTuple[2]));
            description.setText(obtainedTuple[3]);
            if(obtainedTuple[4].equals("IN")) inFlowRadio.setChecked(true);
            else outFlowRadio.setChecked(true);
            previousTime=obtainedTuple[5];
            previousYear_Month=year.getText().toString()+"/"+month.getText().toString();
        }





    }



    private void initialize()
    {

        //CURRENT DATE ATTRIBUTES AND VALUES
        c=Calendar.getInstance();
        curntYr=c.get(Calendar.YEAR);
        curntMnth=c.get(Calendar.MONTH); curntMnth++;         ///CURRENT MONTTH IS INCREASED BY ONE BECAUSE CALENDAR INDEXS MONTH FROM 0
        curntDays=c.get(Calendar.DAY_OF_MONTH);
        description = (EditText) findViewById(R.id.addDescription);
        inFlowRadio = (RadioButton) findViewById(R.id.inFlowRadio);
        outFlowRadio = (RadioButton) findViewById(R.id.outFlowRadio);


        days = (EditText) findViewById(R.id.addDay);            days.setText(""+curntDays);         //Setting
        month = (EditText)findViewById(R.id.addMonth);          month.setText(""+curntMnth);        //Date Edit Text
        year = (EditText) findViewById(R.id.addYear);           year.setText(curntYr+"");           //By the current date
        amount = (EditText)findViewById(R.id.addAmount);


        ///DATE PARTS


        ////BUTTON PARTS
        editButton=(Button)findViewById(R.id.editButton);
        desButton=(Button)findViewById(R.id.desButton);
        timeButton=(Button)findViewById(R.id.timeButton);
        editButton.setOnClickListener(this);
        desButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);


        //SPINNER PARTS

        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        spinner.setAdapter(new CategoryAdapter(this, R.layout.category_row, list));
        spinner.setOnItemSelectedListener(this);


        //APP BAAR PARTS

        appBar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }





    //This method validates the form
    private boolean validation()
    {

        boolean flag=true;  //this flag is used to return flag depeding upon the data given by user


        /*
        *   ######################## DAY VALIDATION ###############################
        *
        *   ->Check if the day is null, or empty If empty or null then simply it
        *       changes the hint of day to "empty"
        *
        *   ->If not empty and not null then do further validation
        *
        *   ->Then pull data from date edit text for comparison.. If days lies in between 0 and current date
        *       then it is valid
        *       else
        *       Check other conditions
        *                  -> if entered month and current month is not same then it is valid with days greater
        *                     than current days and text color as black
        *                     else
        *                     setText as in red color
        *
         */

        if(days!=null && !days.getText().toString().equals(""))
        {
            int dys = Integer.parseInt(days.getText().toString());
            if(dys>0 &&  dys<=curntDays)
            {
                days.setTextColor(this.getResources().getColor(R.color.primaryText));
            }
            else
            {
                int mnts=0;
                if(month!=null && !month.getText().toString().equals(""))
                {
                    mnts = Integer.parseInt(month.getText().toString());
                }

                if(mnts!=curntMnth && dys<32 && dys>0)
                {
                    days.setTextColor(this.getResources().getColor(R.color.primaryText));
                }
                else
                {
                    days.setTextColor(this.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
        }
        else
        {
            days.setHint("dd");
            flag=false;
        }



        /*
        *
        * ######################## MONTH VALIDATION ################################
        *
        * ->Ist checking month if it is null or empty if true changing hint as empty
        *      else
        *      then taking the months value and checking the range within 0 and current month
        *       if true "then it is valid"
        *      else
        *      then checking year.. if year=currentyear-1 and entered month lies in between 0 and 13
        *       then it is valid
        *       else
        *       setting text color red
         */

        if(month!=null && !month.getText().toString().equals(""))
        {


            int mnts = Integer.parseInt(month.getText().toString());
            if(mnts>0 &&  mnts<=curntMnth)
            {
                month.setTextColor(this.getResources().getColor(R.color.primaryText));
            }
            else
            {
                int yr=0;

                if(year!=null && !year.getText().toString().equals(""))
                {
                    yr = Integer.parseInt(year.getText().toString());
                }
                if(curntMnth<=2 && yr==curntYr-1 && mnts>0 && mnts<=12)
                {
                    Log.i("YEAR", "2014");
                    month.setTextColor(this.getResources().getColor(R.color.primaryText));
                }
                else {
                    month.setTextColor(this.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
        }
        else
        {
            month.setHint("mm");
            flag=false;
        }



        /*
        *
        * ########################## YEAR VALIDATION ################################
        *
        * ->Checking year if it is empty or null if true set year color red
        *   else
        *   take the year value and set true only if entered year is equal to
        *   current year or if entered year is less than by 1 of current year if the current moth<=2
         */
        if(year!=null && !year.getText().toString().equals(""))
        {



            int yr = Integer.parseInt(year.getText().toString());
            if(yr!=curntYr)
            {
                if (yr == curntYr - 1 && curntMnth <= 2 )
                {
                    year.setTextColor(this.getResources().getColor(R.color.primaryText));
                }
                else
                {
                    year.setTextColor(this.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
            else
                year.setTextColor(this.getResources().getColor(R.color.primaryText));
        }
        else
        {
            year.setHint("yyyy");
            flag=false;
        }


        if(amount==null || amount.getText().toString().equals(""))
        {
            amount.setHint("empty");
            flag=false;
        }

        if(description==null || description.getText().toString().equals(""))
        {
            description.setHint("empty");

            if(inFlowRadio.isChecked())
            {
                description.setText("I earned  money from " + categoryName);
            }
            else
            {
                description.setText("I spent money  on " + categoryName );
            }

        }

        if(!(inFlowRadio.isChecked() || outFlowRadio.isChecked()))
        {
            flag=false;
        }

        return flag;

    }



    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.editButton)
        {
            if(validation())
            {
                long timeStamp=0;
                //setting a data string variable from the user enetered values
                int month_index=Integer.parseInt(month.getText().toString());
                String year_month=year.getText()+"/"+month_index;
                String date=year_month+"/"+days.getText().toString();
                String amnt=amount.getText().toString();
                String dscrptn=description.getText().toString();
                if(inFlowRadio.isChecked()) in_or_out=1;
                if(outFlowRadio.isChecked()) in_or_out=0;
                String time=previousTime;                                       //By default time variable store current hour and minutes
                if(timeFragment!=null) time=timeFragment.getTime();            //getting time from the timeFragment Because user sets the time in this fragment
                //if user has set timeFragment will not be null and it contains user set time values

                long newTimeStamp=DateAndTimeStamp.returnTimeStamp(date+time);   //If any changges on date has beeen made newTimeStamp will store the value

                if(previousTimeStamp!=newTimeStamp) timeStamp=newTimeStamp;   //if timeStampNot same update timestamp else old timestamp
                else  timeStamp=previousTimeStamp;

               /*
               ######################DATABSE ENTRY PART########################
                */


                SQLiteAdapter dbAdapter=new SQLiteAdapter(this);

                //this line is used to update the data on daily table
                dbAdapter.updateDailyTable(amnt, categoryName, dscrptn, date, in_or_out, timeStamp, year_month, uId);

                /*these two lines will update the monthtly table
                *
                * ist line reduces the amount that was previously stored on a tuple
                * 2nd line insertes the newamount on the new tuple of monthly table
                 */


                dbAdapter.insertIntoMonthlyTable("-"+previousAmount, previousTimeStamp, previousInOut, previousYear_Month);
                dbAdapter.insertIntoMonthlyTable(amnt, newTimeStamp, in_or_out, year_month);

//                Message.message(this, "time="+time);


//                ///CLEANING ALL THE FILELDS ARD RESETTING THEM TO DEFAULT VALUES AFTER USER ENTRY
//                description.setText("");
//                amount.setText("");
//                if(timeFragment!=null) timeFragment.resetTime();
//                year.setText(curntYr+"");
//                month.setText(curntMnth+"");
//                days.setText(curntDays+"");

                Message.message(this, "EDITED SUCCESSFULLY");
                this.finish();

            }
            else
                Toast.makeText(this, "Fill the valid data", Toast.LENGTH_SHORT).show();




        }

        if(v.getId()==R.id.desButton)
        {
            description.setVisibility(View.VISIBLE);
            desButton.setVisibility(View.GONE);

        }

        if(v.getId()==R.id.timeButton)
        {
            watchClicked=true;
            timeFragment=new TimePickerFragment();
            timeFragment.show(getSupportFragmentManager(), "TIME PICKER");


        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {

            return true;
        }

        if (id == android.R.id.home)
        {
            this.finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        categoryName = list[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        categoryName=list[0];

    }


}