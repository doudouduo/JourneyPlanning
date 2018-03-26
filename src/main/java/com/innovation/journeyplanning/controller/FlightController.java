package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.entity.Flight;
//import com.innovation.journeyplanning.util.FlightWriter;
import com.innovation.journeyplanning.entity.FlightOption;
import com.innovation.journeyplanning.util.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class FlightController {
    @Autowired
    private Search search;
//    @Autowired
//    private FlightWriter flightWriter;

    @GetMapping(value="/test_flight")
    public ArrayList<Flight> test() throws IOException{
        String dept_city="成都";String arv_city="杭州";String dept_date="2018-04-01";
        FlightOption flightOption=new FlightOption();
        flightOption.seat_price="2000";
        ArrayList<String>seat_type=new ArrayList<>();
        ArrayList<String> airline=new ArrayList<>();
        ArrayList<String> dept_airport=new ArrayList<>();
        ArrayList<String> arv_airport=new ArrayList<>();
        flightOption.isstop="";
        flightOption.flight_day="";
        flightOption.ontime_rate="";
        flightOption.earliest_dept_time="";
        flightOption.latest_arv_time="";
//      flightOption.seat_type.add("经济舱");
        flightOption.seat_type.add("头等舱");
//      flightOption.seat_type.add("商务舱");

        return search.SearchFlight(dept_city,arv_city,dept_date,flightOption);
//            flightWriter.write();
    }
}
