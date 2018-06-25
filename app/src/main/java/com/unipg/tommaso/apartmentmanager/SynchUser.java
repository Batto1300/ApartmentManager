package com.unipg.tommaso.apartmentmanager;

import android.os.AsyncTask;
import android.util.Log;

import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tommaso on 24/06/2018.
 */

class SynchUser extends AsyncTask<Roommate, Void, Void>{
    private static final String urlString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/inmate/create.php";
    private static final String RequestMethod = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    @Override
    protected Void doInBackground(Roommate... roommates) {
        String roommateName = roommates[0].getName();
        HttpURLConnection connection;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(RequestMethod);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            JSONObject JSONObject = new JSONObject().put("name", roommateName);
            GenericRESTCall.makeRESTCall(connection,JSONObject);
            connection.disconnect();
            //Create a new buffered reader and String Builder
        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}

