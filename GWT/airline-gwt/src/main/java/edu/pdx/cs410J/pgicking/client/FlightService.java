package edu.pdx.cs410J.pgicking.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

/**
 * Created by pgicking on 8/7/14.
 */
@RemoteServiceRelativePath("flight")
public interface FlightService extends RemoteService{

    public AbstractAirline addFlight(String airlineName, Flight flight);

    public AbstractAirline addAirline(String airlineName);

    public AbstractAirline getAirlines(String airlineName);

    public AbstractAirline searchFlights(String airlineName, String Src, String Dest);

}
