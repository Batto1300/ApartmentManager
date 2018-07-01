package com.unipg.tommaso.apartmentmanager.missing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.unipg.tommaso.apartmentmanager.Apartment;
import com.unipg.tommaso.apartmentmanager.R;
import com.unipg.tommaso.apartmentmanager.jobs.CreateJobActivity;

import java.util.ArrayList;

/**
 * Created by tommaso on 29/05/2018.
 */
@SuppressLint("NewApi")

public class MissingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Missing>> {

    View view;
    MissingAdapter adapter;
    Button createJobButton;
    boolean is_loaded = false;
    ProgressDialog nDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_missing, vg,false);
        ListView list = view.findViewById(R.id.missing_list);
        adapter = new MissingAdapter(Apartment.getApartment().getMissingRoommates(), getContext());
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
    public Loader<ArrayList<Missing>> onCreateLoader(int id, Bundle args) {
        nDialog = new ProgressDialog(getActivity());
        nDialog.show();
        return new RetrieveMissingAsync(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Missing>> loader, ArrayList<Missing> data) {
        nDialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Missing>> loader) {

    }
    }


