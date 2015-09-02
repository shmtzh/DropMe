package com.example.shmtzh.dropme.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shmtzh.dropme.DataModels.HistoryModel;
import com.example.shmtzh.dropme.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {


    private static final String TAG = "HistoryAdapter TAG";
    private Context context;
    private ArrayList<HistoryModel> historyList;


    public HistoryAdapter(Context context, ArrayList<HistoryModel> items) {
        this.context = context;
        this.historyList = items;
    }

    private class ViewHolder {
        TextView tvNumber;
        TextView tvX;
        TextView tvY;
        TextView tvZ;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return historyList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView");
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
            holder = new ViewHolder();
            holder.tvNumber = (TextView) convertView.findViewById(R.id.item_number);
            holder.tvX = (TextView) convertView.findViewById(R.id.item_x);
            holder.tvY = (TextView) convertView.findViewById(R.id.item_y);
            holder.tvZ = (TextView) convertView.findViewById(R.id.item_z);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvNumber.setText(String.valueOf(position + 1));
        holder.tvX.setText(String.valueOf(historyList.get(position).getX()));
        holder.tvY.setText(String.valueOf(historyList.get(position).getY()));
        holder.tvZ.setText(String.valueOf(historyList.get(position).getZ()));

        return convertView;
    }
}
