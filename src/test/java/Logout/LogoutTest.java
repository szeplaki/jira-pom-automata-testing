package Logout;

import Model.Logout.LogoutModel;
import User.UserMethods;
import com.codecool.FileReader;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutTest {
    static WebDriver webDriver;
    private static ChromeOptions browserOptions;
    private WebDriverWait driverWait;

    @BeforeAll
    public static void setProperty(){
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
    }

    @BeforeEach
    public void openNewTab(){
        browserOptions = new ChromeOptions();
        browserOptions.addArguments("--incognito");
        webDriver = new ChromeDriver(browserOptions);
        this.driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
        webDriver.manage().window().maximize();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-form-submit")));
    }

    @AfterEach
    public void closeWebDriver(){ webDriver.quit(); }


    @Test
    public void successfulLogout(){
        UserMethods.login(webDriver);
        LogoutModel logoutModel = new LogoutModel(webDriver);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));

        logoutModel.logout();
        Assertions.assertTrue(logoutModel.getLogoutMsg().contains("You are now logged out. Any automatic login has also been stopped."));
    }
}
