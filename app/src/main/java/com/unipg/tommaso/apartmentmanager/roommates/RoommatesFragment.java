package com.unipg.tommaso.apartmentmanager.roommates;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unipg.tommaso.apartmentmanager.R;

import java.util.ArrayList;

/**
 * Created by tommaso on 30/05/2018.
 */

public class RoommatesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<String>>{
    View view;
    RoommatesAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_roommate, vg,false);
        ListView list = view.findViewById(R.id.roommates_list);
        ArrayList roommatesList = new ArrayList<>();
        adapter = new RoommatesAdapter(roommatesList, getContext());
        list.setAdapter(adapter);
        getLoaderManager().initLoader(0,null,this).forceLoad();
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args) {
        return new RetrieveRoommatesAsync(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> data) {
        adapter.setRoommates(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }

}
