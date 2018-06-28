package com.unipg.tommaso.apartmentmanager.jobs;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.unipg.tommaso.apartmentmanager.R;
import com.unipg.tommaso.apartmentmanager.roommates.RoommatesAdapter;

import java.util.ArrayList;

/**
 * Created by tommaso on 30/05/2018.
 */

public class JobsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Job>> {
    View view;
    JobsAdapter adapter;
    Button createJobButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jobs, vg, false);
        createJobButton = view.findViewById(R.id.jobs_button);
        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),CreateJobActivity.class);
                startActivity(i);
            }
        });
        ArrayList<Job> jobsList = new ArrayList<>();
        ListView list = view.findViewById(R.id.jobs_list);
        adapter = new JobsAdapter(jobsList, getContext());
        list.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this).forceLoad();
        return view;
    }

    @Override
    public Loader<ArrayList<Job>> onCreateLoader(int id, Bundle args) {
        return new RetrieveJobsAsync(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Job>> loader, ArrayList<Job> data) {
        adapter.setJobs(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Job>> loader) {

    }
}
