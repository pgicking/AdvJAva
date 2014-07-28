package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import static edu.pdx.cs410J.pgicking.Project3.CalculateFlightLength;
import static edu.pdx.cs410J.pgicking.Project3.FormatDateAsLong;

/**
 * Created by pgicking on 7/19/14.
 */
public class PrettyPrinter implements AirlineDumper{

    private String fileName;

    public PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    public PrettyPrinter(){};
    /**
     * The prettyprint version of the dump. It mimics textfile dump nearly exactly
     * the main difference is it formats the string different.
     * @param abstractAirline   Abstract airline flight list to dump
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        Collection flights;
        flights = abstractAirline.getFlights();
        Long length;
        String prettyLine = null;

        System.out.println(abstractAirline.getName());
        for (Object o : flights) {
            String srcLong = AirportNames.getName(((Flight) o).getSource().toUpperCase());
            String destLong = AirportNames.getName(((Flight) o).getDestination().toUpperCase());

            length = CalculateFlightLength(((Flight) o).getDeparture(), ((Flight) o).getArrival());
            String Depart = (((Flight) o).getDepartureString());
            //System.out.println("Writng: " + abstractAirline.toString());
            String Arrive = (((Flight) o).getArrivalString());
            prettyLine = "Flight: " + ((Flight) o).getNumber() + " Airport: " + srcLong +
                    " departs at " + Depart + " to " +
                    destLong + " arriving at " + Arrive + " Duration: " + length + " minutes";
            //System.out.println(prettyLine);
        }


    }

    public String makePrettyString(Airline abstractAirline, HttpServletResponse response) throws IOException{
        Collection flights;
        flights = abstractAirline.getFlights();
        Long length;
        String prettyLine = null;
        PrintWriter pw = response.getWriter();
        //System.out.println(abstractAirline.getName());
        for (Object o : flights) {
            String srcLong = AirportNames.getName(((Flight) o).getSource().toUpperCase());
            String destLong = AirportNames.getName(((Flight) o).getDestination().toUpperCase());

            length = CalculateFlightLength(((Flight) o).getDeparture(), ((Flight) o).getArrival());
            String Depart = (((Flight) o).getDepartureString());
            //System.out.println("Writng: " + abstractAirline.toString());
            String Arrive = (((Flight) o).getArrivalString());
            prettyLine = "Flight: " + ((Flight) o).getNumber() + " Airport: " + srcLong +
                    " departs at " + Depart + " to " +
                    destLong + " arriving at " + Arrive + " Duration: " + length + " minutes";
            pw.println(prettyLine);
            //System.out.println(prettyLine);
        }
       return prettyLine;
    }
}
