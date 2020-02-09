package com.fyp.ble.ownerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.fyp.ble.ownerapp.Model.BeaconListItem;

import java.util.ArrayList;
import java.util.List;

public class BeaconAdapter extends ArrayAdapter<BeaconListItem> {
    private Context mContext;
    private List<BeaconListItem> beaconListItemList = new ArrayList<>();

    public BeaconAdapter(Context context,ArrayList<BeaconListItem> list) {
        super(context,0,list);
        mContext = context;
        beaconListItemList = list;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }
            BeaconListItem beaconListItem = beaconListItemList.get(pos);
            TextView location = (TextView) listItem.findViewById(R.id.txt1);
            TextView mAC = (TextView) listItem.findViewById(R.id.txt2);

            location.setText(beaconListItem.getLocation());
            mAC.setText(beaconListItem.getMAC());
            return listItem;
    }

}
