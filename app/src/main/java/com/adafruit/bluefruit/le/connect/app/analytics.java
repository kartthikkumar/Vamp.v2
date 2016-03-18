package com.adafruit.bluefruit.le.connect.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.media.MediaPlayer;
import android.os.SystemClock;
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
import android.os.Handler;

import com.adafruit.bluefruit.le.connect.R;
import com.adafruit.bluefruit.le.connect.app.settings.MqttUartSettingsActivity;
import com.adafruit.bluefruit.le.connect.app.update.FirmwareUpdater;
import com.adafruit.bluefruit.le.connect.ble.BleManager;
import com.adafruit.bluefruit.le.connect.mqtt.MqttSettings;

import com.adafruit.bluefruit.le.connect.ble.BleManager;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.nio.ByteBuffer;

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
    private TextView switchSecondStatus;
    private TextView rangeSeekBartext;
    private TextView dimmingText;
    private TextView rssiText;

    private Switch mySwitch;
    private Switch mySecondSwitch;
    private SeekBar dimSettings;
    private long mLastUpdateMillis = 0;

    int num = 0;
    TextView tView;
    Button clickhere;


    public static boolean toggleONOFF;
//    public static boolean pretoggleONOFF;
    public static boolean toggleRSSIDIM;
//    public static boolean pretoggleRSSIDIM;
    public int rssiValueGlobal;
    public String dimmingValueGlobal = "1000";


    public byte[] info = "".getBytes();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        mBleManager = BleManager.getInstance(this);
        onServicesDiscovered();

        rangeSeekBartext = (TextView) findViewById(R.id.seekbar_text);
        dimmingText = (TextView) findViewById(R.id.dimmingText);
        rssiText = (TextView) findViewById(R.id.rssiText);

        dimmingText.setTextColor(Color.WHITE);
        rssiText.setTextColor(Color.WHITE);

        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);

        switchSecondStatus = (TextView) findViewById(R.id.switchSecondStatus);
        mySecondSwitch = (Switch) findViewById(R.id.mySecondSwitch);
        // ****************************************************************************************


            // ********************** Toggle Device ******************************
            // Switch ON
            mySwitch.setChecked(true);
            //attach a listener to check for changes in state
            mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    toggleONOFF = isChecked;
                    if (isChecked) {
                        switchStatus.setTextColor(Color.WHITE);
                        switchStatus.setText("Switch is currently ON");


                        String info = "01000001";
                        int a = Integer.parseInt(info);
                        ByteBuffer bytes = ByteBuffer.allocate(4).putInt(a);
                        byte[] array = bytes.array();
                        sendData(array);


                        //sendData("TURN ON \n");
                        Log.d(TESTBLAH, "Switch is ON: " + info);

                    } else {
                        switchStatus.setTextColor(Color.DKGRAY);
                        switchStatus.setText("Switch is currently OFF");


                        String info = "01000001";
                        int a = Integer.parseInt(info);
                        ByteBuffer bytes = ByteBuffer.allocate(4).putInt(a);
                        byte[] array = bytes.array();
                        sendData(array);


                        //sendData("TURN OFF \n");
                        Log.d(TESTBLAH, "Switch is OFF: " + info);
                    }
                }
            });

            toggleONOFF = mySwitch.isChecked();
            //check the current state before we display the screen
            if (mySwitch.isChecked()) {
                switchStatus.setTextColor(Color.WHITE);
                switchStatus.setText("Switch is currently ON");
                //sendData("Default ON \n");
                Log.d(TESTBLAH, "Default ON");
            } else {
                switchStatus.setTextColor(Color.WHITE);
                switchStatus.setText("Switch is currently OFF");
                //sendData("Default OFF \n");
                Log.d(TESTBLAH, "Default OFF");
            }
            // ***************************************************************


        // ********************** Toggle State ******************************
        // Second Switch ON
        mySecondSwitch.setChecked(true);
        //attach a listener to check for changes in state
        mySecondSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                toggleRSSIDIM = isChecked;
                if (isChecked) {
                    switchSecondStatus.setTextColor(Color.WHITE);
                    switchSecondStatus.setText("RSSI Active");
                    rssiReading();

                } else {
                    switchSecondStatus.setTextColor(Color.WHITE);
                    switchSecondStatus.setText("Dimming Active");
                    seekBarReading();
                }
            }
        });

        toggleRSSIDIM = mySecondSwitch.isChecked();
        //check the current state before we display the screen
        if (mySecondSwitch.isChecked()) {
            switchSecondStatus.setTextColor(Color.WHITE);
            switchSecondStatus.setText("RSSI Active");
            rssiReading();

        } else {
            switchSecondStatus.setTextColor(Color.WHITE);
            switchSecondStatus.setText("Dimming Active");
            seekBarReading();
        }
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
                    Log.d(TESTBLAH, "Toggle " + toggleONOFF);
                    Log.d(TESTBLAH, "RSSID / DIM " + toggleRSSIDIM);
                    Log.d(TESTBLAH, "rssiValue " + rssiValueGlobal);
                    Log.d(TESTBLAH, "Dimming Value " + dimmingValueGlobal);


