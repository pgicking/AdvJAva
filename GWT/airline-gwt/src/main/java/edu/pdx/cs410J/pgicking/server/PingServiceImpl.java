package edu.pdx.cs410J.pgicking.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.pgicking.client.Airline;
import edu.pdx.cs410J.pgicking.client.Flight;
import edu.pdx.cs410J.pgicking.client.PingService;

/**
 * The server-side implementation of the Airline service
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService
{
    public AbstractAirline ping()
    {
        Airline airline = new Airline();
        airline.addFlight( new Flight() );
        return airline;
    }
}
