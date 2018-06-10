package com.unipg.tommaso.apartmentmanager.roommates;


import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;


/**
 * Created by tommaso on 01/06/2018.
 */

class RetrieveRoommatesAsync extends AsyncTaskLoader<ArrayList<String>>  {
    private final static String ROOMMATESAPI = "www.lol.it";

    public RetrieveRoommatesAsync(Context context) {
        super(context);
    }

    @Override
    public ArrayList<String> loadInBackground() {
        return retrieveRoommates();
    }

    private ArrayList<String> retrieveRoommates(){
        //implement this with api
        ArrayList<String> strings = new ArrayList<>();
        strings.add("mate1");
        strings.add("mate2");
        strings.add("mate3");
        return strings;
    }
}
