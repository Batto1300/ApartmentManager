package com.unipg.tommaso.apartmentmanager.missing;

import com.unipg.tommaso.apartmentmanager.roommates.Roommate;

import java.util.Objects;

/**
 * Created by tommaso on 30/06/2018.
 */

public class Missing {
    private String startDate;
    private String endDate;
    private Roommate who;

    public Missing(Roommate who, String startDate,String endDate){
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

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Missing))return false;
        Missing otherMissing = (Missing)other;
        if (!Objects.equals(otherMissing.getWho().getName(),who.getName())) return false;
        if (!Objects.equals(otherMissing.startDate,startDate)) return false;
        return Objects.equals(otherMissing.endDate, endDate);

    }
}
