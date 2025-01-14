package org.example.listener;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.context.SessionContext;
import org.example.context.TestExecutionContext;
import org.example.entities.Platform;
import org.example.reporters.ReportPortalReporter;
import org.example.runner.Drivers;
import org.example.runner.Runner;

import java.util.HashMap;
import java.util.Map;

public class CucumberPlatformEventListener implements ConcurrentEventListener {
    private static final Logger LOGGER = LogManager.getLogger(
            CucumberPlatformEventListener.class.getName());
    private final ReportPortalReporter reportPortalReporter;

    private final Platform platform = Runner.getPlatform();

    private final Map<String, Integer> scenarioRunCounts = new HashMap<>();

    public CucumberPlatformEventListener() {
        LOGGER.info("CucumberPlatformScenarioListener");
        reportPortalReporter = ReportPortalReporter.getReportPortalReporter();
    }
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::testRunStartedHandler);
        publisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStartedHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinishedHandler);
        publisher.registerHandlerFor(TestRunFinished.class, this::testRunFinishedHandler);

    }

    private void testRunStartedHandler(TestRunStarted event) {
        LOGGER.info("testRunStartedHandler for platform :" + platform);
    }

    private void testCaseStartedHandler(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        LOGGER.info(String.format(
                "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   TEST-CASE  -- %s  " +
                        "STARTED   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$",
                scenarioName));

        Integer scenarioRunCount = getScenarioRunCount(scenarioName);
        LOGGER.info("Test Case Started - " + scenarioName);
        TestExecutionContext context = new TestExecutionContext(scenarioRunCount + "-" + scenarioName);
        LOGGER.info("test name " + context.getTestName());
    }
    private void testCaseFinishedHandler(TestCaseFinished event) {
        String scenarioName = event.getTestCase().getName();
        LOGGER.info("testCaseFinishedHandler Name: " + scenarioName +" ,Platform: "+platform);
        LOGGER.info("testCaseFinishedHandler Result: " + event.getResult().getStatus().toString());
        LOGGER.info(
                "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   TEST-CASE  -- " + scenarioName + "  ENDED   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        long threadId = Thread.currentThread().getId();
        TestExecutionContext context = SessionContext.getTestExecutionContext(threadId);
        Drivers.closeDriver(context);
    }

    private void testRunFinishedHandler(TestRunFinished event) {
        LOGGER.info("testRunFinishedHandler: " + event.getResult().toString()+" ,Platform: "+platform);
        reportPortalReporter.printReportPortalUrl();
    }

    private Integer getScenarioRunCount(String scenarioName) {
        scenarioRunCounts.put(scenarioName, scenarioRunCounts.getOrDefault(scenarioName, 1));
        return scenarioRunCounts.get(scenarioName);
    }
}