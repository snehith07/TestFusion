package screen;

import org.example.entities.Platform;
import org.apache.commons.lang3.NotImplementedException;
import org.example.runner.Driver;
import org.example.runner.Drivers;
import org.example.runner.Runner;
import screen.web.HomeScreenWeb;


public abstract class HomeScreen {
    private static String SCREEN_NAME = HomeScreen.class.getSimpleName();
    public static HomeScreen get(){
        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
        Platform platform = Runner.getPlatform();

        switch (platform){
            case web -> {
                return new HomeScreenWeb(driver);
            }
        }
        throw new NotImplementedException(
                SCREEN_NAME + " is not implemented in " + Platform.android);
    }

    public abstract HomeScreen testScreenMethod();

    public abstract HomeScreen testScreenMethod2();
}
