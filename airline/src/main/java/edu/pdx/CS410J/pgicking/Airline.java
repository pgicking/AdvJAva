package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by pgicking on 6/29/14.
 */
public class Airline extends AbstractAirline {
    String Name;
    LinkedList<AbstractFlight> FlightList = new LinkedList<>();

    public Airline(String name) {
        Name = name;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void addFlight(AbstractFlight abstractFlight) {
        FlightList.add(abstractFlight);
    }

    @Override
    public Collection getFlights() {
        return null;
    }
}
