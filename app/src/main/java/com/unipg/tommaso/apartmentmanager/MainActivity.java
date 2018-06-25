package com.unipg.tommaso.apartmentmanager;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.google.firebase.iid.FirebaseInstanceId;
import com.unipg.tommaso.apartmentmanager.jobs.JobsFragment;
import com.unipg.tommaso.apartmentmanager.missing.Apartment;
import com.unipg.tommaso.apartmentmanager.missing.MissingFragment;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;
import com.unipg.tommaso.apartmentmanager.roommates.RoommatesFragment;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    FragmentManager manager;
    MissingFragment missingFragment;
    JobsFragment jobsFragment;
    RoommatesFragment roommatesFragment;
    String userApartment;
    String userName;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_roommates:
                    FragmentTransaction roommatesTransaction = manager.beginTransaction();
                    roommatesTransaction.replace(R.id.container,roommatesFragment,"roommates");
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
        setContentView(R.layout.activity_main_ap);
        try {
            getUserAttributes getUserAttributesTask = new getUserAttributes(this);
            getUserAttributesTask.execute().get();
            Roommate me = new Roommate(userName);
            new SynchUser().execute(me);
            Apartment.getApartment().addRoommate(me,true);
            if(userApartment == null){
                Intent i = new Intent(this, JoinApartment.class);
                startActivity(i);
            }else{
                Apartment.getApartment().setName(userApartment);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        manager = getFragmentManager();
        missingFragment = new MissingFragment();
        jobsFragment = new JobsFragment();
        roommatesFragment = new RoommatesFragment();
        Log.d("token",FirebaseInstanceId.getInstance().getToken());
        FragmentTransaction initial_transaction = manager.beginTransaction();
        initial_transaction.add(R.id.container,roommatesFragment,"missing");
        initial_transaction.commit();

    }
    private class getUserAttributes extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progDailog;
        @SuppressLint("StaticFieldLeak")
        Context context;


        private getUserAttributes(Context context){
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            AWSConfiguration awsConfiguration = new AWSConfiguration(context);
            CognitoUserPool cognitoUserPool = new CognitoUserPool(context,awsConfiguration);
            cognitoUserPool.getCurrentUser().getDetails(new GetDetailsHandler() {
                @Override
                public void onSuccess(CognitoUserDetails cognitoUserDetails) {
                    userApartment = cognitoUserDetails.getAttributes().getAttributes().get("custom:apartment");
                    userName = cognitoUserDetails.getAttributes().getAttributes().get("sub");
                    Log.d("cognito attributes",cognitoUserDetails.getAttributes().getAttributes().toString());
                    Log.d("userApartment",userApartment+" asdad");
                    Log.d("userName",userName+" asdd");

                }
                @Override
                public void onFailure(Exception exception) {}
            });
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            progDailog.dismiss();
        }
        @Override
        protected void onPreExecute() {
            progDailog = new ProgressDialog(context);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(true);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.show();
        }

    }

}
