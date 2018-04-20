package com.innovation.journeyplanning.entity;

public class Hotel implements Comparable{
    private String hotel_city;
    private String come_date;
    private String hotel_name;
    private String hotel_address;
    private String hotel_type;
    private String hotel_star;
    private String hotel_comment;
    private float hotel_score;
    private float user_recommend;
    private int user_number;
    private float hotel_price;


    @Override
    public int compareTo(Object o){
        Hotel h=(Hotel)o;
        if (this.getHotel_price()-h.getHotel_price()>0)return 1;
        else if (this.getHotel_price()-h.getHotel_price()==0)return 0;
        else return -1;
    }

    @Override
    public boolean equals(Object o){
        boolean flag=false;
        if (o instanceof Flight){
            if (this.getHotel_price()==((Hotel)o).getHotel_price())flag=true;
        }
        return flag;
    }

    public String getHotel_city() {
        return hotel_city;
    }

    public void setHotel_city(String hotel_city) {
        this.hotel_city = hotel_city;
    }

    public String getCome_date() {
        return come_date;
    }

    public void setCome_date(String come_date) {
        this.come_date = come_date;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_type() {
        return hotel_type;
    }

    public void setHotel_type(String hotel_type) {
        this.hotel_type = hotel_type;
    }

    public String getHotel_star() {
        return hotel_star;
    }

    public void setHotel_star(String hotel_star) {
        this.hotel_star = hotel_star;
    }

    public String getHotel_comment() {
        return hotel_comment;
    }

    public void setHotel_comment(String hotel_comment) {
        this.hotel_comment = hotel_comment;
    }

    public float getHotel_score() {
        return hotel_score;
    }

    public void setHotel_score(float hotel_score) {
        this.hotel_score = hotel_score;
    }

    public float getUser_recommend() {
        return user_recommend;
    }

    public void setUser_recommend(float user_recommend) {
        this.user_recommend = user_recommend;
    }

    public int getUser_number() {
        return user_number;
    }

    public void setUser_number(int user_number) {
        this.user_number = user_number;
    }

    public float getHotel_price() {
        return hotel_price;
    }

    public void setHotel_price(float hotel_price) {
        this.hotel_price = hotel_price;
    }
}
