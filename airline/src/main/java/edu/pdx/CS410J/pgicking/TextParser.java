package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.File;

/**
 * Created by pgicking on 7/12/14.
 */
public class TextParser implements AirlineParser {
    String File;
    public TextParser(String fileName) {
        this.File = fileName;
    }

    @Override
    public AbstractAirline parse() throws ParserException {
        TextDumper dumper = new TextDumper(File);

        System.out.println(File);
        File f = new java.io.File(File);

        if(!f.exists()){
            System.out.println("Could not find file: " + File + "" +
                    "\nCreating: " + File);
            //dumper.dump();
        }

        return null;
    }
}
