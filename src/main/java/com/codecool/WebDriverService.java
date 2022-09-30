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
        return createWebDriver(FileReader.getValueByKey("browser.type"));
    }

    public void quitWebDriver()
    {
        webDriver.quit();
        webDriver = null;
    }
    private WebDriver createWebDriver(String browserType) {
        if ("firefox".equals(browserType)) {
            webDriver = new FirefoxDriver();
        } else {
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }
}
