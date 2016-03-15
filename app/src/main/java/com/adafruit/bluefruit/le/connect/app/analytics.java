package com.adafruit.bluefruit.le.connect.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.adafruit.bluefruit.le.connect.R;
import com.adafruit.bluefruit.le.connect.app.settings.MqttUartSettingsActivity;
import com.adafruit.bluefruit.le.connect.ble.BleManager;
import com.adafruit.bluefruit.le.connect.mqtt.MqttSettings;

public class analytics extends UartInterfaceActivity {

    private TextView switchStatus;
    private Switch mySwitch;

    protected BleManager mBleManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);

        //set the switch to ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

        analytics a = new analytics();
                mUartService = mBleManager.getGattService(UUID_SERVICE);
                mBleManager.enableNotification(mUartService, UUID_RX, true);


                if (isChecked) {
                    switchStatus.setTextColor(Color.WHITE);
                    switchStatus.setText("Switch is currently ON");
                    a.sendData("ONHELLO");

                } else {
                    switchStatus.setTextColor(Color.WHITE);
                    switchStatus.setText("Switch is currently OFF");
//                    Intent sendOFF = new Intent(analytics.this, com.adafruit.bluefruit.le.connect.app.sendUART.class);
//                    startActivity(sendOFF);
                }

            }
        });

        //check the current state before we display the screen
        if (mySwitch.isChecked()) {
            switchStatus.setTextColor(Color.WHITE);
            switchStatus.setText("Switch is currently ON");
        } else {
            switchStatus.setTextColor(Color.WHITE);
            switchStatus.setText("Switch is currently OFF");
        }

        // ********************** UART Communication ******************************
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startUARTActivity = new Intent(analytics.this, com.adafruit.bluefruit.le.connect.app.UartActivity.class);
                startActivity(startUARTActivity);
            }
        });
        // ***************************************************************

        // Setup the new range seek bar
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
        // Set the range
        rangeSeekBar.setRangeValues(0, 100);
        rangeSeekBar.setSelectedMinValue(20);
        rangeSeekBar.setSelectedMaxValue(88);

//        LinearLayout layout = (LinearLayout) findViewById(R.id.seekbar_placeholder);
//        layout.addView(rangeSeekBar);

//         ********************** PI CHART ******************************
        FloatingActionButton piechartstart = (FloatingActionButton) findViewById(R.id.piechartstart);
        piechartstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startPIChart = new Intent(analytics.this, com.adafruit.bluefruit.le.connect.app.PieChartActivity.class);
                startActivity(startPIChart);
            }
        });
//         ***************************************************************


        // ********************** Line Graph ******************************
        FloatingActionButton linegraphstart = (FloatingActionButton) findViewById(R.id.linegraphstart);
        linegraphstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLineGraph = new Intent(analytics.this, com.adafruit.bluefruit.le.connect.app.LineChartActivity1.class);
                startActivity(startLineGraph);
            }
        });
        // ***************************************************************
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analytics, menu);
        return true;
    }

}