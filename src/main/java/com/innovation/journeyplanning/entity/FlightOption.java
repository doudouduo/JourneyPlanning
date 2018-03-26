package com.innovation.journeyplanning.entity;

import java.util.ArrayList;

public class FlightOption {
    public ArrayList<String> seat_type;
    public String seat_price;
    public ArrayList<String> airline;
    public ArrayList<String> dept_airport;
    public ArrayList<String> arv_airport;
    public String isstop;
    public String flight_day;
    public String ontime_rate;
    public String earliest_dept_time;
    public String latest_arv_time;
    public FlightOption(){
        seat_price="";
        isstop="";
        flight_day="";
        ontime_rate="";
        earliest_dept_time="";
        latest_arv_time="";
        seat_type=new ArrayList<>();
        airline=new ArrayList<>();
        dept_airport=new ArrayList<>();
        arv_airport=new ArrayList<>();
    }

    public FlightOption(ArrayList<String>st,String sp,ArrayList<String>al,ArrayList<String>da,ArrayList<String>aa,String is,String fd,String or,String edt,String lat){
        seat_type=st;
        seat_price=sp;
        airline=al;
        dept_airport=da;
        arv_airport=aa;
        isstop=is;
        flight_day=fd;
        ontime_rate=or;
        earliest_dept_time=edt;
        latest_arv_time=lat;
    }
}
