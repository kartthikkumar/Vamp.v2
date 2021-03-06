
package com.adafruit.bluefruit.le.connect.app.charting.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.adafruit.bluefruit.le.connect.app.charting.data.ScatterData;
import com.adafruit.bluefruit.le.connect.app.charting.interfaces.dataprovider.ScatterDataProvider;
import com.adafruit.bluefruit.le.connect.app.charting.renderer.ScatterChartRenderer;

/**
 * The ScatterChart. Draws dots, triangles, squares and custom shapes into the
 * Chart-View. CIRCLE and SCQUARE offer the best performance, TRIANGLE has the
 * worst performance.
 * 
 * @author Philipp Jahoda
 */
public class ScatterChart extends BarLineChartBase<ScatterData> implements ScatterDataProvider {

    /**
     * enum that defines the shape that is drawn where the values are
     */
    public enum ScatterShape {
        SQUARE, CIRCLE, TRIANGLE, CROSS, X,
    }

    public ScatterChart(Context context) {
        super(context);
    }

    public ScatterChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScatterChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new ScatterChartRenderer(this, mAnimator, mViewPortHandler);
        mXChartMin = -0.5f;
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();

        if (mDeltaX == 0 && mData.getYValCount() > 0)
            mDeltaX = 1;

        mXChartMax += 0.5f;
        mDeltaX = Math.abs(mXChartMax - mXChartMin);
    }

    /**
     * Returns all possible predefined ScatterShapes.
     * 
     * @return
     */
    public static ScatterShape[] getAllPossibleShapes() {
        return new ScatterShape[] {
                ScatterShape.SQUARE, ScatterShape.CIRCLE, ScatterShape.TRIANGLE, ScatterShape.CROSS
        };
    }

    public ScatterData getScatterData() {
        return mData;
    };
}
