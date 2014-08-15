package edu.pdx.cs410J.pgicking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;

import java.io.IOException;
import java.util.*;

/**
 * The main class that handles most of the work for the airline application.
 */
public class AirlineGwt implements EntryPoint {

    /**
     * The brunt of the work for the airline application, loads and organizes all the buttons
     * contains all the functions for what to do on click events
     * Initalizes everything and creates vert and horizontal panels to organize the UI
     */
  public void onModuleLoad() {

      Label lAirline = new Label("Airline");
      Label lFlightNum = new Label("Flight Number");
      Label lSrc = new Label("Source Airport");
      Label lDepart = new Label("Departure Day");
      Label lDepartTime = new Label("Departure Time");
      Label lDest = new Label("Destination Airport");
      Label lArrive = new Label("Arrival Day");
      Label lArriveTime = new Label("Arrival Time");

      Label searchAirlineLabel = new Label("Airline");
      Label searchSrcLabel = new Label("Source Airport");
      Label searchDestLabel = new Label("Destination Airport");

      final TextBox tbAirline = MakeTextBox();
      tbAirline.setMaxLength(30);
      //tbAirline.setVisibleLength(30);
      tbAirline.getElement().setAttribute("placeholder", "Alaska");
      //tbAirline.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);

      final TextBox tbFlightNum = MakeTextBoxNumbersOnly();
      tbFlightNum.getElement().setAttribute("placeholder", "123");
      tbFlightNum.setMaxLength(10);
      //tbFlightNum.setVisibleLength(10);

      final TextBox tbSrc = MakeTextBoxLettersOnly();
      tbSrc.getElement().setAttribute("placeholder", "PDX");
      tbSrc.setMaxLength(3);
      tbSrc.setVisibleLength(3);

      final TextBox tbDepart = MakeTextBox();
      tbDepart.getElement().setAttribute("placeholder", "HH:MM am/pm");

      final TextBox tbDest = MakeTextBoxLettersOnly();
      tbDest.getElement().setAttribute("placeholder", "LAX");
      tbDest.setMaxLength(3);
      tbDest.setVisibleLength(3);

      final TextBox tbArrive = MakeTextBox();
      tbArrive.getElement().setAttribute("placeholder", "HH:MM am/pm");

      final TextBox searchAirlineBox = MakeTextBox();
      searchAirlineBox.setMaxLength(30);
      searchAirlineBox.getElement().setAttribute("placeholder", "Alaska");
      final TextBox searchSrcBox = MakeTextBoxLettersOnly();
      searchSrcBox.getElement().setAttribute("placeholder", "ORD");
      searchSrcBox.setMaxLength(3);
      final TextBox searchDestBox = MakeTextBoxLettersOnly();
      searchDestBox.getElement().setAttribute("placeholder", "PHL");
      searchDestBox.setMaxLength(3);


      HorizontalPanel hpanel = new HorizontalPanel();

      //final DatePicker dpSource = CreateDatePicker(tbDepart);
      //final DatePicker dpDest = CreateDatePicker(tbArrive);

      DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
      final DateBox dateBoxDeparture = new DateBox();
      dateBoxDeparture.setFormat(new DateBox.DefaultFormat(dateFormat));
//      dateBoxDeparture.getDatePicker().setYearArrowsVisible(true);
      dateBoxDeparture.getElement().setAttribute("placeholder", "Select Date");

      final DateBox dateBoxArrival = new DateBox();
      dateBoxArrival.setFormat(new DateBox.DefaultFormat(dateFormat));
      dateBoxArrival.getElement().setAttribute("placeholder", "Select Date");

      VerticalPanel panel_One = new VerticalPanel();
      VerticalPanel flight_Panel = new VerticalPanel();
      VerticalPanel misc_Panel = new VerticalPanel();

      final FlexTable t = InitalizedNewFlexTable();
      final FlexTable searchResultsTable = InitalizedNewFlexTable();

      final TabPanel tp = new TabPanel();

      final HashMap<String, FlexTable> flexMap = new HashMap<>();

      RootPanel rootPanel = RootPanel.get();


      FlightServiceAsync async = GWT.create(FlightService.class);
      async.getAirlineHashMap(new AsyncCallback<HashMap<String, Airline>>() {
          @Override
          public void onFailure(Throwable throwable) {
              Window.alert(throwable.toString());
          }

          @Override
          public void onSuccess(HashMap<String, Airline> AirlineHashMap) {
              updateTable(AirlineHashMap, flexMap, tp);
          }
      });

      String tab = tp.getTabBar().getTabHTML(0);
      //Window.alert(String.valueOf(tp.getTabBar().getTabCount()));
//      if(tab.isEmpty()) {
//          AddTab(tp, t, "Airline");
//          tp.selectTab(0);
//      }
//      else if (tab.equals("Airline")){
//          tp.remove(0);
//          tp.selectTab(0);
//      }




      Button README = new Button("HELP");
      README.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
             String README = getREADME();
             Window.alert(README);
          }
      });
