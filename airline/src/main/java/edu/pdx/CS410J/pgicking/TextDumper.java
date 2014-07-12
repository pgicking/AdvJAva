package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by pgicking on 7/12/14.
 */
public class TextDumper implements AirlineDumper{
    String fileName;

    public TextDumper(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        FileOutputStream fout = null;

            fout = new FileOutputStream(fileName);


    }
}
