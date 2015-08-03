package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.SingleRow;


/**set
 * Created by root on 06/06/15.
 */

public class SQLiteAdapter
{
    private SQLiteHelper sqLiteHelper;
    private Context context;

    
   public SQLiteAdapter(Context context)
    {
        this.context=context;
        sqLiteHelper=new SQLiteHelper(context);
    }

    public long insertIntoDailyTable(String amount,String category,String description,String date,int in_or_out, long time_stamp, String year_month)
    {

        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(SQLiteHelper.DATE_TIME,date);
        contentValues.put(SQLiteHelper.CATEGORY,category);
        contentValues.put(SQLiteHelper.AMOUNT,amount);
        contentValues.put(SQLiteHelper.DESCRIPTION,description);
        contentValues.put(SQLiteHelper.IN_OR_OUT, in_or_out);
        contentValues.put(SQLiteHelper.TIME_STAMP, time_stamp);
        contentValues.put(SQLiteHelper.YEAR_MONTH, year_month);



        long id=db.insert(SQLiteHelper.TABLE_NAME_DAILY,null,contentValues);
        db.close();

        return id;
    }


    public  void deleteRowFromDailyTable(long id)
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();
        String args[]={Long.toString(id)};
        db.delete(SQLiteHelper.TABLE_NAME_DAILY,SQLiteHelper.UID + "=? ", args);


        db.close();

    }



    public void insertIntoMonthlyTable(String amnt, long timeStamp, int in__or_out,String year_month)
    {

        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();
        String[] columns_tableMonthly={SQLiteHelper.TIME_STAMP, SQLiteHelper.AMOUNT, SQLiteHelper.IN_OR_OUT};
        Cursor cursor=db.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME_MONTHLY+" WHERE "+SQLiteHelper.TIME_STAMP+ " = "+timeStamp +" AND "+ SQLiteHelper.IN_OR_OUT+" = "+in__or_out+" ;", null);

        ContentValues contentValues=new ContentValues();
        contentValues.put(SQLiteHelper.AMOUNT,amnt);
        contentValues.put(SQLiteHelper.IN_OR_OUT,in__or_out);
        contentValues.put(SQLiteHelper.TIME_STAMP,timeStamp);
        contentValues.put(SQLiteHelper.YEAR_MONTH,year_month);


        if(cursor.getCount()<=0)                  //being true means no tuples with same timestamp and same inorout were found as entered by user
                                                  //so we must directly add a entered value without comparing
        {

            long id=db.insert(SQLiteHelper.TABLE_NAME_MONTHLY,null,contentValues);
        }
        else
        {
            cursor.moveToNext();

           int columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);
           long updateAmount=Long.parseLong(cursor.getString(columnIndex_Amount))+ Long.parseLong(amnt);
           String amount=updateAmount+"";
           ContentValues contentValue=new ContentValues();
           contentValue.put(SQLiteHelper.AMOUNT, amount);
//           contentValue.put(SQLiteHelper.YEAR_MONTH, year_month);
           String args[]={Long.toString(timeStamp), in__or_out+""};
           db.update(SQLiteHelper.TABLE_NAME_MONTHLY,contentValue, SQLiteHelper.TIME_STAMP+"=? AND "+SQLiteHelper.IN_OR_OUT+"=?", args );
        }

        //getAllDataFromMonthlyTable(1);
        cursor.close();
        db.close();
        //Message.message_longTime(context,cursor.getCount()+"");
    }



    public void updateIntoMonthlyTable(int in_or_out, long time_stamp, String reciveAmnt)
    {

        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();


        Cursor cursor=db.rawQuery("SELECT "+SQLiteHelper.AMOUNT+" FROM "+SQLiteHelper.TABLE_NAME_MONTHLY+" WHERE "+SQLiteHelper.TIME_STAMP+ " = "+time_stamp +" AND "+ SQLiteHelper.IN_OR_OUT+" = "+in_or_out+" ;", null);
        int columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);
        String datBaseAmount="0";
        while (cursor.moveToNext())
        {
           datBaseAmount=cursor.getString(columnIndex_Amount);
        }

        cursor.close();
        String updatedAmount=(Integer.parseInt(datBaseAmount)-Integer.parseInt(reciveAmnt))+"";
        String args[]={Long.toString(time_stamp), in_or_out+""};
        ContentValues contentValues=new ContentValues();
        contentValues.put(SQLiteHelper.AMOUNT, updatedAmount);
        long effectedRows=db.update(SQLiteHelper.TABLE_NAME_MONTHLY, contentValues, SQLiteHelper.TIME_STAMP + "=? AND " + SQLiteHelper.IN_OR_OUT + "=?", args);
