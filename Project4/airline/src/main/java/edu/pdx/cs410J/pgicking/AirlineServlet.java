package edu.pdx.cs410J.pgicking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AirlineServlet extends HttpServlet
{
    private final Map<String, String> data = new HashMap<String, String>();

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String key = getParameter( "key", request );
        if (key != null) {
            writeValue(key, response);

        } else {
            writeAllMappings(response);
        }
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );


        String airline = getParameter( "Airline", request );
        if (airline == null) {
            missingRequiredParameter( response, airline );
            return;
        }


        String Num = getParameter( "Flight number", request );
        if (Num == null) {
            missingRequiredParameter( response, Num );
            return;
        }
        int flightNum = Integer.parseInt(Num);

        String src = getParameter( "Source", request );
        if (src == null) {
            missingRequiredParameter( response, src );
            return;
        }


        String Depart = getParameter( "Departure", request );
        if (Depart == null) {
            missingRequiredParameter( response, Depart );
            return;
        }

        String dest = getParameter( "Destination", request );
        if (dest == null) {
            missingRequiredParameter( response, dest );
            return;
        }

        String Arrive = getParameter( "Arrival", request );
        if (Arrive == null) {
            missingRequiredParameter( response, Arrive );
            return;
        }

        Airline airline1 = new Airline(airline);

        Flight flight = new Flight(flightNum,src,Depart,dest,Arrive);

        airline1.addFlight(flight);

        PrintWriter pw = response.getWriter();
        pw.println(airline1.toString());
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
