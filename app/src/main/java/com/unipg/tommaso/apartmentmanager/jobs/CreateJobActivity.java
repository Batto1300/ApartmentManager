package com.unipg.tommaso.apartmentmanager.jobs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.unipg.tommaso.apartmentmanager.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateJobActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean> {
    String jobName;
    String jobDate;
    String jobTime;
    String jobAssignee;
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
                jobDate = timeView.getText().toString();
                jobTime = timeView.getText().toString();
                jobAssignee = jobAssigneeView.getText().toString();
                getLoaderManager().initLoader(0, null, CreateJobActivity.this).forceLoad();
            }
        });
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
                dateView.setText(sdf.format(myCalendar.getTime()));            }

        };
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
        return new CreateJobAsync(this, jobName,jobDate + " " + jobTime, jobAssignee);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        dialog.dismiss();
        if(data){
            Toast.makeText(this,"Job successfully created",
                    Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,"Error creating job",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }
}
