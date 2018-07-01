package com.unipg.tommaso.apartmentmanager.missing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.unipg.tommaso.apartmentmanager.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateMissingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean>{

    Calendar myCalendar = Calendar.getInstance();
    ProgressDialog dialog;
    TextView startDateView;
    TextView endDateView;
    TextView startTimeView;
    TextView endTimeView;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_missing_activity);
        startDateView = findViewById(R.id.start_date_date);
        endDateView = findViewById(R.id.end_date_date);
        startTimeView = findViewById(R.id.start_date_time);
        endTimeView = findViewById(R.id.end_date_time);
        findViewById(R.id.create_missing_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String startDate = startDateView.getText().toString();
                String endDate = endDateView.getText().toString();
                String startTime = startTimeView.getText().toString();
                String endTime = endTimeView.getText().toString();
                Log.d("startDate",startDate);
                Log.d("endDate",endDate);
                Log.d("startTime",startTime);
                Log.d("endTime",endTime);
                if(startDate.isEmpty() | startTime.isEmpty() | endDate.isEmpty() | endTime.isEmpty()){
                    Toast.makeText(CreateMissingActivity.this,"Please fill every field",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("startDate",startDate);
                    bundle.putString("endDate",endDate);
                    bundle.putString("startTime",startTime);
                    bundle.putString("endTime",endTime);
                    getLoaderManager().initLoader(0,bundle, CreateMissingActivity.this).forceLoad();
                }
            }
        });

        startTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateMissingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeView.setText(String.format("%d:%d:00", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        endTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateMissingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTimeView.setText(String.format("%d:%d:00", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
                startDateView.setText(sdf.format(myCalendar.getTime()));            }

        };
        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
                endDateView.setText(sdf.format(myCalendar.getTime()));            }

        };
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateMissingActivity.this, startDatePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateMissingActivity.this, endDatePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();
        return new CreateMissingAsync(this,
                args.getString("startDate"),
                args.getString("endDate"),
                args.getString("startTime"),
                args.getString("endTime"));
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        dialog.dismiss();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
    }
}
