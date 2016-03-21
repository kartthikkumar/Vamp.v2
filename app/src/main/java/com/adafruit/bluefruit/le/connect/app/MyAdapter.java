package com.adafruit.bluefruit.le.connect.app;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kartthikkumar on 16-03-02.
 */

final class MyAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public MyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("Engineering 6",       R.drawable.school));
        mItems.add(new Item("Bathroom",   R.drawable.bathroom));
        mItems.add(new Item("Kitchen", R.drawable.kitchen));
        mItems.add(new Item("Stairs",      R.drawable.stairs));
        mItems.add(new Item("Laundry",      R.drawable.laundry));
        mItems.add(new Item("Master Bedroom",     R.drawable.master));
        mItems.add(new Item("Office",      R.drawable.study));
        mItems.add(new Item("Children Room",      R.drawable.children));
        mItems.add(new Item("Shower",      R.drawable.bathroomsecond));
        mItems.add(new Item("Garage",      R.drawable.garage));
        mItems.add(new Item("Shed",      R.drawable.shed));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }
        v.setPadding(50,50,50,50);
        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setTextColor(Color.WHITE);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}