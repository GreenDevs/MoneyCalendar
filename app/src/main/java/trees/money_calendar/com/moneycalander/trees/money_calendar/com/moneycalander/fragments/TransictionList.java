package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import trees.money_calendar.com.moneycalander.MainActivity;
import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.SingleRow;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.TransListAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.DateAndTimeStamp;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;

/**
 * Created by trees on 6/5/15.
 */
public class TransictionList extends Fragment implements AbsListView.MultiChoiceModeListener
{



    private ListView list;
    private Context context;
    private TransListAdapter transListAdapter;
    private SQLiteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.transiction,container,false);
       init(v);
       return v;
    }


    private void init(View v)
    {
        list=(ListView)v.findViewById(R.id.transiction_list);
        context=(Context)getActivity();
        transListAdapter=new TransListAdapter(context);
        list.setAdapter(transListAdapter);




        //############### Context Action Menu (MULTIPLE DELETE ACTION ON THE TRANSICATION LIST) ###################################
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(this);               //Capture listView item click.
    }



    @Override
    public void onResume()
    {
        super.onResume();
        transListAdapter=new TransListAdapter(context);
        list.setAdapter(transListAdapter);          //Binds the adapter to the list view.

    }




    //########################################### MULTI CHOICE LISTENER METHODS #####################################

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
    {


        final int checkedCount = list.getCheckedItemCount();   //capture total checked items.
        mode.setTitle(checkedCount + "");                         //set The CAB title according to total checked items.
        transListAdapter.toggleSelection(position);             //calls toggleSelection from listview Adapter class.



    }




    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu)
    {

        mode.getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }




    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu)
    {

        return false;
    }




    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.delete:


                adapter=new SQLiteAdapter(context);
                long id, time_stamp;
                int in_or_out;
                String amount;

                SparseBooleanArray selected = transListAdapter.getSelectedIds();        //calls getSelectedIds method from listView adapter class.
                for (int i = (selected.size() - 1); i >= 0; i--)                        //captures all selected ids with a loop
                {
                    if (selected.valueAt(i))
                    {
                        SingleRow selectedItem = (SingleRow) transListAdapter.getItem(selected.keyAt(i));
                        transListAdapter.remove(selectedItem);                         // remove selected items following the ids.

                        ///############# DAILY TABLE DELETION ###################
                        id=selectedItem.getId();
                        adapter.deleteRowFromDailyTable(id);

                        //############ MONTHLY TABLE UPDATE ####################
                        time_stamp= DateAndTimeStamp.returnTimeStamp(selectedItem.getDate()+" 00:00");
                        amount=selectedItem.getAmount();
                        if(selectedItem.getFlow().equals("IN")) in_or_out=1;
                        else in_or_out=0;
                        adapter.updateIntoMonthlyTable(in_or_out, time_stamp, amount);



                    }



                }

               mode.finish();
//               ((MainActivity)getActivity()).getViewPager(1);
               return true;

            default:
                return false;

        }
    }





    @Override
    public void onDestroyActionMode(ActionMode mode)
    {

        transListAdapter.removeSelection();

    }
}