//      rootPanel.add(README);

      Button SubmitFlight = new Button("Add Flight");
      SubmitFlight.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              //Submit
              final String AirlineName = tbAirline.getText();
              final int FlightNum = Integer.parseInt(tbFlightNum.getText());
              String Src = tbSrc.getText().toUpperCase();
              String departDate = dateBoxDeparture.getTextBox().getText(); //dpSource.getValue().toString();
              String departTime = tbDepart.getText();
              String Depart = departDate + " " + departTime;
              String Dest = tbDest.getText().toUpperCase();
              String arriveDate = dateBoxArrival.getTextBox().getText(); //dpDest.getValue().toString();
              String arriveTime = tbArrive.getText();
              String Arrive = arriveDate + " " + arriveTime;

              try {
                  CheckMissingFields(AirlineName,String.valueOf(FlightNum),Src,departDate,departTime,Dest,arriveDate,arriveTime,false);
              } catch (Exception e) {
                  Window.alert(e.toString());
                  return;
              }

              try {
                  FormatDateStringAsString(Depart);
                  FormatDateStringAsString(Arrive);
              }catch(Exception e){
                  Window.alert(e.toString() + "\nYour flight was not added");
                  return;
              }

              try{
                  ValidateRealAirportCode(Src);
                  ValidateRealAirportCode(Dest);
              } catch(Exception e){
                  Window.alert(e.toString());
                  return;
              }


              Flight flight = new Flight(FlightNum,Src,Depart,Dest,Arrive);

              FlightServiceAsync async = GWT.create(FlightService.class);
              async.addAirline(AirlineName, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(String.valueOf(throwable));
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      //Window.alert("Added airline");
                      String tab = tp.getTabBar().getTabHTML(0);
                      FlexTable t = InitalizedNewFlexTable();
                      if(tab.equals("Airline")){
                          tp.remove(0);
                          t = flexMap.get(AirlineName);
                          if(t == null){
                              FlexTable ft = InitalizedNewFlexTable();
                              AddTab(tp, ft, AirlineName);
                              flexMap.put(AirlineName, ft);
                              flexMap.put("Airline", t);
                          }
                          else{
                              flexMap.put("Airline", t);
                              flexMap.put(AirlineName,t);
                          }
//                          tp.getTabBar().setTabHTML(0, AirlineName);
//                          flexMap.put(AirlineName,t);
//                          tp.getWidgetIndex(t);
                      }
                      if (flexMap.get(AirlineName) == null) {
                          FlexTable ft = InitalizedNewFlexTable();
                          AddTab(tp, ft, AirlineName);
                          flexMap.put(AirlineName, ft);

                      }


                  }
              });

              async.addFlight(AirlineName, flight, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(throwable.toString());
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      try {
                          AddToTable(abstractAirline, flexMap, AirlineName);
                      } catch (IOException e) {
                          Window.alert(e.toString() + "\nYour flight has not been added");
                          return;
                      }

                  }
              });
          }
      });

      Button searchButton = new Button("Search Flights");
      searchButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              final String searchAirline = searchAirlineBox.getText();
              final String searchSrc = searchSrcBox.getText().toUpperCase();
              final String searchDest = searchDestBox.getText().toUpperCase();

              try {
                  CheckMissingFields(searchAirline,null,searchSrc,null,null,searchDest,null,null,true);
              } catch (Exception e) {
                  Window.alert(e.toString());
                  return;
              }

              try {
                  ValidateRealAirportCode(searchSrc);
                  ValidateRealAirportCode(searchDest);
              } catch (IOException e) {
                  Window.alert("You have entered an invalid airport code. Codes needs to correspond" +
                          " to real airports!");
                  return;
              }

              FlightServiceAsync async = GWT.create(FlightService.class);
              async.searchFlights(searchAirline, searchSrc, searchDest, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(throwable.toString());
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      Collection flightList = abstractAirline.getFlights();
                      LinkedList<Flight> searchResults = new LinkedList<Flight>();
                      if(!abstractAirline.getName().equals(searchAirline)){
                          Window.alert("That airline does not exist");
                          return;
                      }
                      for (Object o : flightList) {
                          if (searchSrc.equals(((Flight) o).getSource())) {
                              if (searchDest.equals(((Flight) o).getDestination())) {
                                  searchResults.add(((Flight)o));
                              }
                          }
                      }

                      AddToSearchResults(searchResults, searchResultsTable, abstractAirline.getName());
                  }
              });
          }
      });



      panel_One.add(lAirline);
      panel_One.add(tbAirline);
      panel_One.add(lFlightNum);
      panel_One.add(tbFlightNum);
      panel_One.add(lSrc);
      panel_One.add(tbSrc);
      panel_One.add(lDepart);
      panel_One.add(dateBoxDeparture);
      panel_One.add(lDepartTime);
      panel_One.add(tbDepart);
      panel_One.add(lDest);
      panel_One.add(tbDest);
      panel_One.add(lArrive);
      panel_One.add(dateBoxArrival);
      panel_One.add(lArriveTime);
      panel_One.add(tbArrive);
      panel_One.add(SubmitFlight);

      flight_Panel.add(tp);

      hpanel.add(panel_One);
      hpanel.add(flight_Panel);
      hpanel.add(searchResultsTable);

      misc_Panel.add(README);
      misc_Panel.add(searchAirlineLabel);
      misc_Panel.add(searchAirlineBox);
      misc_Panel.add(searchSrcLabel);
      misc_Panel.add(searchSrcBox);
      misc_Panel.add(searchDestLabel);
      misc_Panel.add(searchDestBox);
      misc_Panel.add(searchButton);

      hpanel.add(misc_Panel);

      rootPanel.add(hpanel);

  }

    /**
     * "Builds" the README
     * @return  A README string
     */
    private String getREADME() {
        String README = "Airline Application Created by Peter Gicking ©\n\n\n" +
                "This application will store multiple flights for multiple airlines.\n" +
                "All you need to do is enter the flight information on the left to create a flight." +
                " All flights will be stored in the tab of the airline you specified.\n" +
                "\nThere is also a search functionality, if you enter the airline and source and destionation airport codes," +
                "it wil return a list of flights that match those source and destination codes in the Airline you specified.\n" +
                "Please enjoy!";
        return README;
    }

    /**
     * Adds to the flextable of a given airline a new flight
     * @param airline
     * @param flexMap
     * @param airlineName
     * @throws IOException
     */
    private void AddToTable(AbstractAirline airline, HashMap<String, FlexTable> flexMap, String airlineName) throws IOException{
        Collection flightList = airline.getFlights();
        int i = 1;
        FlexTable t = flexMap.get(airlineName);
        t.removeAllRows();
        t = RewriteHeader(t);
        Collections.sort((LinkedList) flightList);

        //Window.alert(flightList.toString());
        for (Object o : flightList) {
            try {
                //t.addCell(i);
                t.insertRow(i);
                t.setText(i, 0, String.valueOf(((Flight) o).getNumber()));
                t.setText(i, 1, ValidateRealAirportCode(((Flight) o).getSource()));
                t.setText(i, 2, ((Flight) o).getDepartureString());
                t.setText(i, 3, ValidateRealAirportCode(((Flight) o).getDestination()));
                t.setText(i, 4, ((Flight) o).getArrivalString());
                t.setText(i, 5, String.valueOf(CalculateFlightLength((((Flight) o).getDeparture()), ((Flight) o).getArrival()) +
                " minutes"));
                ++i;
            } catch (IOException e){
                t.removeRow(i);
                String error = e.toString();
                throw new IOException(error);

            }
        }
    }

    /**
     * Adds the results to the search flexTable.
     * @param flightList
     * @param t
     * @param airlineName
     */
    private void AddToSearchResults(LinkedList<Flight> flightList, FlexTable t, String airlineName){
        int i = 1;
        t.removeAllRows();
        t = RewriteHeader(t);

        for (Object o : flightList) {
            try {
                //t.addCell(i);
                t.insertRow(i);
                t.setText(i, 0, String.valueOf(((Flight) o).getNumber()));
                t.setText(i, 1, ValidateRealAirportCode(((Flight) o).getSource()));
                t.setText(i, 2, ((Flight) o).getDepartureString());
                t.setText(i, 3, ValidateRealAirportCode(((Flight) o).getDestination()));
                t.setText(i, 4, ((Flight) o).getArrivalString());
                t.setText(i, 5, String.valueOf(CalculateFlightLength((((Flight) o).getDeparture()), ((Flight) o).getArrival()) +
                        " minutes"));
                ++i;
            } catch (IOException e){
                t.removeRow(i);
                String error = e.toString();

            }
        }
    }

    /**
     * Function called before anything else to keep the data persistant after a refresh
     * A bit buggy though, it doesnt delete the default tab bar.
     * @param airlineHashMap
     * @param flexMap
     * @param tp
     */
    private void updateTable(final HashMap<String, Airline> airlineHashMap, final HashMap<String, FlexTable> flexMap, final TabPanel tp){
        FlightServiceAsync async = GWT.create(FlightService.class);
        async.getAirlines(new AsyncCallback<LinkedList<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            @Override
            public void onSuccess(LinkedList<String> strings) {
                Airline airline = null;
                for(String s : strings){
                   FlexTable t = new FlexTable();

                    if(flexMap.get(s) == null ){
                        t = InitalizedNewFlexTable();
                        flexMap.put(s,t);
                        AddTab(tp,t,s);
                    }
//                    else if(airlineHashMap.get(tab) == null){
////                      tp.getTabBar().setTabHTML(0,s);
//                        t = flexMap.get(s);
//                        AddTab(tp,t,s);
//                        tp.getTabBar().removeTab(0);
//                    }
                    else {
                        t = flexMap.get(s);
                        AddTab(tp, t, s);
                    }
                    airline = airlineHashMap.get(s);

                    try {
                        AddToTable(airline,flexMap,s);
                    } catch (IOException e) {
                        Window.alert("This should never happen, grats you broke it.\n" + e.toString());
                    }
                }
                //Window.alert(String.valueOf(tp.getTabBar().getTabCount()));
                if(tp.getTabBar().getTabCount() == 0) {
                    FlexTable t = InitalizedNewFlexTable();
                    AddTab(tp, t, "Airline");
                    flexMap.put("Airline", t);
                    tp.selectTab(0);
                }
                //tp.selectTab(0);
            }
        });
    }


    /**
     * Checks if any of the fields are empty and throws an error if any are
     * @param airlineName
     * @param FlightNum
     * @param Src
     * @param departDate
     * @param departTime
     * @param Dest
     * @param arriveDate
     * @param arriveTime
     * @param searchFlag    If true, only checks airlinename, source and desination
     * @throws Exception
     */
    private void CheckMissingFields(String airlineName,
                                    String FlightNum,
                                    String Src,
                                    String departDate,
                                    String departTime,
                                    String Dest,
                                    String arriveDate,
                                    String arriveTime,
                                    boolean searchFlag)
                                    throws Exception{
        String empties = "";
        boolean isEmpty = false;

        if(airlineName.isEmpty()){
            empties += "Airline\n";
            isEmpty = true;
        }

        if(Src.isEmpty()){
            empties += "Source Aiport\n";
            isEmpty = true;
        }

        if (Dest.isEmpty()) {
            empties += "Destination Airport\n";
            isEmpty = true;
        }

        if(!searchFlag) {
            if(FlightNum.isEmpty()){
                empties += "Flight Number\n";
                isEmpty = true;
            }

            if (departDate.isEmpty()) {
                empties += "Departure Day\n";
                isEmpty = true;
            }

            if (departTime.isEmpty()) {
                empties += "Departure Time\n";
                isEmpty = true;
            }
            if (arriveDate.isEmpty()) {
                empties += "Arrival Day\n";
                isEmpty = true;
            }
            if (arriveTime.isEmpty()) {
                empties += "Arrival Time\n";
                isEmpty = true;
            }
        }

        if(isEmpty){
            throw new Exception("Missing required fields: " + "\n" + empties);
        }

    }

    /**
     * Initalizes a new flex table with headers for information
     * about a flight
     * @return  An initalized flex table
     */
    private FlexTable InitalizedNewFlexTable() {
        FlexTable t = new FlexTable();
        t.getElement().getStyle().setBorderWidth(20, Style.Unit.PX);
        t.setCellSpacing(10);
        t.setText(0, 0, "Flight Number");
        t.setText(0, 1, "Source Airport");
        t.setText(0, 2, "Departure Time");
        t.setText(0, 3, "Destination Airport");
        t.setText(0, 4, "Arrival Time");
        t.setText(0, 5, "Duration");


        // ...and set it's column span so that it takes up the whole row.
        //t.getFlexCellFormatter().setColSpan(1, 0, 3);

        return t;
    }

    /**
     * Rewrites the header for the tables beucase they are often deleted to display
     * the sorted list
     * @param t  Flextable to write to
     * @return  Written flexTable
     */
    private FlexTable RewriteHeader(FlexTable t){
        t.setCellSpacing(10);
        t.setText(0, 0, "Flight Number");
        t.setText(0, 1, "Source Airport");
        t.setText(0, 2, "Departure Time");
        t.setText(0, 3, "Destination Airport");
        t.setText(0, 4, "Arrival Time");
        t.setText(0, 5, "Duration");

        return t;
    }

    /**
     * Adds a tab to tab panel containing airline name and adds a flex table
     * that will contain the flight list
     * @param tp    The Tab panel
     * @param t     The flex table to be added
     * @param airlineName   The airline name to label the tab with
     */
    private void AddTab(TabPanel tp, FlexTable t, String airlineName) {
        tp.add(t, airlineName);
        tp.selectTab(tp.getWidgetIndex(t));
    }

    private DatePicker CreateDatePicker(TextBox tb) {
        DatePicker datePicker = new DatePicker();
        final Label text = new Label();
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
        // Set the value in the text box when the user selects a date
        datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
            public void onValueChange(ValueChangeEvent<Date> event) {
                Date date = event.getValue();
                String dateString = DateTimeFormat.getMediumDateFormat().format(date);
                text.setText(dateString);
            }
        });

        // Set the default value
        datePicker.setValue(new Date(), true);
        return datePicker;
    }


    /**
     * Uses daves AirportNames class to validate if the airport code is actually a real
     * airport
     * @param code  A specific airport code
     * @return The validated airport code
     */
    public static String ValidateRealAirportCode(String code) throws IOException{
        String name = AirportNames.getName(code.toUpperCase());

        if(name == null){
           throw new IOException(code + " is not a valid airport code!\n" +
                    "Airport codes need to correspond to real airports!\n");
        }
        return name;
    }

    /**
     * Converts a date string into a date object
     * @param arg  The array of command line arguments
     * @return   A date object
     */
    public static Date FormatDateStringAsDate(String arg) {
        //DateTimeFormat dtf = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT);

        DateTimeFormat dtf = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        //DateTimeFormat dtf = DateTimeFormat.getMediumDateTimeFormat();
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        //Date parsedDate = formatter.parse(arg);
        Date date = null;
        date = dtf.parse(arg);
        //dtf.format(date);
        //Window.alert("Formatted date object: " + date.toString());
        return date;
    }
    /**
     * Checks to make sure the date is in the correct format (no regex this time)
     * and then puts it in a certain format
     * @param arg The arrival or departure date strings
     * @return The formatted date
     */
    public static String FormatDateStringAsString(String arg) throws Exception {
        Date date = null;

        DateTimeFormat dtf = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");

        try {
            date = dtf.parse(arg);
        }catch (Exception e){
            throw new Exception("Your date is text: " + arg + " is malformed!" );

        }
        //dtf.format(date);
        //Window.alert("Formatted date object: " + date.toString());
        return date.toString();
    }
    /**
     * Calculates the flight time in minutes for prettyPrint
     * @param depart    The departure date
     * @param arrive    The arrive date
     * @return  The length in minutes
     */
    public static Long CalculateFlightLength(Date depart, Date arrive){
        Long length = (arrive.getTime() - depart.getTime());
        length = length / (60000L);

        return length;
    }

    /**
     * A textbox that accepts alphanumerics
     * @return A textbox
     */
    private TextBox MakeTextBox() {
        TextBox tb = new TextBox();

        tb.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
            }
        });
        return tb;
    }

    /**
     * Makes a text box that only accepts numerics
     * @return  A textbox that only returns numerics
     */
    private TextBox MakeTextBoxNumbersOnly() {
        TextBox tb = new TextBox();

        tb.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
                if (!Character.isDigit(event.getCharCode())) {
                    ((TextBox) event.getSource()).cancelKey();
                }
            }
        });
        return tb;
    }

    /**
     * Makes a text box which can only accept letters
     * @return A textbo which only accepts letters
     */
    private TextBox MakeTextBoxLettersOnly() {
        TextBox tb = new TextBox();

        tb.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
                if (Character.isDigit(event.getCharCode())) {
                    ((TextBox) event.getSource()).cancelKey();
                }
            }
        });
        return tb;
    }
}
