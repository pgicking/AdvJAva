package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


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
        File f = new java.io.File(fileName);

        if(!f.exists()) {
            System.out.println("Could not find file: " + fileName + "" +
                    "\nCreating: " + fileName);
            //TextDump

            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(abstractAirline.getName());
            writer.println(abstractAirline.toString());
            writer.close();
        }


    }
}
