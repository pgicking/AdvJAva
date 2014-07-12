package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

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
        return null;
    }
}
