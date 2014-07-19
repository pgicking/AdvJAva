package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by pgicking on 7/12/14.
 */
public class Project2 {

    private static int Display = 0;
    private static int indx = 0;
    private static int parseFlag = 0;

    /**
     * Does the brunt of the work for project2, it is almost exactly the same
     * as my project1 with a couple modifications. I kept it this way so adding flights
     * is verified through the main so no malformed strings should (knock on wood) show up in
     * the file unless the user edits it themselves. I added a check for it just in case anyway.
     *
     * It calls parser to parse the file and add its flights to the flight list then
     * display them if the user specifies -print. Otherwise it has the same functionality as
     * project 1
     *
     * @param args Command line arguments containing a flight to add
     */
    public static void main(String[] args){
      /*
      name The name of the airline
      flightNumber The flight number
      src Three-letter code of departure airport
      departTime Departure date and time (24-hour time)
      dest Three-letter code of arrival airport
      arriveTime Arrival date and time (24-hour time)
      */

        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

        System.out.println(parseFlag + " " + Display);
        parseArgs(args);

        System.out.println(parseFlag);
        ValidateArgsLength(args);

        System.out.println(parseFlag);
        ValidateAirportCodes(args);

        System.out.println(parseFlag);
        AbstractAirline airline = AssignArgs(args);

        System.out.println(parseFlag);
        if(parseFlag == 1) {
            ParseFile(args, airline);
        }

        if(Display == 1) {
            Display(airline);
        }

        System.exit(0);
    }

    private static void ParseFile(String[] args, AbstractAirline airline) {
        String fileName;
        fileName = parseFileName(args);
        TextParser parser = new TextParser(fileName,airline);
        try {
            parser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

    }

    private static void Display(AbstractAirline airline) {
        Collection p = airline.getFlights();
        //System.out.println(airline.toString());
        System.out.println(airline.getName());
        for (Object s : p) {
            System.out.println(s);
        }
    }

    private static AbstractAirline AssignArgs(String[] args) {
        String name;
        int number;
        String src;
        String dummy;
        String Depart;
        String dest;
        String Arrive;

        name = args[indx];
        number = ValidateFlightNumber(args[1+indx]);
        src = args[2+indx];
        dummy = args[3+indx] + " " + args[4+indx];
        Depart = ValidateDepartureString(dummy);
        dest = args[5+indx];
        try {
            dummy = args[6 + indx] + " " + args[7 + indx];
        } catch (Exception e) {
            System.err.print("Missing Command line arguments." +
                    "\nCheck to make sure you have a departure date and time\n");
        }
        Arrive = ValidateArrivalString(dummy);

        AbstractAirline airline = new Airline(name);

        Flight flight = new Flight(number,src,dest,Depart,Arrive);

        airline.addFlight(flight);
        return airline;
    }

    private static void ValidateAirportCodes(String[] args) {
        if(args[2+indx].length() > 3 || args[5+indx].length() > 3){
            System.err.println("Source or Destination airport codes are larger than 3 letters");
            System.exit(1);
        }
        if(args[2+indx].length() < 3 || args[5+indx].length() < 3){
            System.err.println("Source or Destination airport codes are less than 3 letters");
            System.exit(1);
        }
    }

    private static void parseArgs(String [] args){
        int i = 0;
        
        for (String arg : args) {
            if(arg.contains("-README")){
                DisplayREADME();
            }
            //System.out.println(arg);
            //System.out.println(arg.contains("-print"));
            if(arg.contains("-print")){
                Display = 1;
                //If -print is at beginning, offset indx by one
                //otherwise, offset index by zero
                if(arg.compareTo("-print") == 0 && i == 0){
                    indx = 1;
                }
            }
            if(arg.contains("-textFile")){
                indx += 2;
                parseFlag = 1;

            }
            ++i;
        }
    }

    /**
     * Gets the name of the file in a hacky way. It assumes that
     * the file name is the next argument after -textFile, grabs that
     * and returns it
     *
     * @param args Command line string
     * @return The file name
     */
    private static String parseFileName(String [] args){
        String fileName = null;
        int i = 0;
        int num = 0;
        for(String arg : args) {
            if (arg.contains("-textFile")) {
                num = i;
            }
            //Grab the next element after textfile to get
            //The file name
            if (i - num == 1) {
                fileName = arg;
                break;
            }
            ++i;
         }
        //System.out.println(fileName);
        return fileName;
        }


    private static void ValidateArgsLength(String[] args) {
        if(args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        if(args.length > 12){
            System.err.println("Too many command line arguments");
            System.exit(1);
        }
    }

    /**
     Validates the arrival string to make sure its in correct format, then returns it
     @param    arg     The index of the arguments containing arrival string
     @return   The validated arrival string
     */
    public static String ValidateArrivalString(String arg) {
        if(!arg.matches("^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]")){
            System.err.println("\n"+ arg + " is not a valid arrival date/time format!\n" +
                    "Correct format is: MM/DD/YYYY HH:MM" +
                    "\nHint: Single digit days must be followed by a zero" +
                    "\ne.g. 01/01/2014 instead of 1/1/2014");
            System.exit(1);
        }
        return arg;
    }

    /**
     Validates the departure string to make sure its in correct format, then returns it
     @param    arg    The index of the arguments containing departure string
     @return   The validated departure string
     */
    public static String ValidateDepartureString(String arg) {
        if(!arg.matches("^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d ([01]?[0-9]|2[0-3]):[0-5][0-9]")){
            System.err.println("\n"+ arg + " is not a valid departure date/time format!\n" +
                    "Correct format is: MM/DD/YYYY HH:MM" +
                    "\nHint: Single digit days must be followed by a zero" +
                    "\ne.g. 01/01/2014 instead of 1/1/2014");
            System.exit(1);
        }
        return arg;

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
     * Displays the readme if -README is passed in as an argument, ignoring all other arguments
     */
    private static void DisplayREADME() {
        System.out.println("Created by: Peter Gicking\n" +
                "This program takes in arguments to create an airline" +
                " and flights for that airline that can be stored in a file." +
                "\nUsage:  java edu.pdx.cs410J.<login-id>.Project1 [options] <args> [options]\n" +
                "The arguments need to be put in the correct order for the program to work" +
                "\n This order is" +
                "name: The name of the airline\n" +
                "flightNumber: The flight number\n" +
                "src: Three-letter code of departure airport\n" +
                "departTime: Departure date and time (24-hour time)\n" +
                "dest: Three-letter code of arrival airport\n" +
                "arriveTime: Arrival date and time (24-hour time)\n" +
                "[Options]\n-textFile file: Where to read/write the airline info\n" +
                "-print: Prints a description of the new flight\n" +
                "-README: Prints a README for this project and exits\n" +
                "Example command -print -textFile output.txt alaska 123 PDX \"03/15/2014 10:39\" ALA  \"03/02/2014 01:35\"" +
                "");
        System.exit(0);
    }
}
