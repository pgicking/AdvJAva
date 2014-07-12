package edu.pdx.CS410J.pgicking;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;
import static junit.framework.Assert.assertEquals;

/**
 * Created by pgicking on 7/12/14.
 */
public class Project2Test extends InvokeMainTestCase{
    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains( "Missing command line arguments" ));
    }

    @Test
    public void testREADMEOnly(){
        String [] Arguments = {"-README"};
        String errormessage = "This program takes in arguments to create an airline";
        InvokeMainWithArgsMatchForStdOut(Arguments,errormessage);
    }

    @Test
    public void testgetNumber() throws Exception {
        Flight flight = new Flight();
        int num;
        num = flight.getNumber();
    }
    @Test
    public void testTooManyArguments(){
        String [] Arguments = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String errormessage = "Too many command line arguments";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);

    }

    @Test
    public void testBadFlightNumber(){
        String [] Arguments = {"-print", "alaska", "q123", "PDX", "3/15/2014 10:39", "ALA", "03/2/14 1:35"};
        String errormessage = "Flight number is invalid";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }

    @Test
    public void testAirportCodesLargerThanThree(){
        String [] Arguments = {"-print", "alaska", "123", "PDXXXXXXX", "3/15/2014 10:39", "ALAXXX", "03/2/14 1:35"};
        String errormessage = "Source or Destination airport codes are larger than 3 letters";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }

    @Test
    public void testAirportCodesSmallerThanThree(){
        String [] Arguments = {"-print", "alaska", "123", "PX", "3/15/2014 10:39", "AL", "03/2/14 1:35"};
        String errormessage = "Source or Destination airport codes are less than 3 letters";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }

    @Test
    public void testWrongDateFormatShortYear(){
        String[] Arguments = {"-print", "alaska", "123", "PDX", "3/15/2014 10:39", "ALA", "03/2/14 1:35"};
        String errormessage = "is not a valid date/time format!";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }

    private void InvokeMainWithArgsCheckForErrorMessage(String[] arguments, String errormessage) {
        MainMethodResult result = invokeMain(arguments);
        assertTrue(result.getErr().contains(errormessage));
    }

    @Test
    public void testWrongDateFormatDashes(){
        String[] Arguments = {"-print", "alaska", "123", "PDX", "3-15-2014 10:39", "ALA", "03/2/2014 1:35"};
        String errormessage = "is not a valid date/time format!";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }


    @Test
    public void testDateFormat(){
        String[] Arguments = {"-print", "alaska", "123", "PDX", "03/15/2014 10:39", "ALA", "03/02/2014 01:35"};
        MainMethodResult result = invokeMain(Arguments);
        assertEquals(result.getExitCode().intValue(), 0);
    }

    @Test
    public void testREADMEWithPrintAtStart(){
        String[] Arguments = {"-print", "alaska", "123", "PDX", "3/15/2014 10:39", "ALA", "03/2/2014 1:35", "-README"};
        String errormessage = "This program takes in arguments to create an airline";
        InvokeMainWithArgsMatchForStdOut(Arguments, errormessage);
    }

    private void InvokeMainWithArgsMatchForStdOut(String[] arguments, String errormessage) {
        MainMethodResult result = invokeMain(arguments);
        assertTrue(result.getOut().contains(errormessage));
    }

    @Test
    public void testREADMEWithPrintAtEnd(){
        String[] Arguments = {"alaska", "123", "PDX", "3/15/2014 10:39", "ALA", "03/2/2014 1:35", "-README", "-print"};
        String errormessage = "This program takes in arguments to create an airline";
        InvokeMainWithArgsMatchForStdOut(Arguments, errormessage);
    }

    @Test
    public void testREADMEAtEndWithNoPrint(){
        String[] Arguments = {"alaska", "123", "PDX", "3/15/2014 10:39", "ALA", "03/2/2014 1:35", "-README"};
        String errormessage = "This program takes in arguments to create an airline";
        InvokeMainWithArgsMatchForStdOut(Arguments, errormessage);
    }
    @Test
    public void testREADMEAtStartWithNoPrint(){
        String[] Arguments = {"-README", "alaska", "123", "PDX", "3/15/2014 10:39", "ALA", "03/2/2014 1:35"};
        String errormessage = "This program takes in arguments to create an airline";
        InvokeMainWithArgsMatchForStdOut(Arguments, errormessage);
    }
    @Ignore
    @Test
    public void testOnlyTextFileArgs(){
        String[] Arguments = {"-textFile", "parser.txt"};
        String errormessage = "parser.txt";
        InvokeMainWithArgsMatchForStdOut(Arguments,errormessage);
    }
}
