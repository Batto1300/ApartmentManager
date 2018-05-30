package com.unipg.tommaso.apartmentmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tommaso on 30/05/2018.
 */

class JobsFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_jobs, vg,false);
    }
}
