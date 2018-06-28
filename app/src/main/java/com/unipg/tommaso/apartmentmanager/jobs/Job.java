package com.unipg.tommaso.apartmentmanager.jobs;

import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import java.sql.Date;

/**
 * Created by tommaso on 10/06/2018.
 */

public class Job {
    String jobName;
    String date;
    Roommate assignee;
    public Job(String jobName, String date, Roommate assignee){
        this.jobName = jobName;
        this.date = date;
        this.assignee = assignee;
    }
}
