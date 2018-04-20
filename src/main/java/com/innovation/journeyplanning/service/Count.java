package com.innovation.journeyplanning.service;

import com.innovation.journeyplanning.entity.*;
import com.innovation.journeyplanning.util.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Component
public class Count {
    private Float[][][]cost;
    private ArrayList<Flight>flights[][][];
    private ArrayList<Hotel>hotels[][];
    @Autowired
    private Search search;
    public Result CountCost(String start_date, String end_date, ArrayList<String>city, ArrayList<Integer>time, FlightOption flightOption, HotelOption hotelOption){
        try {
            int day = CountDay(start_date, end_date)+1;
            flights=new ArrayList[day][city.size()][city.size()];
            hotels=new ArrayList[day][city.size()];
            cost = new Float[day][city.size()][city.size()];
            //计算符合要求的flight最低价及航班
            for (int i=0;i<day;++i){
                String date=getDate(start_date,i);
                for (int j=0;j<city.size()-1;++j){
                    for (int k=j+1;k<city.size();++k){
                        flights[i][j][k]=search.SearchFlight(city.get(j),city.get(k),date,flightOption);
                        if (j==0||k==city.size()-1){
                            Flight f=new Flight();
                            flights[i][k][j]=new ArrayList<Flight>();
                            f.setPrice((float)(1<<30));
                            flights[i][k][j].add(f);
                        }
                        else flights[i][k][j]=search.SearchFlight(city.get(k),city.get(j),date,flightOption);
                    }
                }
            }

            //计算符合要求的hotel最低价及酒店
            for (int i=0;i<day;++i){
                String date=getDate(start_date,i);
                for (int j=0;j<city.size();++j) {
                    hotels[i][j] = search.SearchHotel(city.get(j), date, hotelOption);
                    if (hotels[i][j].size()==1&&hotels[i][j].get(0).getHotel_price()==(float)(1<<30)){
                        hotelOption.hotel_star.clear();
                        hotels[i][j]= search.SearchHotel(city.get(j), date, hotelOption);
                    }
                }
            }


            //初始化cost数组
            for (int i=0;i<day;++i)
                for (int j=0;j<city.size();++j)
                    for (int k=0;k<city.size();++k)cost[i][j][k]=(float)(1<<30);
            //计算cost数组
            for (int i=0;i<day;++i){
                for (int j=0;j<city.size()-1;++j){
                    for (int k=j+1;k<city.size();++k){
                        if(i+time.get(k)<day&&flights[i][j][k].get(0).getPrice()!=(float)(1<<30)&&hotels[i][k].get(0).getHotel_price()!=(float)(1<<30)) {
                            cost[i][j][k] = flights[i][j][k].get(0).getPrice() + hotels[i][k].get(0).getHotel_price() * time.get(k);
                        }

                        if (i+time.get(j)<day&&flights[i][k][j].get(0).getPrice()!=(float)(1<<30)&&hotels[i][j].get(0).getHotel_price()!=(float)(1<<30)) {
                            cost[i][k][j] = flights[i][k][j].get(0).getPrice() + hotels[i][j].get(0).getHotel_price() * time.get(j);
                        }
                    }
                }
            }

            //输出cost数组
            for (int i=0;i<day;++i) {
                for (int j = 0; j < city.size(); ++j) {
                    for (int k = 0; k < city.size(); ++k) {
                        System.out.print(cost[i][j][k] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }

            return new Result(flights,hotels,cost);
        }catch (ParseException e){}
        return null;
    }
    public int CountDay(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    public String getDate(String smdate,int day) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        cal.add(Calendar.DATE, day);
        String date = sdf.format(cal.getTime());
        return date;
    }
}
