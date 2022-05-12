package com.example.mygarage.models;

public class CarModel {

    private String id;
    private String make;
    private String model;
    private String color;
    private String manufactured_date;
    private String emission_standard;
    private String engine_capacity;
    private String fuel_type;
    private int horse_power;
    private String rim_size;
    private String current_market_value;
    private int odometer;
    private boolean manual_gearbox;
    private byte[] carImage;
    private String documents_id;
    private String service_history_id;

    public CarModel(String id, String make, String model, String color,  String manufactured_date, String emission_standard, String engine_capacity, String fuel_type, int horse_power, String rim_size, String current_market_value, int odometer, boolean manual_gearbox, byte[] carImage, String documents_id, String service_history_id) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
        this.manufactured_date = manufactured_date;
        this.emission_standard = emission_standard;
        this.engine_capacity = engine_capacity;
        this.fuel_type = fuel_type;
        this.horse_power = horse_power;
        this.rim_size = rim_size;
        this.current_market_value = current_market_value;
        this.odometer = odometer;
        this.manual_gearbox = manual_gearbox;
        this.carImage = carImage;
        this.documents_id = documents_id;
        this.service_history_id = service_history_id;
    }

    public CarModel() {

    }

    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", manufactured_date=" + manufactured_date +
                ", emission_standard='" + emission_standard + '\'' +
                ", engine_capacity='" + engine_capacity + '\'' +
                ", horse_power=" + horse_power +
                ", rim_size='" + rim_size + '\'' +
                ", current_market_value='" + current_market_value + '\'' +
                ", odometer=" + odometer +
                ", manual_gearbox=" + manual_gearbox +
                ", documents_id=" + documents_id +
                ", service_history_id=" + service_history_id +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }


    public String getManufactured_date() {
        return manufactured_date;
    }

    public void setManufactured_date(String manufactured_date) {
        this.manufactured_date = manufactured_date;
    }

    public String getEmission_standard() {
        return emission_standard;
    }

    public void setEmission_standard(String emission_standard) {
        this.emission_standard = emission_standard;
    }

    public String getEngine_capacity() {
        return engine_capacity;
    }

    public void setEngine_capacity(String engine_capacity) {
        this.engine_capacity = engine_capacity;
    }


    public String getFuel_type() { return fuel_type;}

    public void setFuel_type(String fuel_type) { this.fuel_type = fuel_type; }

    public int getHorse_power() {
        return horse_power;
    }

    public void setHorse_power(int horse_power) {
        this.horse_power = horse_power;
    }

    public String getRim_size() {
        return rim_size;
    }

    public void setRim_size(String rim_size) {
        this.rim_size = rim_size;
    }

    public String getCurrent_market_value() {
        return current_market_value;
    }

    public void setCurrent_market_value(String current_market_value) {
        this.current_market_value = current_market_value;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public boolean isManual_gearbox() {
        return manual_gearbox;
    }

    public void setManual_gearbox(boolean manual_gearbox) {
        this.manual_gearbox = manual_gearbox;
    }

    public byte[] getCarImage() {
        return carImage;
    }

    public void setCarImage(byte[] carImage) {
        this.carImage = carImage;
    }

    public String getDocuments_id() {
        return documents_id;
    }

    public void setDocuments_id(String documents_id) {
        this.documents_id = documents_id;
    }

    public String getService_history_id() {
        return service_history_id;
    }

    public void setService_history_id(String service_history_id) {
        this.service_history_id = service_history_id;
    }
}
