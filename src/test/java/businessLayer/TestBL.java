package businessLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screen.HomeScreen;

public class TestBL {
    private Logger LOGGER = LogManager.getLogger(TestBL.class.getName());
    public TestBL(){
    }

    public TestBL basicTestMethod() {
        LOGGER.info("write business logic like flow of scenario, assertions... and call screen methods");
        HomeScreen.get().testScreenMethod();

        return this;
    }
    public TestBL basicTestMethod2() {
        LOGGER.info("write business logic like flow of scenario, assertions... and call screen methods");
            HomeScreen.get().testScreenMethod2();
        return this;
    }

}
