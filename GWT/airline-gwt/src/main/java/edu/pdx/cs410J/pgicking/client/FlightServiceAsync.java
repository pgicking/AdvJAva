package edu.pdx.cs410J.pgicking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public interface FlightServiceAsync {

    void addFlight(String airlineName, Flight flight, AsyncCallback<AbstractAirline> async);

    void addAirline(String airlineName, AsyncCallback<AbstractAirline> async);

    void searchFlights(String airlineName, String Src, String Dest, AsyncCallback<AbstractAirline> async);

    void getAirlines(AsyncCallback<LinkedList<String>> async);

    void getAirlineHashMap(AsyncCallback<HashMap<String, Airline>> async);

    //void addFlight(String airlineName, String FlightNum, String Src, String Depart, String dest, String Arrive, AsyncCallback<AbstractAirline> async);
}
