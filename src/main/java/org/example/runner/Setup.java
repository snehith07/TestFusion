package org.example.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.Platform;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Setup {
    private static final Logger LOGGER = LogManager.getLogger(Setup.class.getName());
    public static String BROWSER_CONFIG_FILE = "BROWSER_CONFIG_FILE";
    public static String BASE_URL_FOR_WEB = "BASE_URL_FOR_WEB";
    static final String BROWSER = "BROWSER";
    private static Platform currentPlatform = Platform.web;
    public static Map<String, String> configs = new HashMap<>();
    private static String configFilePath;

    private Setup() {}

    public static Map<String, String> load(String providedConfigFilePath) {
        configFilePath = providedConfigFilePath;
        if (configs.isEmpty()) {
            configs = loadProperties();
            return configs;
        } else {
            return configs;
        }
    }

    private static Map<String, String> loadProperties() {
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(System.getenv("CONFIG"));
            properties.load(fis);
            for (String key : properties.stringPropertyNames()) {
                configs.put(key, properties.getProperty(key));
            }
            setConfigVariables(configs);
            fis.close();
            return configs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setConfigVariables(Map<String, String> propertiesMap){
        BROWSER_CONFIG_FILE = propertiesMap.get(Setup.BROWSER_CONFIG_FILE);
        currentPlatform = Platform.valueOf(propertiesMap.get("PLATFORM"));
    }

    static Platform getPlatform() {
        return currentPlatform;
    }

    static String getFromConfigs(String key) {
        return configs.get(key);
    }

    static void loadBaseURLForWeb(String key, Driver driver) {
        String baseURL = getFromConfigs(key);
        LOGGER.info("base URL:" + baseURL);
        driver.getInnerDriver().get(baseURL);
    }
}

