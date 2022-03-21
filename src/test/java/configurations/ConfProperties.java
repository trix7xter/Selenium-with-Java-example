package configurations;

import java.util.ResourceBundle;

public class ConfProperties {
    private static ConfProperties instance;
    private ResourceBundle rb;

    private ConfProperties() {
        rb = ResourceBundle.getBundle("conf");
    }

    public static ConfProperties  getConfProperties() {
        if (instance == null) {
            instance = new ConfProperties();
        }
        return instance;
    }

    public String getProperty(String key) {
        if (System.getenv(key) == null){
            return rb.getString(key);
        }else {
            return System.getenv(key);
        }
    }
}
