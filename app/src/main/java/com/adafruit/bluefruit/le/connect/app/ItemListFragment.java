package com.adafruit.bluefruit.le.connect.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;
import java.util.List;


final public class ItemListFragment extends ListFragment {

    public List<Item> availableDevices = new ArrayList<Item>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        availableDevices.add(new Item(R.drawable.music, "Home Theatre Receiver", "South Wall"));
        availableDevices.add(new Item(R.drawable.monitor, "Flat Screen", "North Wall"));
        availableDevices.add(new Item(R.drawable.lamp, "Central Lamp", "East Wall"));
        availableDevices.add(new Item(R.drawable.computerdesk, "Computer", "East Wall"));
        availableDevices.add(new Item(R.drawable.monitortwo, "Additional Monitor", "East Wall"));
        availableDevices.add(new Item(R.drawable.desklamp, "Desk Lamp", "East Wall"));
        availableDevices.add(new Item(R.drawable.fan, "Fan", "East Wall"));
        availableDevices.add(new Item(R.drawable.fireplace, "Fireplace", "West Wall"));
        availableDevices.add(new Item(R.drawable.outlet, "Central Plug", "North Wall"));
        availableDevices.add(new Item(R.drawable.plug, "Extension Bar", "North Wall"));

        setListAdapter(new ItemAdapter(getActivity(), availableDevices));

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        Log.i("testy", "I Clicked on Row " + index + " and it worked!");

        switch (position) {
            case 0:
//                Intent analyticsMain = new Intent(getActivity().getApplicationContext(), analytics.class);
//                startActivity(analyticsMain);
//                break;
            default:
                Intent analyticsMain = new Intent(getActivity().getApplicationContext(), analytics.class);
                startActivity(analyticsMain);
                break;
        }
    }
}