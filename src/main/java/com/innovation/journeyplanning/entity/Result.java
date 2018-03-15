package com.innovation.journeyplanning.entity;

import java.util.ArrayList;

public class Result {
    public Float[][][]cost;
    public ArrayList<Flight>flights[][][];
    public ArrayList<Hotel>hotels[][];
    public Result(ArrayList<Flight>Resflights[][][],ArrayList<Hotel>Reshotels[][],Float[][][]Rescost){
        this.cost=Rescost;
        this.flights=Resflights;
        this.hotels=Reshotels;
    }


}
