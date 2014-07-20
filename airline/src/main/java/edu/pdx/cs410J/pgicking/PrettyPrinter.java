package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;

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
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(abstractAirline.getName());
        writer.println(abstractAirline.toString());
        writer.close();
    }
}
