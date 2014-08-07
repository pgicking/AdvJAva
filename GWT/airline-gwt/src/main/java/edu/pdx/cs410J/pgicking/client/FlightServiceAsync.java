package edu.pdx.cs410J.pgicking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import edu.pdx.cs410J.AbstractAirline;

public interface FlightServiceAsync {

    void addFlight(String airlineName, Flight flight, AsyncCallback<AbstractAirline> async);

    void addAirline(String airlineName, AsyncCallback<AbstractAirline> async);
}
