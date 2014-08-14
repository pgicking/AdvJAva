package edu.pdx.cs410J.pgicking.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.pgicking.client.Airline;
import edu.pdx.cs410J.pgicking.client.Flight;
import edu.pdx.cs410J.pgicking.client.FlightService;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by pgicking on 8/7/14.
 */
public class FlightServiceImpl extends RemoteServiceServlet implements FlightService {
    HashMap<String, Airline> airlineHashMap = new HashMap<String, Airline>();
    int j = 0;

    /**
     * Adds a flight to a specific airline
     * @param airlineName
     * @param flight
     * @return an airline object
     */
    @Override
    public AbstractAirline addFlight(String airlineName, Flight flight) {
//        Airline airline = airlineHashMap.get(airlineName);
//        airline.addFlight(flight);
//        airlineHashMap.put(airlineName,airline);
//        System.out.println(airlineHashMap.toString());
        airlineHashMap.get(airlineName).addFlight(flight);
        return airlineHashMap.get(airlineName);
    }

    /**
     * Adds an airline to the hashmap
     * @param airlineName
     * @return  An airline object
     */
    @Override
    public AbstractAirline addAirline(String airlineName) {
        Airline airline = airlineHashMap.get(airlineName);
        if(airline != null){
            return airline;
        }
        else {
            airline = new Airline(airlineName);
            airlineHashMap.put(airlineName, airline);
            return airline;
        }
    }

    /**
     * Returns all the current airlines as a string
     * @return Linked list of strings of airline names
     */
    @Override
    public LinkedList<String> getAirlines() {
        LinkedList<String> airlineList = new LinkedList<>();
        for (Airline airline : airlineHashMap.values()) {
             airlineList.add(airline.getName());
        }
        return airlineList;
    }

    /**
     * Searches the flights, used to do the actual searching but then I put that locally so the
     * src and dest paramters arent needed.
     * @param airlineName
     * @param Src
     * @param Dest
     * @return
     */
    @Override
    public AbstractAirline searchFlights(String airlineName, String Src, String Dest) {
        Airline airline = airlineHashMap.get(airlineName);

        return airline;
    }

    /**
     * Returns the airline hashmap, which will be used in conjuction with getairlines
     * @return The servers airline hashmap.
     */
    @Override
    public HashMap<String, Airline> getAirlineHashMap() {

        return airlineHashMap;
    }
}
