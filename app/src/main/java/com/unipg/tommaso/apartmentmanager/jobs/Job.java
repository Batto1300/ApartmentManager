package com.unipg.tommaso.apartmentmanager.jobs;

import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Job))return false;
        Job otherJob = (Job)other;
        if (!Objects.equals(otherJob.jobName,jobName)) return false;
        if (!Objects.equals(otherJob.date,date)) return false;
        return Objects.equals(otherJob.assignee, assignee);
    }
}
