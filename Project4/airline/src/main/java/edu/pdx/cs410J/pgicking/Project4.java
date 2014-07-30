package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * The main function for project 4, Does do much except keep things organized, tthe brunt of the work is doen in the servlet
     * and other functions in this main. It will error out if it cant contact the given server though.
     * @param args  The command line arguments
     */
    public static void main(String[] args) {

        CheckArgumentLength(args);

        String[] arrayArgs = AssignArgs(args);

        String hostName = arrayArgs[0];
        //System.out.println(arrayArgs[0]);
        String portString = arrayArgs[1];
        //System.out.println(arrayArgs[1]);
        String printFlag = arrayArgs[8];
        String searchFlag = arrayArgs[9];
        //hostname
        if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );
            
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        HttpRequestHelper.Response response = null;

        try {
            response = client.addFlight(arrayArgs);

            if(printFlag != null){
                response = client.getFlights(arrayArgs[2],printFlag);
            }

            if(searchFlag != null){
                response = client.searchFlights(arrayArgs[2],arrayArgs[4],arrayArgs[6],searchFlag);
            }


            checkResponseCode(HttpURLConnection.HTTP_OK, response);
        } catch (IOException e) {
            error("While contacting server: " + e);
            e.printStackTrace();
            return;
        }

        System.out.println(response.getContent());

        System.exit(0);
    }

    /**
     * PArses the command line arguments and validates them for correctness, then returns them in an array
     * to be given to a function that posts to the servlet to add a flight to an airline
     * @param args  The command line arguments
     * @return  An array with the validated strings from the command line
     */
    public static String[] AssignArgs(String[] args){
        String hostName = null;
        String port = null;
        String Airline = null;
        int FlightNum = 0;
        String src = null;
        String Depart = null;
        String dest = null;
        String Arrive = null;
        String dummy = null;
        String print = null;
        String search = null;
        int index = 0;

        for(String arg : args){
            if(arg.contains("-README")){
                DisplayREADME();
            }

            if(arg.contains("-host")){
                index += 2;
                hostName = getNext(args,"-host");
            }

            if(arg.contains("-port")){
                index += 2;
                port = getNext(args,"-port");
            }

            if(arg.contains("-search")){
                index += 1;
                search = "1";
                //search path
            }
            if(arg.contains("-print")){
                index += 1;
                print = "1";
                //printflag
            }
        }

        if(search != null && print != null){
            System.err.println("Can only search or print, cant do both at once");
            System.exit(1);
        }

        if(search == null) {
            Airline = args[index];
            FlightNum = ValidateFlightNumber(args[1 + index]);
            src = ValidateRealAirportCode(args[2 + index]);
            dummy = args[3 + index] + " " + args[4 + index] + " " + args[5 + index];
            Depart = FormatDateStringAsString(dummy);
            dest = ValidateRealAirportCode(args[6 + index]);
            dummy = args[7 + index] + " " + args[8 + index] + " " + args[9 + index];
            Arrive = FormatDateStringAsString(dummy);
        }
        else{
            Airline = args[index];
            src = ValidateRealAirportCode(args[1 + index]);
            dest = ValidateRealAirportCode(args[2 + index]);
        }

        String[] arrayArgs = {hostName, port, Airline, String.valueOf(FlightNum), src, Depart, dest, Arrive, print, search};

        return arrayArgs;
    }

    /**
     * Gets the next element from the element passed in the command line arguments
     * @param args  The command line arguments
     * @param option    The element you want to get the next element of. Usually -host or -port
     * @return  Returns the next element
     */
    public static String getNext(String[] args, String option){
        String nextArg = null;
        int i = 0;
        int num = 9999;
        for(String arg : args) {
            if (arg.contains(option)) {
                num = i;
            }
            //Grab the next element after textfile to get
            //The file name
            if (i - num == 1) {
                nextArg = arg;
                break;
            }
            ++i;
        }
        //System.out.println(nextArg);
        return nextArg;
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    /**
     * Daves function, returns an error string
     * @param message
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }


    /**
     * Uses daves AirportNames class to validate if the airport code is actually a real
     * airport
     * @param code  A specific airport code
     * @return The validated airport code
     */
    public static String ValidateRealAirportCode(String code){
        String name = AirportNames.getName(code.toUpperCase());

        if(name == null){
            System.err.print(code + " is not a valid airport code!\n" +
                    "Airport codes need to correspond to real airports!\n");
            System.exit(1);
        }
        return code;
    }

    /**
     Validates to make sure the flight number is numeric
     @param  arg    The flight number from command line arguments
     @return The validated flight number
     */
    public static int ValidateFlightNumber(String arg){
        int number = 0;
        try{
            number = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            System.err.println("Flight number is invalid");
            System.exit(1);
        }
        return number;
    }

    /**
     * Checks to make sure the date is in the correct format (no regex this time)
     * and then puts it in a certain format
     * @param arg The arrival or departure date strings
     * @return The formatted date
     */
    public static String FormatDateStringAsString(String arg) {
        //System.out.println("Formatting: " + arg);
        int j = DateFormat.SHORT;
        Date date = null;
        DateFormat df = DateFormat.getDateTimeInstance(j, j, Locale.US);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try{
            Date parsedDate = formatter.parse(arg);
            date = df.parse(arg);
        } catch (ParseException e) {
            System.err.print("Departure or Arrival date is not valid.\n" +
                    "Correct format is MM/DD/YYYY HH:MM am/pm\n" +
                    "You put " + arg + "\n");
            //e.printStackTrace();
        }
        //System.out.println(date.toString());
        return df.format(date);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [key] [value]");
        err.println("  host    Host of web server");
        err.println("  port    Port of web server");
        err.println("  key     Key to query");
        err.println("  value   Value to add to server");
        err.println();
        err.println("This simple program posts key/value pairs to the server");
        err.println("If no value is specified, then all values are printed");
        err.println("If no key is specified, all key/value pairs are printed");
        err.println();

        System.exit(1);
    }

    /**
     * Checks the argument lengths, make sure its not nothing or too many
     * @param args  The command line arguments
     */
    public static void CheckArgumentLength(String[] args){
        if(args.length == 0){
            System.err.println("Not enough command line arguments");
            System.exit(1);
        }
        if(args.length > 17){
            System.err.println("Too many command line arguments");
            System.exit(1);
        }
    }

    /**
     * The readme function, displays how to use the program
     */
    public static void DisplayREADME(){
        System.out.println();
        System.out.println("Created by Peter Gicking");
        System.out.println("usage: java Project4 -host host -port port -print -search [airline name] [flight number]");
        System.out.println("[Source] [Departure Time] [Destination] [Arrival Time]");
        System.out.println("  host    Host of web server");
        System.out.println("  port    Port of web server");
        System.out.println("  airline name      Name of the airline   ");
        System.out.println("  Flight numbers    The flight number");
        System.out.println("  Source            Source airport code");
        System.out.println("  Departure Time    Departure time Format: hh/dd/yyyy HH:MM am/pm");
        System.out.println("  Destination       Destination airport code");
        System.out.println("  Arrival           Arrival time Format: hh/dd/yyyy HH:MM am/pm");
        System.out.println();
        System.out.println("Optional arguments:");
        System.out.println("-print");
        System.out.println("-search");
        System.out.println("This simple program posts airlines to the server");
        System.out.println("The -print option can be used to print the flights");
        System.out.println("The -search option can be used with airline source and destination");
        System.out.println("to print matching flights within the airline");

        System.exit(1);
    }
}