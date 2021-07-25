
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class HtmlParser implements Parser {

    private String url;
    private WebClient webClient;
    private CsvFileWriter csvFileWriter;

    public HtmlParser(String url, WebClient webClient, CsvFileWriter csvFileWriter) {
        this.url = url;
        this.webClient = webClient;
        this.csvFileWriter = csvFileWriter;
    }

    public void setOptions(WebClient webClient) {
        webClient.getOptions().setThrowExceptionOnScriptError(false); // Выдается ли исключение при сбое выполнения JS
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); // Выдается ли исключение, если состояние HTTP не равно 200
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false); // Включить ли CSS
        webClient.getOptions().setJavaScriptEnabled(true); // Важно включить JS
        webClient.getOptions().setRedirectEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController()); // Важно, чтобы параметр поддерживал AJAX

        webClient.getCookieManager().setCookiesEnabled(true);

        webClient.getOptions().setTimeout(60000); // Установить время ожидания запроса «Браузер»
        webClient.setJavaScriptTimeout(6000); // Установить время ожидания для выполнения JS
        webClient.getCurrentWindow().setInnerHeight(Integer.MAX_VALUE);


        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.waitForBackgroundJavaScript(30 * 1000);
    }

    @Override
    public void toParse() throws IOException {


        HtmlPage page = (HtmlPage) webClient.getPage(url);
        String s = page.asXml();

        List<HtmlElement> div = page.getByXPath("//div[@class='upper-and-lower deals-item-outer']");

        csvFileWriter.toCreateFile("data.csv");

        String[] header = {"Link", "Image", "Title", "Current price", "Original price", "Discount", "Sold", "Claimed"};
        csvFileWriter.toWrite(header);


        if (div.isEmpty()) {
            System.out.println("Тэг div с классом  'upper-and-lower deals-item-outer' не найден ");
        } else {
            for (HtmlElement divElemnt : div) {
                Object link = divElemnt.getFirstByXPath("div[@class='deals-item-inner']/a/@href");
                Object image = divElemnt.getFirstByXPath(".//a/div[@class='item-image']/img/@src");
                Object title = divElemnt.getFirstByXPath(".//div[@class='item-details']/h3/text()");
                Object currentPrice = divElemnt.getFirstByXPath(".//p[@class='current-price']/text()");
                Object originalPrice = divElemnt.getFirstByXPath(".//p[@class='original-price']/del/text()");
                Object discount = divElemnt.getFirstByXPath(".//span[@class='folatR']/text()");
                Object solder = divElemnt.getFirstByXPath(".//span[@class='solder']/text()");
                Object claim = divElemnt.getFirstByXPath(".//span[@class='float-r']/text()");

                ItemHtmlUnit itemHtmlUnit = new ItemHtmlUnit();
                itemHtmlUnit.setItemLink((DomAttr) link);
                itemHtmlUnit.setItemImage((DomAttr) image);
                itemHtmlUnit.setTitle(title.toString());
                itemHtmlUnit.setOriginalPrice(originalPrice.toString());
                itemHtmlUnit.setCurrentPrice(currentPrice.toString());
                itemHtmlUnit.setDiscount(discount.toString());
                itemHtmlUnit.setQuantitySold(solder.toString());
                itemHtmlUnit.setClaimed(claim.toString());

                String[] itemToRecord = {

                        itemHtmlUnit.getItemLink().toString(),
                        itemHtmlUnit.getItemImage().toString(),
                        itemHtmlUnit.getTitle(),
                        itemHtmlUnit.getCurrentPrice(),
                        itemHtmlUnit.getOriginalPrice(),
                        itemHtmlUnit.getDiscount(),
                        itemHtmlUnit.getQuantitySold(),
                        itemHtmlUnit.getClaimed()
                };

                csvFileWriter.toWrite(itemToRecord);
                if (!csvFileWriter.checkError()) {
                    System.out.println(" Сведения о товаре " + itemHtmlUnit.getTitle() + " добавлены в файл");
                }
            }
        }
        csvFileWriter.toClose();
    }
}
