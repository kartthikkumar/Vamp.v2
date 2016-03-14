package com.xxmassdeveloper.mpchartexample.custom;

import com.adafruit.bluefruit.le.connect.app.charting.formatter.FillFormatter;
import com.adafruit.bluefruit.le.connect.app.charting.interfaces.datasets.ILineDataSet;
import com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider.LineDataProvider;

/**
 * Created by Philipp Jahoda on 12/09/15.
 */
public class MyFillFormatter implements FillFormatter {

    private float mFillPos = 0f;

    public MyFillFormatter(float fillpos) {
        this.mFillPos = fillpos;
    }

    @Override
    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
        // your logic could be here
        return mFillPos;
    }
}
