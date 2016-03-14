package com.adafruit.bluefruit.le.connect.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adafruit.bluefruit.le.connect.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("KK", "Hello");
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

                switch (position) {
                    case 0:
                        Intent ListActivity = new Intent(MainActivity.this, com.adafruit.bluefruit.le.connect.app.ListActivity.class);
                        startActivity(ListActivity);
                        break;
                    case  1:
                        Intent ListActivity2 = new Intent(MainActivity.this, com.adafruit.bluefruit.le.connect.app.LineChartActivity1.class);
                        startActivity(ListActivity2);
                        break;
                    case  2:
                        Intent ListActivity3 = new Intent(MainActivity.this, com.adafruit.bluefruit.le.connect.app.PieChartActivity.class);
                        startActivity(ListActivity3);
                        break;
                    default:
                        Intent ListActivityDefault = new Intent(MainActivity.this, com.adafruit.bluefruit.le.connect.app.ListActivity.class);
                        startActivity(ListActivityDefault);
                        break;
                }
            }
        });
        // ***************************************************************


    }

}
