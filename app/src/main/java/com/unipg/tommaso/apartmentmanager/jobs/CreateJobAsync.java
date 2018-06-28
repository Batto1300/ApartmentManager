package com.unipg.tommaso.apartmentmanager.jobs;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.unipg.tommaso.apartmentmanager.GenericRESTCall;
import com.unipg.tommaso.apartmentmanager.missing.Apartment;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by tommaso on 28/06/2018.
 */

public class CreateJobAsync extends AsyncTaskLoader<Boolean>{
    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private String jobName;
    private String jobDate;
    private Roommate jobAssignee;


    public CreateJobAsync(Context context, String jobName, String jobDate, String jobAssignee) {
        super(context);
        this.jobName = jobName;
        this.jobDate = jobDate;
        this.jobAssignee = Apartment.getApartment().getRoommate(jobAssignee);
    }

    @Override
    public Boolean loadInBackground() {
        try {
            String createJobURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/missing/create.php";
            URL url = new URL(createJobURLString);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            JSONObject postData = new JSONObject()
                    .put("name", Apartment.getApartment().getMe().getName())
                    .put("date", jobDate)
                    .put("assignee",jobAssignee.getName());
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,postData);
            Log.d("json response",RESTResponse.toString());
            if(Objects.equals(RESTResponse.get("error_message"),"")){
                Apartment.getApartment().addJob(new Job(jobName,jobDate,jobAssignee));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return true;
    }
}
