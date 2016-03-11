package com.adafruit.bluefruit.le.connect.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.adafruit.bluefruit.le.connect.BuildConfig;
import com.adafruit.bluefruit.le.connect.R;

public class ListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
//        if (BuildConfig.DEBUG)
//            ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (BuildConfig.DEBUG)
//            ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (BuildConfig.DEBUG)
//            ViewServer.get(this).setFocusedWindow(this);
    }
}
