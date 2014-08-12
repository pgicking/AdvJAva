package edu.pdx.cs410J.pgicking.server;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.pgicking.client.Airline;
import edu.pdx.cs410J.pgicking.client.Flight;
import edu.pdx.cs410J.pgicking.client.FlightService;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by pgicking on 8/7/14.
 */
public class FlightServiceImpl extends RemoteServiceServlet implements FlightService {
    HashMap<String, Airline> airlineHashMap = new HashMap<>();

    @Override
    public AbstractAirline addFlight(String airlineName, Flight flight) {
        Airline airline = airlineHashMap.get(airlineName);
        airline.addFlight(flight);
        return airline;
    }

    @Override
    public AbstractAirline addAirline(String airlineName) {
        Airline airline = new Airline(airlineName);
        airlineHashMap.put(airlineName, airline);
        return airline;
    }

    @Override
    public AbstractAirline getAirlines(String airlineName) {
        return airlineHashMap.get(airlineName);
    }

    @Override
    public AbstractAirline searchFlights(String airlineName, String Src, String Dest) {
        Airline airline = airlineHashMap.get(airlineName);

        return airline;
    }
}
