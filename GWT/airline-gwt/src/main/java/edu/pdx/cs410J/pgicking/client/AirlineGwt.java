package edu.pdx.cs410J.pgicking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

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
      final FlexTable searchResults = InitalizedNewFlexTable();

      final TabPanel tp = new TabPanel();
      AddTab(tp,t,"Null");
      tp.selectTab(0);
      final HashMap<String, FlexTable> flexMap = new HashMap<>();

//      Button button = new Button("Ping Server");
//      button.addClickHandler(new ClickHandler() {
//          public void onClick(ClickEvent clickEvent) {
//              PingServiceAsync async = GWT.create(PingService.class);
//              async.ping(new AsyncCallback<AbstractAirline>() {
//
//                  public void onFailure(Throwable ex) {
//                      Window.alert(ex.toString());
//                  }
//
//                  public void onSuccess(AbstractAirline airline) {
//                      StringBuilder sb = new StringBuilder(airline.toString());
//                      Collection<AbstractFlight> flights = airline.getFlights();
//                      for (AbstractFlight flight : flights) {
//                          sb.append(flight);
//                          sb.append("\n");
//                      }
//                      Window.alert(sb.toString());
//                  }
//              });
//          }
//      });
      RootPanel rootPanel = RootPanel.get();
//      rootPanel.add(button);


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
              String Src = tbSrc.getText();
              String departDate = dateBoxDeparture.getTextBox().getText(); //dpSource.getValue().toString();
              String departTime = tbDepart.getText();
              String Depart = departDate + " " + departTime;
              //Date DDay = FormatDateStringAsDate(Depart);
              //Window.alert(String.valueOf(DDay));
              String Dest = tbDest.getText();
              String arriveDate = dateBoxArrival.getTextBox().getText(); //dpDest.getValue().toString();
              String arriveTime = tbArrive.getText();
              String Arrive = arriveDate + " " + arriveTime;

              Flight flight = new Flight(FlightNum,Src,Depart,Dest,Arrive);
              //Window.alert("Adding flight: " + flight.toString());
              FlightServiceAsync async = GWT.create(FlightService.class);
              async.addAirline(AirlineName, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(String.valueOf(throwable));
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      //Window.alert("Added airline");
                      if(flexMap.get(AirlineName) == null) {
                          FlexTable t = InitalizedNewFlexTable();
                          AddTab(tp, t, AirlineName);
                          flexMap.put(AirlineName, t);
                      }
//                      String tab = tp.getTabBar().getTabHTML(0);
//                      if(tab.equals("Null")){
//                          tp.remove(0);
//                      }

                  }
              });

              async.addFlight(AirlineName, flight, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(throwable.toString());
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      //Window.alert("Sucessfully added flight " + FlightNum + " to airline " + AirlineName);
                      //update flextable

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
              String searchAirline = searchAirlineBox.getText();
              final String searchSrc = searchSrcBox.getText();
              final String searchDest = searchDestBox.getText();

              try {
                  ValidateRealAirportCode(searchSrc);
                  ValidateRealAirportCode(searchDest);
              } catch (IOException e) {
                  Window.alert("You have entered an invalid airport code. Codes needs to correspond" +
                          " to real airports!");
                  return;
              }

              Window.alert("Before search call");
              FlightServiceAsync async = GWT.create(FlightService.class);
              Window.alert("After async");
              async.searchFlights(searchAirline, searchSrc, searchDest, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(throwable.toString());
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      Collection flightList = abstractAirline.getFlights();
                        Window.alert("After search call");
                      for (Object o : flightList) {
                          if (searchSrc.equals(((Flight) o).getSource())) {
                              if (searchDest.equals(((Flight) o).getDestination())) {
                                  try {
                                      AddToTable(abstractAirline, flexMap, abstractAirline.getName());
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }
                          }
                      }
                  }
              });
          }
      });



      // Add it to the root panel.
      //RootPanel.get().add(tp);

      // Add the widgets to the page
//      RootPanel.get().add(text);
//      RootPanel.get().add(datePicker);


      // Add them to the root panel_One.
      panel_One.add(lAirline);
      panel_One.add(tbAirline);
      panel_One.add(lFlightNum);
      panel_One.add(tbFlightNum);
      panel_One.add(lSrc);
      panel_One.add(tbSrc);
      panel_One.add(lDepart);
      //panel_One.add(dpSource);
      panel_One.add(dateBoxDeparture);
      panel_One.add(lDepartTime);
      panel_One.add(tbDepart);
      panel_One.add(lDest);
      panel_One.add(tbDest);
      panel_One.add(lArrive);
      //panel_One.add(dpDest);
      panel_One.add(dateBoxArrival);
      panel_One.add(lArriveTime);
      panel_One.add(tbArrive);
      panel_One.add(SubmitFlight);

      flight_Panel.add(tp);

      hpanel.add(panel_One);
      hpanel.add(flight_Panel);
      hpanel.add(searchResults);

      misc_Panel.add(README);
      misc_Panel.add(searchAirlineLabel);
      misc_Panel.add(searchAirlineBox);
      misc_Panel.add(searchSrcLabel);
      misc_Panel.add(searchSrcBox);
      misc_Panel.add(searchDestLabel);
      misc_Panel.add(searchDestBox);
      misc_Panel.add(searchButton);
//      misc_Panel.add(button);

      hpanel.add(misc_Panel);

      rootPanel.add(hpanel);

  }

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

    private void AddToTable(AbstractAirline airline, HashMap<String, FlexTable> flexMap, String airlineName) throws IOException{
        Collection flightList = airline.getFlights();
        int i = 1;
        FlexTable t = flexMap.get(airlineName);
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



    private FlexTable InitalizedNewFlexTable() {
        FlexTable t = new FlexTable();
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

    private void AddTab(TabPanel tp, FlexTable t, String airlineName) {
        tp.add(t, airlineName);
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
    public static Date FormatDateStringAsDate(String arg){
        Date date = null;
        //DateTimeFormat dtf = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT);

        DateTimeFormat dtf = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        //DateTimeFormat dtf = DateTimeFormat.getMediumDateTimeFormat();
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        //Date parsedDate = formatter.parse(arg);

        date = dtf.parse(arg);
        //dtf.format(date);
        //Window.alert("Formatted date object: " + date.toString());
        return date;
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

    private TextBox MakeTextBox() {
        TextBox tb = new TextBox();

        tb.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
            }
        });
        return tb;
    }

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
