package Calendar;

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

        URL oracle = null;
        try {
            url += yearBuilder + year + monthBuilder;
            if (month < 10) {
                url += "0";
            }
            oracle = new URL(url + month);

            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                result.append(inputLine);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return result.toString();
    }
}
