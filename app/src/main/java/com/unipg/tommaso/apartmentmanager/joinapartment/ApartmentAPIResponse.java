package com.unipg.tommaso.apartmentmanager.joinapartment;

/**
 * Created by tommaso on 23/06/2018.
 */

class ApartmentAPIResponse {
    public String type;
    public String errorMessage;
    public String successMessage;
    public String apartmentName;
    public ApartmentAPIResponse(String apartmentName, String type) {
        this.apartmentName = apartmentName;
        this.type = type;
    }
}
