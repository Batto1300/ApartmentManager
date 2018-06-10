package com.unipg.tommaso.apartmentmanager.jobs;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommaso on 10/06/2018.
 */

class RetrieveJobsAsync extends AsyncTaskLoader<ArrayList<Job>>{
    final static String url = "api url";

    public RetrieveJobsAsync(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Job> loadInBackground() {
        //implement with api here
        ArrayList<Job> l = new ArrayList<>();
        l.add(new Job("job1"));
        l.add(new Job("job2"));
        l.add(new Job("job3"));
        return l;
    }
}