//                    // LAMP
//                    String infoLamp = "00";
//                    if (toggleONOFF) {
//                        infoLamp += "1";
//                    }
//                    else{
//                        infoLamp += "0";
//                    }
//
//                    infoLamp += "0000";
//                    if (rssiValueGlobal < -50) {
//                        infoLamp += "0";
//                    }
//                    else{
//                        infoLamp += "1";
//                    }

                    // FAN
                    String infoFan = "01";
                    if(toggleONOFF){
                        infoFan += "1";
                    }
                    else{
                        infoFan += "0";
                    }
                    infoFan += "1";
                    infoFan += dimmingValueGlobal;


                    String s = infoFan;
                    String str = "";

                    for (int i = 0; i < s.length()/8; i++) {

                        int a = Integer.parseInt(s.substring(8*i,(i+1)*8),2);
                        str += (char)(a);
                    }

                    sendData(str);
                    Log.d(TESTBLAH, "Final Array Sent: " + str);
                }
            });
            // ***************************************************************

        final Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                // upadte textView here
                mBleManager.readRssi();
                String display = Integer.toString(BleManager.rssiReading);
                tView.setText(display);
                handler.postDelayed(this,250); // set time here to refresh textView
            }
        });
    }



        // ********************** READING ******************************
        public void rssiReading(){

        boolean rssiBoolean = mBleManager.readRssi();
        Log.d("RSSI", Boolean.toString(rssiBoolean));

        int rssiValue = BleManager.rssiReading;
        //sendData(Integer.toString(BleManager.rssiReading));
        Log.d("RSSI", Integer.toString(BleManager.rssiReading));

        tView = (TextView) findViewById(R.id.rssiValue);
        tView.setTextColor(Color.WHITE);

        rssiValueGlobal = rssiValue;
        }
        // ********************** READING ******************************


        // ********************** SeekBar ******************************
        public void seekBarReading()
        {
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

                    String progressString = "0000";
                    Log.d(TESTBLAH, "Dimming Setting: " + progress);

                    if (progress == 0) {
                        progress = 0;
                        progressString = "0000";
                    } else if (progress <= 12.5) {
                        progress = 1;
                        progressString = "0001";
                    } else if (progress <= 25) {
                        progress = 2;
                        progressString = "0010";
                    } else if (progress <= 37.5) {
                        progress = 3;
                        progressString = "0011";
                    } else if (progress <= 50) {
                        progress = 4;
                        progressString = "0100";
                    } else if (progress <= 62.5) {
                        progress = 5;
                        progressString = "0101";
                    } else if (progress <= 75) {
                        progress = 6;
                        progressString = "0110";
                    } else if (progress <= 87.5) {
                        progress = 7;
                        progressString = "0111";
                    } else {
                        progress = 8;
                        progressString = "1000";
                    }

                    dimmingValueGlobal = progressString;
                }
            });
            // ***************************************************************
        }



        // **************************  Region Default ************************

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

    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String language = settings.getString("myRXValue", "");
        Log.d(TESTBLAH, "Shared Pref Data" + language);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean(Boolean.toString(toggleONOFF), previousONOFFState);
//        outState.putBoolean(Boolean.toString(toggleRSSIDIM), previousRSSIDIM);
////        outState.putString(rssiValueGlobal, previousRssiValue);
////        outState.putString(dimmingValueGlobal, previousDimmingValue);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        previousONOFFState = savedInstanceState.getBoolean(Boolean.toString(toggleONOFF));
//        previousRSSIDIM = savedInstanceState.getBoolean(previousRSSIDIM);
//    }


}
