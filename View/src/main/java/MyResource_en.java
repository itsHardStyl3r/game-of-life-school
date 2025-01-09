import java.util.ListResourceBundle;

public class MyResource_en extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][]{
                {"authorTitle", "Authors"},
                {"authorName1", "Aleksander Gencel"},
                {"authorName2", "Bartosz Zelazinski"}
        };
    }
}

