package com.unipg.tommaso.apartmentmanager;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.unipg.tommaso.apartmentmanager.missing.Apartment;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by tommaso on 22/06/2018.
 */

class ApartmentAPI extends AsyncTaskLoader<APIApartmentResponse> {
    private String type;
    private String apartmentName = "";
    private APIApartmentResponse APIResponse;
    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final String joinApartmentURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/joined_apartment/create.php";
    private static final String createApartmentURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/apartment/create.php";

    public ApartmentAPI(Context context,String type,String apartmentName){
        super(context);
        this.type = type;
        this.apartmentName = apartmentName;
        this.APIResponse = new APIApartmentResponse(apartmentName,type);

    }



    @Override
    public APIApartmentResponse loadInBackground() {

        try {
            URL urlJoin = new URL(joinApartmentURLString);
            URL urlCreate = new URL(createApartmentURLString);

            HttpURLConnection connection;
            HttpURLConnection connectionb;
            APIApartmentResponse response;

            //create ap
            if(Objects.equals(type,"create")){
                connection = (HttpURLConnection) urlCreate.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();
                createApartment(connection);
                connection.disconnect();
                if(Objects.equals(APIResponse.successMessage,"")){
                    Apartment.getApartment().getMe().setAdmin(true);
                }
            }
            //join ap
            connectionb = (HttpURLConnection) urlJoin.openConnection();
            connectionb.setRequestMethod(REQUEST_METHOD);
            connectionb.setReadTimeout(READ_TIMEOUT);
            connectionb.setConnectTimeout(CONNECTION_TIMEOUT);
            connectionb.connect();
            joinApartment(connectionb);
            connectionb.disconnect();
            if(Objects.equals(APIResponse.successMessage,"")) {
                return APIResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return APIResponse;
    }

    private void createApartment(HttpURLConnection connection) {
        try {
            JSONObject postData = new JSONObject()
                    .put("apartment_name", apartmentName)
                    .put("requester_name", Apartment.getApartment().getMe().getName());

            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,postData);
            Log.d("json response",RESTResponse.toString());
            APIResponse.errorMessage = getValue(RESTResponse,"error_message");
            APIResponse.successMessage = getValue(RESTResponse,"message");
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void joinApartment(HttpURLConnection connection){
        try {
            JSONObject postData = new JSONObject()
                    .put("apartment_name", apartmentName)
                    .put("inmate_name",Apartment.getApartment().getMe().getName());
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection,postData);
            Log.d("json response",RESTResponse.toString());
            APIResponse.errorMessage = getValue(RESTResponse,"error_message");
            APIResponse.successMessage = getValue(RESTResponse,"message");;
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateCongitoUser(){}



        // TODO: call joined_apartment api if call was successful


    private static String getValue(JSONObject json, String key){
        String value;
        try{
            value =json.getString(key);
        }
        catch (JSONException e) {
            value = "";
        }
        return value;
        
    }
}
