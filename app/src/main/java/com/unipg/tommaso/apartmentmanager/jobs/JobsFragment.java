package com.unipg.tommaso.apartmentmanager.jobs;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.unipg.tommaso.apartmentmanager.R;
import com.unipg.tommaso.apartmentmanager.Apartment;

import java.util.ArrayList;

/**
 * Created by tommaso on 30/05/2018.
 */

public class JobsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Job>> {
    View view;
    JobsAdapter adapter;
    Button createJobButton;
    boolean is_loaded = false;
    ProgressDialog nDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jobs, vg, false);
        createJobButton = view.findViewById(R.id.jobs_button);
        ListView list = view.findViewById(R.id.jobs_list);
        adapter = new JobsAdapter(Apartment.getApartment().getJobs(), getContext());
        list.setAdapter(adapter);
        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),CreateJobActivity.class);
                startActivityForResult(i, 0);
            }
        });
        if (!is_loaded) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
            is_loaded = true;
        }else{
            //adapter.setJobs(Apartment.getApartment().getJobs());
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult","called");

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                Log.d("notifydatasetchanged","notified");
                adapter.notifyDataSetChanged();
            }
        }
    }



    @Override
    public Loader<ArrayList<Job>> onCreateLoader(int id, Bundle args) {
        nDialog = new ProgressDialog(getActivity());
        nDialog.show();
        return new RetrieveJobsAsync(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Job>> loader, ArrayList<Job> data) {
        nDialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Job>> loader) {

    }
}
