package com.unipg.tommaso.apartmentmanager.jobs;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.unipg.tommaso.apartmentmanager.GenericRESTCall;
import com.unipg.tommaso.apartmentmanager.missing.Apartment;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by tommaso on 10/06/2018.
 */

class RetrieveJobsAsync extends AsyncTaskLoader<ArrayList<Job>>{
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    
    public RetrieveJobsAsync(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Job> loadInBackground() {
        ArrayList<Job> jobs = new ArrayList<>();
        try {
            String readJobsURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/job/read.php/?apartment_name=%s&date=%s";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd ", Locale.ITALY);
            String strDate = dateFormat.format(calendar.getTime());
            readJobsURLString = String.format(readJobsURLString,Apartment.getApartment().getName(),strDate);
            URL url = new URL(readJobsURLString);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,null);
            connection.disconnect();
            JSONArray jsonJobs = RESTResponse.getJSONArray("records");
            for(int i=0 ; i < jsonJobs.length(); i++){
                JSONObject jsonJob = jsonJobs.getJSONObject(i);
                Roommate assignee = Apartment.getApartment().getRoommate(jsonJob.get("assignee").toString());
                jobs.add(new Job(jsonJob.get("name").toString(),jsonJob.get("date").toString(),assignee));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        //implement with api here
        return jobs;
    }
}
