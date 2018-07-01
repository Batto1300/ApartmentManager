package com.unipg.tommaso.apartmentmanager.roommates;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unipg.tommaso.apartmentmanager.R;
import com.unipg.tommaso.apartmentmanager.Apartment;

import java.util.ArrayList;

/**
 * Created by tommaso on 30/05/2018.
 */

public class RoommatesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Roommate>>{
    View view;
    RoommatesAdapter adapter;
    boolean is_loaded = false;
    ProgressDialog nDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_roommate, vg,false);
        ListView list = view.findViewById(R.id.roommates_list);
        adapter = new RoommatesAdapter(Apartment.getApartment().getRoommates(), getContext());
        list.setAdapter(adapter);
        if (!is_loaded) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
            is_loaded = true;
        }else{
            //adapter.setRoommates(Apartment.getApartment().getRoommates());
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh){
            getLoaderManager().initLoader(0,null,this ).forceLoad();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Roommate>> onCreateLoader(int id, Bundle args) {
        nDialog = new ProgressDialog(getActivity());
        nDialog.show();
        return new RetrieveRoommatesAsync(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Roommate>> loader, ArrayList<Roommate> data) {
        nDialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Roommate>> loader) {

    }

}
