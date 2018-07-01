package com.unipg.tommaso.apartmentmanager.missing;

import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

/**
 * Created by tommaso on 30/06/2018.
 */

public class Missing {
    private String startDate;
    private String endDate;
    private Roommate who;

    public Missing(String startDate,String endDate,Roommate who){
        this.startDate = startDate;
        this.endDate = endDate;
        this.who = who;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Roommate getWho() {
        return who;
    }

}
