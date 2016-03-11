package com.adafruit.bluefruit.le.connect.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;
import java.util.List;

final public class ItemListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

         ArrayList<Item> items = new ArrayList<Item>();
          for (int i = 0; i < 10; i++) {
              String url = String.format("http://www.google.com/image/%d.png", i);
              String title = String.format("Item %d", i);
              String description = String.format("Description of Item %d", i);
              Item item = new Item(url, title, description);
              items.add(item);
          }
        
        setListAdapter(new ItemAdapter(getActivity(), items));

        return v;
    }

}