package com.adafruit.bluefruit.le.connect.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adafruit.bluefruit.le.connect.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // ********************** BLE Search ******************************
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startBluetoothActivity = new Intent(MainActivity.this, com.adafruit.bluefruit.le.connect.app.BLE_MainActivity.class);
                startActivity(startBluetoothActivity);
            }
        });
        // ***************************************************************

        // ********************** Grid View ******************************
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startAnalytics = new Intent(MainActivity.this, com.adafruit.bluefruit.le.connect.app.analytics.class);
                startActivity(startAnalytics);
            }
        });
        // ***************************************************************


    }

}
