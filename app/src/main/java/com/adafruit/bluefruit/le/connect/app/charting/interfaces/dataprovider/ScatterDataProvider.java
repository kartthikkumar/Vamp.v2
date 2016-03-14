package com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider;

import com.adafruit.bluefruit.le.connect.app.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
