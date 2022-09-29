package com.codecool;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverService {
    private static final WebDriverService INSTANCE = new WebDriverService();
    private WebDriver webDriver;

    public static WebDriverService getInstance() {
        return INSTANCE;
    }

    public WebDriver getWebDriver() {
        if (webDriver != null) return webDriver;
        return createWebDriver();
    }

    public void quitWebDriver()
    {
        webDriver.quit();
    }
    private WebDriver createWebDriver() {
        String browserType = FileReader.getValueByKey("browser.type");
        String driverLocation = FileReader.getValueByKey("driver.location");
        String extension = System.getProperty("os.name").contains("Windows") ? ".exe" : "";

        switch(browserType) {
            case "chrome":
                if(driverLocation != null) System.setProperty("webdriver.chrome.driver",driverLocation+"chromedriver" + extension);
                webDriver =new ChromeDriver();
                break;
            case "firefox":
                if(driverLocation != null) System.setProperty("webdriver.chrome.driver",driverLocation+"geckodriver" + extension);
                webDriver =  new FirefoxDriver();
                break;
            default:
                throw new RuntimeException(new Exception("Unknown browser type: " + browserType));
        }
        return webDriver;
    }
}
