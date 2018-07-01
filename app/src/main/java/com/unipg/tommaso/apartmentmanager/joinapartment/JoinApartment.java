package com.unipg.tommaso.apartmentmanager.joinapartment;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unipg.tommaso.apartmentmanager.R;
import com.unipg.tommaso.apartmentmanager.Apartment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * Created by tommaso on 17/06/2018.
 */

public class JoinApartment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ApartmentAPIResponse> {
    TextView apartmentNameView;
    TextView displayNameView;
    Button joinButton;
    String displayName;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_apartment);
        joinButton = findViewById(R.id.join_apartment_button);
        apartmentNameView = findViewById(R.id.apartment_name);
        displayNameView = findViewById(R.id.display_name);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayName = displayNameView.getText().toString();
                if (Objects.equals(displayName,"")){
                    Toast.makeText(JoinApartment.this, "Please provide a display name", Toast.LENGTH_SHORT).show();
                    return;
                }
                String apartmentName =  apartmentNameView.getText().toString();
                if (Objects.equals(apartmentName,"")){
                    Toast.makeText(JoinApartment.this, "Please provide an apartment", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("type","join");
                bundle.putString("apartmentName", apartmentName);
                bundle.putString("displayName",displayName);
                getLoaderManager().initLoader(0, bundle, JoinApartment.this).forceLoad();
            }
        });
        Button createButton = findViewById(R.id.create_apartment_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apartmentNameView = findViewById(R.id.apartment_name);
                if (Objects.equals(displayName,"")){
                    Toast.makeText(JoinApartment.this, "Please provide a display name", Toast.LENGTH_SHORT).show();
                    return;
                }
                String apartmentName = apartmentNameView.getText().toString();
                if (Objects.equals(apartmentName,"")){
                    Toast.makeText(JoinApartment.this, "Please provide an apartment", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("type","create");
                bundle.putString("apartmentName",apartmentName);
                getLoaderManager().initLoader(1 , bundle, JoinApartment.this).forceLoad();
            }
        });
    }

    @Override
    public void onBackPressed() {}


    @Override
    public Loader<ApartmentAPIResponse> onCreateLoader(int id, Bundle args) {
        dialog = new ProgressDialog(this);
        String type = args.getString("type");
        String apartmentName = args.getString("apartmentName");
        String displayName = args.getString("displayName");
        if(id == 0) {
            dialog.setMessage("Joining Apartment...");
        }else{
            dialog.setMessage("Creating Apartment...");
        }
        dialog.show();
        return new ApartmentAPI(this,type,apartmentName,displayName);
    }

    @Override
    public void onLoadFinished(Loader<ApartmentAPIResponse> loader, ApartmentAPIResponse data) {
        dialog.dismiss();
        if(Objects.equals(data.type, "join")){
            if( !Objects.equals(data.errorMessage, "")) {
                Toast.makeText(this, String.format("Apartment %s does not exists",data.apartmentName),
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, String.format("Apartment %s joined",data.apartmentName),
                        Toast.LENGTH_LONG).show();
                Apartment.getApartment().setName(data.apartmentName);
                Apartment.getApartment().getMe().setDisplayName(displayName);
                new File(this.getFilesDir(), "AppData");
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput("AppData", Context.MODE_PRIVATE);
                    outputStream.write(displayName.getBytes());
                    Log.d("displayName",displayName);
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        }else{
            if( Objects.equals(data.errorMessage, "")) {
                Toast.makeText(this, String.format("Apartment %s created",data.apartmentName),
                        Toast.LENGTH_LONG).show();
                Apartment.getApartment().setName(data.apartmentName);
                Apartment.getApartment().getMe().setDisplayName(displayName);
                finish();
            }else{
                Toast.makeText(this, String.format("Apartment %s already exists",data.apartmentName),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ApartmentAPIResponse> loader) {
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
