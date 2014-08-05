package edu.pdx.cs410J.pgicking.client;

import edu.pdx.cs410J.AbstractFlight;

import java.lang.Comparable;
import java.util.Date;

import static edu.pdx.cs410J.pgicking.Project3.FormatDateStringAsDate;
import static edu.pdx.cs410J.pgicking.Project3.FormatDateStringAsString;


/**
@author Peter Gicking
 */

/**
 * <p>Class flight creates a new flight which can be
 * added to airlines, Flights consist of source and
 * destination airport codes, a flight number, and
 * arrival and departure times</p>
*/
public class Flight extends AbstractFlight implements Comparable<Flight>{

   int number;
   String src;
   String dest;
   String Depart;
   String Arrive;

    public Flight(){}

    /**
    @param  number  The flight number
    @param  src     The source airports 3 letter code
    @param  dest    The destination airports 3 letter code
    @param  depart  The departure time
    @param  arrive  The arrival time
     */
    public Flight(int number, String src, String depart, String dest, String arrive) {
        this.number = number;
        this.src = src;
        this.dest = dest;
        this.Depart = depart;
        this.Arrive = arrive;
        //System.out.println("Constructor flight: " + "Arrive: " + this.Arrive + " Depart: " + this.Depart);
    }
    /**
    @return Returns the flight number
     */
    public int getNumber(){

        return number;
    }
    /**
    @return  Returns the source airports code
    */
    public  String getSource(){
        return src;
    }

    /**
    @return Returns the depart time as a string
    */
    public String getDepartureString(){
        Depart = FormatDateStringAsString(Depart);
        return Depart;
    }

    /**
     * Returns the departure string as a date object
     * @return The departure string as a date object
     */
    public Date getDeparture(){
        Date Departure = FormatDateStringAsDate(Depart);
        return Departure;
    }

    /**
     * Returns the arrival string formatted as a date object
     * @return The arrival string s a date object
     */
    public Date getArrival(){
        Date Arrival = FormatDateStringAsDate(Arrive);
        return Arrival;
    }

    /**
    @return  returns the destination airports code
    */
    public  String getDestination(){
        return dest;
    }

    /**
    @return  returns the arrival time
    */
    public  String getArrivalString(){
        Arrive = FormatDateStringAsString(Arrive);
        return Arrive;
    }


    /**
     * @return  Returns the string of the flight information
     */
    @Override
    public String toString() {
        return
                 number + " " +
                 src + " " +
                 Depart + " " +
                 dest + " " +
                 Arrive;
    }

    /**
     * The overridden compare to method. I found this in daves notes
     * and I dont really understand quite it works though
     * @param o A flight to compare its source or departure time
     * @return  Returns an int, -1, 0 or 1
     */
    @Override
    public int compareTo(Flight o) {
        if(this.getSource().compareTo((o).getSource()) != 0) {
            return (this.getSource().compareTo(o.getSource()));
        }
        else
            return this.getDepartureString().compareTo(o.getDepartureString());
    }
}


