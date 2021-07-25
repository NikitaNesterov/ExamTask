public class UrlBuilder {

    final String callBack = "jQuery183036233163592076556_1626925634436";
    final String widgetId = "5547572";
    final String platform = "12";
    final String limit = "12";
    private int offSet = 0;
    final String phase = "1";
    final String postBack = "614f97c3-8d66-440b-a144-48b33fb9b377";
    StringBuilder stringBuilder;


    public UrlBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int number) {
        this.offSet -= number;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public StringBuilder getUrl() {
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


}
