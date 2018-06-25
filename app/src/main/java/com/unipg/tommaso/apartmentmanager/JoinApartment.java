package com.unipg.tommaso.apartmentmanager;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.unipg.tommaso.apartmentmanager.missing.Apartment;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by tommaso on 17/06/2018.
 */

class JoinApartment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<APIApartmentResponse> {
    TextView apartmentNameView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_apartment);
        Button joinButton = findViewById(R.id.join_apartment_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apartmentNameView = findViewById(R.id.apartment_name);
                Bundle bundle = new Bundle();
                String apartmentName =  apartmentNameView.getText().toString();
                bundle.putString("type","join");
                bundle.putString("apartmentName", apartmentName);
                getLoaderManager().initLoader(0, bundle, JoinApartment.this).forceLoad();
            }
        });
        Button createButton = findViewById(R.id.create_apartment_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apartmentNameView = findViewById(R.id.apartment_name);
                Bundle bundle = new Bundle();
                String apartmentName = apartmentNameView.getText().toString();
                bundle.putString("type","create");
                bundle.putString("apartmentName",apartmentName);
                getLoaderManager().initLoader(1 , bundle, JoinApartment.this).forceLoad();
            }
        });
    }

    @Override
    public void onBackPressed() {}


    @Override
    public Loader<APIApartmentResponse> onCreateLoader(int id, Bundle args) {
        dialog = new ProgressDialog(this);
        String type = args.getString("type");
        String apartmentName = args.getString("apartmentName");
        if(id == 0) {
            dialog.setMessage("Joining Apartment...");
        }else{
            dialog.setMessage("Creating Apartment...");
        }
        dialog.show();
        return new ApartmentAPI(this,type,apartmentName);
    }

    @Override
    public void onLoadFinished(Loader<APIApartmentResponse> loader, APIApartmentResponse data) {
        dialog.dismiss();
        if(Objects.equals(data.type, "join")){
            if( !Objects.equals(data.errorMessage, "")) {
                Toast.makeText(this, String.format("Apartment %s does not exists",data.apartmentName),
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, String.format("Apartment %s joined",data.apartmentName),
                        Toast.LENGTH_LONG).show();
                Apartment.getApartment().setName(data.apartmentName);
                finish();
            }
        }else{
            if( Objects.equals(data.errorMessage, "")) {
                Toast.makeText(this, String.format("Apartment %s created",data.apartmentName),
                        Toast.LENGTH_LONG).show();
                Apartment.getApartment().setName(data.apartmentName);
                finish();
            }else{
                Toast.makeText(this, String.format("Apartment %s already exists",data.apartmentName),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<APIApartmentResponse> loader) {
        /*Log.d("resettin","loader resetting");
        if (loader.getId() == 0) {
            dialog.show();
            Bundle bundle = new Bundle();
            String apartmentName =  apartmentNameView.getText().toString();
            bundle.putString("type","join");
            bundle.putString("apartmentName", apartmentName);
            getLoaderManager().restartLoader(0, bundle, this).forceLoad();
        }
        else {
            dialog.show();
            Bundle bundle = new Bundle();
            String apartmentName = apartmentNameView.getText().toString();
            bundle.putString("type","create");
            bundle.putString("apartmentName",apartmentName);
            getLoaderManager().restartLoader(1 , bundle, this).forceLoad();
        }*/
    }
}
