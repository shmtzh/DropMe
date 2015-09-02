package com.example.shmtzh.dropme.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.shmtzh.dropme.Adapters.HistoryAdapter;
import com.example.shmtzh.dropme.DataModels.HistoryModel;
import com.example.shmtzh.dropme.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.InjectView;

public class HistoryActivity extends AppCompatActivity {

    //    @InjectView(R.id.history_list_view)
    ListView listview;

    ArrayList<HistoryModel> drops = new ArrayList<HistoryModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listview = (ListView) findViewById(R.id.history_list_view);

        loadList();

        if (drops != null) listview.setAdapter(new HistoryAdapter(this, drops));
    }

    private void loadList() {
        SharedPreferences mPrefs;
        Gson gson = new Gson();

        mPrefs = this.getSharedPreferences("history", Context.MODE_PRIVATE);
        String json = mPrefs.getString("history", "");
        drops = gson.fromJson(json, new TypeToken<ArrayList<HistoryModel>>() {
        }.getType());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
