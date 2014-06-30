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

      for (String arg : args) {
          System.out.println(arg);
      }

      name = args[0];
      number = Integer.parseInt(args[1]);
      src = args[2];
      dest = args[3];

      System.out.println(name);
      System.out.println(number);
      System.out.println(src);
      System.out.println(dest);

      Airline airline = new Airline(name);

      Flight flight = new Flight(number,src,dest);

      airline.addFlight(flight);


  }

}

