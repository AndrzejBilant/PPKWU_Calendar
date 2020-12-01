# PPKWU_Calendar

####API for generating iCal file (.isc) for the requested month from http://www.weeia.p.lodz.pl calendar.

API downloads .ics file filed with list of events form WEEIA calendar for the requested month.

To do so type:
``` 
http://localhost:8080/calendar?month=MONTH&year=YEAR
```
Replace MONTH and YEAR with number of month and year you want to see.


##Mode of action:

First API collects data from WEEIA website using jSoup. 

After that data is filtered to gather useful information.
API looks for events. In WEEIA calendar events content of "active" class.
Date is text, link is in href and description is in a paragraph  \<p>.
  
After filtering the data String in iCal format, is created, written to file and saved.
File is later uploaded.

##Example .ics file generated for March 2020
<div>
<iframe src="https://raw.githubusercontent.com/AndrzejBilant/PPKWU_Calendar/master/032020.ics"></iframe>
</div>

##Reference documentation

* https://en.wikipedia.org/wiki/ICalendar
* https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/

