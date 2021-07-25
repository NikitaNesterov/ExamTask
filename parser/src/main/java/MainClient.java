import au.com.bytecode.opencsv.CSVWriter;
import com.gargoylesoftware.htmlunit.*;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

public class MainClient {

    public static void main(String[] args) throws Exception {

        long before = System.currentTimeMillis();

        DynamicPageParser dynamicPageParser = new DynamicPageParser();
        for(int i = 0; i < 9; i++) {
            dynamicPageParser.toParse();
        }
        long after = System.currentTimeMillis();

        System.out.println("Файл dynamicdata.csv заполнен за " + (after - before)/1000 + " сек" +
                "\nКоличество записей - " + (dynamicPageParser.getCount() - 1));

        dynamicPageParser.toCloseWriter();

    }
}

