package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all keys and values from the server
     */
    public Response getAllKeysAndValues() throws IOException
    {
        return get(this.url );
    }

    /**
     * Returns all values for the given key
     */
    public Response getValues( String key ) throws IOException
    {
        return get(this.url, "key", key);
    }

    public Response addKeyValuePair( String key, String value ) throws IOException
    {
        return post( this.url, "key", key, "value", value );
    }

    public Response addAirline(String Airline) throws IOException
    {
        return post(this.url, "Airline", Airline);
    }

    public Response searchFlights(String airline, String src, String dest, String searchFlag) throws IOException {
        return get(this.url, "Airline", airline, "Source", src, "Destination", dest, "searchFlag", searchFlag);
    }

    public Response getFlights(String airline, String printFlag) throws IOException {
        return get(this.url, "Airline", airline, "printFlag", printFlag);
    }

    public Response addFlight(String[] args) throws IOException
    {
        String hostName = null;
        String port = null;
        String Airline = null;
        String FlightNum = null;
        String src = null;
        String Depart = null;
        String dest = null;
        String Arrive = null;

        for(String arg : args){
            if (hostName == null) {
                hostName = arg;

            } else if ( port == null) {
                port = arg;
            } else if (Airline == null){
                Airline = arg;
            } else if (FlightNum == null){
                FlightNum = arg;
            } else if (src == null){
                src = arg;
            } else if (Depart == null){
                Depart = arg;
            } else if (dest == null){
                dest = arg;
            } else if (Arrive == null) {
                Arrive = arg;
            } else if (arg == null || arg.equals("1")){
                //do nothing
            } else{
                System.err.println("Too many arguments");
            }
        }

        return post(this.url, "Airline", Airline, "Flight Number", FlightNum, "Source", src, "Departure", Depart,
                "Destination", dest, "Arrival", Arrive);
        //return post(this.url, "Airline", Airline, "Flight Number", FlightNum);
    }
}