//        Message.message(context, "time stamp="+time_stamp+"  in_or_out_flag="+in_or_out+  " AMount="+reciveAmnt);

        db.close();

    }



    ///////////////////////////////
    public ArrayList<SingleRow> getAllDataFromDailyTable()
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();

//     select  Date, Category, Amount, Description from Table -> Incoming.
        String[] allColumnsFrom_tableDaily={SQLiteHelper.UID,SQLiteHelper.DATE_TIME,SQLiteHelper.CATEGORY,SQLiteHelper.AMOUNT,SQLiteHelper.DESCRIPTION,SQLiteHelper.IN_OR_OUT};
        Cursor cursor=db.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME_DAILY+" ORDER BY "+SQLiteHelper.TIME_STAMP+" DESC;",null);


        ///////////////////////////////
        String date,category,amount,description,inOut="OUT";
        int uid, columnIndex_uid, columnIndex_date, columnIndex_category, columnIndex_Amount, columnIndex_Description, columnIndex_INOUT;
        int in_or_out;
        ArrayList<SingleRow> list=new ArrayList<SingleRow>();
        String test="";


        columnIndex_uid=cursor.getColumnIndex(sqLiteHelper.UID); //  gives index of UID i.e =0;
        columnIndex_date=cursor.getColumnIndex(sqLiteHelper.DATE_TIME);
        columnIndex_category=cursor.getColumnIndex(sqLiteHelper.CATEGORY);
        columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);
        columnIndex_Description=cursor.getColumnIndex(sqLiteHelper.DESCRIPTION);
        columnIndex_INOUT=cursor.getColumnIndex(sqLiteHelper.IN_OR_OUT);

        while(cursor.moveToNext())
        {
//            fetching index of corresponding table table attributes.


//            Retrieving values associated to the corresponding columnIndex of database table -one row at a time.
            uid=cursor.getInt(columnIndex_uid);
            date=cursor.getString(columnIndex_date);
            category=cursor.getString(columnIndex_category);
            amount=cursor.getString(columnIndex_Amount);
            description=cursor.getString(columnIndex_Description);
            in_or_out=cursor.getInt(columnIndex_INOUT);

            //Message.message_longTime(context, description+" "+in_or_out+"\n");
            if(in_or_out==1)
                inOut="IN";
            else
                inOut="OUT";

            //LIST ADDING
            list.add(new SingleRow(R.drawable.trees, amount, date, description, inOut, category, uid));


        }

//        Message.message(context, test);
        db.close();
        cursor.close();

        return list;
    }


    public String getDataFromDailyTable(long filterId)
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();

//     select  Date, Category, Amount, Description from Table -> Incoming.
        String[] allColumnsFrom_tableDaily={SQLiteHelper.UID,SQLiteHelper.DATE_TIME,SQLiteHelper.CATEGORY,SQLiteHelper.AMOUNT,SQLiteHelper.DESCRIPTION,SQLiteHelper.IN_OR_OUT};
        Cursor cursor=db.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME_DAILY+" WHERE "+SQLiteHelper.UID+" = "+filterId+" ;",null);


        ///////////////////////////////
        String date,category,amount,description,inOut="OUT";
        int uid, columnIndex_uid, columnIndex_date, columnIndex_category, columnIndex_Amount, columnIndex_Description, columnIndex_INOUT;
        int in_or_out;
        StringBuffer selectedTuple=new StringBuffer();


        columnIndex_uid=cursor.getColumnIndex(sqLiteHelper.UID); //  gives index of UID i.e =0;
        columnIndex_date=cursor.getColumnIndex(sqLiteHelper.DATE_TIME);
        columnIndex_category=cursor.getColumnIndex(sqLiteHelper.CATEGORY);
        columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);
        columnIndex_Description=cursor.getColumnIndex(sqLiteHelper.DESCRIPTION);
        columnIndex_INOUT=cursor.getColumnIndex(sqLiteHelper.IN_OR_OUT);

        while(cursor.moveToNext())
        {
//            fetching index of corresponding table table attributes.


//            Retrieving values associated to the corresponding columnIndex of database table -one row at a time.
            uid=cursor.getInt(columnIndex_uid);
            date=cursor.getString(columnIndex_date);
            category=cursor.getString(columnIndex_category);
            amount=cursor.getString(columnIndex_Amount);
            description=cursor.getString(columnIndex_Description);
            in_or_out=cursor.getInt(columnIndex_INOUT);

            //Message.message_longTime(context, description+" "+in_or_out+"\n");
            if(in_or_out==1)
                inOut="IN";
            else
                inOut="OUT";

            selectedTuple.append(amount+":"+date+":"+category+":"+description+":"+inOut);


        }

        Message.message(context, selectedTuple.toString());
        db.close();
        cursor.close();

        return selectedTuple.toString();
    }



    public int getAllCategoryFromDailyTable(String cat, int in_out_flag, String year_month)
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();

