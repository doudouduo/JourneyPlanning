package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.entity.Flight;
import com.innovation.journeyplanning.util.FlightWriter;
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
        String dept_city="北京";String arv_city="上海";String dept_day="2018-04-03";String price="";
        return search.SearchFlight(dept_city,arv_city,dept_day,price);
//            flightWriter.write();
    }
}
