package com.uas.rentalin.ui.menu;

public class PojoDataCar {
    private String attachment;
    private String code_car;
    private String id;
    private String price_day;
    private String type_car;
    private String type_enginee;
    private String type_seats;
    private String url_data;
    private String data;

    public PojoDataCar() {}

    public PojoDataCar(String attachment, String code_car, String id, String price_day, String type_car, String type_enginee, String type_seats, String url_data, String data) {
        this.attachment = attachment;
        this.code_car = code_car;
        this.id = id;
        this.price_day = price_day;
        this.type_car = type_car;
        this.type_enginee = type_enginee;
        this.type_seats = type_seats;
        this.url_data = url_data;
        this.data = data;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCode_car() {
        return code_car;
    }

    public void setCode_car(String code_car) {
        this.code_car = code_car;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice_day() {
        return price_day;
    }

    public void setPrice_day(String price_day) {
        this.price_day = price_day;
    }

    public String getType_car() {
        return type_car;
    }

    public void setType_car(String type_car) {
        this.type_car = type_car;
    }

    public String getType_enginee() {
        return type_enginee;
    }

    public void setType_enginee(String type_enginee) {
        this.type_enginee = type_enginee;
    }

    public String getType_seats() {
        return type_seats;
    }

    public void setType_seats(String type_seats) {
        this.type_seats = type_seats;
    }

    public String getUrl_data() {
        return url_data;
    }

    public void setUrl_data(String url_data) {
        this.url_data = url_data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
