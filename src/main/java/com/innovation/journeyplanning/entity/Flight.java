package com.innovation.journeyplanning.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class Flight  implements Comparable{
    private String flight_id;
    //    private String flight_number;
    private String airline;
    private String model;
    private String dept_date;
    private String dept_time;
    private String dept_airport;
    private String dept_city;
    private String arv_date;
    private String arv_time;
    private String arv_airport;
    private String arv_city;
    private float ontime_rate;
    //     private String seat_type;
    private float price;
    private int flight_day;

    public Flight(){

    }

    @Override
    public int compareTo(Object o){
        Flight f=(Flight)o;
        if (this.getPrice()-f.getPrice()>0)return 1;
        else if (this.getPrice()-f.getPrice()==0)return 0;
        else return -1;
    }

    @Override
    public boolean equals(Object o){
        boolean flag=false;
        if (o instanceof Flight){
            if (this.getPrice()==((Flight)o).getPrice())flag=true;
        }
        return flag;
    }


    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

//    public String getFlight_number() {
//        return flight_number;
//    }
//
//    public void setFlight_number(String flight_number) {
//        this.flight_number = flight_number;
//    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDept_date() {
        return dept_date;
    }

    public void setDept_date(String dept_date) {
        this.dept_date = dept_date;
    }

    public String getDept_time() {
        return dept_time;
    }

    public void setDept_time(String dept_time) {
        this.dept_time = dept_time;
    }

    public String getDept_airport() {
        return dept_airport;
    }

    public void setDept_airport(String dept_airport) {
        this.dept_airport = dept_airport;
    }

    public String getDept_city() {
        return dept_city;
    }

    public void setDept_city(String dept_city) {
        this.dept_city = dept_city;
    }

    public String getArv_date() {
        return arv_date;
    }

    public void setArv_date(String arv_date) {
        this.arv_date = arv_date;
    }

    public String getArv_time() {
        return arv_time;
    }

    public void setArv_time(String arv_time) {
        this.arv_time = arv_time;
    }

    public String getArv_airport() {
        return arv_airport;
    }

    public void setArv_airport(String arv_airport) {
        this.arv_airport = arv_airport;
    }

    public String getArv_city() {
        return arv_city;
    }

    public void setArv_city(String arv_city) {
        this.arv_city = arv_city;
    }

    public float getOntime_rate() {
        return ontime_rate;
    }

    public void setOntime_rate(float ontime_rate) {
        this.ontime_rate = ontime_rate;
    }

//    public String getSeat_type() {
//        return seat_type;
//    }
//
//    public void setSeat_type(String seat_type) {
//        this.seat_type = seat_type;
//    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getFlight_day() {
        return flight_day;
    }

    public void setFlight_day(int flight_day) {
        this.flight_day = flight_day;
    }
}