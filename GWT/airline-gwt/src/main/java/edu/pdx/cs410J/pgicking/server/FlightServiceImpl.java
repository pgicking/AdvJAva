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

    @Override
    public AbstractAirline addFlight(String airlineName, Flight flight) {
//        Airline airline = airlineHashMap.get(airlineName);
//        airline.addFlight(flight);
//        airlineHashMap.put(airlineName,airline);
//        System.out.println(airlineHashMap.toString());
        airlineHashMap.get(airlineName).addFlight(flight);
        return airlineHashMap.get(airlineName);
    }

//    @Override
//    public AbstractAirline addFlight(String airlineName, String FlightNum, String Src, String Depart, String dest, String Arrive) {
//        Flight flight = new Flight(Integer.parseInt(FlightNum), Src, Depart, dest, Arrive );
//        airlineHashMap.get(airlineName).addFlight(flight);
//
//        int i = 0;
//        for(Object o : airlineHashMap.get(airlineName).getFlights()) {
//            System.out.println(airlineName);
//            System.out.println(o.toString());
//            ++i;
//            ++j;
//            System.out.println(i);
//            System.out.println("j: " + j);
//        }
//        return airlineHashMap.get(airlineName);
//
//    }

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

    @Override
    public LinkedList<String> getAirlines() {
        LinkedList<String> airlineList = new LinkedList<>();
        for (Airline airline : airlineHashMap.values()) {
             airlineList.add(airline.getName());
        }
        return airlineList;
    }

    @Override
    public AbstractAirline searchFlights(String airlineName, String Src, String Dest) {
        Airline airline = airlineHashMap.get(airlineName);

        return airline;
    }
}
