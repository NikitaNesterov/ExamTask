import au.com.bytecode.opencsv.CSVWriter;
import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;

public class DynamicPageParser implements Parser {

    UrlBuilder urlBuilder;

    private int recordsCount = 0;

    private CsvFileWriter csvFileWriter = new CsvFileWriter(new CSVWriter(new FileWriter("dynamicdata.csv")));

    public DynamicPageParser() throws IOException {

        String[] header = {"Product Id", "Seller Id", "Product Title", "Current Price", "Original price", "Discount", "Orders", "Product Image",
                "Product Link", "Product rate", "Product Stars"};
        csvFileWriter.toCreateFile("dynamicdata.csv");
        csvFileWriter.toWrite(header);
        urlBuilder = new UrlBuilder();
    }

   public int getCount() {
        return  recordsCount;
   }

    public static String getStringItemdata(JSONObject itemData, String str) {
        String dataCheck = (String) itemData.get(str);
        return dataCheck;
    }

    public static Long getLongItemdata(JSONObject itemData, String str) {
        Long data = (Long) itemData.get(str);
        return data;
    }

    public void toCloseWriter(){
        csvFileWriter.toClose();
    }

    @Override
    public void toParse() throws IOException, ParseException {
        urlBuilder.setOffSet(12);

        urlBuilder.getUrl();

        FileWriter fileWriter = new FileWriter(new File("data.json"));

        Content getResult = Request.Get(urlBuilder.getStringBuilder().toString()).execute().returnContent();
        String data = getResult.toString();
        fileWriter.write(data.substring(data.indexOf("{"), data.lastIndexOf(")")));
        fileWriter.flush();
        fileWriter.close();

        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(new FileReader("data.json"));
        JSONArray productData = (JSONArray) response.get("results");

        for (int i = 0; i < productData.size(); i++) {

            if (recordsCount <= 100) {
                recordsCount++;
                JSONObject siteObject = new JSONObject((Map) productData.get(i));
                ItemApache itemApache = new ItemApache();
                itemApache.setProductId(getLongItemdata(siteObject, "productId"));
                itemApache.setSellerId(getLongItemdata(siteObject, "sellerId"));
                itemApache.setProductTitle(getStringItemdata(siteObject, "productTitle"));
                itemApache.setMinPrice(getStringItemdata(siteObject, "minPrice"));
                itemApache.setMaxPrice(getStringItemdata(siteObject, "maxPrice"));
                itemApache.setDiscount(getStringItemdata(siteObject, "discount"));
                itemApache.setOrders(getStringItemdata(siteObject, "orders"));
                itemApache.setProductImage(getStringItemdata(siteObject, "productImage"));
                itemApache.setProductDetailUrl(getStringItemdata(siteObject, "productDetailUrl"));
                itemApache.setProductPositiveRate(getStringItemdata(siteObject, "productPositiveRate"));
                itemApache.setProductAverageStar(getStringItemdata(siteObject, "productAverageStar"));


                String[] itemToRecord = {
                        String.valueOf(itemApache.getProductId()),
                        String.valueOf(itemApache.getSellerId()),
                        itemApache.getProductTitle(),
                        itemApache.getMinPrice(),
                        itemApache.getMaxPrice(),
                        itemApache.getDiscount(),
                        itemApache.getOrders(),
                        itemApache.getProductImage(),
                        itemApache.getProductDetailUrl(),
                        itemApache.getProductPositiveRate(),
                        itemApache.getProductAverageStar()
                };

                csvFileWriter.toWrite(itemToRecord);
                if (csvFileWriter.checkError()) {
                    System.out.println(" Ошибка заполнения файла");
                }
            }
        }
    }
}

