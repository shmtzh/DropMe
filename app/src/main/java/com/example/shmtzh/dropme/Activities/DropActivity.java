package com.example.shmtzh.dropme.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmtzh.dropme.DataModels.DataManager;
import com.example.shmtzh.dropme.DataModels.HistoryModel;
import com.example.shmtzh.dropme.Interfaces.ServiceHttpbin;
import com.example.shmtzh.dropme.Listeners.OnSwitcherClickListener;
import com.example.shmtzh.dropme.R;
import com.google.gson.Gson;

import java.util.Date;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DropActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private SharedPreferences mPrefs;

    //    @InjectView(R.id.switcher)
    Button switcher;

    //    @InjectView(R.id.number)
    TextView number;

    private int drops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);

        switcher = (Button) findViewById(R.id.switcher);
        number = (TextView) findViewById(R.id.number);

        switcher.setOnClickListener(new OnSwitcherClickListener());
        number.setText(String.valueOf(getTheNumber()));
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            saveNumber(0);
            number.setText("0");

            mPrefs = this.getSharedPreferences("history", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.clear();
            prefsEditor.apply();

        }

        if (id == R.id.action_history) {

            Intent mainIntent = new Intent(DropActivity.this, HistoryActivity.class);
            DropActivity.this.startActivity(mainIntent);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
//        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void callWebService(float x, float y, float z) {

        final String API_URL = "http://httpbin.org";


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        Gson gson = new Gson();
        HistoryModel model = new HistoryModel(x, y, z);
        String json = gson.toJson(model);


        ServiceHttpbin post = restAdapter.create(ServiceHttpbin.class);


        post.sendModel(json, new Callback<HistoryModel>() {

            @Override
            public void success(HistoryModel model, Response response) {
                Log.d("uploading", "success");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("uploading", "failure");
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (OnSwitcherClickListener.checkTheState()) {
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                long curTime = System.currentTimeMillis();

                if ((curTime - lastUpdate) > 150) {
                    long diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;


                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 5000;

                    if (speed > SHAKE_THRESHOLD) {
                        detectTheDrop(x, y, z);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.d("ERROR", "EROOR");
                        }
                        Log.d("dropped", String.valueOf(speed));
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }

            }

        }
    }

    private void detectTheDrop(float x, float y, float z) {

        drops = getTheNumber();
        drops = increaseTheNumber(drops);
        number.setText(String.valueOf(drops));

        saveNumber(drops);
        saveToHistory(x, y, z);
        playTheMusic(drops);

        callWebService(x, y, z);

        Date d = new Date();
        CharSequence s = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime());

    }


    private void playTheMusic(int drops) {
        MediaPlayer mp = null;
        boolean state = true;
        switch (drops) {
            case 5:
                mp = MediaPlayer.create(this, R.raw.firstblood);
                break;
            case 10:
                mp = MediaPlayer.create(this, R.raw.doublekill);
                break;
            case 15:
                mp = MediaPlayer.create(this, R.raw.triplekill);
                break;
            case 20:
                mp = MediaPlayer.create(this, R.raw.megakill);
                break;
            case 25:
                mp = MediaPlayer.create(this, R.raw.multikill);
                break;
            case 30:
                mp = MediaPlayer.create(this, R.raw.monsterkill);
                break;
            case 35:
                mp = MediaPlayer.create(this, R.raw.ultrakill);
                break;
            case 40:
                mp = MediaPlayer.create(this, R.raw.unstoppable);
                break;
            default:
                state = false;
        }

        if (state) mp.start();
    }

    private void saveNumber(int drops) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt("drops", drops);
        prefsEditor.apply();

    }


    private void saveToHistory(float x, float y, float z) {
        HistoryModel model = new HistoryModel(x, y, z);
        DataManager data = new DataManager(model, this);
        data.saveDrop();
    }

    private int increaseTheNumber(int drops) {

        drops += 1;
        return drops;
    }

    private int getTheNumber() {

        mPrefs = this.getSharedPreferences("drops", Context.MODE_PRIVATE);
        return mPrefs.getInt("drops", 0);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
