package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.pgicking.Airline;
import edu.pdx.cs410J.pgicking.Flight;

/**
 * @author Peter Gicking
 */

/**
 * <p>The main class for the CS410J airline Project, contains the methods that parse the command line
 * and format and validate the string of flight information to print out</p>
 */
public class Project1 {

  /**
  <p>The main method, does most of the work for the project
  Reads the command line arguments and parses them into respective
  data fields, which are then passed into the airline and flight classes
  to make an airline with at most one flight (for project 1).</p>

  <p>Theres two option arguments, -README and -print.</p>
  <p>-print prints out the flight information</p>
  <p>-README prints out how to use the program</p>
  @param    args    The list of arguments to create a flight and airline
   */
  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    if(args.length == 0) {
        System.err.println("Missing command line arguments");
        System.exit(1);
    }
    if(args.length > 9){
        System.err.println("Too many command line arguments");
        System.exit(1);
    }
      /*
      name The name of the airline
      flightNumber The flight number
      src Three-letter code of departure airport
      departTime Departure date and time (24-hour time)
      dest Three-letter code of arrival airport
      arriveTime Arrival date and time (24-hour time)
      */
      String name = null;
      int number;
      String src = null;
      String dest = null;
      String Depart = null;
      String Arrive = null;
      String print;
      int Display = 0; //Display flag
      int indx = 0;
      int i = 0;
      String realArgs;
      String dummy;

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
          //System.out.println(indx);
          ++i;
      }

      if(args[2+indx].length() > 3 || args[4+indx].length() > 3){
          System.err.println("Source or Destination airport codes are larger than 3 letters");
          System.exit(1);
      }
      if(args[2+indx].length() < 3 || args[4+indx].length() < 3){
          System.err.println("Source or Destination airport codes are less than 3 letters");
          System.exit(1);
      }

      name = args[indx];
      number = ValidateFlightNumber(args[1+indx]);
      src = args[2+indx];
      dummy = args[3+indx] + " " + args[4+indx];
      Depart = ValidateDepartureString(dummy);
      dest = args[4+indx];
      try {
          dummy = args[6 + indx] + " " + args[7 + indx];
      } catch (Exception e) {
          System.err.print("Missing Command line arguments." +
                  "\nCheck to make sure you have a departure date and time\n");
      }
      Arrive = ValidateArrivalString(dummy);

      Airline airline = new Airline(name);

      Flight flight = new Flight(number,src,dest,Depart,Arrive);

      airline.addFlight(flight);
      if(Display == 1) {
          print = airline.toString();

          System.out.println(print);
      }

      System.exit(0);
  }
    /**
    Validates the arrival string to make sure its in correct format, then returns it
    @param    arg     The index of the arguments containing arrival string
    @return   The validated arrival string
    */
    private static String ValidateArrivalString(String arg) {
        if(!arg.matches("\\d{1,2}" + "/" + "\\d{1,2}" + "/" + "\\d{4}" + "\\s+" + "\\d{1,2}:\\d{2}")){
            System.err.println("\n"+ arg + " is not a valid date/time format!\n" +
                    "Correct format is: MM/DD/YYYY HH:MM");
            System.exit(1);
        }
        return arg;
    }

    /**
    Validates the departure string to make sure its in correct format, then returns it
    @param    arg    The index of the arguments containing departure string
    @return   The validated departure string
    */
    private static String ValidateDepartureString(String arg) {
        if(!arg.matches("\\d{1,2}" + "/" + "\\d{1,2}" + "/" + "\\d{4}" + "\\s+" + "\\d{1,2}:\\d{2}")){
            System.err.println("\n"+ arg + " is not a valid date/time format!\n" +
                    "Correct format is: MM/DD/YYYY HH:MM");
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
     *Displays the readme if -README is passed in as an argument, ignoring all other arguments
     */
    private static void DisplayREADME() {
        System.out.println("This program takes in arguments to create an airline" +
                " and flights for that airline. \n Usage:  java edu.pdx.cs410J.<login-id>.Project1 [options] <args> [options]\n" +
                "The arguments need to be put in the correct order for the program to work" +
                "\n This order is" +
                "name: The name of the airline\n" +
                "flightNumber: The flight number\n" +
                "src: Three-letter code of departure airport\n" +
                "departTime: Departure date and time (24-hour time)\n" +
                "dest: Three-letter code of arrival airport\n" +
                "arriveTime: Arrival date and time (24-hour time)\n" +
                "Example command line string alaska 123 PDX 3/15/2014 10:39 ALA 03/2/2014 1:35");
        System.exit(0);
    }

}

