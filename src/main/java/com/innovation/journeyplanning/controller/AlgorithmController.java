package com.innovation.journeyplanning.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.innovation.journeyplanning.entity.FlightOption;
import com.innovation.journeyplanning.entity.HotelOption;
import com.innovation.journeyplanning.service.Algorithm;
import com.innovation.journeyplanning.service.Count;
import com.innovation.journeyplanning.util.Producer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

@RestController
@Component
public class AlgorithmController {
    @Autowired
    private Count count;
    @Autowired
    private Algorithm algorithm;
    @Autowired
    private Producer producer;
    @GetMapping(value="/test_algorithm")
    public void test() throws ParseException {
        String start_date="2018-05-01", end_date="2018-05-31";
        ArrayList<String> city=new ArrayList<>();
        ArrayList<Integer>time=new ArrayList<>();
        ArrayList<String> seat_type=new ArrayList<>();
        FlightOption flightOption=new FlightOption();
        HotelOption hotelOption=new HotelOption();
        int day=count.CountDay(start_date,end_date)+1;
        city.add("北京");city.add("深圳");city.add("杭州");city.add("重庆");city.add("济南");
        time.add(0);time.add(2);time.add(2);time.add(2);time.add(0);

        //航班定制
        flightOption.isstop="0";
        flightOption.flight_day="0";
        flightOption.ontime_rate="1.0";
        flightOption.earliest_dept_time="00:01";
        flightOption.latest_arv_time="23:59";

        //酒店定制
        hotelOption.lowest_price="1";
        hotelOption.highest_price="1500";
        hotelOption.hotel_score="0.0";
        hotelOption.user_recommend="0.0";
        hotelOption.user_number="0";
//        cost=count.CountCost(start_date,end_date,city,time,flightOption,hotelOption).cost;
        JSONObject result=algorithm.main(start_date,end_date,city,time,flightOption,hotelOption,1,1);
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

    @GetMapping(value="/algorithm")
    public void plan(String message) throws ParseException,TimeoutException,IOException{
        JSONObject request=JSONObject.fromObject(message);
        JSONArray jsonArray;
        ArrayList<String> city=new ArrayList<>();
        ArrayList<Integer>time=new ArrayList<>();

        FlightOption flightOption=new FlightOption();
        String dept_city=request.getString("depart_city");
        String arv_city=request.getString("final_city");
        int via_city_number=request.getInt("via_city_number");
        String start_date=request.getString("depart_date");
        String end_date=request.getString("final_date");
        int user_id=request.getInt("user_id");
        int schedule_id=request.getInt("id");

        //航班选项

//        jsonArray=request.getJSONArray("dept_airport");
//        for (int i=0;i<jsonArray.size();++i){
//            JSONObject jsonObject=jsonArray.getJSONObject(i);
//            flightOption.dept_airport.add(jsonObject.getString("dept_airport"));
//        }

//        jsonArray=request.getJSONArray("arv_airport");
//        for (int i=0;i<jsonArray.size();++i){
//            JSONObject jsonObject=jsonArray.getJSONObject(i);
//            flightOption.arv_airport.add(jsonObject.getString("arv_airport"));
//        }

//        jsonArray=request.getJSONArray("seat_type");
//        for (int i=0;i<jsonArray.size();++i){
//            JSONObject jsonObject=jsonArray.getJSONObject(i);
//            flightOption.seat_type.add(jsonObject.getString("seat_type"));
//        }

        flightOption.isstop=request.getString("isstop");
        flightOption.flight_day=request.getString("flight_day");
        flightOption.ontime_rate=request.getString("ontime_rate");
        flightOption.earliest_dept_time=request.getString("earliest_dept_hour")+":"+request.getString("earliest_dept_minute");
//        flightOption.earliest_dept_time= formatdate(request.getString("earliest_dept_time"));
        flightOption.latest_arv_time=request.getString("latest_arv_hour")+":"+request.getString("latest_arv_minute");
//        flightOption.latest_arv_time= formatdate(request.getString("latest_arv_time"));

        //酒店选项
        HotelOption hotelOption=new HotelOption();
        hotelOption.lowest_price=request.getString("lowest_price");
        hotelOption.highest_price=request.getString("highest_price");
        hotelOption.hotel_score=request.getString("hotel_score");
        hotelOption.user_recommend=request.getString("user_recommend");
        hotelOption.user_number=Integer.toString((int)(request.getDouble("user_number")*100));
        if(!request.getString("hotel_type").equals("无要求"))hotelOption.hotel_type.add(request.getString("hotel_type"));
        if(request.getInt("hotel_star")>2){
            int hotel_star=request.getInt("hotel_star");
            for (int i=hotel_star;i<=5;++i){
                switch (i){
//                    case 1:hotelOption.hotel_star.add("一星级");break;
//                    case 2:hotelOption.hotel_star.add("二星级");break;
                    case 3:hotelOption.hotel_star.add("三星级");break;
                    case 4:hotelOption.hotel_star.add("四星级");break;
                    case 5:hotelOption.hotel_star.add("五星级");break;
                }
            }
        }

        jsonArray=request.getJSONArray("cities");
        city.add(dept_city);
        time.add(0);
        String flag="";
        int total_time=0;
        for (int i=0;i<jsonArray.size();++i){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String city_name=jsonObject.getString("city_name");
            int stay_days=jsonObject.getInt("stay_days");
            for (int j=0;j<city.size();++j){
                if (city.get(j).equals(city_name)) {
                    if (time.get(j)!=stay_days){
                        flag="输入途经城市信息有误！";
                        break;
                    }
                    else {
                        flag="输入途经城市信息有重复！";
                        continue;
                    }
                }
            }
            if(flag.equals("")){
                city.add(city_name);
                time.add(stay_days);
                total_time=total_time+stay_days;
            }
        }

        for (int i=1;i<city.size();++i){
            if (arv_city.equals(city.get(i))){
                flag="输入途经城市信息有误！";
                break;
            }
        }
        if (city.size()==1&&arv_city.equals(city.get(0)))flag="输入途经城市信息有误！";
        if (flag.equals(""))city.add(arv_city);
        time.add(0);
        int day = count.CountDay(start_date, end_date) + 1;
        if (total_time>day)flag="输入出行时间有误！";

        if (flag.equals("")) {
            JSONObject result = algorithm.main(start_date, end_date, city, time, flightOption, hotelOption, user_id, schedule_id);
            producer.main(result.toString());
        }
        else {
            JSONObject result=new JSONObject();
            result.put("user_id",new Integer(user_id));
            result.put("schedule_id",new Integer(schedule_id));
            result.put("error",flag);
            result.put("min_cost",new Integer(0));
            result.put("result_num",new Integer(0));
        }
    }

    public String formatdate(String date) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        SimpleDateFormat format1=new SimpleDateFormat("HH:mm");
//        date=date.replace("Z", " UTC");
        Date date1=format.parse(date);
        String date2=format1.format(date1);
        return date2.toString();
    }

}
