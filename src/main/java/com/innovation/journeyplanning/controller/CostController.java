package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.entity.Flight;
import com.innovation.journeyplanning.entity.FlightOption;
import com.innovation.journeyplanning.entity.HotelOption;
import com.innovation.journeyplanning.service.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
public class CostController {
    @Autowired
    private Count count;
    @GetMapping(value="/test_cost")
    public void test() throws ParseException {
//        String dept_city="北京";String arv_city="上海";String dept_day="2018-04-03";String price="";
        String start_date="2018-04-01", end_date="2018-04-10";
        ArrayList<String>city=new ArrayList<>();
        ArrayList<Integer>time=new ArrayList<>();
        ArrayList<String>seat_type=new ArrayList<>();
        int day=count.CountDay(start_date,end_date)+1;
        FlightOption flightOption=new FlightOption();
        HotelOption hotelOption=new HotelOption();
        city.add("北京");city.add("上海");city.add("南昌");city.add("重庆");
        time.add(0);time.add(2);time.add(3);time.add(2);time.add(0);
        Float[][][]cost=new Float[count.CountDay(start_date,end_date)][city.size()][city.size()];
        cost=count.CountCost(start_date,end_date,city,time,flightOption,hotelOption).cost;
        for (int i=0;i<day;++i) {
            for (int j = 0; j < city.size(); ++j) {
                for (int k = 0; k < city.size(); ++k) {
                    System.out.print(cost[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
//            flightWriter.write();
    }

}