package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.context.SessionContext;
import org.example.context.TestExecutionContext;
import org.example.runner.Drivers;

public class Hooks {
    private static final Logger LOGGER = LogManager.getLogger(Hooks.class.getName());
    @Before
    public void beforeScenario(){

    }
    @After
    public void afterScenario(){
        LOGGER.info("in after scenario");
    }
}
