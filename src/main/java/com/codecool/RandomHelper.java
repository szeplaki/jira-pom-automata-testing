package com.codecool;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RandomHelper {
    public static void Wait(WebDriver webDriver){
        synchronized (webDriver){
            try {
                webDriver.wait(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void waitUntilVisible(WebDriver webDriver, String elementType, String elementId){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

        switch (elementType){
            case "id":
                wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.id(elementId))));
                break;
            case "xpath":
                wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath(elementId))));
                break;
            case "className":
                wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.className(elementId))));
                break;
            case "css":
                wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.cssSelector(elementId))));
                break;
            default:
                RandomHelper.Wait(webDriver);
        }
    }
}
