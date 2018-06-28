package com.unipg.tommaso.apartmentmanager.roommates;

/**
 * Created by tommaso on 23/06/2018.
 */

public class Roommate {
    private String name;
    private Boolean isAdmin;
    private String displayName;

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
}
