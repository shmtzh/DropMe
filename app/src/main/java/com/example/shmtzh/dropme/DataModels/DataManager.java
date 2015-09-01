package com.example.shmtzh.dropme.DataModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class DataManager {
    private Context context;
    private ArrayList<HistoryModel> historyList = new ArrayList<HistoryModel>();
    private HistoryModel item;
    private SharedPreferences mPrefs;
    Gson gson = new Gson();

    public DataManager(HistoryModel item, Context context) {
        this.context = context;
        this.item = item;
        mPrefs = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        String json = mPrefs.getString("history", "");
        historyList = gson.fromJson(json, new TypeToken<ArrayList<HistoryModel>>() {
        }.getType());
    }

    public void saveDrop() {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        if (historyList == null) historyList = new ArrayList<HistoryModel>();
        historyList.add(item);
        String json = gson.toJson(historyList);
        Log.d("TAG", String.valueOf(historyList.size()));
        prefsEditor.putString("history", json);
        prefsEditor.apply();
    }




    public ArrayList<HistoryModel> loadFavorite() {
        return historyList;
    }


}