package edu.pdx.cs410J.pgicking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AirlineServlet extends HttpServlet
{
    private final Map<String, String> data = new HashMap<String, String>();
    private final Map<String, Airline> airlineMap = new HashMap<String, Airline>();
    private Airline airline1 = null;

    /**
     * Catches the parameters of a GET and gets the variables. If the user wants to search,
     * it calls a function that displays the matches. If the user passes -print then it
     * pretty prints the list of flights. Otherwise it just adds and does nothing.
     * @param request   The httpServletrequest
     * @param response  The httpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String airline = getParameter("name", request);
        String src = getParameter("src", request);
        String dest = getParameter("dest", request);
        String searchFlag = getParameter("searchFlag", request);
        String printFlag = getParameter("printFlag", request);

        if(src == null && dest == null) {
            writeNewFlight(response, airline);
        }

        else{
            searchFlights(response, airline,src,dest);
            //search function
        }
    }

    /**
     * The doPost method catches al the parameters sent by a POST. This is where
     * airlines are created and flights are added to them.
     * @param request The httpServletrequest
     * @param response The httpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter( "Airline", request );
//        if (airlineName == null) {
//            DebugOutput(response, "Airline");
//            missingRequiredParameter(response, "Airline");
//            return;
//        }


        String Num = getParameter( "Flight Number", request );
//        if (Num == null) {
//            DebugOutput(response, "Flight Number");
//            missingRequiredParameter( response, "Flight Number" );
//            return;
//        }
        int flightNum = Integer.parseInt(Num);

        String src = getParameter( "Source", request );
//        if (src == null) {
//            DebugOutput(response, "Source");
//            missingRequiredParameter( response, "Source" );
//            return;
//        }


        String Depart = getParameter("Departure", request);
//        if (Depart == null) {
//            DebugOutput(response, "Departure");
//            missingRequiredParameter( response, "Departure" );
//            return;
//        }

        String dest = getParameter( "Destination", request );
//        if (dest == null) {
//            DebugOutput(response, "Destination");
//            missingRequiredParameter( response, "Destination" );
//            return;
//        }

        String Arrive = getParameter( "Arrival", request );
//        if (Arrive == null) {
//            DebugOutput(response, "Arrival");
//            missingRequiredParameter( response, "Arrival" );
//            return;
//        }

        Flight flight = new Flight(flightNum,src,Depart,dest,Arrive);

        if(airlineMap.get(airlineName) == null){
            airlineMap.put(airlineName,new Airline(airlineName));
            airline1 = airlineMap.get(airlineName);
        }
        else{
            airline1 = airlineMap.get(airlineName);
        }

        airline1.addFlight(flight);

        PrintWriter pw = response.getWriter();
        //pw.println(airline1.getName() + " blah");
        //pw.println(airline1.toString());
        //writeNewFlight(response);
        //pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);

    }

    /**
     * This pretty prints the all flights in the server for a specific airline
     * @param response The httpServletResponse
     * @param airlineName The string containing the airline name.
     * @throws IOException
     */
    public void writeNewFlight(HttpServletResponse response, String airlineName) throws IOException {

        Airline airline = airlineMap.get(airlineName);

        PrettyPrinter printer = new PrettyPrinter();

        PrintWriter pw = response.getWriter();

        printer.makePrettyString(airline, response);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Displays all the flights that match the source and destination for the given airline. Errors out
     * if the airline name doesnt exist.
     * @param response The httpServletResponse
     * @param airlineName   The string containing the airline name.
     * @param src   The airport source code
     * @param dest  The airport destination code
     * @throws IOException
     */
    public void searchFlights(HttpServletResponse response, String airlineName, String src, String dest) throws IOException {
        Airline airline = airlineMap.get(airlineName);
        PrettyPrinter printer = new PrettyPrinter();

        if(airline == null){
            System.err.println("That airline does not exist!");
            return;
        }

        Collection flightlist = airline.getFlights();
        PrintWriter pw = response.getWriter();
        pw.println(airline.getName());
        for(Object o : flightlist){
            if(src.equals(((Flight)o).getSource())){
                if(dest.equals(((Flight)o).getDestination())){
                    printer.makePrettyFlight(((Flight)o),response);
                }
            }
        }

    }

    /**
     * A simple function that I used to try and debug the server.
     * @param response  The httpServletResponse
     * @param value     String containing any given string to display
     * @throws IOException
     */
    public void DebugOutput(HttpServletResponse response,String value) throws IOException {

        PrintWriter pw = response.getWriter();
        pw.println( Messages.Debugger(value));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * One of daves functions, it is not used.
     * @param response
     * @param key
     * @throws IOException
     */
    private void missingRequiredParameter( HttpServletResponse response, String key )
        throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }


    /**
     * One of daves functions, it is not used
     * @param key
     * @param response
     * @throws IOException
     */
    private void writeValue( String key, HttpServletResponse response ) throws IOException
    {
        String value = this.data.get(key);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        pw.println(Messages.formatKeyValuePair( key, value ));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * One of daves functions, it is not used
     * @param response
     * @throws IOException
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( data.size() ));

        for (Map.Entry<String, String> entry : this.data.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue()));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Gets the parameter from the servlet request and returns it.
     * @param name  The name of the value
     * @param request   request containing the actual string
     * @return
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

}
