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

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String airline = getParameter("Airline", request);

        writeNewFlight(response, airline);
        /*String key = getParameter( "key", request );
        if (key != null) {
            writeValue(key, response);

        } else {
            writeAllMappings(response);
        }*/
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter( "Airline", request );
        if (airlineName == null) {
            DebugOutput(response, "Airline");
            missingRequiredParameter(response, "Airline");
            return;
        }


        String Num = getParameter( "Flight Number", request );
        if (Num == null) {
            DebugOutput(response, "Flight Number");
            missingRequiredParameter( response, "Flight Number" );
            return;
        }
        int flightNum = Integer.parseInt(Num);

        String src = getParameter( "Source", request );
        if (src == null) {
            DebugOutput(response, "Source");
            missingRequiredParameter( response, "Source" );
            return;
        }


        String Depart = getParameter("Departure", request);
        if (Depart == null) {
            DebugOutput(response, "Departure");
            missingRequiredParameter( response, "Departure" );
            return;
        }

        String dest = getParameter( "Destination", request );
        if (dest == null) {
            DebugOutput(response, "Destination");
            missingRequiredParameter( response, "Destination" );
            return;
        }

        String Arrive = getParameter( "Arrival", request );
        if (Arrive == null) {
            DebugOutput(response, "Arrival");
            missingRequiredParameter( response, "Arrival" );
            return;
        }

        Flight flight = new Flight(flightNum,src,Depart,dest,Arrive);

        if(airlineMap.get(airlineName) == null){
            airlineMap.put(airlineName,new Airline(airlineName));
            airline1 = airlineMap.get(airlineName);
        }
        else{
            airline1 = airlineMap.get(airlineName);
        }


        //airline1 = new Airline(airlineName);


        airline1.addFlight(flight);

        PrintWriter pw = response.getWriter();
        //pw.println(airline1.toString());
        //writeNewFlight(response);
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);

        /*String key = getParameter( "key", request );
        if (key == null) {
            missingRequiredParameter( response, key );
            return;
        }

        String value = getParameter( "value", request );
        if ( value == null) {
            missingRequiredParameter( response, value );
            return;
        }

        this.data.put(key, value);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.mappedKeyValue(key, value));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
        */
    }

    public void writeNewFlight(HttpServletResponse response, String airlineName) throws IOException {

//        String airlineName = this.airline1.getName();
//        Collection flightlist = this.airline1.getFlights();
//        Airline airline = this.airline1;

        Airline airline = airlineMap.get(airlineName);

        PrettyPrinter printer = new PrettyPrinter();
        //String pretty = printer.makePrettyString(airline, response);

        PrintWriter pw = response.getWriter();

        pw.println(airline.getName());
        //pw.println(pretty);
        String pretty = printer.makePrettyString(airline, response);

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    public void DebugOutput(HttpServletResponse response,String value) throws IOException {

        PrintWriter pw = response.getWriter();
        pw.println( Messages.Debugger(value));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    private void missingRequiredParameter( HttpServletResponse response, String key )
        throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(key));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }



    private void writeValue( String key, HttpServletResponse response ) throws IOException
    {
        String value = this.data.get(key);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        pw.println(Messages.formatKeyValuePair( key, value ));

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

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

    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

}
