package edu.pdx.cs410J.pgicking;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;


/**
 * Created by pgicking on 7/12/14.
 */
public class TextDumper implements AirlineDumper{
    String fileName;

    public TextDumper(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Dump the contents of an airline to a textfile
     *
     * @param abstractAirline Airline which dumps its flights to a textfile
     * @throws IOException
     */
    @Override
    public void dump(AbstractAirline abstractAirline) throws IOException {
        if(!fileName.contains(".txt")){
            System.err.print("File must be a .txt");
            System.exit(1);
        }
        File f = new java.io.File(fileName);

        if(!f.exists()) {
            System.out.println("Could not find file: " + fileName + "" +
                    "\nCreating: " + fileName);
            //TextDump

            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(abstractAirline.getName());
            writer.println(abstractAirline.toString());
            writer.close();
            System.exit(0);
        }

        FileReader reader = null;
        try {
            reader = new FileReader(f);
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
            if(!abstractAirline.getName().equals(fileAirline)){
                System.err.print("Wrong file. Airline: " + abstractAirline.getName() + "" +
                        " does not match: " + fileAirline);
                System.err.print("\nYou can only add new flights to the same airline");
                System.exit(1);
            }
            else{
                Collection flights = new LinkedList<Flight>();
                flights = abstractAirline.getFlights();
                //PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                System.out.println("Appending to " + fileName);
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
                writer.println(abstractAirline.getName());
                for(Object o : flights) {
                    //System.out.println("Writng: " + abstractAirline.toString());
                    writer.println(o.toString());
                }
                writer.close();
            }
        }



    }

}
