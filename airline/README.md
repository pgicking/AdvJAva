CS410 AdvJava Project 1
=======================
In this project you will create the fundamental Airline and Flight classes that you will work with for

the duration of the course.

Goals: Extend classes that you did not write and perform more complex command line parsing

The edu.pdx.cs410J package contains two abstract classes, AbstractAirline and AbstractFlight.

For this project you will write two concrete classes in your edu.pdx.cs410J.login package: Airline

that extends AbstractAirline and Flight that extends AbstractFlight2

. Each of your classes

must implement all of the abstract methods of its superclass.

An Airline has a name and consists of multiple Flights. A Flight departs from a source and leaves

at a given departure time3

, and arrives at a destination at a given arrival time. For this assignment, all of this

data should be modeled with Strings and you may also ignore the getDeparture and getArrival

methods. Each Flight is assigned an identifying number.

You should also create a Project1 class that contains a main method that parses the command line,

creates an Airline and a Flight as speciﬁed by the command line, adds the Flight to the Airline,

and optionally prints a description of the Flight by invoking its toString method4

. Your Project1

class should have the following command line interface:

usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>

args are (in this order):

name The name of the airline

flightNumber The flight number

src Three-letter code of departure airport

departTime Departure date and time (24-hour time)

dest Three-letter code of arrival airport

arriveTime Arrival date and time (24-hour time)

options are (options may appear in any order):

-print Prints a description of the new flight

-README Prints a README for this project and exits

Date and time should be in the format: mm/dd/yyyy hh:mm

Note that multi-word arguments should be delimited by double quotes. For instance the name argument

could be "CS410J Air Express". However, the dates and times should not be quoted (they are two

separate command line arguments). The following dates and times are valid: 3/15/2014 10:39 and

03/2/2014 1:03
