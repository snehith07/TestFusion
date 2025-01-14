package org.example.reporters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.example.listener.ReportPortalScenarioListener.launchUUID;

public class ReportPortalReporter {
    private final static Logger LOGGER = LogManager.getLogger(ReportPortalReporter.class);
    private final Properties reportPortalProperties;
    private static ReportPortalReporter reportPortalReporter;

    private ReportPortalReporter() {
        reportPortalProperties = loadReportPortalProperties();
    }

    public static ReportPortalReporter getReportPortalReporter() {
        if (reportPortalReporter == null) {
            reportPortalReporter = new ReportPortalReporter();
        }
        return reportPortalReporter;
    }

    private Properties loadReportPortalProperties() {
        Properties properties = new Properties();
        try {
            String reportPortalPropertiesFile = "src/test/resources/reportportal.properties";
            LOGGER.info("Using reportportal.properties file from "
                    + reportPortalPropertiesFile);
            File reportPortalFile = new File(reportPortalPropertiesFile);
            String absolutePath = reportPortalFile.getAbsolutePath();
            if (reportPortalFile.exists()) {
                properties.load(Files.newInputStream(Paths.get(absolutePath)));
                LOGGER.info("Loaded reportportal.properties file - " + absolutePath);
            } else {
                LOGGER.info("reportportal.properties file NOT FOUND - " + absolutePath);
            }

        } catch (IOException e) {
            LOGGER.info("ERROR in loading reportportal.properties file\n" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return properties;
    }

    public void printReportPortalUrl() {
        boolean isReportPortalEnabledInProperties =
                (null == reportPortalProperties.getProperty("rp.enable")
                        || (reportPortalProperties.getProperty("rp.enable")
                        .equalsIgnoreCase("true")));
        if (!isReportPortalEnabledInProperties) {
            LOGGER.warn("Report portal is disabled");
            return;
        }
        String rpLaunchId = launchUUID;
        String rpUrl = String.format("%s/ui/#%s/launches/all/%s",
                reportPortalProperties.getProperty("rp.endpoint"),
                reportPortalProperties.getProperty("rp.project"),
                rpLaunchId);
        LOGGER.info(String.format("Report portal url : %s", rpUrl));
    }
}
