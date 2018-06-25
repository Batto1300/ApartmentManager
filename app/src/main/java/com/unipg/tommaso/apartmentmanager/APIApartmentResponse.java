package com.unipg.tommaso.apartmentmanager;

/**
 * Created by tommaso on 23/06/2018.
 */

class APIApartmentResponse {
    public String type;
    public String errorMessage;
    public String successMessage;
    public String apartmentName;
    public APIApartmentResponse(String apartmentName, String type) {
        this.apartmentName = apartmentName;
        this.type = type;
    }
}
