package org.example.runner;

import io.cucumber.core.cli.Main;
import net.masterthought.cucumber.Reportable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.context.SessionContext;
import org.example.context.TestExecutionContext;
import org.example.entities.Platform;
import org.example.exceptions.InvalidTestDataException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());
    static final String REPORTS_DIR = "reports";
    private static final String CucumberReportListener = "org.example.listener.CucumberPlatformEventListener";
    private static final String ReportPortalListener = "org.example.listener.ReportPortalScenarioListener";



    public Runner(String configFilePath, String steps, String features) {
        Path path = Paths.get(configFilePath);
        if (!Files.exists(path)) {
            throw new InvalidTestDataException(
                    String.format("Invalid path ('%s') provided for config", configFilePath));
        }
        Setup.load(configFilePath);
        run(steps, features);
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        new Runner(args[0], args[1], args[2]);
    }

    public static String getBrowser() {
        return Setup.getFromConfigs(Setup.BROWSER);
    }

    private void run(String stepDefsDir, String featuresDir) {
        List<String> args = new ArrayList<>();
        args.add("--threads");
        args.add(String.valueOf(2));
        args.add("--tags");
        LOGGER.info("TAGS property: " + System.getenv("TAGS"));
        args.add(System.getenv("TAGS"));
        args.add("--glue");
        args.add(stepDefsDir);
        args.add(featuresDir);
        args.add("--plugin");
        args.add(CucumberReportListener);

        // cucumber reporting
        args.add("--plugin");
        args.add("pretty");
        String logDir = Setup.getFromConfigs("LOG_DIR");
        args.add("--plugin");
        args.add("json:" + logDir + File.separator + REPORTS_DIR + File.separator + "cucumber-json-report.json");

        // report portal reporting
        args.add("--plugin");
        args.add(ReportPortalListener);


        LOGGER.info("Begin running tests with args: {}", args);
        String[] array = args.toArray(String[]::new);
        try {
            byte status = Main.run(array);
            Reportable overviewReport = CustomReports.generateReport();
            int totalFeatures = overviewReport.getFeatures();
            int totalScenarios = overviewReport.getScenarios();
            int failedScenarios = overviewReport.getFailedScenarios();
            int passedScenarios = overviewReport.getPassedScenarios();
            LOGGER.info("Total features: {}", totalFeatures);
            LOGGER.info("Total scenarios: {}", totalScenarios);
            LOGGER.info("Passed scenarios: {}", passedScenarios);
            LOGGER.info("Failed scenarios: {}", failedScenarios);
            LOGGER.info("Execution status: {}", status);
            System.exit(status);
        }
        catch (Exception e){
            LOGGER.error("EXCEPTION: {}", e.getMessage());
            System.exit(1);
        }
    }

    public static Platform getPlatform() {
        return Setup.getPlatform();
    }

    public static TestExecutionContext getTestExecutionContext(long threadId) {
        return SessionContext.getTestExecutionContext(threadId);
    }
}