package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.entity.Hotel;
import com.innovation.journeyplanning.util.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class HotelController {
    @Autowired
    private Search search;
    @GetMapping(value="/test_hotel")
    public ArrayList<Hotel> test() throws IOException {
        String hotel_city="北京";String come_date="2018-04-03";String price="";
        return search.SearchHotel(hotel_city,come_date,price);
//            flightWriter.write();
    }
}