//     select  Date, Category, Amount, Description from Table -> Incoming.
        String[] allColumnsFrom_tableDaily={SQLiteHelper.UID,SQLiteHelper.DATE_TIME,SQLiteHelper.CATEGORY,SQLiteHelper.AMOUNT,SQLiteHelper.DESCRIPTION,SQLiteHelper.IN_OR_OUT};
        Cursor cursor=db.rawQuery("SELECT "+SQLiteHelper.AMOUNT+", "+SQLiteHelper.CATEGORY+" FROM "+SQLiteHelper.TABLE_NAME_DAILY+ " WHERE "+
                SQLiteHelper.CATEGORY+"= \""+cat+"\" AND "+SQLiteHelper.IN_OR_OUT+"="+in_out_flag+" AND "+SQLiteHelper.YEAR_MONTH+"" +
                                      "= \""+year_month+"\" ;",null);


        ///////////////////////////////
        String amount;
        int categoryAmount=0;
        int columnIndex_Amount, catPERCENT=0;
        String test="";

        columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);

        while(cursor.moveToNext())
        {
            amount=cursor.getString(columnIndex_Amount);
            categoryAmount+=Integer.parseInt(amount);

        }


        db.close();
        cursor.close();

        if(getTotalAmountSumDatabase(in_out_flag, year_month)==0)
        {
            //Message.message(context, "CAT PERCETN="+0);
            return 0;
        }
        else
        {

            catPERCENT=(categoryAmount*100/getTotalAmountSumDatabase(in_out_flag, year_month));
            //
            // /Message.message(context, "PERCENTAGE="+catPERCENT);
            return catPERCENT;

        }

    }


    public int getTotalAmountSumDatabase(int in_out_flag, String year_month)
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT "+SQLiteHelper.AMOUNT+" FROM "+SQLiteHelper.TABLE_NAME_DAILY+ " WHERE "+SQLiteHelper.IN_OR_OUT+" = "+in_out_flag
                +" AND "+SQLiteHelper.YEAR_MONTH+"=\""+year_month+"\" ;",null);


        ///////////////////////////////
        String amount;
        int columnIndex_Amount;
        int totalAmount=0;
        columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);

        while(cursor.moveToNext())
        {
//
            amount=cursor.getString(columnIndex_Amount);
            totalAmount+=Integer.parseInt(amount);

        }

        //Message.message(context, "Total Amount=" + totalAmount);

        db.close();
        cursor.close();


        return totalAmount;
    }


    public Map<Integer, Integer> getAllDataFromMonthlyTable(int in_out_flag, String year_month)
    {

        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();
        String[] allColumnsFrom_tableDaily={SQLiteHelper.UID,SQLiteHelper.AMOUNT,SQLiteHelper.IN_OR_OUT};
       ///Cursor cursor=db.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME_MONTHLY + " ORDER BY " + SQLiteHelper.TIME_STAMP +
          //    " DESC WHERE "+SQLiteHelper.IN_OR_OUT+"="+in_out_flag+";", null);

        Cursor cursor=db.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME_MONTHLY+" WHERE "+SQLiteHelper.IN_OR_OUT+"="+in_out_flag+" AND "+sqLiteHelper.YEAR_MONTH+ " = \""+year_month+"\" ;", null);

        String uid,amount,inOut="OUT";
        int in_or_out;
        int timeStamp=0;
        String test="";

        int columnIndex_uid=cursor.getColumnIndex(sqLiteHelper.UID); //  gives index of UID i.e =0;
        int columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);
        int columnIndex_INOUT=cursor.getColumnIndex(sqLiteHelper.IN_OR_OUT);
        int columnIndex_TIMESTAMP=cursor.getColumnIndex(sqLiteHelper.TIME_STAMP);
        int columnIndex_Year_month=cursor.getColumnIndex(sqLiteHelper.YEAR_MONTH);
        Map<Integer, Integer> map=new HashMap<Integer, Integer>();


        while(cursor.moveToNext())
        {
//            fetching index of corresponding table table attributes.


//            Retrieving values associated to the corresponding columnIndex of database table -one row at a time.
            uid = cursor.getString(columnIndex_uid);
            amount= cursor.getString(columnIndex_Amount);
            in_or_out=cursor.getInt(columnIndex_INOUT);
            timeStamp=cursor.getInt(columnIndex_TIMESTAMP);

//
//           test+=uid + "     "+amount+"      "+in_or_out+ "           "+timeStamp+ "      "+year_month+"\n";

            map.put(timeStamp, Integer.parseInt(amount));

         //  txt=txt+"Time-Stamp="+timeStamp+"   Amount="+amount+'\n';

        }

