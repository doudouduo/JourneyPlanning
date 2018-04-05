package com.innovation.journeyplanning.util;

import com.innovation.journeyplanning.entity.Flight;
import com.innovation.journeyplanning.entity.FlightOption;
import com.innovation.journeyplanning.entity.Hotel;
import com.innovation.journeyplanning.entity.HotelOption;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

@Component
public class Search {
    //驱动程序名
    private String driver = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名mydata
    private String flight_url = "jdbc:mysql://111.231.132.139:3306/Flight?characterEncoding=utf8&useSSL=true";
    private String hotel_url="jdbc:mysql://119.23.41.32:3306/Hotel?characterEncoding=utf8&useSSL=true";

//    private String flight_url = "jdbc:mysql://localhost:3306/JourneyPlanning?characterEncoding=utf8&useSSL=true";
//    private String hotel_url="jdbc:mysql://localhost:3306/JourneyPlanning?characterEncoding=utf8&useSSL=true";

    //MySQL配置时的用户名
    private String user = "root";
    //MySQL配置时的密码
    private String password = "woshinibaba";

    private ArrayList<Flight> flights;
    private ArrayList<Hotel>hotels;
    public ArrayList<Flight> SearchFlight(String dept_city,
                                          String arv_city,
                                          String dept_date,
                                          FlightOption flightOption) {
        flights=new ArrayList<Flight>();
        //声明Connection对象
        Connection con;
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            Date date=new Date();
            switch (getMonth(dept_date)-date.getMonth()){
//                case 0:flight_url="jdbc:mysql://111.231.132.139:3306/Flight?characterEncoding=utf8&useSSL=true";break;
                case 0:flight_url="jdbc:mysql://111.231.132.139:3306/Flight?characterEncoding=utf8&useSSL=true";break;
                case 1:flight_url="jdbc:mysql://119.23.23.245:3306/Flight?characterEncoding=utf8&useSSL=true";break;
                case 2:flight_url="jdbc:mysql://101.200.58.140:3306/Flight?characterEncoding=utf8&useSSL=true";break;
                case 3:flight_url="jdbc:mysql://39.106.192.93:3306/Flight?characterEncoding=utf8&useSSL=true";break;
                case 4:flight_url="jdbc:mysql://120.78.161.197:3306/Flight?characterEncoding=utf8&useSSL=true";break;
                default:{
                    Flight f=new Flight();
                    f.setPrice((float)(1<<30));
                    flights.add(f);
                    return flights;
                }
            }
            con = DriverManager.getConnection(flight_url, user, password);
            if (con.isClosed())
                System.out.println("Failed to connect to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            //要执行的SQL语句
            int num=3;
            String select = "flight_id,airline,dept_date,dept_time,dept_airport,dept_city,arv_date,arv_time,arv_airport,arv_city,ontime_rate,price,flight_day";
            String where="dept_city=? and arv_city=? and dept_date=?";
//            if (!flightOption.isstop.equals(""))where=where+" and isstop<=?";
            if (!flightOption.flight_day.equals("1"))where=where+" and flight_day=?";
//            if (!flightOption.ontime_rate.equals(""))where=where+" and ontime_rate>=?";
            if (!flightOption.earliest_dept_time.equals(""))where=where+" and dept_time>=?";
            if (!flightOption.latest_arv_time.equals(""))where=where+" and arv_time<=?";
            //机场限制
            if (flightOption.dept_airport.size()!=0) {
                where = where + " and(dept_airport=?";
                for (int i = 1; i < flightOption.dept_airport.size(); ++i) where = where + " or dept_airport=?";
                where = where + ")";
            }
            if (flightOption.arv_airport.size()!=0){
                where = where + " and(arv_airport=?";
                for (int i = 1; i < flightOption.arv_airport.size(); ++i) where = where + " or arv_airport=?";
                where = where + ")";
            }

            //航空公司限制
            if (flightOption.airline.size()!=0) {
                where = where + " and(airline like ?";
                for (int i = 1; i < flightOption.airline.size(); ++i) where = where + " or airline=?";
                where = where + ")";
            }

            String sql="select ";
            sql=sql+select+" from FlightInfo where "+where;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,dept_city);
            stmt.setString(2,arv_city);
            stmt.setString(3,dept_date);
//            if (!flightOption.isstop.equals("")){
//                ++num;
//                stmt.setString(num,flightOption.isstop);
//            }
            if (!flightOption.flight_day.equals("1")){
                ++num;
                stmt.setString(num,flightOption.flight_day);
            };
//            if (!flightOption.ontime_rate.equals("")){
//                ++num;
//                stmt.setString(num,flightOption.ontime_rate);
//            }
            if (!flightOption.earliest_dept_time.equals("")){
                ++num;
                stmt.setString(num,flightOption.earliest_dept_time);
            }
            if (!flightOption.latest_arv_time.equals("")){
                ++num;
                stmt.setString(num,flightOption.latest_arv_time);
            }

            //机场限制
            if (flightOption.dept_airport.size()!=0){
                for (int i=0;i<flightOption.dept_airport.size();++i) {
                    ++num;
                    stmt.setString(num, flightOption.dept_airport.get(i));
                }
            };
            if (!flightOption.arv_airport.equals("")) {
                for (int i = 0; i < flightOption.arv_airport.size(); ++i) {
                    ++num;
                    stmt.setString(num, flightOption.arv_airport.get(i));
                }
            }

            //航空公司限制
            if (flightOption.airline.size()!=0) {
                for (int i = 0; i < flightOption.airline.size(); ++i) {
                    ++num;
                    stmt.setString(num, flightOption.airline.get(i));
                }
            }

            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Flight f=new Flight();
                f.setFlight_id(rs.getString("flight_id"));
//                f.setFlight_number(rs.getString("flight_number"));
                f.setAirline(rs.getString("airline"));
//                f.setModel(rs.getString("model"));
                f.setDept_date(rs.getString("dept_date"));
                f.setDept_time(rs.getString("dept_time"));
                f.setDept_airport(rs.getString("dept_airport"));
                f.setDept_city(rs.getString("dept_city"));
                f.setArv_date(rs.getString("arv_date"));
                f.setArv_time(rs.getString("arv_time"));
                f.setArv_airport(rs.getString("arv_airport"));
                f.setArv_city(rs.getString("arv_city"));
                f.setOntime_rate(rs.getFloat("ontime_rate"));
//                f.setSeat_type(rs.getString("seat_type"));
                f.setPrice(rs.getFloat("price"));
                f.setFlight_day(rs.getInt("flight_day"));
                flights.add(f);
            }
            while (rs.next()) {
                Flight f=new Flight();
                if(flights.size()==0){
                    int a=0;
                }
                f.setPrice(rs.getFloat("price"));
                if (f.getPrice()==flights.get(0).getPrice()){
                    f.setFlight_id(rs.getString("flight_id"));
//                    f.setFlight_number(rs.getString("flight_number"));
                    f.setAirline(rs.getString("airline"));
//                    f.setModel(rs.getString("model"));
                    f.setDept_date(rs.getString("dept_date"));
                    f.setDept_time(rs.getString("dept_time"));
                    f.setDept_airport(rs.getString("dept_airport"));
                    f.setDept_city(rs.getString("dept_city"));
                    f.setArv_date(rs.getString("arv_date"));
                    f.setArv_time(rs.getString("arv_time"));
                    f.setArv_airport(rs.getString("arv_airport"));
                    f.setArv_city(rs.getString("arv_city"));
                    f.setOntime_rate(rs.getFloat("ontime_rate"));
//                    f.setSeat_type(rs.getString("seat_type"));
                    f.setPrice(rs.getFloat("price"));
                    f.setFlight_day(rs.getInt("flight_day"));
                    flights.add(f);
                }
                else break;
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
//            System.out.println("数据库数据成功获取！！");
            System.out.println(dept_date+dept_city+"至"+arv_city+"Flight数据成功获取！！");
        }
        if (flights.size()!=0)return flights;
        else {
            Flight f=new Flight();
            f.setPrice((float)(1<<30));
            flights.add(f);
            return flights;
        }
    }

    public ArrayList<Hotel>SearchHotel(String hotel_city,
                                       String come_date,
                                       HotelOption hotelOption){
        hotels=new ArrayList<Hotel>();
        //声明Connection对象
        Connection con;
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(hotel_url, user, password);
            if (con.isClosed())
                System.out.println("Failed to connect to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            //要执行的SQL语句
            int num=2;
            String select = "hotel_city,come_date,hotel_name,hotel_address,hotel_type,hotel_star,hotel_comment,hotel_score,user_recommend,user_number,hotel_price";
            String where="hotel_city=? and come_date=?";
            if (!hotelOption.lowest_price.equals("")&&!hotelOption.highest_price.equals(""))where=where+" and hotel_price between ? and ?";
            else {
                if (!hotelOption.lowest_price.equals(""))where=where+" and hotel_price>=?";
                if (!hotelOption.highest_price.equals(""))where=where+" and hotel_price<=?";
            }
            if (!hotelOption.hotel_score.equals(""))where=where+" and hotel_score>=?";
            if (!hotelOption.user_number.equals(""))where=where+" and user_number>=?";
            if (!hotelOption.user_recommend.equals(""))where=where+" and user_recommend>=?";

            //酒店类型限制
            if (hotelOption.hotel_type.size()!=0){
                where = where + " and((hotel_type=?";
                for (int i = 1; i < hotelOption.hotel_type.size(); ++i) where = where + " or hotel_type=?";
                where = where + ")";
            }

            //酒店星级限制
            if (hotelOption.hotel_star.size()!=0){
                if (hotelOption.hotel_star.size()!=0)where=where+" or (hotel_star=?";
                else where = where + " and(hotel_star=?";
                for (int i = 1; i < hotelOption.hotel_star.size(); ++i) where = where + " or hotel_star=?";
                where = where + ")";
            }

            if (hotelOption.hotel_type.size()!=0)where=where+")";

            String sql="select "+select+" from HotelInfo where "+where+" order by hotel_price asc";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,hotel_city);
            stmt.setString(2,come_date);
            if (!hotelOption.lowest_price.equals("")&&!hotelOption.highest_price.equals("")){
                ++num;
                stmt.setString(num, hotelOption.lowest_price);
                ++num;
                stmt.setString(num, hotelOption.highest_price);
            }
            else {
                if (!hotelOption.lowest_price.equals("")) {
                    ++num;
                    stmt.setString(num, hotelOption.lowest_price);
                }
                if (!hotelOption.highest_price.equals("")) {
                    ++num;
                    stmt.setString(num, hotelOption.highest_price);
                }
            }
            if (!hotelOption.hotel_score.equals("")){
                ++num;
                stmt.setString(num,hotelOption.hotel_score);
            }
            if (!hotelOption.user_number.equals("")){
                ++num;
                stmt.setString(num,hotelOption.user_number);
            }
            if (!hotelOption.user_recommend.equals("")){
                ++num;
                stmt.setString(num,hotelOption.user_recommend);
            }

            //酒店类型限制
            if (hotelOption.hotel_type.size()!=0){
                for (int i = 0; i < hotelOption.hotel_type.size(); ++i) {
                    ++num;
                    stmt.setString(num, "%"+hotelOption.hotel_type.get(i)+"%");
                }
            }

            //酒店星级限制
            if (hotelOption.hotel_star.size()!=0){
                for (int i = 0; i < hotelOption.hotel_star.size(); ++i) {
                    ++num;
                    stmt.setString(num, "%"+hotelOption.hotel_star.get(i)+"%");
                }
            }

            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Hotel h=new Hotel();
                h.setCome_date(rs.getString("come_date"));
                h.setHotel_address(rs.getString("hotel_address"));
                h.setHotel_type(rs.getString("hotel_type"));
                h.setHotel_star(rs.getString("hotel_star"));
                h.setHotel_city(rs.getString("hotel_city"));
                h.setHotel_comment(rs.getString("hotel_comment"));
                h.setHotel_name(rs.getString("hotel_name"));
                h.setHotel_price(rs.getFloat("hotel_price"));
                h.setHotel_score(rs.getFloat("hotel_score"));
                h.setUser_recommend(rs.getFloat("user_recommend"));
                h.setUser_number(rs.getInt("user_number"));
                hotels.add(h);
            }
            while (rs.next()){
                Hotel h=new Hotel();
                h.setHotel_price(rs.getFloat("hotel_price"));
                if (h.getHotel_price()==hotels.get(0).getHotel_price()) {
                    h.setCome_date(rs.getString("come_date"));
                    h.setHotel_address(rs.getString("hotel_address"));
                    h.setHotel_type(rs.getString("hotel_type"));
                    h.setHotel_star(rs.getString("hotel_star"));
                    h.setHotel_city(rs.getString("hotel_city"));
                    h.setHotel_comment(rs.getString("hotel_comment"));
                    h.setHotel_name(rs.getString("hotel_name"));
                    h.setHotel_price(rs.getFloat("hotel_price"));
                    h.setHotel_score(rs.getFloat("hotel_score"));
                    h.setUser_recommend(rs.getFloat("user_recommend"));
                    h.setUser_number(rs.getInt("user_number"));
                    hotels.add(h);
                }
                else break;
            }
            rs.close();
            con.close();
        }catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println(come_date+hotel_city+"Hotel数据成功获取！！");
        }

        if (hotels.size()!=0)return hotels;
        else {
            Hotel h=new Hotel();
            h.setHotel_price((float)(1<<30));
            hotels.add(h);
            return hotels;
        }
    }
    public float min(float economy_price,float first_price,float business_price){
        if (economy_price<first_price&&economy_price<business_price)return economy_price;
        else if (first_price<economy_price&&first_price<business_price)return first_price;
        else return business_price;
    }
    public float min(float price1,float price2){
        if (price1<price2)return price1;
        else return  price2;
    }
    public int getMonth(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1=format.parse(date);
        return date1.getMonth();
    }
}
