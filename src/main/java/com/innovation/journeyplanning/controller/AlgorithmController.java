package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.entity.FlightOption;
import com.innovation.journeyplanning.entity.HotelOption;
import com.innovation.journeyplanning.service.Algorithm;
import com.innovation.journeyplanning.service.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;

@RestController
public class AlgorithmController {
    @Autowired
    private Count count;
    @Autowired
    private Algorithm algorithm;
    @GetMapping(value="/test_algorithm")
    public void test() throws ParseException {
        String start_date="2018-05-01", end_date="2018-05-07";
        ArrayList<String> city=new ArrayList<>();
        ArrayList<Integer>time=new ArrayList<>();
        ArrayList<String> seat_type=new ArrayList<>();
        FlightOption flightOption=new FlightOption();
        HotelOption hotelOption=new HotelOption();
        int day=count.CountDay(start_date,end_date)+1;
        city.add("北京");city.add("深圳");city.add("杭州");city.add("重庆");city.add("济南");
        time.add(0);time.add(1);time.add(1);time.add(1);time.add(0);
//        flightOption.seat_type.add("经济舱");flightOption.seat_type.add("头等舱");flightOption.seat_type.add("商务舱");
        Float[][][]cost=new Float[count.CountDay(start_date,end_date)][city.size()][city.size()];
        hotelOption.lowest_price="300";
        cost=count.CountCost(start_date,end_date,city,time,flightOption,hotelOption).cost;
        algorithm.main(start_date,end_date,city,time,flightOption,hotelOption);
//        for (int i=0;i<day;++i) {
//            for (int j = 0; j < city.size(); ++j) {
//                for (int k = 0; k < city.size(); ++k) {
//                    System.out.print(cost[i][j][k] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
    }
}
