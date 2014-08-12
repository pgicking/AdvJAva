package edu.pdx.cs410J.pgicking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public interface FlightServiceAsync {

    void addFlight(String airlineName, Flight flight, AsyncCallback<AbstractAirline> async);

    void addAirline(String airlineName, AsyncCallback<AbstractAirline> async);

    void getAirlines(String airlineName, AsyncCallback<AbstractAirline> async);

    void searchFlights(String airlineName, String Src, String Dest, AsyncCallback<AbstractAirline> async);
}
