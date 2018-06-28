package com.unipg.tommaso.apartmentmanager.missing;

import com.unipg.tommaso.apartmentmanager.jobs.Job;
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

    public  void addRoommate(Roommate roommate) {Apartment.roommates.add(roommate);}

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void addJob(Job job) { jobs.add(job); }

    public Roommate getRoommate(String roommateName){
        for(Roommate e: roommates){
            if(Objects.equals(e.getName(),roommateName)){
                return e;
            }
        }
        return null;
    }
}
