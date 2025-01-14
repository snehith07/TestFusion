package org.example.runner;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomReports {
    static final String LOG_DIR = "LOG_DIR";
    private static final Logger LOGGER = LogManager.getLogger(CustomReports.class.getName());

    static Reportable generateReport() {
        String reportsDir = System.getProperty("user.dir") + File.separator + Setup.getFromConfigs(
                LOG_DIR);
        LOGGER.info(
                "================================================================================================");
        LOGGER.info(String.format("Generating reports here: '%s'", reportsDir));
        LOGGER.info(
                "================================================================================================");
        List<String> jsonPaths = processTestResultJsonFiles(reportsDir);

        Configuration config = createCucumberReportsConfiguration(reportsDir);

        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        Reportable overviewReport = reportBuilder.generateReports();
        String generatedReportsMessage = String.format(
                "Reports available here: file://%s/cucumber-html-reports/overview-features.html",
                config.getReportDirectory().getAbsolutePath());
        LOGGER.info(generatedReportsMessage);
        return overviewReport;
    }
    private static List<String> processTestResultJsonFiles(String reportsDir) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(reportsDir), new String[]{"json"}, true);
        LOGGER.info(String.format("\tFound '%s' result files for processing", jsonFiles.size()));
        if(jsonFiles.isEmpty()) {
            LOGGER.info("Reports not generated");
        }
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> {
            LOGGER.info(String.format("\tProcessing result file: %s", file.getAbsolutePath()));
            jsonPaths.add(file.getAbsolutePath());
        });
        return jsonPaths;
    }
    private static Configuration createCucumberReportsConfiguration(String reportsDir) {
        File richReportsPath = new File(reportsDir + File.separator + "reports");
        LOGGER.info(String.format("\tCreating rich reports: %s", richReportsPath));
        Configuration config = new Configuration(richReportsPath, Setup.getFromConfigs("APP_NAME"));
        return config;
    }
}
