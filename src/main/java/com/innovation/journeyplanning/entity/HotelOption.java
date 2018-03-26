package com.innovation.journeyplanning.entity;

import java.util.ArrayList;

public class HotelOption {
    public ArrayList<String> hotel_star;
    public ArrayList<String> hotel_type;
    public String lowest_price;
    public String highest_price;
    public String hotel_score;
    public String user_recommend;
    public String user_number;

    public HotelOption() {
        hotel_star=new ArrayList<>();
        hotel_type=new ArrayList<>();
        lowest_price="";
        highest_price="";
        hotel_score="";
        user_recommend="";
        user_number="";
    }

    public HotelOption(ArrayList<String> hst,ArrayList<String>ht,String lp,String hp,String hsc,String ur,String un){
        hotel_star=hst;
        hotel_type=ht;
        lowest_price=lp;
        highest_price=hp;
        hotel_score=hsc;
        user_recommend=ur;
        user_number=un;
    }
}