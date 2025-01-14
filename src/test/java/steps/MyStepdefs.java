package steps;

import businessLayer.TestBL;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.context.TestExecutionContext;
import org.example.runner.Drivers;
import org.example.runner.Runner;


public class MyStepdefs {
    private static final Logger LOGGER = LogManager.getLogger(MyStepdefs.class.getName());
    private final TestExecutionContext testExecutionContext;

    public MyStepdefs(){
        testExecutionContext = Runner.getTestExecutionContext(Thread.currentThread().getId());
    }

    @Given("a test statement")
        public void test() throws InterruptedException {
        LOGGER.info("tests are executing");
        Drivers.createDriver(testExecutionContext);
        new TestBL().basicTestMethod();
        Thread.sleep(2000);
        LOGGER.info("tests execution completed");
    }

    @Given("a second test statement")
    public void aSecondTest() throws InterruptedException {
        LOGGER.info("second set of tests are executing");
        Drivers.createDriver(testExecutionContext);
        new TestBL().basicTestMethod2();
        Thread.sleep(2000);
        LOGGER.info("tests2 execution completed");
    }
}
