package com.innovation.journeyplanning.service;

import com.innovation.journeyplanning.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class Algorithm {

	private Integer[] time;
	private Float[][] f;
	private Float[][][] cost;
	private ArrayList<Integer> last[][];
	private ArrayList<Integer> first[][];
	private ArrayList<ArrayList<Integer>> path[][];
//	private ArrayList a;
	private ArrayList<Integer> end_day;
	private int dd = 0;
	private int m = 0;
	private int result_num;
	private Result count_result;
	private ArrayList<Flight> flights[][][];
	private ArrayList<Hotel> hotels[][];
	@Autowired
	private Count count;
	private JSONObject result;
	private JSONArray jsonObject_result;

	public void print1(int d, int status, ArrayList<String> city_list, ArrayList<Integer> time_list) {
//
//		if (status == 0) {
//			jsonObject_result = new JSONArray();
//			for (int i = a.size() - 1; i > 0; --i) System.out.print(city_list.get((int) a.get(i)) + "->");
//			System.out.println(city_list.get((int) a.get(0)));
//			for (int i = a.size() - 1; i > 0; --i) {
//				JSONObject jsonObject_plan = new JSONObject();
//				JSONArray jsonArray_f = new JSONArray();
//				JSONArray jsonArray_h = new JSONArray();
//				String date = "";
//				System.out.println(cost[m][(int) a.get(i)][(int) a.get(i - 1)]);
//				for (int j = 0; j < flights[m][(int) a.get(i)][(int) a.get(i - 1)].size(); ++j) {
//					Flight f = flights[m][(int) a.get(i)][(int) a.get(i - 1)].get(j);
//					JSONObject jsonObject_f = JSONObject.fromObject(f);
//					jsonArray_f.add(j, jsonObject_f);
//					date = f.getDept_date();
//					System.out.println(f.getFlight_id() + " " + f.getDept_date() + " " + f.getDept_city() + " " + f.getArv_city() + " " + f.getPrice());
//				}
//				for (int j = 0; j < hotels[m][(int) a.get(i - 1)].size(); ++j) {
//					Hotel h = hotels[m][(int) a.get(i - 1)].get(j);
//					if (time_list.get(((int) a.get(i - 1))) != 0) {
//						JSONObject jsonObject_h = JSONObject.fromObject(h);
//						jsonArray_h.add(j, jsonObject_h);
//						date = h.getCome_date();
//						System.out.println(h.getHotel_city() + " " + h.getHotel_name() + " " + h.getCome_date() + " " + h.getHotel_price());
//					}
//				}
//				jsonObject_plan.put("cost", new Float(cost[m][(int) a.get(i)][(int) a.get(i - 1)]));
//				jsonObject_plan.put("flight", jsonArray_f);
//				jsonObject_plan.put("hotel", jsonArray_h);
//				jsonObject_plan.put("date", date);
//				jsonObject_result.add(jsonObject_plan);
//				m = m + time[1 << (int) a.get(i - 1)];
//			}
//			++result_num;
//			result.put("plan" + Integer.toString(result_num), jsonObject_result);
//			return;
//		}
//		for (int i = 0; i < last[dd][status].size(); ++i) {
//			a.add(last[dd][status].get(i));
//			m = dd;
//			int mm = dd;
//			dd = dd - time[1 << last[dd][status].get(i)];
//			//status=status^(1<<last[m][status].get(i));
//			print(dd, status ^ (1 << last[m][status].get(i)), city_list, time_list);
//			//status=status^(1<<last[mm][status].get(i));
//			dd = dd + time[1 << last[mm][status].get(i)];
//			a.remove(a.size() - 1);
//		}
	}

	public void print(int m, ArrayList<String> city_list, ArrayList<Integer> time_list) {
		int city=city_list.size();
		int d=m-time[(1<<city)-1];
		for (int k=0;k<path[m][(1<<city)-1].size();++k) {
			ArrayList<Integer>a=path[m][(1<<city)-1].get(k);
			jsonObject_result = new JSONArray();
			for (int i = 0; i <a.size()-1; ++i) System.out.print(city_list.get((int) a.get(i)) + "->");
			System.out.println(city_list.get((int) a.get(a.size()-1)));
			for (int i = 0; i < a.size()-1; ++i) {
				JSONObject jsonObject_plan = new JSONObject();
				JSONArray jsonArray_f = new JSONArray();
				JSONArray jsonArray_h = new JSONArray();
				String date = "";
				System.out.println(cost[d][(int) a.get(i)][(int) a.get(i + 1)]);
				for (int j = 0; j < flights[d][(int) a.get(i)][(int) a.get(i + 1)].size(); ++j) {
					Flight f = flights[d][(int) a.get(i)][(int) a.get(i + 1)].get(j);
					JSONObject jsonObject_f = JSONObject.fromObject(f);
					jsonArray_f.add(j, jsonObject_f);
					date = f.getDept_date();
					System.out.println(f.getFlight_id() + " " + f.getDept_date() + " " + f.getDept_city() + " " + f.getArv_city() + " " + f.getPrice());
				}
				for (int j = 0; j < hotels[d][(int) a.get(i + 1)].size(); ++j) {
					Hotel h = hotels[d][(int) a.get(i + 1)].get(j);
					if (time_list.get(((int) a.get(i + 1))) != 0) {
						JSONObject jsonObject_h = JSONObject.fromObject(h);
						jsonArray_h.add(j, jsonObject_h);
						date = h.getCome_date();
						System.out.println(h.getHotel_city() + " " + h.getHotel_name() + " " + h.getCome_date() + " " + h.getHotel_price());
					}
				}
				jsonObject_plan.put("cost", new Float(cost[m][(int) a.get(i)][(int) a.get(i + 1)]));
				jsonObject_plan.put("flight", jsonArray_f);
				jsonObject_plan.put("hotel", jsonArray_h);
				jsonObject_plan.put("date", date);
				jsonObject_result.add(jsonObject_plan);
				d=d+time[1<<(int) a.get(i+1)];
			}
			++result_num;
			result.put("plan" + Integer.toString(result_num), jsonObject_result);
			return;
		}
	}

	public void solve1(int day,int city) {
//		f=new Float[day][1<<city];
//		last=new ArrayList[day][1<<city];
//		first=new ArrayList[day][1<<city];
//		path=new ArrayList[day][1<<city];
//		for (int i=0;i<day;++i)
//			for (int j=0;j<(1<<city);++j) {
//				last[i][j]=new ArrayList<Integer>();
//				first[i][j]=new ArrayList<Integer>();
//				path[i][j]=new ArrayList<ArrayList<Integer>>();
//			}
//		for (int i=0;i<day;++i) {
//			for (int j=0;j<1<<city;++j) {
//				f[i][j]=(float)(1<<30);
//				last[i][j].add(-1);
//				first[i][j].add(-1);
//				if (j==1) {
//					f[i][j]=(float)0;
//					first[i][j].set(0,0);
//					last[i][j].set(0, 0);
//				}
//			}
//			for (int j=0;j<city;++j) {
//				f[i][1<<j]=(float)0;
//				first[i][1<<j].set(0, j);
//				last[i][1<<j].set(0, j);
//			}
//		}
//		for (int i=1;i<1<<(city-1);++i) {
////			if ((i&1)==0)continue;
//			for (int j=0;j<day-1;++j) {
////				for (int k=1;k<city;++k) {
////					int m=i^(1<<k);
////					if (k==city-1&&m!=(1<<city)-1)continue;
////					if (m>i&&last[j][i].get(0)!=-1&&j+time[1<<k]<day) {
////						for (int l=0;l<last[j][i].size();++l) {
////							if (f[j+time[1<<k]][m]>f[j][i]+cost[j][last[j][i].get(l)][k]) {
////								f[j+time[1<<k]][m]=f[j][i]+cost[j][last[j][i].get(l)][k];
////							    first[j+time[1<<k]][m].clear();
////							    first[j+time[1<<k]][m].add(first[j][i].get(l));
////							    last[j+time[1<<k]][m].clear();
////							    last[j+time[1<<k]][m].add(k);
////						    }
////							else if (f[j+time[1<<k]][m]==f[j][i]+cost[j][last[j][i].get(l)][k]) {
////								first[j+time[1<<k]][m].add(first[j][i].get(l));
////								last[j+time[1<<k]][m].add(k);
////							}
////						}
////					}
////				}
////				if((i&(1<<(city-1)))!=0)continue;
//				for (int k=1;k<1<<city;++k) {
//					if ((i&k)!=0||(k&1)==1)continue;
//					int m=i^k;
//					if (m==15){
//						int x=0;
//					}
//					if (last[j][i].get(0)!=-1&&j+time[k]<day) {
//						for (int l1=0;l1<last[j][i].size();++l1)
//						for (int l2=0;l2<first[j+time[k]][k].size();++l2){
//							if (last[j][i].get(l1)!=-1&&first[j+time[k]][k].get(l2)!=-1) {
//								if (f[j+time[k]][m]>f[j][i]+f[j+time[k]][k]+cost[j][last[j][i].get(l1)][first[j+time[k]][k].get(l2)]) {
//									f[j+time[k]][m]=f[j][i]+f[j+time[k]][k]+cost[j][last[j][i].get(l1)][first[j+time[k]][k].get(l2)];
//									first[j+time[k]][m].clear();
//									first[j+time[k]][m].add(first[j][i].get(l1));
//									last[j+time[k]][m].clear();
//									last[j+time[k]][m].add(last[j+time[k]][k].get(l2));
//
//									System.out.println("第"+Integer.toString(j+time[k])+"天状态"+Integer.toString(m)+"由第"+Integer.toString(j)+"天状态"+Integer.toString(i)+"和第"+Integer.toString(j+time[k])+"天状态"+Integer.toString(k)+"组成\n");
//								}
//								else if (Math.abs(f[j+time[k]][m]-f[j][i]+f[j+time[k]][k]+cost[j][last[j][i].get(l1)][first[j+time[k]][k].get(l2)])<=0) {
//									boolean flag=true;
//									for (int l3=0;l3<first[j+time[k]][m].size();++l3){
//										if (first[j+time[k]][m].get(l3)==first[j][i].get(l1)&&last[j + time[k]][m].get(l3)==last[j + time[k]][k].get(l2)){
//											flag=false;
//											break;
//										}
//									}
//									if (flag) {
//										first[j + time[k]][m].add(first[j][i].get(l1));
//										last[j + time[k]][m].add(last[j + time[k]][k].get(l2));
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
	}

	public void solve(int day, int city) {
		f = new Float[day][1 << city];
		last = new ArrayList[day][1 << city];
		first = new ArrayList[day][1 << city];
		path = new ArrayList[day][1 << city];
		for (int i = 0; i < day; ++i)
			for (int j = 0; j < (1 << city); ++j) {
				last[i][j] = new ArrayList<Integer>();
				first[i][j] = new ArrayList<Integer>();
				path[i][j] = new ArrayList<ArrayList<Integer>>();
			}
		for (int i = 0; i < day; ++i) {
			for (int j = 0; j < 1 << city; ++j) {
				f[i][j] = (float) (1 << 30);
				if (j == 1) {
					f[i][j] = (float) 0;
				}
			}
			for (int j = 0; j < city; ++j) {
				f[i][1 << j] = (float) 0;
				ArrayList<Integer> p = new ArrayList<Integer>();
				p.add(j);
				path[i][1<<j].add(p);
			}
		}
		for (int i = 1; i < 1 << (city - 1); ++i) {
			for (int j = 0; j < day - 1; ++j) {
				for (int k = 1; k < 1 << city; ++k) {
					if ((i & k) != 0 || (k & 1) == 1) continue;
					int m = i ^ k;
					for (int l1 = 0; l1 < path[j][i].size(); ++l1) {
						if (j + time[k] < day) {
							for (int l2 = 0; l2 < path[j + time[k]][k].size(); ++l2) {
								if (f[j + time[k]][m] > f[j][i] + f[j + time[k]][k] + cost[j][path[j][i].get(l1).get(path[j][i].get(l1).size() - 1)][path[j + time[k]][k].get(l2).get(0)]) {
									f[j + time[k]][m] = f[j][i] + f[j + time[k]][k] + cost[j][path[j][i].get(l1).get(path[j][i].get(l1).size() - 1)][path[j + time[k]][k].get(l2).get(0)];
									path[j + time[k]][m].clear();
									ArrayList<Integer> p = new ArrayList<Integer>();
									for (int m1 = 0; m1 < path[j][i].get(l1).size(); ++m1)
										p.add(path[j][i].get(l1).get(m1));
									for (int m2 = 0; m2 < path[j + time[k]][k].get(l2).size(); ++m2)
										p.add(path[j + time[k]][k].get(l2).get(m2));
									path[j + time[k]][m].add(p);
//									System.out.println("第" + Integer.toString(j + time[k]) + "天状态" + Integer.toString(m) + "由第" + Integer.toString(j) + "天状态" + Integer.toString(i) + "和第" + Integer.toString(j + time[k]) + "天状态" + Integer.toString(k) + "组成\n");
								} else if (Math.abs(f[j + time[k]][m] - f[j][i] + f[j + time[k]][k] + cost[j][path[j][i].get(l1).get(path[j][i].get(l1).size() - 1)][path[j + time[k]][k].get(l2).get(0)]) <= 0) {
									ArrayList<Integer> p = new ArrayList<Integer>();
									for (int m1 = 0; m1 < path[j][i].get(l1).size(); ++m1)
										p.add(path[j][i].get(l1).get(m1));
									for (int m2 = 0; m2 < path[j + time[k]][k].get(l2).size(); ++m2)
										p.add(path[j][i].get(l1).get(m2));
									boolean flag = true;
									for (int l3 = 0; l3 < path[j + time[k]][m].size(); ++l3) {
										if (path[j + time[k]][m].get(l3).equals(p)) {
											flag = false;
											break;
										}
									}
									if (flag) {
										path[j + time[k]][m].add(p);
									}
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

	public JSONObject main(String start_date, String end_date, ArrayList<String>city_list, ArrayList<Integer>time_list, FlightOption flightOption, HotelOption hotelOption,int user_id,int schedule_id) throws ParseException

	{
		int city=city_list.size();
		int day=count.CountDay(start_date,end_date)+1;
		time=new Integer[1<<city];
		cost=new Float[day][city][city];
		flights=new ArrayList[day][city][city];
		hotels=new ArrayList[day][city];
		result_num=0;
		end_day=new ArrayList<>();
//		a=new ArrayList();
//		a.add(city-1);
		for (int i=0;i<city;++i)time[i]=time_list.get(i);
		long data_start_time=System.nanoTime();
		count_result=count.CountCost(start_date,end_date,city_list,time_list,flightOption,hotelOption);
		long data_end_time=System.nanoTime();
		cost=count_result.cost;
		flights=count_result.flights;
		hotels=count_result.hotels;

		for (int i=city;i<1<<city;++i)time[i]=0;
		for (int i=city;i<1<<city;++i)
			for (int j=0;j<city;++j)
				if ((i&(1<<j))!=0)
					time[i]=time[i]+time[j];
		for (int i=city-1;i>0;--i) {
			boolean flag = false;
			for (int j = 0; j < city; ++j)
				if (i == (1 << j)){
					time[i]=time[j];
					flag=true;
				}
			if (!flag)time[i]=0;
		}
		for (int i=0;i<city;++i)
			for (int j=0;j<city;++j)
				if ((i&(1<<j))!=0&&i!=(1<<j))
					time[i]=time[i]+time[1<<j];

		long solve_start_time=System.nanoTime();
		solve(day,city);
		long solve_end_time=System.nanoTime();
		Float min=(float)(1<<30);

		for (int i=0;i<day;++i) {
			if (min>f[i][(1<<city)-1]) {
				min=f[i][(1<<city)-1];
				end_day.clear();
				end_day.add(i);
			}
			else {
				if (Math.abs(min-f[i][(1<<city)-1])<=0&&min!=(float)(1<<30)){
					end_day.add(i);
				}
			}
		}
		if (Math.abs(min-(float)(1<<30))<=0){
			for (int i=0;i<day;++i){
				for (int j=0;j<city_list.size()-1;++j){
					for (int k=j+1;k<city_list.size();++k){
						if (city_list.get(j)!=city_list.get(k)&&flights[i][j][k].get(0).getPrice()==(float)(1<<30)){
							flights[i][j][k].get(0).setPrice(0);
							cost[i][j][k]=hotels[i][k].get(0).getHotel_price()*time_list.get(k);
						}
						if (!(j==0||k==city_list.size()-1||city_list.get(j)==city_list.get(k))&&flights[i][j][k].get(0).getPrice()==(float)(1<<30)){
							flights[i][k][j].get(0).setPrice(0);
							cost[i][k][j]=hotels[i][j].get(0).getHotel_price()*time_list.get(j);
						}
					}
				}
			}
			solve(day,city);
			solve_end_time=System.nanoTime();
			for (int i=0;i<day;++i) {
				if (min>f[i][(1<<city)-1]) {
					min=f[i][(1<<city)-1];
					end_day.clear();
					end_day.add(i);
				}
				else if (min==f[i][(1<<city)-1]&&min!=(float)(1<<30))end_day.add(i);
			}
		}
		result=new JSONObject();
		result.put("user_id", new Integer(user_id));
		result.put("schedule_id", new Integer(schedule_id));
		result.put("min_cost",min);
		System.out.println("最少费用为：" + min);
		for (int i=0;i<end_day.size();++i) {
			System.out.println("建议第" + (end_day.get(i)-time[(1<<city)-1]) + "天开始旅行");
			System.out.print("最廉价旅游方案为：");

			print(end_day.get(i), city_list, time_list);
//			result.put("result"+Integer.toString(i+1), jsonObject_result);
//			System.out.println();
		}
		result.put("result_num",result_num);
		if (result_num==0)result.put("error","找不到合适的出行方案！");
		System.out.println(result.toString());
		System.out.println("数据库访问时间:"+Double.toString((float)(data_end_time-data_start_time)/1e9/60)+"分钟");
		System.out.println("计算solution时间:"+Double.toString((float)(solve_end_time-solve_start_time)/1e6)+"毫秒");
		return result;
	}
}
