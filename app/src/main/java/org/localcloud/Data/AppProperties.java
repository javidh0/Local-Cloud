package org.localcloud.Data;

import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static Properties properties;

    public static void loadProperties()
    {
        properties = new Properties();
        try (InputStream input = AppProperties.class.getClassLoader().getResourceAsStream("app.properties")) {
            if(input == null) {
                throw new RuntimeException("app.properties not found!");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
