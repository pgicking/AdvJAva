package edu.pdx.CS410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * Created by pgicking on 7/12/14.
 */
public class TextParser implements AirlineParser {
    String File;
    Airline airline;
    public TextParser(String fileName, Airline airline) {
        this.File = fileName;
        this.airline = airline;
    }

    @Override
    public AbstractAirline parse() throws ParserException {
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
            if(!airline.Name.equals(fileAirline)){
                System.err.print("Wrong file. Airline: " + airline.getName() + "" +
                        " does not match: " + fileAirline);
                System.err.print("\nYou can only add new flights to the same airline");
                System.exit(1);
            }
            else{
                //Read airline flights and print if needed
                String dummy;
                String[] split;
                int flightNum;
                try {
                    while((dummy = in.readLine()) != null) {
                        //System.out.println(dummy);
                        split = dummy.split(" ");
                        split[3] += " " + split[4];
                        split[4] = split[5];
                        split[5] = "";
                        split[5] += split[6] + " " + split[7];
                        split = removeAt(6,split);
                        split = removeAt(6,split);
                        flightNum = Integer.parseInt(split[1]);
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


        return null;
    }

    public void CreateFlight(String [] args, int flightNum){
        String name = args[0];
        String src = args[2];
        String Depart = args[3];
        String dest = args[4];
        String Arrive = args[5];

        Flight flight = new Flight(flightNum,src,Depart,dest,Arrive);
        airline.addFlight(flight);
    }

    //Blatantly copied from
    //http://stackoverflow.com/questions/2777762/shorten-array-length-once-element-is-remove-in-java
    static String[] removeAt(int k, String[] arr) {
        final int L = arr.length;
        String[] ret = new String[L - 1];
        System.arraycopy(arr, 0, ret, 0, k);
        System.arraycopy(arr, k + 1, ret, k, L - k - 1);
        return ret;
    }
}
