package com.fyp.ble.ownerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fyp.ble.ownerapp.Model.BeaconListItem;
import com.fyp.ble.ownerapp.Model.PathModel;

import java.util.ArrayList;
import java.util.List;

public class PathAdapter extends ArrayAdapter<PathModel> {
    private Context mContext;
    private List<PathModel> pathModelList = new ArrayList<>();

    public PathAdapter(Context context,ArrayList<PathModel> list) {
        super(context,0,list);
        mContext = context;
        pathModelList = list;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_path_list_item, parent, false);
        }

        PathModel pathModel = pathModelList.get(pos);
        TextView m1 = (TextView) listItem.findViewById(R.id.m1);
        TextView m2 = (TextView) listItem.findViewById(R.id.m2);

        m1.setText(pathModel.getStartMac());
        m2.setText(pathModel.getEndMac());
        return listItem;
    }
}
