import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static Document getPage() throws IOException {
        String url = "http://www.pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");
    private static String getDAteFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if(matcher.find()){
            return matcher.group();
        }
        throw new Exception("Can't extract");

    }
    private static void printFourValues(Elements values, int index){
        if(index==0){
            Element valueLn = values.get(3);
            boolean isMorning = valueLn.text().contains("Утро");
            int iterationCount = 4;
            if(isMorning){
                iterationCount = 3;
            }
            for (int i=0; i<iterationCount; i++){
                Element valueline = values.get(index);
            for(Element td : valueline.select("td")) {
                System.out.print(td.text() + "  ");
            }
                System.out.println();
            }
        }
        for(int i=0; i<4; i++){
            Element valueline = values.get(index+i);
            for(Element td : valueline.select("td")){
                System.out.print(td.text()+"  ");
            }
            System.out.println();
        }

    }


    public static void main(String[] args) throws Exception {
        Document page = getPage();
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth]");
        Elements values = tableWth.select("tr[valign=top]");
        int index =0;
        for(Element name : names){
            String dateString = name.select("th[id=dt]").text();
            String date = getDAteFromString(dateString);
            System.out.println(date);
            printFourValues(values, index);

        }




    }
}
