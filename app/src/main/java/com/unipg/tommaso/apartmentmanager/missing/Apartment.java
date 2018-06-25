package com.unipg.tommaso.apartmentmanager.missing;

import com.unipg.tommaso.apartmentmanager.jobs.Job;
import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import java.util.ArrayList;

/**
 * Created by tommaso on 23/06/2018.
 */

public class Apartment {
    private static final Apartment apartment = new Apartment();
    private static final ArrayList<Roommate> roommates = new ArrayList<>();
    private static String name;
    private static Roommate me;
    private static ArrayList<Job> jobs;


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

    public void addRoommate(Roommate roommate,Boolean is_me) {
        roommates.add(roommate);
        if(is_me) {
            me = roommate;
        }
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        Apartment.jobs = jobs;
    }


}
