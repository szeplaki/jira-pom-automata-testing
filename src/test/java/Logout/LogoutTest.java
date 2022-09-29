package Logout;

import Model.Login.LoginPageModel;
import Model.Logout.LogoutModel;
import User.UserMethods;
import com.codecool.FileReader;
import com.codecool.RandomHelper;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LogoutTest {
    static WebDriver webDriver;
    private static ChromeOptions browserOptions;

    @BeforeAll
    public static void setProperty(){
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
    }

    @BeforeEach
    public void openNewTab(){
        browserOptions = new ChromeOptions();
        browserOptions.addArguments("--incognito");
        webDriver = new ChromeDriver(browserOptions);

        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
        webDriver.manage().window().maximize();
        RandomHelper.Wait(webDriver);
    }

    @AfterEach
    public void closeTab(){ webDriver.close(); }

    @AfterAll
    public static void closeWebDriver(){ webDriver.quit(); }

    @Test
    public void successfulLogout(){
        UserMethods.login(webDriver);
        LogoutModel logoutModel = new LogoutModel(webDriver);
        RandomHelper.Wait(webDriver);

        logoutModel.logout();
        Assertions.assertTrue(logoutModel.getLogoutMsg().contains("You are now logged out. Any automatic login has also been stopped."));
    }
}
