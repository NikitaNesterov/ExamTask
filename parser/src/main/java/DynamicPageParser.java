import au.com.bytecode.opencsv.CSVWriter;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Map;

public class DynamicPageParser implements Parser {

    final String callBack = "jQuery183036233163592076556_1626925634436";
    final String widgetId = "5547572";
    final String platform = "12";
    final String limit = "12";
    private int offSet = 0;
    final String phase = "1";
    final String postBack = "614f97c3-8d66-440b-a144-48b33fb9b377";
    StringBuilder stringBuilder;
    private int recordsCount = 0;

    private CsvFileWriter csvFileWriter = new CsvFileWriter(new CSVWriter(new FileWriter("dynamicdata.csv")));

    public DynamicPageParser() throws IOException {

        String[] header = {"Product Id", "Seller Id", "Product Title", "Current Price", "Original price", "Discount", "Orders", "Product Image",
                "Product Link", "Product rate", "Product Stars"};
        csvFileWriter.toCreateFile("dynamicdata.csv");
        csvFileWriter.toWrite(header);

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



    public StringBuilder getUrl() {
        stringBuilder = new StringBuilder();
        return stringBuilder.append("https://gpsfront.aliexpress.com/getRecommendingResults.do?callback=" +
                callBack +
                "&widget_id=" +
                widgetId +
                "&platform=" +
                platform +
                "&limit=" +
                limit +
                "&offset=" +
                String.valueOf(offSet) +
                "&phase=" +
                phase +
                "&productIds2Top=&postback=" +
                postBack
        );
    }

    public void toCloseWriter(){
        csvFileWriter.toClose();
    }

    @Override
    public void toParse() throws IOException, ParseException {
        offSet += 12;

        getUrl();

        FileWriter fileWriter = new FileWriter(new File("data.json"));

        Content getResult = Request.Get(stringBuilder.toString()).execute().returnContent();
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

