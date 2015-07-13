package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.Plot;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import trees.money_calendar.com.moneycalander.R;

/**
 * Created by trees on 6/5/15.
 */
public class NetResult extends Fragment

{

    private Integer maxiumumRange = 10000;
    private Integer minimumRange = 0;
    XYPlot mySimpleXYPlot;
    Integer[] numSightings = {500, 1200, 190, 200, 207, 679, 234, 1200, 190, 200, 507, 679, 234, 1200,
            190, 200, 507, 679, 234, 1200, 190, 200, 507, 679, 234, 1200, 190, 200, 507, 679, 234};


    Integer[] years =
            {

                    1009843200,1010853200, 1011853200,1012853200, 1013853200, 1014853200, 1016473828,
                    1018473828, 1020473828, 1022473828, 1024473828, 1026473828, 1028473828, 1030473828, 1032473828,
                    1034473828, 1036473828, 1038473828, 1040473828, 1042473828, 1044473828, 1046473828, 1048473828,
                    1050473828, 1052473828, 1054473828, 1056473828, 1058473828, 1060473828, 1062473828, 1066473828
            };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.net_result, container, false);
        mySimpleXYPlot = (XYPlot)v.findViewById(R.id.mySimpleXYPlot);
        graphInitialize();
        return v;
    }






    //#################################   GRPAH INITIALIZEER ########################################


    private void graphInitialize()
    {


        //GRAPH PLOTING DATA CORDINATES
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(years), Arrays.asList(numSightings), "Monthly Expendeture");



        //GRPAH ATTRIBUTES NORMALIZATION
        mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(Color.rgb(51, 204, 255), Color.rgb(8, 50, 50), Color.argb(120, 8, 160, 134), null));
        mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        mySimpleXYPlot.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        mySimpleXYPlot.getGraphWidget().getRangeGridLinePaint().setColor(Color.TRANSPARENT);
        mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.NONE, null, null);
        mySimpleXYPlot.getGraphWidget().setMarginTop(20);
        mySimpleXYPlot.getGraphWidget().setMarginRight(20);
        mySimpleXYPlot.getGraphWidget().setPaddingBottom(10);
        mySimpleXYPlot.getBorderPaint().setStrokeWidth(10);
        mySimpleXYPlot.getBorderPaint().setAntiAlias(true);






        //Paint to paint in the graph below the line
        Paint lineFill = new Paint();
        lineFill.setAlpha(20);
        // lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));
        lineFill.setColor(Color.BLUE);
        LineAndPointFormatter formatter = new LineAndPointFormatter();
        formatter.setFillPaint(lineFill);


        mySimpleXYPlot.addSeries(series2, formatter);
        mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE, 10);
        mySimpleXYPlot.setRangeStep(XYStepMode.SUBDIVIDE, 11);
        mySimpleXYPlot.setGridPadding(2, 2, 2, 2);
        mySimpleXYPlot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
        mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        mySimpleXYPlot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        mySimpleXYPlot.setBorderPaint(null);
        mySimpleXYPlot.setPlotMargins(0, 0, 0, 0);
        mySimpleXYPlot.getGraphWidget().setSize(new SizeMetrics(0, SizeLayoutType.FILL, 0, SizeLayoutType.FILL));




        // customize our domain/range labels
        mySimpleXYPlot.setDomainLabel("");
        mySimpleXYPlot.setRangeLabel("");
        mySimpleXYPlot.centerOnRangeOrigin(0);
        // get rid of decimal points in our range labels:o
        mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0"));
        //have to work in this shit so that the maximum will set itself to meagningful maximum value accordint to the maximum havlue of expendeture
        //like if the max expendeture is 1300 , it might set it to be 2000 , if its 900 it would set itself into 1000
        maxiumumRange = findMaxRange(numSightings);
        minimumRange = findMinRange(numSightings);
        maxiumumRange = findHandsomeMaxRange(maxiumumRange);
        minimumRange = findHandsomeMinRange(minimumRange);
        mySimpleXYPlot.setRangeBoundaries(minimumRange, maxiumumRange, BoundaryMode.FIXED);
        mySimpleXYPlot.setDomainValueFormat(new Format() {
            // create a simple date format that draws on the year portion of our timestamp.
            // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
            // for a full description of SimpleDateFormat.
            private SimpleDateFormat dateFormat = new SimpleDateFormat("" +"MMM dd");





            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
            {
                // because our timestamps are in seconds and SimpleDateFormat expects milliseconds
                // we multiply our timestamp by 1000:
                long timestamp = ((Number) obj).longValue() * 1000;
                Date date = new Date(timestamp);
                return dateFormat.format(date, toAppendTo, pos);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos)
            {
                return null;

            }
        });


    }



    //############################ FINDING MAXIMUM VALUE OF Y ORDINATES ##################################


    public Integer findMaxRange(Integer[] array)
    {
        int index = 0;
        Integer maxValue = array[0];
        int arrayLength = array.length;
        for (index = 1; index < arrayLength; index++) {
            if (array[index] > maxValue) {
                maxValue = array[index];
            }
        }
        return maxValue;

    }




    //####################################### RESOLVING THE MAX RANGE ##################################

    public Integer findHandsomeMaxRange(Integer max)
    {

        int floorValue = (int) Math.log10(max);
        int multiplier = 2;
        double value = 0;
        Integer valueint = 0;


        for (multiplier = 2; multiplier <= 20; multiplier++) {
            value = Math.pow(10, floorValue) * multiplier;
            value = value / 2;
            valueint = (int) value;

            if (max < valueint) {
                break;
            }

        }

        // return (int) Math.pow(10,floorValue)*multiplier;
        return valueint;
    }



    //########################## FINDING THE MINIMUM Y ORDINATE ######################################
    public Integer findMinRange(Integer[] array)
    {
        int index = 0;
        Integer minValue = array[0];
        int arrayLength = array.length;
        for (index = 1; index < arrayLength; index++) {
            if (array[index] < minValue) {
                minValue = array[index];
            }
        }
        return minValue;

    }


    //############################# FINDING THE APPROPIRATE MINUMUM GRAPH RANGE ##########################
    public Integer findHandsomeMinRange(Integer min)
    {


        Integer minimum = (-1) * min;
        int floorValue = (int) Math.log10(minimum);
        int multiplier = 2;
        double value = 0;
        Integer valueint = 0;


        for (multiplier = 2; multiplier <= 20; multiplier++) {
            value = Math.pow(10, floorValue) * multiplier;
            value = value / 2;
            valueint = (int) value;

            if (minimum < valueint) {
                break;
            }

        }

        // return (int) Math.pow(10,floorValue)*multiplier;
        return (-1) * valueint;
    }


}
