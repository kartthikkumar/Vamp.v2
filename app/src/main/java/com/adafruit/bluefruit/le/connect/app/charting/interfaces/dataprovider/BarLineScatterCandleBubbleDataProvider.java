package com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider;

import com.adafruit.bluefruit.le.connect.app.charting.components.YAxis.AxisDependency;
import com.adafruit.bluefruit.le.connect.app.charting.data.BarLineScatterCandleBubbleData;
import com.adafruit.bluefruit.le.connect.app.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    BarLineScatterCandleBubbleData getData();
}
