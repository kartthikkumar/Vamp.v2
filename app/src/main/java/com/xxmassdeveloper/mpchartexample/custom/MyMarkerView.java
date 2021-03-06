
package com.xxmassdeveloper.mpchartexample.custom;

import android.content.Context;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.app.charting.components.MarkerView;
import com.adafruit.bluefruit.le.connect.app.charting.data.CandleEntry;
import com.adafruit.bluefruit.le.connect.app.charting.data.Entry;
import com.adafruit.bluefruit.le.connect.app.charting.highlight.Highlight;
import com.adafruit.bluefruit.le.connect.app.charting.utils.Utils;
import com.adafruit.bluefruit.le.connect.R;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}
