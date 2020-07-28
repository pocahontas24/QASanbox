package util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static final String ADMIN_PROPERTIES = "admin.properties";

    public Properties properties;

    public PropertiesUtil(String file) throws IOException {
        properties = new Properties();
        properties.load(getClass().getResourceAsStream("/" + file));
    }

    public String getValue(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

    public String setValue(String propertyKey, String propertyValue) {
        return (String) properties.setProperty(propertyKey, propertyValue);
    }

}
