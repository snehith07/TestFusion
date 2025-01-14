package org.example.runner;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.context.TestExecutionContext;
import org.example.entities.Platform;
import org.example.entities.TEST_CONTEXT;
import org.example.exceptions.InvalidTestDataException;
import org.example.tools.JsonFile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class Drivers {
    private static Logger LOGGER = LogManager.getLogger(Drivers.class.getName());
    private static int count = 0;

    public static Driver createDriver(TestExecutionContext context){
        return createDriver(Runner.getPlatform(), context);
    }


    private static Driver createDriver(Platform platform, TestExecutionContext context){
        String browserName = Runner.getBrowser();
        Driver currentDriver = null;
        if (platform.equals(Platform.web)){
            currentDriver = createWebDriver(browserName, context);
            Setup.loadBaseURLForWeb(Setup.BASE_URL_FOR_WEB, currentDriver);
        }
        if (currentDriver==null)
            throw new InvalidTestDataException(platform + " does not exist");
        LOGGER.info(String.format("Driver_%s_%s created for platfrom", currentDriver, ++count));
        return currentDriver;
    }

    private static Driver createWebDriver(String browserName, TestExecutionContext context){
        WebDriver driver;
        switch (browserName.toLowerCase()){
            case "chrome" ->{
                driver = createWebDriverChrome(browserName);
            }
            default -> throw new NotImplementedException(String.format("driver creation not implemented for browser: %s", browserName));
        }
        Driver currentDriver = new Driver(driver);
        context.addTestState(TEST_CONTEXT.CURRENT_DRIVER, currentDriver);
        LOGGER.info("Driver created for test - " + context.getTestName());
        return currentDriver;

//        WebDriverFactory webDriverFactory = new WebDriverFactory();
//        driver = webDriverFactory.getDriver(browserName);
    }

    private static WebDriver createWebDriverChrome(String browserName){
        Map<String, Map> browserConfigs = JsonFile.getNodeValueAsMapFromJsonFile(Setup.BROWSER_CONFIG_FILE, browserName);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBrowserVersion(String.valueOf(browserConfigs.get("browserVersion")));
        WebDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    public static void closeDriver(TestExecutionContext context){
        Driver driver = (Driver) context.getTestState(TEST_CONTEXT.CURRENT_DRIVER);
        driver.getInnerDriver().quit();
    }

    public static Driver getCurrentDriver(long threadId) {
        TestExecutionContext context = Runner.getTestExecutionContext(threadId);
        return (Driver) context.getTestState(TEST_CONTEXT.CURRENT_DRIVER);
    }

//    public static Driver getDriverForCurrentUser(long threadId) {
//        TestExecutionContext context = getTestExecutionContext(threadId);
//        String userPersona = context.getTestStateAsString(TEST_CONTEXT.CURRENT_USER_PERSONA);
//        UserPersonaDetails userPersonaDetails = getUserPersonaDetails(context);
//
//        if(!userPersonaDetails.isDriverAssignedForUser(userPersona)) {
//            LOGGER.info(
//                    "getDriverForUser: Drivers available for userPersonas: " + userPersonaDetails.getAllUserPersonasForAssignedDrivers());
//            throw new InvalidTestDataException(
//                    String.format(NO_DRIVER_FOUND_FOR_USER_PERSONA, userPersona));
//        }
//
//        return userPersonaDetails.getDriverAssignedForUser(userPersona);
//        return drivers.get(keyForCurrentThread(userPersona));
//    }

}
