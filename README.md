# PPKWU_Calendar

API for generating iCal file (.isc) for requested month from http://www.weeia.p.lodz.pl calendar.

API downloads .ics file filed with list of events form WEEIA calendar for requested month.

To do so type http://localhost:8080/calendar?month=MONTH&year=YEAR

Replace MONTH and YEAR with number of month and year you want to see.


Mode of action:

First API collects data from weeia website using jSoup. 

After that data is filtered to gather useful infromation.
API looks for events. In WEEIA calendar eventscontent of "active" class.
Date is text, link is in href and description is in paragraph <p>.
  
After filtering the data String in iCal format, is created, written to file and saved.
File is later uploaded.