//           Message.message(context, test);





        cursor.close();
        db.close();

        return map;
    }






    static class SQLiteHelper extends SQLiteOpenHelper
    {

        private Context context;

        //      Database attributes.
        private static final String DATABASE_NAME = "money_calendar";
        private static final int DATABASE_VERSION = 1;

        //    Table attributes.
        private static final String TABLE_NAME_DAILY = "daily_table";
        private static final String TABLE_NAME_MONTHLY = "monthly_table";
        private static final String UID = "_id";
        private static final String DESCRIPTION = "Description";
        private static final String CATEGORY = "Category";
        private static final String DATE_TIME = "Date";
        private static final String AMOUNT = "Amount";
        private static final String IN_OR_OUT="in_or_out";
        private static final String TIME_STAMP="time_stamp";
        private static final String YEAR_MONTH="year_month";

        //    create table money_calendar (_id primary key autoincrement,date_time,amount,description ,category)
        private static final String CREATE_TABLE_DAILY = "create table if not exists " + TABLE_NAME_DAILY + " (" + UID + " integer primary key autoincrement," +
                "" + DATE_TIME + " date not null," + AMOUNT + " text not null," + DESCRIPTION + " varchar(60)," + CATEGORY + " text not null, "+IN_OR_OUT+" integer,"+TIME_STAMP+" integer not null, " +
        YEAR_MONTH+ " text not null);";

        //    create table money_calendar (_id primary key autoincrement,date_time,amount,description ,category)
        private static final String CREATE_TABLE_MONTHLY= "create table if not exists " + TABLE_NAME_MONTHLY + " (" + UID + " integer primary key autoincrement," +
                 AMOUNT + " text not null,"  +IN_OR_OUT+" integer, "+TIME_STAMP+" integer not null, "+YEAR_MONTH+" text not null );";

        //    drop table if exists money_calendar
        private static final String DROP_TABLE_DAILY= "drop table if exists " + TABLE_NAME_DAILY + " ";
        private static final String DROP_TABLE_MONTHLY= "drop table if exists " + TABLE_NAME_MONTHLY + " ";

        public SQLiteHelper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

// We need to check whether table that we are going to create already exists.
            //Because this method gets executed every time we create an object of this class.
            db.execSQL(CREATE_TABLE_DAILY);
            db.execSQL(CREATE_TABLE_MONTHLY);
//            Message.message(context, "database has been called.");

            // Log.d("onCreate()", "database has been created!");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int old_version, int new_version)
        {
            db.execSQL(DROP_TABLE_DAILY);
            db.execSQL(DROP_TABLE_MONTHLY);
            onCreate(db);
//            Message.message(context,"onUpgrade() -> database has been called.");
            //Log.d("onUpgrade()", "database has been deleted and is re-created!!");
        }
    }
}