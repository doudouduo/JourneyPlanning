package com.innovation.journeyplanning.controller;

import com.innovation.journeyplanning.entity.Hotel;
import com.innovation.journeyplanning.entity.HotelOption;
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
        String hotel_city="北京";String come_date="2018-05-01";
        HotelOption hotelOption=new HotelOption();
        hotelOption.user_number="100";
        hotelOption.user_recommend="80";
        hotelOption.hotel_type.add("舒适型");
         hotelOption.hotel_star.add("四星");
        return search.SearchHotel(hotel_city,come_date,hotelOption);
//            flightWriter.write();
    }
}
