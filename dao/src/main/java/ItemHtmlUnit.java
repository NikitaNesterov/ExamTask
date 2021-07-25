import com.gargoylesoftware.htmlunit.html.DomAttr;

public class ItemHtmlUnit {

    private DomAttr itemLink;
    private DomAttr itemImage;
    private String title;
    private String currentPrice;
    private String originalPrice;
    private String discount;
    private String quantitySold;
    private String claimed;

    public ItemHtmlUnit(DomAttr itemLink, DomAttr itemImage, String title, String currentPrice, String originalPrice, String discount, String quantitySold, String claimed) {
        this.itemLink = itemLink;
        this.itemImage = itemImage;
        this.title = title;
        this.currentPrice = currentPrice;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.quantitySold = quantitySold;
        this.claimed = claimed;
    }

    public ItemHtmlUnit() {

    }

    public DomAttr getItemLink() {
        return itemLink;
    }

    public void setItemLink(DomAttr o) {
        this.itemLink = o;
    }

    public DomAttr getItemImage() {
        return itemImage;
    }

    public void setItemImage(DomAttr d) {
        this.itemImage = d;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(String quntitySold) {
        this.quantitySold = quntitySold;
    }

    public String getClaimed() {
        return claimed;
    }

    public void setClaimed(String claimed) {
        this.claimed = claimed;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemLink=" + itemLink +
                ", itemImage=" + itemImage +
                ", title='" + title + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", discount='" + discount + '\'' +
                ", quantitySold='" + quantitySold + '\'' +
                ", claimed='" + claimed + '\'' +
                '}';
    }
}
