package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;


import java.io.*;

import static edu.pdx.cs410J.pgicking.Project3.FormatDateStringAsString;

/**
 * Created by pgicking on 7/12/14.
 */
public class TextParser implements AirlineParser {
    String File;
    AbstractAirline airline;

    public TextParser(String fileName, AbstractAirline airline) {
        this.File = fileName;
        this.airline = airline;
    }

    /**
     * Parses the text file for data and adds the flights to the flight list
     * Then can print them out or do nothing.
     *
     * @return the airline which can be displayed
     * @throws ParserException
     */
    @Override
    public AbstractAirline parse() throws ParserException {
        if(!File.contains(".txt")){
            System.err.print("File must be a .txt");
            System.exit(1);
        }
        TextDumper dumper = new TextDumper(File);

        //System.out.println(File);
        File f = new java.io.File(File);

        if(!f.exists()){
            try {
                dumper.dump(airline);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileReader reader = null;
        try {
            reader = new FileReader(File);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(reader);


        if(f.exists()){
            //read only the first line, hopefully the airline name is in it
            String fileAirline = null;
            try {
                fileAirline = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!airline.getName().equals(fileAirline)){
                System.err.print("Wrong file. Airline: " + airline.getName() + "" +
                        " does not match: " + fileAirline);
                System.err.print("\nYou can only add new flights to the same airline");
                System.exit(1);
            }
            else{
                //Read airline flights and print if needed
                String dummy;
                String[] split;
                String[] test = {"1", "2", "3", "4", "5","6","7","8", "9"};
                int flightNum;
                try {
                    while((dummy = in.readLine()) != null) {
                        //System.out.println(dummy);
                        split = dummy.split(" ");
                        if(test.length != split.length){
                            System.out.println(test.length);
                            System.out.println(split.length);
                            System.err.print("Textfile is be malformed");
                            System.exit(1);
                        }
                        flightNum = Integer.parseInt(split[0]);
                        CreateFlight(split,flightNum);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    dumper.dump(airline);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return airline;
    }

    /**
     * Creates a flight similar in the way Project2 main does. If I was smart I'd have
     * project2 main refer to this function
     *
     * @param args     Contains the data to be assigned to fields to add to flights
     * @param flightNum Casted to a number and passed in
     */
    public void CreateFlight(String [] args, int flightNum){

        //String name = args[0];
        String src = args[1];
        String dummy = args[2] + " " + args[3] + " " + args[4];
        String Depart = FormatDateStringAsString(dummy);
        String dest = args[5];
        try {
            dummy = args[6] + " " + args[7] + " " + args[8];
            //System.out.println(dummy);
        } catch (Exception e) {
            System.err.print("Missing Command line arguments." +
                    "\nCheck to make sure you have a departure date and time\n");
        }
        String Arrive = FormatDateStringAsString(dummy);

        Flight flight = new Flight(flightNum,src,dest,Depart,Arrive);
        airline.addFlight(flight);
    }


    //Blatantly copied from
    //http://stackoverflow.com/questions/2777762/shorten-array-length-once-element-is-remove-in-java
    /**
     * Removes an element from an String array and then shortens the
     * array to the new length instead of leaving an empty element
     *
     * @param k     Index to remove from
     * @param arr   String to be shortened
     * @return      The string
     */
    static String[] removeAt(int k, String[] arr) {
        final int L = arr.length;
        String[] ret = new String[L - 1];
        System.arraycopy(arr, 0, ret, 0, k);
        System.arraycopy(arr, k + 1, ret, k, L - k - 1);
        return ret;
    }
}
