import java.util.ListResourceBundle;

public class MyResource_pl extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][]{
                {"authorTitle", "Autorzy"},
                {"authorName1", "Aleksander Gencel"},
                {"authorName2", "Bartosz Żelaziński"}
        };
    }
}
