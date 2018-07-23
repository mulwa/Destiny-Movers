package com.example.gen.destinymovers.Pojo;

import com.google.android.gms.maps.model.LatLng;

public class Request {
    private String pickaddress;
    private LatLng picklatlng;
    private String dropaddress;
    private LatLng droplatlng;
    private String cost;
    private String truckType;
    private String requestNote;

    public Request() {
    }

    public Request(String pickaddress, LatLng picklatlng, String dropaddress,
                   LatLng droplatlng, String cost, String truckType, String requestNote) {
        this.pickaddress = pickaddress;
        this.picklatlng = picklatlng;
        this.dropaddress = dropaddress;
        this.droplatlng = droplatlng;
        this.cost = cost;
        this.truckType = truckType;
        this.requestNote = requestNote;
    }

    public String getPickaddress() {
        return pickaddress;
    }

    public void setPickaddress(String pickaddress) {
        this.pickaddress = pickaddress;
    }

    public LatLng getPicklatlng() {
        return picklatlng;
    }

    public void setPicklatlng(LatLng picklatlng) {
        this.picklatlng = picklatlng;
    }

    public String getDropaddress() {
        return dropaddress;
    }

    public void setDropaddress(String dropaddress) {
        this.dropaddress = dropaddress;
    }

    public LatLng getDroplatlng() {
        return droplatlng;
    }

    public void setDroplatlng(LatLng droplatlng) {
        this.droplatlng = droplatlng;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getRequestNote() {
        return requestNote;
    }

    public void setRequestNote(String requestNote) {
        this.requestNote = requestNote;
    }
}
