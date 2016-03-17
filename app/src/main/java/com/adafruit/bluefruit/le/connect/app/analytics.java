package com.adafruit.bluefruit.le.connect.app;

import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.adafruit.bluefruit.le.connect.R;
import com.adafruit.bluefruit.le.connect.app.settings.MqttUartSettingsActivity;
import com.adafruit.bluefruit.le.connect.app.update.FirmwareUpdater;
import com.adafruit.bluefruit.le.connect.ble.BleManager;
import com.adafruit.bluefruit.le.connect.mqtt.MqttSettings;

import com.adafruit.bluefruit.le.connect.ble.BleManager;

import java.util.Calendar;

public class analytics extends UartInterfaceActivity implements BleManager.BleManagerListener  {

        // BLE
    private final static String TESTBLAH = analytics.class.getSimpleName();
    private AlertDialog mConnectingDialog;
    protected BLE_MainActivity mBleActivity;


    // SWITCH & SEEKBAR
    private TextView switchStatus;
    private TextView rangeSeekBartext;
    private Switch mySwitch;
    private SeekBar dimSettings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        mBleManager = BleManager.getInstance(this);
        onServicesDiscovered();

        rangeSeekBartext = (TextView) findViewById(R.id.seekbar_text);


        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        // Switch ON
        mySwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    switchStatus.setTextColor(Color.WHITE);
                    switchStatus.setText("Switch is currently ON");
                    sendData("SENDING \n");
                    Log.d(TESTBLAH, "Passed sendData");

                } else {
                    switchStatus.setTextColor(Color.DKGRAY);
                    switchStatus.setText("Switch is currently OFF");
                    sendData("RECEIVING \n");
                    Log.d(TESTBLAH, "Passed receiveData");

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


        // ********************** SeekBar ******************************
        dimSettings = (SeekBar) findViewById(R.id.seekbar_placeholder);
        dimSettings.setMax(100);
        dimSettings.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                rangeSeekBartext.setTextColor(Color.WHITE);
                rangeSeekBartext.setText(String.valueOf(progress));
                Log.d(TESTBLAH, "Dimming Setting" + progress);

            }
        });
        // ***************************************************************


        // ********************** Scheduling ******************************
        FloatingActionButton scheduling = (FloatingActionButton) findViewById(R.id.scheduling);
        scheduling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startScheduling = new Intent(analytics.this, com.adafruit.bluefruit.le.connect.app.AlarmActivity.class);
                startActivity(startScheduling);
            }
        });
        // ***************************************************************


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



        // ********************** UART TEMP ******************************
        FloatingActionButton uartTEMP = (FloatingActionButton) findViewById(R.id.uartTEMP);
        uartTEMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startUART = new Intent(analytics.this, com.adafruit.bluefruit.le.connect.app.UartActivity.class);
                startActivity(startUART);
            }
        });
        // ***************************************************************
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_analytics, menu);
        return true;
    }

    // **************************  Region BleManagerListener ************************
    @Override
    public void onConnected() {
    }

    @Override
    public void onConnecting() {
    }

    @Override
    public void onDisconnected() {
        showConnectionStatus(false);
    }

    private void showConnectionStatus(boolean enable) {
        showStatusDialog(enable, R.string.scan_connecting);
    }

    private void showStatusDialog(boolean show, int stringId) {
        if (show) {

            // Remove if a previous dialog was open (maybe because was clicked 2 times really quick)
            if (mConnectingDialog != null) {
                mConnectingDialog.cancel();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(stringId);

            // Show dialog
            mConnectingDialog = builder.create();
            mConnectingDialog.setCanceledOnTouchOutside(false);

            mConnectingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mBleManager.disconnect();
                        mConnectingDialog.cancel();
                    }
                    return true;
                }
            });
            mConnectingDialog.show();
        } else {
            if (mConnectingDialog != null) {
                mConnectingDialog.cancel();
            }
        }
    }


    @Override
    public void onServicesDiscovered() {
        mUartService = mBleManager.getGattService(UUID_SERVICE);
        mBleManager.enableNotification(mUartService, UUID_RX, true);
    }


    @Override
    public void onDataAvailable(BluetoothGattCharacteristic characteristic) {
    }

    @Override
    public void onDataAvailable(BluetoothGattDescriptor descriptor) {
    }

    @Override
    public void onReadRemoteRssi(int rssi) {
    }
    // **************************  End Of Region BleManagerListener ************************
}