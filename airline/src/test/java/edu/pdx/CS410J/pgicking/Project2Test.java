package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        String [] Arguments = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
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


    @Test
    public void testMisMatchedAirline(){
        String [] Arguments2 = {"-print", "-textFile", "output.txt",
                "alaska", "123", "PDX", "03/15/2014 10:39", "ALA",  "03/02/2014 01:35"};
        MainMethodResult result = invokeMain(Arguments2);

        String [] Arguments = {"-print", "-textFile", "output.txt",
                "derp", "123", "PDX", "03/15/2014 10:39", "ALA",  "03/02/2014 01:35"};
        String errormessage = "Wrong file";
        InvokeMainWithArgsCheckForErrorMessage(Arguments,errormessage);
    }

    @Test
    public void testNewFileIsCreatedIfNotExist(){
        String [] Arguments = {"-print", "-textFile", "output2.txt",
                "united", "123", "PDX", "03/15/2014 10:39", "ALA",  "03/02/2014 01:35"};
        String errormessage = "Could not find file";
        InvokeMainWithArgsMatchForStdOut(Arguments,errormessage);
        File file = new File("output2.txt");
        if(file.delete()){
            System.out.println(file.getName() + " deleted");
        }
    }

    @Test
    public void testTextFileIncorrectFormat(){
        String [] Arguments = {"-print", "-textFile", "output",
                "alaska", "123", "PDX", "03/15/2014 10:39", "ALA",  "03/02/2014 01:35"};
        String errormessage = "File must be a .txt";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }

    @Ignore //temporary ignore
    @Test
    public void testMalformedTextFiles(){
        String [] Arguments = {"-print", "-textFile", "output3.txt",
                "alaska", "123", "PDX", "03/15/2014 10:39", "ALA",  "03/02/2014 01:35"};
        String errormessage = "Textfile might be malformed";
        InvokeMainWithArgsCheckForErrorMessage(Arguments, errormessage);
    }
}
