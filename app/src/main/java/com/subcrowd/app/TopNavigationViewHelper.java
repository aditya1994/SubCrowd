package com.cureApp.app;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.cureApp.app.Matches.MatchesActivity;

/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class TopNavigationViewHelper {

    private static final String TAG = "TopNavigationViewHelper";

    public static void setupTopNavigationView(BottomNavigationViewEx tv) {
        Log.d(TAG, "setupTopNavigationView: setting up navigationview");


    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_profile:
                        Intent intent2 = new Intent(context, SettingsActivity.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_matched:
                        Intent intent3 = new Intent(context, MatchesActivity.class);
                        context.startActivity(intent3);

                        break;
                }

                return false;
            }
        });
    }
}
