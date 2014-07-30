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

    /**
     * One of daves functions, it is not used
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    public Response addKeyValuePair( String key, String value ) throws IOException
    {
        return post( this.url, "key", key, "value", value );
    }

    /**
     * A function I wrote originally to test adding an airline
     * @param Airline   Airline name
     * @return  A post request
     * @throws IOException
     */
    public Response addAirline(String Airline) throws IOException
    {
        return post(this.url, "Airline", Airline);
    }

    /**
     * does a GET to the servlet to search its flights in a given airline to see matches
     * @param airline   The String containing the airline name
     * @param src       The string containing the source airport code
     * @param dest      The string containing the destination airport code
     * @return  A GET request to get matching flights for the given airline
     * @throws IOException
     */
    public Response searchFlights(String airline, String src, String dest) throws IOException {
        return get(this.url, "name", airline, "src", src, "dest", dest);
    }

    /**
     * A function that does a GET to print out all the flights for a given airline
     * @param airline   The String containing the airline name
     * @return  A GET request with the airline name and print flag
     * @throws IOException
     */
    public Response getFlights(String airline) throws IOException {
        return get(this.url, "name", airline);
    }

    /**
     * A function that does a POST to the server to add a flight and potentially a new airline.
     *
     * @param args  An array containing all the parameters to make an airline and a flight
     * @return  A post request to the servlet
     * @throws IOException
     */
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
