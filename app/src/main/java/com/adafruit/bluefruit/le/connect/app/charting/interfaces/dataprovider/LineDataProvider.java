package com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider;

import com.adafruit.bluefruit.le.connect.app.charting.components.YAxis;
import com.adafruit.bluefruit.le.connect.app.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
