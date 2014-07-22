package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

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

    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        Collection flights;
        flights = abstractAirline.getFlights();
        int length;

        if(!fileName.equals("-")) {
            if (!fileName.contains(".txt")) {
                System.err.print("File must be a .txt");
                System.exit(1);
            }
            //PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
            writer.println(abstractAirline.getName());
            for(Object o : flights) {
                length = CalculateFlightLength(((Flight)o).getDeparture(),((Flight)o).getArrival());
                String Depart = (((Flight)o).getDepartureString());
                //System.out.println("Writng: " + abstractAirline.toString());
                String Arrive = (((Flight)o).getArrivalString());
                String prettyLine = "Flight: " + ((Flight)o).getNumber() + " Airport: " + ((Flight)o).getSource() +
                        " departs at " + Depart + " to " +
                        ((Flight)o).getDestination() + " arriving at " + Arrive + " Duration: " + length + " minutes";
                //System.out.println("Writng: " + abstractAirline.toString());
                writer.println(prettyLine);
            }
            writer.close();
        }
        else {
            System.out.println(abstractAirline.getName());
            for (Object o : flights) {
                length = CalculateFlightLength(((Flight)o).getDeparture(),((Flight)o).getArrival());
                String Depart = (((Flight)o).getDepartureString());
                //System.out.println("Writng: " + abstractAirline.toString());
                String Arrive = (((Flight)o).getArrivalString());
                String prettyLine = "Flight: " + ((Flight)o).getNumber() + " Airport: " + ((Flight)o).getSource() +
                        " departs at " + Depart + " to " +
                        ((Flight)o).getDestination() + " arriving at " + Arrive + " Duration: " + length + " minutes";
                System.out.println(prettyLine);
            }
        }
    }
}
