package trees.money_calendar.com.moneycalander;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.Message;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;


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
    private Button editButton, desButton;
    private EditText amount, description;
    private String categoryName;
    private Toolbar appBar;
    private Calendar c;
    private int in_or_out, previousInOut;
    private long uId=0;
    private long previousTimeStamp=0;
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
        String year, month, days;
        if(obtainedTuple!=null)
        {
            previousAmount=obtainedTuple[0];
            amount.setText(previousAmount);
            previousDate=obtainedTuple[1];
            year=(previousDate.split("/")[0]);
            month=(previousDate.split("/")[1]);
            days=(previousDate.split("/")[2]);
            spinner.setSelection(CategoryClass.getCategoryPosition(obtainedTuple[2]));
            description.setText(obtainedTuple[3]);
            if(obtainedTuple[4].equals("IN")) inFlowRadio.setChecked(true);
            else outFlowRadio.setChecked(true);
            previousTime=obtainedTuple[5];
            previousYear_Month=year+"/"+month;
        }





    }



    private void initialize()
    {


        description = (EditText) findViewById(R.id.addDescription);
        inFlowRadio = (RadioButton) findViewById(R.id.inFlowRadio);
        outFlowRadio = (RadioButton) findViewById(R.id.outFlowRadio);
        amount = (EditText)findViewById(R.id.addAmount);


        ///DATE PARTS


        ////BUTTON PARTS
        editButton=(Button)findViewById(R.id.editButton);
        desButton=(Button)findViewById(R.id.desButton);
        editButton.setOnClickListener(this);
        desButton.setOnClickListener(this);


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
                String amnt=amount.getText().toString();
                String dscrptn=description.getText().toString();
                if(inFlowRadio.isChecked()) in_or_out=1;
                if(outFlowRadio.isChecked()) in_or_out=0;


                SQLiteAdapter dbAdapter=new SQLiteAdapter(this);

                //this line is used to update the data on daily table
                dbAdapter.updateDailyTable(amnt, categoryName, dscrptn, previousDate, in_or_out, timeStamp, previousYear_Month, uId);

                /*these two lines will update the monthtly table
                *
                * ist line reduces the amount that was previously stored on a tuple
                * 2nd line insertes the newamount on the new tuple of monthly table
                 */


                dbAdapter.updateIntoMonthlyTable(previousInOut, previousTimeStamp,  previousAmount);
                dbAdapter.updateIntoMonthlyTable(in_or_out, previousTimeStamp, "-"+amnt);

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
