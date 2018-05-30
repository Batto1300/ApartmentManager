package com.unipg.tommaso.apartmentmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FragmentManager manager;
    MissingFragment missingFragment;
    JobsFragment jobsFragment;
    RoommatesFragment roommatesFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_roommates:
                    FragmentTransaction roommatesTransaction = manager.beginTransaction();
                    roommatesTransaction.replace(R.id.container,roommatesFragment,"jobs");
                    roommatesTransaction.commit();
                    return true;
                case R.id.navigation_jobs:
                    FragmentTransaction jobsTransaction = manager.beginTransaction();
                    jobsTransaction.replace(R.id.container,jobsFragment,"jobs");
                    jobsTransaction.commit();
                    return true;
                case R.id.navigation_missing:
                    FragmentTransaction missingTransaction = manager.beginTransaction();
                    missingTransaction.replace(R.id.container,missingFragment,"missing");
                    missingTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        missingFragment = new MissingFragment();
        jobsFragment = new JobsFragment();
        roommatesFragment = new RoommatesFragment();
        manager = getFragmentManager();
        FragmentTransaction initial_transaction = manager.beginTransaction();
        initial_transaction.add(R.id.container,roommatesFragment,"missing");
        initial_transaction.commit();

    }

}
