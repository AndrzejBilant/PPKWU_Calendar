package Calendar;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@RestController public class DataCollector {

    @RequestMapping(path = "/calendar")
    public void getCalendarForRequestedMonthFromWEEIA(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year,
            HttpServletResponse response) {

        Document document = getDataFromWebsite(month, year);
        StringBuilder result = new StringBuilder(
                "BEGIN:VCALENDAR\r\n" + "VERSION:2.0\r\n" + "PRODID:-//hacksw/handcal//NONSGML v1.0//EN\r\n");
        ArrayList<Event> events = getEvents(month, year, document);
        for (Event e : events) {
            String temp = e.generateICal();
            result.append(temp);
        }
        result.append("END:VCALENDAR");
        File file = generateFile(result.toString(), month, year);

        sendFile(response, file);
    }

    private void sendFile(HttpServletResponse response, File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            response.setContentType("text/calendar;charset=utf-8");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private ArrayList<Event> getEvents(int month, int year, Document document) {
        ArrayList<Event> events = new ArrayList();
        Elements elements = document.select("td");
        for (Element element : elements) {
            if (element.attr("class").equals("active")) {
                Elements date = element.select("a[href]");
                String link = date.attr("href");
                if (link.contains("javascript:void()")) {
                    link = null;
                }
                Elements text = element.getElementsByTag("p");

                String tempMonth = "";
                if (month < 10) {
                    tempMonth = tempMonth + "0";
                }
                tempMonth += String.valueOf(month);
                String tempDay = "";
                if (Integer.valueOf(date.text()) < 10) {
                    tempDay += "0";
                }
                tempDay += date.text();
                events.add(new Event(link, tempDay, text.text(), String.valueOf(year), tempMonth));

            }
        }
        return events;
    }

    private Document getDataFromWebsite(int month, int year) {
        String url = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?";
        String yearBuilder = "rok=";
        String monthBuilder = "&miesiac=";

        url += yearBuilder + year + monthBuilder;
        if (month < 10) {
            url += "0";
        }

        Document document = null;
        try {
            document = Jsoup.connect(url + month).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return document;
    }

    public File generateFile(String content, int month, int year) {
        String name = month + year + ".ics";
        File file = new File(name);
        try {
            FileWriter fileWriter = new FileWriter(name);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return file;
    }
}
