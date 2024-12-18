import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleManager {
    private static Locale locale = Locale.getDefault();

    public static void setLocale(Locale newLocale) {
        locale = newLocale;
    }

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle("messages", locale);
    }
}
