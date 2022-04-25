package com.example.mygarage.models;

public class ServiceHistoryModel {

    private int id;
    private String service_made_date;
    private String details;

    public ServiceHistoryModel(int id, String service_made_date, String details) {
        this.id = id;
        this.service_made_date = service_made_date;
        this.details = details;
    }

    public ServiceHistoryModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_made_date() {
        return service_made_date;
    }

    public void setService_made_date(String service_made_date) {
        this.service_made_date = service_made_date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
