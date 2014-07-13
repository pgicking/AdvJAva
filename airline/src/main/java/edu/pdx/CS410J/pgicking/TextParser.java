package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * Created by pgicking on 7/12/14.
 */
public class TextParser implements AirlineParser {
    String File;
    Airline airline;
    public TextParser(String fileName, Airline airline) {
        this.File = fileName;
        this.airline = airline;
    }

    @Override
    public AbstractAirline parse() throws ParserException {
        TextDumper dumper = new TextDumper(File);

        System.out.println(File);
        File f = new java.io.File(File);

        FileReader reader = null;
        try {
            reader = new FileReader(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(reader);

        if(!f.exists()){
            System.out.println("Could not find file: " + File + "" +
                    "\nCreating: " + File);
            try {
                dumper.dump(airline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(f.exists()){
            //read only the first line, hopefully the airline name is in it
            String fileAirline = null;
            try {
                fileAirline = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(airline.Name != fileAirline){
                System.err.print("Wrong file. Airline: " + airline.getName() + "" +
                        " does not match: " + fileAirline);
                System.err.print("\nYou can only add new flights to the same airline");
                System.exit(1);
            }
            else{
                //Read airline flights and print if needed
                String dummy;
                try {
                    while((dummy = in.readLine()) != null) {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
}
