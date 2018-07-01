package com.unipg.tommaso.apartmentmanager.jobs;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.unipg.tommaso.apartmentmanager.R;
import com.unipg.tommaso.apartmentmanager.Apartment;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateJobActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean> {
    String jobName;
    String jobDate;
    String jobTime;
    Roommate jobAssignee;
    TextView jobNameView;
    TextView timeView;
    TextView dateView;
    TextView jobAssigneeView;
    Button createJobButton;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);
        createJobButton = findViewById(R.id.create_job_button);
        jobNameView = findViewById(R.id.job_name);
        jobAssigneeView = findViewById(R.id.job_assignee);
        timeView = findViewById(R.id.job_time);
        dateView = findViewById(R.id.job_date);


        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobName = jobNameView.getText().toString();
                jobDate = dateView.getText().toString();
                Log.d("jobDate",jobDate);
                jobTime = timeView.getText().toString();
                jobAssignee =  Apartment.getApartment().getRoommate(jobAssigneeView.getText().toString());
                getLoaderManager().initLoader(0, null, CreateJobActivity.this).forceLoad();
            }
        });
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateJobActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeView.setText(String.format("%d:%d:00", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
                dateView.setText(sdf.format(myCalendar.getTime()));            }

        };
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateJobActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Creating Job...");
        dialog.show();
        return new CreateJobAsync(this, jobName,jobDate,jobAssignee);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        dialog.dismiss();
        if(data){
            Toast.makeText(this,"Job successfully created",
                    Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);

            finish();
        }else{
            Toast.makeText(this,"Error creating job",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
    }
}
