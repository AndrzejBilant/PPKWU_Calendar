package Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@RestController public class DataCollector {

    @RequestMapping(path = "/analyseString")
    public String getCalendarForRequestedMonthFromWEEIA(@RequestParam(value = "month", required = true) int month,
            @RequestParam(value = "year", required = true) int year) {

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
        Elements elements = document.select("td");

        for (Element element : elements) {
            if (element.attr("class").equals("active")) {
                result.append(element);
            }
        }

        return result.toString();
    }
}
