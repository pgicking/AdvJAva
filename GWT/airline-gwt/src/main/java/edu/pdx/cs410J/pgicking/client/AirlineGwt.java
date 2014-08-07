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
import com.google.gwt.user.datepicker.client.DatePicker;
import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.util.Collection;
import java.util.Date;

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

      final TextBox tbAirline = MakeTextBox();
      final TextBox tbFlightNum = MakeTextBoxNumbersOnly();
      final TextBox tbSrc = MakeTextBox();
      TextBox tbDepart = MakeTextBox();
      final TextBox tbDest = MakeTextBox();
      TextBox tbArrive = MakeTextBox();

      HorizontalPanel hpanel = new HorizontalPanel();

      final DatePicker dpSource = CreateDatePicker(tbDepart);
      final DatePicker dpDest = CreateDatePicker(tbArrive);

      VerticalPanel panel_One = new VerticalPanel();
      VerticalPanel flight_Panel = new VerticalPanel();
      VerticalPanel misc_Panel = new VerticalPanel();

      final FlexTable t = InitalizedNewFlexTable();

      final TabPanel tp = new TabPanel();
      AddTab(tp,t,"Null");
      tp.selectTab(0);

      Button button = new Button("Ping Server");
      button.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent clickEvent) {
              PingServiceAsync async = GWT.create(PingService.class);
              async.ping(new AsyncCallback<AbstractAirline>() {

                  public void onFailure(Throwable ex) {
                      Window.alert(ex.toString());
                  }

                  public void onSuccess(AbstractAirline airline) {
                      StringBuilder sb = new StringBuilder(airline.toString());
                      Collection<AbstractFlight> flights = airline.getFlights();
                      for (AbstractFlight flight : flights) {
                          sb.append(flight);
                          sb.append("\n");
                      }
                      Window.alert(sb.toString());
                  }
              });
          }
      });
      RootPanel rootPanel = RootPanel.get();
//      rootPanel.add(button);


      Button README = new Button("HELP");
      README.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
             Window.alert("README will go here");
          }
      });
//      rootPanel.add(README);

      Button SubmitFlight = new Button("Add Flight");
      SubmitFlight.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
              //Submit
              //Window.alert("Adding flight");
              final String AirlineName = tbAirline.getText();
              final int FlightNum = Integer.parseInt(tbFlightNum.getText());
              String Src = tbSrc.getText();
              String departDate = dpSource.getValue().toString();
              String Dest = tbDest.getText();
              String arriveDate = dpDest.getValue().toString();

              Flight flight = new Flight(FlightNum,Src,departDate,Dest,arriveDate);
              FlightServiceAsync async = GWT.create(FlightService.class);
              async.addAirline(AirlineName, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(String.valueOf(throwable));
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      Window.alert("Added airline");
                  }
              });
              async.addFlight(AirlineName, flight, new AsyncCallback<AbstractAirline>() {
                  @Override
                  public void onFailure(Throwable throwable) {
                      Window.alert(throwable.toString());
                  }

                  @Override
                  public void onSuccess(AbstractAirline abstractAirline) {
                      Window.alert("Sucessfully added flight " + FlightNum + " to airline " + AirlineName);
                      AddTab(tp, t, AirlineName);
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
      panel_One.add(dpSource);
      panel_One.add(lDepartTime);
      panel_One.add(tbDepart);
      panel_One.add(lDest);
      panel_One.add(tbDest);
      panel_One.add(lArrive);
      panel_One.add(dpDest);
      panel_One.add(lArriveTime);
      panel_One.add(tbArrive);
      panel_One.add(SubmitFlight);

      flight_Panel.add(tp);

      hpanel.add(panel_One);
      hpanel.add(flight_Panel);

      misc_Panel.add(README);
      misc_Panel.add(button);

      hpanel.add(misc_Panel);

      rootPanel.add(hpanel);

      //rootPanel.add(text);
      //rootPanel.add(datePicker);

      // Attach two child widgets to a LayoutPanel, laying them out horizontally,
      // splitting at 50%.
      //Widget childOne = new HTML("left"), childTwo = new HTML("right");
//      LayoutPanel p = new LayoutPanel();
//      //p.add(datePicker);
//      p.add(b);
//      p.add(README);
//      p.add(button);
//
//      //p.setWidgetLeftWidth(datePicker, 0, Style.Unit.PCT, 50, Style.Unit.PCT);
//      p.setWidgetRightWidth(README, 25, Style.Unit.PCT, 50, Style.Unit.PCT);
//      p.setWidgetTopHeight(README, 10, Style.Unit.PCT, 5, Style.Unit.PCT);
//      p.setWidgetLeftWidth(button, 0, Style.Unit.PCT, 15, Style.Unit.PCT);
//      p.setWidgetTopHeight(button, 50, Style.Unit.PCT, 5, Style.Unit.PCT);
//      p.setWidgetRightWidth(b, 25, Style.Unit.PCT, 50, Style.Unit.PCT);
//      p.setWidgetTopHeight(b, 15, Style.Unit.PCT, 5, Style.Unit.PCT);
//      // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
//      // resize events on the window to ensure that its children are informed of
//      // possible size changes.
//      RootLayoutPanel rp = RootLayoutPanel.get();
//      rp.add(p);
  }

    private FlexTable InitalizedNewFlexTable() {
        FlexTable t = new FlexTable();
        t.setText(0, 0, "Flight Number");
        t.setText(0, 1, "Source Airport");
        t.setText(0, 2, "Departure Time");
        t.setText(0, 3, "Destination Airport");
        t.setText(0, 4, "Arrival Time");


        // ...and set it's column span so that it takes up the whole row.
        t.getFlexCellFormatter().setColSpan(1, 0, 3);

        return t;
    }

    private void AddTab(TabPanel tp, FlexTable t, String airlineName) {
        tp.add(t, airlineName);
    }

    private DatePicker CreateDatePicker(TextBox tb) {
        DatePicker datePicker = new DatePicker();
        final Label text = new Label();

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

    private TextBox MakeTextBox() {
        TextBox tb = new TextBox();

        // TODO(ECC) must be tested.
        tb.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
//                if (Character.isDigit(event.getCharCode())) {
//                    ((TextBox) event.getSource()).cancelKey();
//                }
            }
        });
        return tb;
    }

    private TextBox MakeTextBoxNumbersOnly() {
        TextBox tb = new TextBox();

        // TODO(ECC) must be tested.
        tb.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
                if (!Character.isDigit(event.getCharCode())) {
                    ((TextBox) event.getSource()).cancelKey();
                }
            }
        });
        return tb;
    }
}
