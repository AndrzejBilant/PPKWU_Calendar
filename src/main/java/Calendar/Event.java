package Calendar;

class Event {

    String link;
    String date;
    String text;
    String year;
    String month;
    String uid;
    String dtstamp;

    public Event(String link, String date, String text, String year, String month) {
        this.link = link;
        this.date = date;
        this.text = text;
        this.year = year;
        this.month = month;

        this.dtstamp = year + month + date + "T000000Z";
        this.uid = "-1234@example.com";
    }

    public String generateICal() {
        String result = "BEGIN:VEVENT\n"
                        + "UID:"
                        + dtstamp
                        + uid
                        + "\r\n"
                        + "DTSTAMP:"
                        + dtstamp
                        + "\r\n"
                        + "DTSTART:"
                        + dtstamp
                        + "\r\n"
                        + "SUMMARY:"
                        + text
                        + "\r\n";
        if (link != null) {
            result = result + "URL:" + link + "\r\n";
        }
        result = result + "END:VEVENT\r\n";

        return result;
    }
}
