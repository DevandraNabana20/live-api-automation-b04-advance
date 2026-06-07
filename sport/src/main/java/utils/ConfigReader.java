package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

    //Method untuk mengambil properties
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Failed to load config.properties: " + CONFIG_PATH, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static synchronized void setProperty(String key, String value) {
        properties.setProperty(key, value);

        try (FileOutputStream fos = new FileOutputStream(CONFIG_PATH)) {
            properties.store(fos, null);
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Failed to save config.properties: " + CONFIG_PATH, e);
        }
    }
}
