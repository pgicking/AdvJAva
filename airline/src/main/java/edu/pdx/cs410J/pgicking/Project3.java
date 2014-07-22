package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AirportNames;

/**
 * Created by pgicking on 7/19/14.
 */
public class Project3 {

    private static int Display = 0;
    private static int indx = 0;
    private static int parseFlag = 0;

    /**
     * Does the brunt of the work for project3, it is almost exactly the same
     * as my project1 with a couple modifications. I kept it this way so adding flights
     * is verified through the main so no malformed strings should (knock on wood) show up in
     * the file unless the user edits it themselves. I added a check for it just in case anyway.
     *
     * It calls parser to parse the file and add its flights to the flight list then
     * display them if the user specifies -print. Otherwise it has the same functionality as
     * project 1 and 2
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
        int[] flags;
        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

        flags = parseArgs(args);

        ValidateArgsLength(args);

        ValidateAirportCodeLength(args);

        AbstractAirline airline = AssignArgs(args);

        if(flags[2] == 1) {
            ParseFile(args, airline);
        }

        if(flags[1] == 1) {
            Display(airline);
        }

        if(flags[3] == 1){
            String prettyFile = parsePrettyFileName(args);
            PrettyPrint(airline,prettyFile);
            //PrettyPrint
        }

        System.exit(0);
    }

    /**
     * Grabs the name of the file to parse and then calls the parser to read it
     * @param args   The array of the command line arguments
     * @param airline The airline is passed in so it can be added to parsers constructor
     */
    private static void ParseFile(String[] args, AbstractAirline airline) {
        String fileName;
        fileName = parseTextFileName(args);
        TextParser parser = new TextParser(fileName,airline);
        try {
            parser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

    }

    /**
     * Displays the contents of the airline flights line by line
     * @param airline The airline to be displayed
     */
    private static void Display(AbstractAirline airline) {
        Collection p = airline.getFlights();
        //System.out.println(airline.toString());
        System.out.println(airline.getName());
        for (Object s : p) {
            System.out.println(s);
        }
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
        src = ValidateRealAirportCode(args[2 + indx]);
        dummy = args[3+indx] + " " + args[4+indx] + " " + args[5+indx];

        Depart = FormatDateStringAsString(dummy);
        dest = ValidateRealAirportCode(args[6 + indx]);

        try {
            dummy = args[7 + indx] + " " + args[8 + indx] + " " + args[9+indx];
        } catch (Exception e) {
            System.err.print("Missing Command line arguments." +
                    "\nCheck to make sure you have a departure date and time\n");
        }
        Arrive = FormatDateStringAsString(dummy);

        AbstractAirline airline = new Airline(name);

        Flight flight = new Flight(number,src,dest,Depart,Arrive);

        airline.addFlight(flight);
        return airline;
    }

    /**
     * Checks to make sure the source or destination airport codes are exactly 3 letters
     * It pulls them directly from the array of command line arguments
     * @param args The array of the command line arguments
     */
    private static void ValidateAirportCodeLength(String[] args) {
        if(args[2+indx].length() > 3 || args[6+indx].length() > 3){
            //System.out.println(args[2+indx]);
            //System.out.println(args[6+indx]);
            System.err.println("Source or Destination airport codes are larger than 3 letters");
            System.exit(1);
        }
        if(args[2+indx].length() < 3 || args[6+indx].length() < 3){
            //System.out.println(args[2+indx]);
            //System.out.println(args[6+indx]);
            System.err.println("Source or Destination airport codes are less than 3 letters");
            System.exit(1);
        }
    }

    private static int[] parseArgs(String[] args){
        int i = 0;
        String split;
        int [] flags = new int[4];

        for (String arg : args) {
            if(arg.contains("-README")){
                DisplayREADME();
            }
            if(arg.contains("-print")){
                Display = 1;
                flags[1] = 1;
                indx += 1;
                flags[0] += 1;
            }
            if(arg.contains("-textFile")){
                indx += 2;
                flags[0] += 2;
                parseFlag = 1;
                flags[2] = 1;

            }
            if(arg.contains("-pretty")){
                indx += 2;
                flags[0] += 2;
                flags[3] = 1;
            }
            ++i;

        }

        return flags;
    }

    /**
     * Gets the name of the file in a hacky way. It assumes that
     * the file name is the next argument after -textFile, grabs that
     * and returns it
     *
     * @param args Command line string
     * @return The file name
     */
    private static String parseTextFileName(String[] args){
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

    private static String parsePrettyFileName(String [] args){
        String fileName = null;
        int i = 0;
        int num = 0;
        for(String arg : args) {
            if (arg.contains("-pretty")) {
                num = i;
            }
            //Grab the next element after -pretty to get
            //The file name
            if (i - num == 1) {
                if(arg.contains(".txt")) {
                    fileName = arg;
                    break;
                }
                if(arg.equals("-")){
                    fileName = arg;
                    break;
                }
            }
            ++i;
        }
        //System.out.println(fileName);
        return fileName;
    }

    private static void PrettyPrint(AbstractAirline airline, String fileName){
        PrettyPrinter pretty = new PrettyPrinter(fileName);
        try {
            pretty.dump(airline);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String FormatDateAsLong(String dateString){
        int j = DateFormat.LONG;
        DateFormat df = DateFormat.getDateTimeInstance(j, j, Locale.US);
        Date date = null;

        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return df.format(date);
    }

    public static Long CalculateFlightLength(Date depart, Date arrive){
        Long length = (arrive.getTime() - depart.getTime());
        length = length / (60000L);

        return length;
    }


    /**
     * Checks to see if there are zero or too many arguments passed into the command line
     * @param args  The array of the command line arguments
     */
    private static void ValidateArgsLength(String[] args) {
        if(args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        if(args.length > 16){
            System.err.println("Too many command line arguments");
            System.exit(1);
        }
    }

    /**
     * @deprecated replaced by FormatDatestring in Project 3
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
                    "Correct format is MM/DD/YYYY HH:MM am/pm\n");
            //e.printStackTrace();
        }
        //System.out.println(date.toString());
        return df.format(date);
    }

    public static Date FormatDateStringAsDate(String arg){
        int j = DateFormat.SHORT;
        Date date = null;
        DateFormat df = DateFormat.getDateTimeInstance(j, j, Locale.US);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try{
            Date parsedDate = formatter.parse(arg);
            date = df.parse(arg);
        } catch (ParseException e) {
            System.err.print("Departure or Arrival date is not valid.\n" +
                    "Correct format is MM/DD/YYYY HH:MM am/pm\n");
            //e.printStackTrace();
        }
        //System.out.println(date.toString());
        return date;
    }

    /**
     * @deprecated Replaced by FormatDatestring in Project 3
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

    public static void setIndx(int i){
        indx = i;
    }

}
