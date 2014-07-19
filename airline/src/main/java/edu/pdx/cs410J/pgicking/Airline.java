package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Peter Gicking on 6/29/14.
 */

/**
 * <p>The Airline class, which holds a list of flights, and can return them
 * The airline only takes in a name, the rest of the command line arguments
 *  go to the Flight class to be stored.</p>
 */

public class Airline extends AbstractAirline {
    String Name;
    LinkedList<AbstractFlight> FlightList = new LinkedList<AbstractFlight>();

    /**
    Creates an airline and sets the name passed in from flight.java
    @param name     The airlines name
    */
    public Airline(String name) {
        Name = name;
    }


    /**
    Returns the name of the airline
    @return  The name of the airline
    */
    @Override
    public String getName() {
        return Name;
    }


    /**
    addsa new flight to the flight list passed in from Project1.java
    @param abstractFlight  A object of type flight
    */
    @Override
    public void addFlight(AbstractFlight abstractFlight) {
        FlightList.add(abstractFlight);
    }


    /**
    Return the list of flight, for project1 there will only be one
    @return   The list of all the flights
    */
    @Override
    public Collection getFlights() {
        return FlightList;
    }


    /**
    Returns the string of flight information
    @return     returns the name plus the rest of the string of flight info
    */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FlightList.element().toString());

        return  Name + " " + sb;
    }
}
