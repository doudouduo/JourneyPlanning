package com.innovation.journeyplanning.util;

import com.innovation.journeyplanning.entity.Flight;
import com.innovation.journeyplanning.entity.Hotel;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class Search {
    //驱动程序名
    private String driver = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名mydata
    private String url = "jdbc:mysql://localhost:3306/JourneyPlanning?characterEncoding=utf8&useSSL=true";
    //MySQL配置时的用户名
    private String user = "root";
    //MySQL配置时的密码
    private String password = "123456";

    private ArrayList<Flight>flights;
    private ArrayList<Hotel>hotels;
    public ArrayList<Flight> SearchFlight(String dept_city,String arv_city,String dept_date,String seat_price) {
        ArrayList<Flight>flights=new ArrayList<Flight>();
        //声明Connection对象
        Connection con;
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            //要执行的SQL语句
            int num=3;
            String sql = "select * from FlightInfo where dept_city=? and arv_city=? and dept_date=?";
//            if (!seat_type.equals(""))sql=sql+" and seat_type=?";
            if (!seat_price.equals(""))sql=sql+" and price<=?";
            sql=sql+" order by price asc";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,dept_city);
            stmt.setString(2,arv_city);
            stmt.setString(3,dept_date);
//            if (!seat_type.equals("")){
//                ++num;
//                stmt.setString(num,seat_type);
//            }
            if (!seat_price.equals("")){
                ++num;
                stmt.setFloat(num,Float.parseFloat(seat_price));
            }
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Flight f=new Flight();
                f.setFlight_id(rs.getString("flight_id"));
//                f.setFlight_number(rs.getString("flight_number"));
                f.setAirline(rs.getString("airline"));
                f.setModel(rs.getString("model"));
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
                f.setPrice(rs.getFloat("price"));
                if (f.getPrice()==flights.get(0).getPrice()){
                    f.setFlight_id(rs.getString("flight_id"));
//                    f.setFlight_number(rs.getString("flight_number"));
                    f.setAirline(rs.getString("airline"));
                    f.setModel(rs.getString("model"));
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
            System.out.println("数据库数据成功获取！！");
        }
        if (flights.size()!=0)return flights;
        else {
            Flight f=new Flight();
            f.setPrice((float)(1<<30));
            flights.add(f);
            return flights;
        }
    }
    public ArrayList<Hotel>SearchHotel(String hotel_city,String come_date,String hotel_price){
        ArrayList<Hotel>hotels=new ArrayList<Hotel>();
        //声明Connection对象
        Connection con;
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            //要执行的SQL语句
            int num=2;
            String sql = "select * from HotelInfo where hotel_city=? and come_date=?";
            if (!hotel_price.equals(""))sql=sql+" and hotel_price<=?";
            sql=sql+" order by hotel_price asc";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,hotel_city);
            stmt.setString(2,come_date);
            if (!hotel_price.equals(""))stmt.setFloat(3, Float.parseFloat(hotel_price));
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Hotel h=new Hotel();
                h.setCome_date(rs.getString("come_date"));
                h.setHotel_address(rs.getString("hotel_address"));
                h.setHotel_city(rs.getString("hotel_city"));
                h.setHotel_comment(rs.getString("hotel_comment"));
                h.setHotel_name(rs.getString("hotel_name"));
                h.setHotel_price(rs.getFloat("hotel_price"));
                h.setHotel_score(rs.getFloat("hotel_score"));
                h.setUser_recommend(rs.getFloat("user_recommend"));
                h.setUser_star(rs.getString("user_star"));
                hotels.add(h);
            }
            while (rs.next()){
                Hotel h=new Hotel();
                h.setHotel_price(rs.getFloat("hotel_price"));
                if (h.getHotel_price()==hotels.get(0).getHotel_price()) {
                    h.setCome_date(rs.getString("come_date"));
                    h.setHotel_address(rs.getString("hotel_address"));
                    h.setHotel_city(rs.getString("hotel_city"));
                    h.setHotel_comment(rs.getString("hotel_comment"));
                    h.setHotel_name(rs.getString("hotel_name"));
                    h.setHotel_score(rs.getFloat("hotel_score"));
                    h.setUser_recommend(rs.getFloat("user_recommend"));
                    h.setUser_star(rs.getString("user_star"));
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
            System.out.println("数据库数据成功获取！！");
        }

        return hotels;
    }
}
