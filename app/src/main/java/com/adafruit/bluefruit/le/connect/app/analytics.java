package com.adafruit.bluefruit.le.connect.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.widget.Button;
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

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.SharedPreferences;
import android.content.Context;



public class analytics extends UartInterfaceActivity implements BleManager.BleManagerListener {

    // BLE
    private final static String TESTBLAH = analytics.class.getSimpleName();
    private AlertDialog mConnectingDialog;
    protected BLE_MainActivity mBLEActivity;

    private volatile int mReceivedBytes;
    private ArrayList<UartDataChunk> mDataBuffer;
    private boolean mShowDataInHexFormat;
    private boolean mIsTimestampDisplayMode;


    // SWITCH & SEEKBAR
    private TextView switchStatus;
    private TextView rangeSeekBartext;
    private TextView dimmingText;
    private Switch mySwitch;
    private SeekBar dimSettings;

    int num = 0;
    TextView tView;
    Button clickhere;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        // DO NOT TOUCH
        mBleManager = BleManager.getInstance(this);
        onServicesDiscovered();

        rangeSeekBartext = (TextView) findViewById(R.id.seekbar_text);
        dimmingText = (TextView) findViewById(R.id.dimmingText);
        dimmingText.setTextColor(Color.WHITE);

        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);

        // ********************** RSSI ******************************
            // ********************** READING ******************************

        boolean rssiBoolean = mBleManager.readRssi();
        Log.d("RSSI", Boolean.toString(rssiBoolean));

        int rssiValue = BleManager.rssiReading;
        sendData(Integer.toString(BleManager.rssiReading));
        Log.d("RSSI", Integer.toString(BleManager.rssiReading));

        tView = (TextView) findViewById(R.id.rssiValue);
        tView.setTextColor(Color.WHITE);
        clickhere = (Button) findViewById(R.id.rssiButton);

        clickhere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mBleManager.readRssi();
                String display = Integer.toString(BleManager.rssiReading);
                tView.setText(display);
            }
        });

            // ********************** READING ******************************

//        SharedPreferences prefs = this.getSharedPreferences("general_settings", Context.MODE_PRIVATE);
//        String lanSettings = prefs.getString("myRXValue");


//        int index;
//        if (rssiValue == 127 || rssiValue <= -84) {   // 127 reserved for RSSI not available
//            index = 0;
//        } else if (rssiValue <= -72) {
//            index = 1;
//        } else if (rssiValue <= -60) {
//            index = 2;
//        } else if (rssiValue <= -48) {
//            index = 3;
//        } else {
//            index = 4;
//        }

            // ***************************************************************


            // ********************** Switch ******************************
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
            // ***************************************************************


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
                    Log.d(TESTBLAH, "Dimming Setting: " + progress);

                    if (progress == 0) {
                        progress = 0;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 12.5) {
                        progress = 1;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 25) {
                        progress = 2;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 37.5) {
                        progress = 3;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 50) {
                        progress = 4;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 62.5) {
                        progress = 5;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 75) {
                        progress = 6;
                        sendData(String.valueOf(progress));
                    } else if (progress <= 87.5) {
                        progress = 7;
                        sendData(String.valueOf(progress));
                    } else {
                        progress = 8;
                        sendData(String.valueOf(progress));
                    }
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
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_analytics, menu);
            return true;
        }

        // **************************  Region BleManagerListener ************************
        @Override
        public void onConnected () {
        }

        @Override
        public void onConnecting () {
        }

        @Override
        public void onDisconnected () {
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




    private String asciiToHex(String text) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            String charString = String.format("0x%02X", (byte) text.charAt(i));

            stringBuffer.append(charString + " ");
        }
        return stringBuffer.toString();
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

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String language = settings.getString("myRXValue", "");
        Log.d(TESTBLAH, "Shared Pref Data" + language);
    }
    // **************************  End Of Region BleManagerListener ************************
}
