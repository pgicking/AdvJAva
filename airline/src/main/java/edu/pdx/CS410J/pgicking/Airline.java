package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Peter Gicking on 6/29/14.
 */
//The Airline class, which holds a list of flights
//The airline only takes in a name.
public class Airline extends AbstractAirline {
    String Name;
    LinkedList<AbstractFlight> FlightList = new LinkedList<AbstractFlight>();

    /*
    Creates an airline and sets the name passed in
    @param name     The airlines name
    */
    public Airline(String name) {
        Name = name;
    }

    @Override
    /*
    Returns the name of the airline
    @returns  The name of the airline
    */
    public String getName() {
        return Name;
    }

    @Override
    /*
    addsa new flight to the flight list passed in from Project1.java
    @param abstractFlight  A object of type flight
    */
    public void addFlight(AbstractFlight abstractFlight) {
        FlightList.add(abstractFlight);
    }

    @Override
    /*
    Return the list of flight, for project1 there will only be one
    @return   The list of all the flights
    */
    public Collection getFlights() {
        return FlightList;
    }

    @Override
    /*
    Returns the string of flight information
    @return     returns the name plus the rest of the string of flight info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FlightList.element().toString());

        return  Name + " " + sb;
    }
}
