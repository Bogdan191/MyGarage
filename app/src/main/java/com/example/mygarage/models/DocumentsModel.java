package com.example.mygarage.models;

public class DocumentsModel {

    private String id;
    private String itp_end_date;
    private String insurance_end_date;
    private String road_tax;
    private String car_id;

    public DocumentsModel(String id, String itp_end_date, String insurance_end_date, String road_tax, String car_id) {
        this.id = id;
        this.itp_end_date = itp_end_date;
        this.insurance_end_date = insurance_end_date;
        this.road_tax = road_tax;
        this.car_id = car_id;
    }

    public DocumentsModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItp_end_date() {
        return itp_end_date;
    }

    public void setItp_end_date(String itp_end_date) {
        this.itp_end_date = itp_end_date;
    }

    public String getInsurance_end_date() {
        return insurance_end_date;
    }

    public void setInsurance_end_date(String insurance_end_date) {
        this.insurance_end_date = insurance_end_date;
    }

    public String getRoad_tax() {
        return road_tax;
    }

    public void setRoad_tax(String road_tax) {
        this.road_tax = road_tax;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
}
