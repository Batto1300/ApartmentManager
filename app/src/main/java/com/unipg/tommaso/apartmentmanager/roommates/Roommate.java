package com.unipg.tommaso.apartmentmanager.roommates;

import com.unipg.tommaso.apartmentmanager.missing.Missing;

import java.util.Objects;

/**
 * Created by tommaso on 23/06/2018.
 */

public class Roommate {
    private String name;
    private Boolean isAdmin;
    private String displayName;

    public Roommate(String name, String displayName){
        this.name = name;
        this.displayName = displayName;
    }
    public Roommate(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Roommate))return false;
        Roommate otherRoommate = (Roommate) other;
        return Objects.equals(otherRoommate.getName(), name);
    }
}
