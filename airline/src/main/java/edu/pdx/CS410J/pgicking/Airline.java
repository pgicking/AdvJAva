package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by pgicking on 6/29/14.
 */
//The Airline class, which holds a list of flights
//The airline only takes in a name.
public class Airline extends AbstractAirline {
    String Name;
    LinkedList<AbstractFlight> FlightList = new LinkedList<>();

    //@Param    name    The airlines name
    //Creates an airline and sets the name passed in
    public Airline(String name) {
        Name = name;
    }

    @Override
    //@returns  The name of the airline
    public String getName() {
        return Name;
    }

    @Override
    //@Param    abstractFlight  A object of type flight
    public void addFlight(AbstractFlight abstractFlight) {
        FlightList.add(abstractFlight);
    }

    @Override
    //@return   The list of all the flights
    //For project 1 it will only be one
    public Collection getFlights() {
        return FlightList;
    }

    @Override
    public String toString() {
        return  Name + " " + FlightList.toString();
    }
}
