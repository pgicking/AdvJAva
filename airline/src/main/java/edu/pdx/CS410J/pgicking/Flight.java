package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractFlight;

/*
Class flight creates a new flight which can be
added to airlines. Flights consist of source and
destination airport codes, a flight number, and
arrival and departure times
*/
public class Flight extends AbstractFlight {

   int number;
   String src;
   String dest;
   String Depart;
   String Arrive;

    public Flight(){}

    /*
    @param  number  The flight number
    @param  src     The source airports 3 letter code
    @param  dest    The destination airports 3 letter code
    @param  depart  The departure time
    @param  arrive  The arrival time
     */
    public Flight(int number, String src, String dest, String depart, String arrive) {
        this.number = number;
        this.src = src;
        this.dest = dest;
        Depart = depart;
        Arrive = arrive;
    }
    /*
    @return Returns the flight number
     */
    public int getNumber(){

        return number;
    }
    /*
    @return  Returns the source airports code
    */
    public  String getSource(){
        return src;
    }

    /*
    @return Returns the depart time
    */
    public  String getDepartureString(){
        return Depart;
    }

    /*
    @return  returns the destination airports code
    */
    public  String getDestination(){
        return dest;
    }

    /*
    @return  returns the arrival time
    */
    public  String getArrivalString(){
        return Arrive;
    }

    @Override
    /*
    @return  Returns the string of the flight information
     */
    public String toString() {
        return
                 number + " " +
                 src + " " +
                 Depart + " " +
                 dest + " " +
                 Arrive;
    }
}

