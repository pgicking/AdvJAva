package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    if(args.length == 0) {
        System.err.println("Missing command line arguments");
        System.exit(1);
    }
    if(args.length > 8){
        System.err.println("Too many command line arguments");
        System.exit(1);
    }
/*    name The name of the airline
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

      for (String arg : args) {
          if(arg.contains("-README")){
              DisplayREADME();
          }
          if(arg.contains("-print")){
              Display = 1;
              //If -print is at beginning, offset indx by one
              //otherwise, offset index by zero
              if(arg.indexOf("-print") == 0){
                  indx = 1;
              }
              else{
                  indx = 0;
              }
          }
          //System.out.println(arg);
      }
      if(args[2+indx].length() > 3 || args[4+indx].length() > 3){
          System.err.println("Source or Destination airport codes are" +
                  "larger than 3 letters");
          System.exit(1);
      }
      if(args[2+indx].length() < 3 || args[4+indx].length() < 3){
          System.err.println("Source or Destination airport codes are" +
                  "less than 3 letters");
          System.exit(1);
      }
      if(!args[3+indx].matches("\\d{1,2}" + "/" + "\\d{1,2}" + "/" + "\\d{4}" + "\\s+" + "\\d{1,2}:\\d{2}")){
          System.err.println("\n"+ args[3+indx] + " is not a valid date/time format!\n" +
                  "Correct format is: MM/DD/YYYY 12:34");
          System.exit(1);
      }
      if(!args[5+indx].matches("\\d{1,2}" + "/" + "\\d{1,2}" + "/" + "\\d{4}" + "\\s+" + "\\d{1,2}:\\d{2}")){
          System.err.println("\n"+ args[5+indx] + " is not a valid date/time format!\n" +
                  "Correct format is: MM/DD/YYYY 12:34");
          System.exit(1);
      }
      name = args[indx];
      number = Integer.parseInt(args[1+indx]);
      src = args[2+indx];
      Depart = args[3+indx];
      dest = args[4+indx];
      Arrive = args[5+indx];

      Airline airline = new Airline(name);

      Flight flight = new Flight(number,src,dest,Depart,Arrive);

      airline.addFlight(flight);
      if(Display == 1) {
          print = airline.toString();

          System.out.println(print);
      }

  }


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
                "Example command line string alaska 123 PDX \"3/15/2014 10:39\" ALA \"03/2/2014 1:35\"");
        System.exit(0);
    }

}

