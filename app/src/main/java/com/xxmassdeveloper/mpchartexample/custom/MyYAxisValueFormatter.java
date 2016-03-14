package com.xxmassdeveloper.mpchartexample.custom;

import com.adafruit.bluefruit.le.connect.app.charting.components.YAxis;
import com.adafruit.bluefruit.le.connect.app.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

public class MyYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return mFormat.format(value) + " $";
    }
}
