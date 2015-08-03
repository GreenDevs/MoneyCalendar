package trees.money_calendar.com.moneycalander;



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


public class AddActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    /*
    *
    * THIS ACTIVITY IS USED TO TAKE THE ENTRY FROM 0630189A 2055/12/27 USER.. USER HAS TO INSERT AMOUNT COMPUSALYLY REST FIELDS ARE OPTIONAL.
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
     */


    ////JUST INITIALIZATION OF ALL THE VIEWS AND REQUIRED REFRENCES
    private RadioButton inFlowRadio, outFlowRadio;
    private Spinner spinner;
    private Button addButton, desButton, timeButton;
    private EditText days, month, year, amount, description;
    private String categoryName;
    private Toolbar appBar;
    private TimePickerFragment timeFragment;
    private Calendar c;
    private int curntYr,curntMnth, curntDays, in_or_out;



    //################# CATEGORIES ########################
    private String list[] = {CategoryClass.MISCAL,CategoryClass.GROC, CategoryClass.FOOD,CategoryClass.SHOP,CategoryClass.EDU,
            CategoryClass.PERSO,CategoryClass.MAINT, CategoryClass.ENTERT,CategoryClass.HEALTH, CategoryClass.TRAVEL,
            CategoryClass.SAVE,CategoryClass.HOME};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initialize();
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



        ///DATE PARTS
        days = (EditText) findViewById(R.id.addDay);            days.setText(""+curntDays);         //Setting
        month = (EditText)findViewById(R.id.addMonth);          month.setText(""+curntMnth);        //Date Edit Text
        year = (EditText) findViewById(R.id.addYear);           year.setText(curntYr+"");           //By the current date
        amount = (EditText)findViewById(R.id.addAmount);



        ////BUTTON PARTS
        addButton=(Button)findViewById(R.id.addButton);
        desButton=(Button)findViewById(R.id.desButton);
        timeButton=(Button)findViewById(R.id.timeButton);
        addButton.setOnClickListener(this);
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

        if(v.getId()==R.id.addButton)
        {
            if(validation())
            {
                //setting a data string variable from the user enetered values
                int month_index=Integer.parseInt(month.getText().toString());
                String year_month=year.getText()+"/"+month_index;
                String date=year.getText().toString()+"/"+(month_index)+"/"+days.getText().toString();
                String amnt=amount.getText().toString();
                String dscrptn=description.getText().toString();
                if(inFlowRadio.isChecked()) in_or_out=1;
                if(outFlowRadio.isChecked()) in_or_out=0;
                String time=" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);   //By default time variable store current hour and minutes
                if(timeFragment!=null) time=timeFragment.getTime();            //getting time from the timeFragment Because user sets the time in this fragment
                                                                              //if user has set timeFragment will not be null and it contains user set time values


               /*
               ######################DATABSE ENTRY PART########################
                */


                SQLiteAdapter dbAdapter=new SQLiteAdapter(this);
                dbAdapter.insertIntoDailyTable(amnt, categoryName, dscrptn, date, in_or_out,DateAndTimeStamp.returnTimeStamp(date+time), year_month);
                dbAdapter.insertIntoMonthlyTable(amnt, DateAndTimeStamp.returnTimeStamp(date+" 00:00"), in_or_out, year_month);;
               // Message.message(this, date+time);



                ///CLEANING ALL THE FILELDS ARD RESETTING THEM TO DEFAULT VALUES AFTER USER ENTRY
                description.setText("");
                amount.setText("");
                if(timeFragment!=null) timeFragment.resetTime();
                year.setText(curntYr+"");
                month.setText(curntMnth+"");
                days.setText(curntDays+"");

                Message.message(this, "ADDED SUCCESSFULLY");

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
