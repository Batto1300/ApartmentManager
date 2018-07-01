package com.unipg.tommaso.apartmentmanager;

import com.unipg.tommaso.apartmentmanager.jobs.Job;
import com.unipg.tommaso.apartmentmanager.missing.Missing;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by tommaso on 23/06/2018.
 */

public class Apartment {
    private static final Apartment apartment = new Apartment();
    private static final ArrayList<Roommate> roommates = new ArrayList<>();
    private static final ArrayList<Job> jobs = new ArrayList<>();
    private static final ArrayList<Missing> missingRoommates = new ArrayList<>();
    private static String name;
    private static Roommate me;


    public static Apartment getApartment() {
        return apartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Apartment.name = name;
    }

    public Roommate getMe() {
        return me;
    }

    public void setMe(Roommate me) {
        Apartment.me = me;
    }

    public ArrayList<Roommate> getRoommates() {
        return roommates;
    }


    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void addRoommate(Roommate roommate) {
        for(Roommate e: roommates){
            if(roommate.equals(e)) return;
        }
        roommates.add(roommate);
    }

    public void addJob(Job job) {
        for(Job e: jobs){
            if(job.equals(e)) return;
        }
        jobs.add(job);
    }

    public void addMissing(Missing missing){
        for(Missing e: missingRoommates){
            if(missing.equals(e)) return;
        }
        missingRoommates.add(missing);
    }

    public ArrayList<Missing> getMissingRoommates(){ return missingRoommates;}

    public Missing getMissing() {
        throw new UnsupportedOperationException();
    }

    public Roommate getRoommate(String roommateName){
        for(Roommate e: roommates){
            if(Objects.equals(e.getDisplayName(),roommateName) | Objects.equals(e.getName(),roommateName)){
                return e;
            }
        }
        return null;
    }
}
