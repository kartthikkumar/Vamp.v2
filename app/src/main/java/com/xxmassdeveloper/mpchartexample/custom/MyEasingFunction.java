
package com.xxmassdeveloper.mpchartexample.custom;

import com.adafruit.bluefruit.le.connect.app.charting.animation.EasingFunction;

/**
 * Example of a custom made animation EasingFunction.
 * 
 * @author Philipp Jahoda
 */
public class MyEasingFunction implements EasingFunction {

    @Override
    public float getInterpolation(float input) {
        // do awesome stuff here, this is just linear easing
        return input;
    }
}
