package com.unipg.tommaso.apartmentmanager.joinapartment;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.unipg.tommaso.apartmentmanager.GenericRESTCall;
import com.unipg.tommaso.apartmentmanager.missing.Apartment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by tommaso on 22/06/2018.
 */

class ApartmentAPI extends AsyncTaskLoader<ApartmentAPIResponse> {
    private String type;
    private String apartmentName = "";
    private String displayName;
    private ApartmentAPIResponse APIResponse;
    private static final String REQUEST_METHOD = "POST";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final String joinApartmentURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/joined_apartment/create.php";
    private static final String createApartmentURLString = "http://ec2-34-242-40-246.eu-west-1.compute.amazonaws.com/api/apartment/create.php";

    public ApartmentAPI(Context context,String type,String apartmentName, String displayName){
        super(context);
        this.type = type;
        this.apartmentName = apartmentName;
        this.displayName = displayName;
        this.APIResponse = new ApartmentAPIResponse(apartmentName,type);

    }



    @Override
    public ApartmentAPIResponse loadInBackground() {

        try {
            URL urlJoin = new URL(joinApartmentURLString);
            URL urlCreate = new URL(createApartmentURLString);

            HttpURLConnection connection_create;
            HttpURLConnection connection_join;

            //create ap
            if(Objects.equals(type,"create")){
                connection_create = (HttpURLConnection) urlCreate.openConnection();
                connection_create.setRequestMethod(REQUEST_METHOD);
                connection_create.setReadTimeout(READ_TIMEOUT);
                connection_create.setConnectTimeout(CONNECTION_TIMEOUT);
                connection_create.connect();
                createApartment(connection_create);
                connection_create.disconnect();
                if(Objects.equals(APIResponse.successMessage,"")){
                    Apartment.getApartment().getMe().setAdmin(true);
                }
            }
            //join ap
            connection_join = (HttpURLConnection) urlJoin.openConnection();
            connection_join.setRequestMethod(REQUEST_METHOD);
            connection_join.setReadTimeout(READ_TIMEOUT);
            connection_join.setConnectTimeout(CONNECTION_TIMEOUT);
            connection_join.connect();
            joinApartment(connection_join);
            connection_join.disconnect();
            if(Objects.equals(APIResponse.successMessage,"")) {
                return APIResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return APIResponse;
    }

    private void createApartment(HttpURLConnection connection_create) {
        try {
            JSONObject postData = new JSONObject()
                    .put("apartment_name", apartmentName)
                    .put("requester_name", Apartment.getApartment().getMe().getName());

            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection_create,postData);
            Log.d("json response",RESTResponse.toString());
            APIResponse.errorMessage = getValue(RESTResponse,"error_message");
            APIResponse.successMessage = getValue(RESTResponse,"message");
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void joinApartment(HttpURLConnection connection_create){
        try {
            JSONObject postData = new JSONObject()
                    .put("apartment_name", apartmentName)
                    .put("inmate_name",Apartment.getApartment().getMe().getName());
            JSONObject RESTResponse = GenericRESTCall.makeRESTCall(connection_create,postData);
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
