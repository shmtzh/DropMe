package com.example.shmtzh.dropme.Listeners;

import android.view.View;
import android.widget.TextView;

/**
 * Created by shmtzh on 31.08.15.
 */
public class OnSwitcherClickListener implements View.OnClickListener {
    public static boolean state;


    public OnSwitcherClickListener() {
        state = true;
    }


    public static boolean checkTheState() {
        return state;
    }

    @Override
    public void onClick(View v) {
        state = !state;
        if (state) ((TextView) v).setText("Turn off");
        if (!state) ((TextView) v).setText("Turn on");
    }


}
