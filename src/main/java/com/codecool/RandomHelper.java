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

    /**
     * Method will wait for element to be visible for 15 second until proceed.
     * @param webDriver webDriver you are currently using.
     * @param elementType should be a string: id, xpath, className or css or click. If you give anything else
     *                    will default wait 15 seconds.
     * @param elementId the element name you are looking for.
     */
    public static void waitUntilVisibleOrClickable(WebDriver webDriver, String elementType, String elementId){
        WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

        switch (elementType){
            case "id":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
                break;
            case "xpath":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementId)));
                break;
            case "className":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(elementId)));
                break;
            case "css":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementId)));
                break;
            case "click":
                driverWait.until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
                break;
            default:
                RandomHelper.Wait(webDriver);
                break;
        }
    }
}
