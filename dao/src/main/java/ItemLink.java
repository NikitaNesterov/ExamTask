import com.gargoylesoftware.htmlunit.html.DomAttr;

import java.util.Objects;

public class ItemLink {

    private DomAttr object;

    public ItemLink(DomAttr object) {
        this.object = object;
    }

    public ItemLink() {
    }

    public DomAttr getObject() {
        return object;
    }

    public void setObject(DomAttr object) {
        this.object = object;
    }


}
