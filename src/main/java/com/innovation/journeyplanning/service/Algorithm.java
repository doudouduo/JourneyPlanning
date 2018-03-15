package com.innovation.journeyplanning.service;

import com.innovation.journeyplanning.entity.Flight;
import com.innovation.journeyplanning.entity.Hotel;
import com.innovation.journeyplanning.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class Algorithm {
	
	private Integer[] time;
	private Float[][] f;
	private Float[][][]cost;
	private ArrayList<Integer> last[][];
	private ArrayList<Integer> first[][];
	private ArrayList a;
	private int dd=0;
	private int m=0;
	private Result count_result;
	private ArrayList<Flight>flights[][][];
	private ArrayList<Hotel>hotels[][];
	@Autowired
	private Count count;

	public void print(int d,int status,ArrayList<String>city_list,ArrayList<Integer>time_list)
	{
		if (status==0) {
			for (int i=a.size()-1;i>0;--i)System.out.print(city_list.get((int)a.get(i))+"->");
			System.out.println(city_list.get((int)a.get(0)));
			for (int i=a.size()-1;i>0;--i) {
				System.out.println(cost[m][(int) a.get(i)][(int) a.get(i-1)]);
				for (int j=0;j<flights[m][(int)a.get(i)][(int)a.get(i-1)].size();++j){
					Flight f=flights[m][(int)a.get(i)][(int)a.get(i-1)].get(j);
					System.out.println(f.getFlight_id()+" "+f.getDept_date()+" "+f.getDept_city()+" "+f.getArv_city()+" "+f.getPrice());
				}
				for (int j=0;j<hotels[m][(int)a.get(i-1)].size();++j){
					Hotel h=hotels[m][(int)a.get(i-1)].get(j);
					if (time_list.get((int)a.get(i-1))!=0)
					System.out.println(h.getHotel_city()+" "+h.getHotel_name()+" "+h.getCome_date()+" "+h.getHotel_price());
				}
				m=m+time[(int) a.get(i-1)];
			}
			return;
		}
		for (int i=0;i<last[dd][status].size();++i) {
		    a.add(last[dd][status].get(i));
		    m=dd;
		    int mm=dd;
		    dd=dd-time[last[dd][status].get(i)];
		    //status=status^(1<<last[m][status].get(i));
		    print(dd,status^(1<<last[m][status].get(i)),city_list,time_list);
		    //status=status^(1<<last[mm][status].get(i));
		    dd=dd+time[last[mm][status].get(i)];
		    a.remove(a.size()-1);
		}
	}
	
	public void solve(int day,int city) {
		f=new Float[day][1<<city];
		for (int i=0;i<day;++i) {
			for (int j=0;j<1<<city;++j) {
				f[i][j]=(float)(1<<30);
				last[i][j].add(-1);
				first[i][j].add(-1);
				if (j==1) {
					f[i][j]=(float)0;
					first[i][j].set(0,0);
					last[i][j].set(0, 0);
				}
			}
			/*for (int j=0;j<city;++j) {
				f[i][1<<j]=0;
				first[i][1<<j].set(0, j);
				last[i][1<<j].set(0, j);
			}*/
		}
		for (int i=1;i<1<<(city-1);++i) {
			if ((i&1)==0)continue;
			for (int j=0;j<day-1;++j) {
				for (int k=1;k<city;++k) {
					int m=i^(1<<k);
					if (k==city-1&&m!=(1<<city)-1)continue;
					if (m>i&&last[j][i].get(0)!=-1&&j+time[k]<day) {
						for (int l=0;l<last[j][i].size();++l) {
							if (f[j+time[k]][m]>f[j][i]+cost[j][last[j][i].get(l)][k]) {
								f[j+time[k]][m]=f[j][i]+cost[j][last[j][i].get(l)][k];
							    first[j+time[k]][m].clear();
							    first[j+time[k]][m].add(first[j][i].get(l));
							    last[j+time[k]][m].clear();
							    last[j+time[k]][m].add(k);
						    }
							else if (f[j+time[k]][m]==f[j][i]+cost[j][last[j][i].get(l)][k]) {
								first[j+time[k]][m].add(first[j][i].get(l));
								last[j+time[k]][m].add(k);
							}
						}
					}
				}
				if((i&(1<<(city-1)))!=0)continue;
				for (int k=1;k<1<<(city-1);++k) {
					if ((i&k)!=0||(k&1)==1)continue;
					int m=i^k;
					if (last[j][i].get(0)!=-1&&j+time[k]<day) {
						for (int l1=0;l1<last[j][i].size();++l1) 
						for (int l2=0;l2<first[j+time[k]][k].size();++l2){
							if (last[j][i].get(l1)!=-1&&first[j+time[k]][k].get(l2)!=-1) {
								if (f[j+time[k]][m]>f[j][i]+f[j+time[k]][k]+cost[j][last[j][i].get(l1)][first[j+time[k]][k].get(l2)]) {
									f[j+time[k]][m]=f[j][i]+f[j+time[k]][k]+cost[j][last[j][i].get(l1)][first[j+time[k]][k].get(l2)];
									first[j+time[k]][m].clear();
									first[j+time[k]][m].add(first[j][i].get(l1));
									last[j+time[k]][m].clear();
									last[j+time[k]][m].add(last[j+time[k]][k].get(l2));
								}
								else if (f[j+time[k]][m]==f[j][i]+f[j+time[k]][k]+cost[j][last[j][i].get(l1)][first[j+time[k]][k].get(l2)]) {
									first[j+time[k]][m].add(first[j][i].get(l1));
									last[j+time[k]][m].add(last[j+time[k]][k].get(l2));
								}
							}
						}
					}
				}
			}
		}
	}
	static int min(int a,int b) {
		if (a<b)return a;
		else return b;
	}

	public void main(String start_date, String end_date, ArrayList<String>city_list,ArrayList<Integer>time_list) throws ParseException

	{
		int city=city_list.size();
		int day=count.CountDay(start_date,end_date)+1;
		time=new Integer[1<<city];
		cost=new Float[day][city][city];
		flights=new ArrayList[day][city][city];
		hotels=new ArrayList[day][city];
		last=new ArrayList[day][1<<city];
		first=new ArrayList[day][1<<city];
		a=new ArrayList();
		a.add(city-1);
		for (int i=0;i<city;++i)time[i]=time_list.get(i);
		for (int i=city;i<1<<city;++i)time[i]=0;
		for (int i=city;i<1<<city;++i)
			for (int j=0;j<city;++j)
				if ((i&(1<<j))!=0)
					time[i]=time[i]+time[j];
		count_result=count.CountCost(start_date,end_date,city_list,time_list);
		cost=count_result.cost;
		flights=count_result.flights;
		hotels=count_result.hotels;
		for (int i=0;i<day;++i)
			for (int j=0;j<(1<<city);++j) {
				last[i][j]=new ArrayList<Integer>();
				first[i][j]=new ArrayList<Integer>();
			}
		solve(day,city);
		Float min=(float)(1<<30);
		
		for (int i=0;i<day;++i) {
			if (min>f[i][(1<<city)-1]) {
				min=f[i][(1<<city)-1];
				dd=i;
			}
		}
		System.out.println("建议第"+dd+"天结束旅行");
		int status=(1<<(city-1))-1;
		System.out.println("最少费用为："+min);
		System.out.print("最廉价旅游方案为：");
		
		print(dd,status,city_list,time_list);
		System.out.println();
	}
}