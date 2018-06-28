package com.unipg.tommaso.apartmentmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by tommaso on 25/06/2018.
 */

public class GenericRESTCall {

    public static JSONObject makeRESTCall(HttpURLConnection connection, JSONObject postData) {
        JSONObject result = new JSONObject();
        try {
            String inputLine;
            GenericRESTCall.putPostData(connection, postData);
            InputStreamReader streamReader;
            streamReader = new InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            result = new JSONObject(stringBuilder.toString());
            //Set our result equal to our stringBuilder
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    private static void putPostData(HttpURLConnection connection, JSONObject jsonObject) {
        try {
            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
