package org.example.runner;

import org.openqa.selenium.WebDriver;

public class Driver {
    private final WebDriver driver;

    Driver(WebDriver webDriver){
        this.driver = webDriver;
    }
    public WebDriver getInnerDriver(){
        return this.driver;
    }

    public void waitFor(int milliSecs){
        try {
            Thread.sleep(milliSecs);
        } catch (InterruptedException ie){
            throw new RuntimeException(ie);
        }

    }
}
