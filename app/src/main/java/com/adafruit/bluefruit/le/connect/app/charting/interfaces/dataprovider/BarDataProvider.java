package com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider;

import com.adafruit.bluefruit.le.connect.app.charting.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isDrawHighlightArrowEnabled();
}
