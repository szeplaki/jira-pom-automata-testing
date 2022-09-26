package com.codecool;

import org.openqa.selenium.WebDriver;

public class RandomHelper {
    // TODO this is the old wait, Zsoli wants to replace it
    public static void Wait(WebDriver webDriver){
        synchronized (webDriver){
            try {
                webDriver.wait(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
