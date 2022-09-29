package Logout;

import Model.Logout.LogoutModel;
import com.codecool.FileReader;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutTest {
    
    @AfterEach
    public void closeWebDriver(){
        WebDriverService.getInstance().quitWebDriver();
    }


    @Test
    public void successfulLogout(){
        LogoutModel logoutModel = new LogoutModel();
        logoutModel.doLogin();
        logoutModel.waitUntilLoggedIn();

        logoutModel.logout();
        Assertions.assertTrue(logoutModel.getLogoutMsg().contains("You are now logged out. Any automatic login has also been stopped."));
    }
}
