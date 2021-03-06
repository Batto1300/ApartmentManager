package com.unipg.tommaso.apartmentmanager.missing;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.unipg.tommaso.apartmentmanager.Apartment;
import com.unipg.tommaso.apartmentmanager.GenericRESTCall;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by tommaso on 30/06/2018.
 */

class RetrieveMissingAsync extends AsyncTaskLoader<ArrayList<Missing>> {
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;


    public RetrieveMissingAsync(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Missing> loadInBackground() {
        ArrayList<Missing> missingRoommates = new ArrayList<>();
        try {
            String readJobsURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/missing/read.php/?apartment_name=%s";
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
            String strDate = dateFormat.format(calendar.getTime());
            readJobsURLString = String.format(readJobsURLString, Apartment.getApartment().getName(),strDate);
            URL url = new URL(readJobsURLString);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,null);
            connection.disconnect();
            JSONArray jsonMissings = RESTResponse.getJSONArray("records");
            for(int i=0 ; i < jsonMissings.length(); i++) {
                JSONObject jsonMissing = jsonMissings.getJSONObject(i);
                Roommate who = Apartment.getApartment().getRoommate(jsonMissing.get("name").toString());
                String startDateTime = jsonMissing.get("start_date").toString();
                String endDateTime = jsonMissing.get("end_date").toString();
                Missing newMissing = new Missing(who, startDateTime, endDateTime);
                Apartment.getApartment().addMissing(newMissing);
                missingRoommates.add(newMissing);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return missingRoommates;
    }
}
