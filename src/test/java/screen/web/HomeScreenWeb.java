package screen.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.runner.Driver;
import org.openqa.selenium.By;
import screen.HomeScreen;

public class HomeScreenWeb extends HomeScreen {
    private final Driver driver;
    private static final Logger LOGGER = LogManager.getLogger(HomeScreenWeb.class.getName());

    public HomeScreenWeb(Driver driver){
        this.driver = driver;
    }

    @Override
    public HomeScreen testScreenMethod() {
        driver.getInnerDriver().get("https://www.flipkart.com");
        driver.waitFor(2000);
        driver.getInnerDriver().findElement(By.xpath("//input[@type='text']")).sendKeys("whey protein");
        driver.waitFor(2000);

        driver.getInnerDriver().findElement(By.xpath("(//a[contains(@href,'search')])[1]")).click();
        return this;
    }

    @Override
    public HomeScreen testScreenMethod2() {
        driver.getInnerDriver().get("https://www.amazon.com");
        driver.waitFor(2000);
//        driver.getInnerDriver().findElement(By.className("_yb_15atr0o  _yb_gy0mix undefined")).click();
        return this;
    }

}
