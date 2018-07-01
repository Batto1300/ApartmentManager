package com.unipg.tommaso.apartmentmanager.roommates;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.unipg.tommaso.apartmentmanager.GenericRESTCall;
import com.unipg.tommaso.apartmentmanager.Apartment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by tommaso on 01/06/2018.
 */

class RetrieveRoommatesAsync extends AsyncTaskLoader<ArrayList<Roommate>>  {

    public RetrieveRoommatesAsync(Context context) {
        super(context);
    }
        private static final String REQUEST_METHOD = "GET";
        private static final int READ_TIMEOUT = 15000;
        private static final int CONNECTION_TIMEOUT = 15000;

    @Override
    public ArrayList<Roommate> loadInBackground() {

        ArrayList<Roommate> roommates = new ArrayList<>();
        try {
            String readRoommateURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/joined_apartment/read.php?apartment_name=%s";
            readRoommateURLString = String.format(readRoommateURLString, Apartment.getApartment().getName());
            URL url = new URL(readRoommateURLString);
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,null);
            connection.disconnect();
            JSONArray jsonRoommates = RESTResponse.getJSONArray("records");
            for(int i=0 ; i < jsonRoommates.length(); i++){
                JSONObject jsonRoommate = jsonRoommates.getJSONObject(i);
                String userName = jsonRoommate.get("roommate_name").toString();
                if(!Objects.equals(userName,Apartment.getApartment().getMe().getName())) {
                    Roommate newRoommate = new Roommate(userName, jsonRoommate.get("display_name").toString());
                    Apartment.getApartment().addRoommate(newRoommate);
                    roommates.add(newRoommate);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return roommates;
    }
}
