package com.example.gen.destinymovers.Pojo;

import com.google.android.gms.maps.model.LatLng;

public class Request {
    private String pickaddress;
    private String dropaddress;
    private long cost;
    private String truckType;
    private String requestNote;
    private Double  droplat;
    private Double droplng;
    private Double  picklat;
    private Double picklng;

    public Request() {
    }

    public Request(String pickaddress, String dropaddress, long cost, String truckType, String requestNote,
                   Double droplat, Double droplng, Double picklat, Double picklng) {
        this.pickaddress = pickaddress;
        this.dropaddress = dropaddress;
        this.cost = cost;
        this.truckType = truckType;
        this.requestNote = requestNote;
        this.droplat = droplat;
        this.droplng = droplng;
        this.picklat = picklat;
        this.picklng = picklng;
    }

    public String getPickaddress() {
        return pickaddress;
    }

    public void setPickaddress(String pickaddress) {
        this.pickaddress = pickaddress;
    }

    public String getDropaddress() {
        return dropaddress;
    }

    public void setDropaddress(String dropaddress) {
        this.dropaddress = dropaddress;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
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

    public Double getDroplat() {
        return droplat;
    }

    public void setDroplat(Double droplat) {
        this.droplat = droplat;
    }

    public Double getDroplng() {
        return droplng;
    }

    public void setDroplng(Double droplng) {
        this.droplng = droplng;
    }

    public Double getPicklat() {
        return picklat;
    }

    public void setPicklat(Double picklat) {
        this.picklat = picklat;
    }

    public Double getPicklng() {
        return picklng;
    }

    public void setPicklng(Double picklng) {
        this.picklng = picklng;
    }
}
