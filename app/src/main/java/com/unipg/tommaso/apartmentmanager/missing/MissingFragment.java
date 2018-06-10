package com.unipg.tommaso.apartmentmanager.missing;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import com.unipg.tommaso.apartmentmanager.R;

/**
 * Created by tommaso on 29/05/2018.
 */
@SuppressLint("NewApi")

public class MissingFragment extends Fragment {

        public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState){
           return inflater.inflate(R.layout.fragment_missing, vg,false);
        }
    }


