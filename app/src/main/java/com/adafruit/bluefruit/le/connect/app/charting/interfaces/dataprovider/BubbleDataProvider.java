package com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider;

import com.adafruit.bluefruit.le.connect.app.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
