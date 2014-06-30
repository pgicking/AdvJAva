package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractFlight;


public class Flight extends AbstractFlight {

   int number;
   String src;
   String dest;
   String Depart;
   String Arrive;

    public Flight(){}

    public Flight(int number, String src, String dest) {
        this.number = number;
        this.src = src;
        this.dest = dest;
        //Depart = depart;_
        //Arrive = arrive;
    }

    public int getNumber(){

        return number;
    }

    public  String getSource(){
        return src;
    }

    public  String getDepartureString(){
        return Depart;
    }

    public  String getDestination(){
        return dest;
    }

    public  String getArrivalString(){
        return Arrive;
    }


}

