package com.uas.rentalin.ui.my_book;

class PojoMyBook {
    private String address;
    private String code_car;
    private String days;
    private String id;
    private String name_tenant;
    private String no_hp;
    private String no_ktp;
    private String type_car;
    private String type_seats;
    private String type_enginee;
    private String total_price;
    private String url_data;

    public PojoMyBook() {}

    public PojoMyBook(String address, String code_car, String days, String id, String name_tenant, String no_hp, String no_ktp, String type_car, String type_seats, String type_enginee, String total_price, String url_data) {
        this.address = address;
        this.code_car = code_car;
        this.days = days;
        this.id = id;
        this.name_tenant = name_tenant;
        this.no_hp = no_hp;
        this.no_ktp = no_ktp;
        this.type_car = type_car;
        this.type_seats = type_seats;
        this.type_enginee = type_enginee;
        this.total_price = total_price;
        this.url_data = url_data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode_car() {
        return code_car;
    }

    public void setCode_car(String code_car) {
        this.code_car = code_car;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_tenant() {
        return name_tenant;
    }

    public void setName_tenant(String name_tenant) {
        this.name_tenant = name_tenant;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getType_car() {
        return type_car;
    }

    public void setType_car(String type_car) {
        this.type_car = type_car;
    }

    public String getType_seats() {
        return type_seats;
    }

    public void setType_seats(String type_seats) {
        this.type_seats = type_seats;
    }

    public String getType_enginee() {
        return type_enginee;
    }

    public void setType_enginee(String type_enginee) {
        this.type_enginee = type_enginee;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUrl_data() {
        return url_data;
    }

    public void setUrl_data(String url_data) {
        this.url_data = url_data;
    }
}
