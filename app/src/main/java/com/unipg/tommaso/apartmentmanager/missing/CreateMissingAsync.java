package com.unipg.tommaso.apartmentmanager.missing;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.unipg.tommaso.apartmentmanager.Apartment;
import com.unipg.tommaso.apartmentmanager.GenericRESTCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by tommaso on 01/07/2018.
 */

class CreateMissingAsync extends AsyncTaskLoader<Boolean> {
    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;


    public CreateMissingAsync(Context context, String startDate, String endDate, String startTime, String endTime) {
        super(context);
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;

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
            String my_id = Apartment.getApartment().getMe().getName();
            String startDateTime = String.format("%s %s",startDate, startTime);
            String endDateTime = String.format("%s %s",endDate, endTime);
            JSONObject postData = new JSONObject()
                    .put("start_date", startDateTime)
                    .put("end_date", endDateTime)
                    .put("inmate_name", my_id);
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,postData);
            Log.d("json create missing response",RESTResponse.toString());
            if(!Objects.equals(RESTResponse.get("message"),"")){
                Log.d("Missing added","it worked!");
                Apartment.getApartment().addMissing(new Missing(Apartment.getApartment().getRoommate(my_id),startDateTime,endDateTime));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
