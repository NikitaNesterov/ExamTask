import java.util.Objects;

public class ItemImage {

    private Object Object;

    public ItemImage(java.lang.Object object) {
        Object = object;
    }

    public ItemImage() {
    }

    public java.lang.Object getObject() {
        return Object;
    }

    public void setObject(java.lang.Object object) {
        Object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemImage itemImage = (ItemImage) o;
        return Objects.equals(Object, itemImage.Object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Object);
    }
}
