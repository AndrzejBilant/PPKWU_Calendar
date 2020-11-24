package Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController public class DataCollector {

    @RequestMapping(path = "/calendar")
    public String getCalendarForRequestedMonthFromWEEIA(@RequestParam(value = "month") int month,
            @RequestParam(value = "year") int year) {

        String url = "http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?";
        String yearBuilder = "rok=";
        String monthBuilder = "&miesiac=";

        StringBuilder result = new StringBuilder();

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

        ArrayList<Event> events = new ArrayList();
        Elements elements = document.select("td");

        for (Element element : elements) {
            if (element.attr("class").equals("active")) {
                Elements date = element.select("a[href]");
                String link = date.attr("href");
                Elements text = element.getElementsByTag("p");

                System.out.println(link+date.text()+text.text());
                events.add(new Event(link,date.text(),text.text(),String.valueOf(year), String.valueOf(month)));

            }
        }

        return result.toString();
    }
}
