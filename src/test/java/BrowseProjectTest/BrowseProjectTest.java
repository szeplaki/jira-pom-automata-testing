package BrowseProjectTest;

import Model.BrowseProjectModel;
import Model.LoginPageModel;
import Model.LogoutModel;
import com.codecool.FileReader;
import com.codecool.RandomHelper;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowseProjectTest {

    static WebDriver webDriver;
    private static ChromeOptions browserOptions;

    @BeforeAll
    public static void setProperty() {
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
    }

    @BeforeEach
    public void openNewTab() {
        browserOptions = new ChromeOptions();
        browserOptions.addArguments("--incognito");
        webDriver = new ChromeDriver(browserOptions);

        webDriver.get("https://jira-auto.codecool.metastage.net/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
        webDriver.manage().window().maximize();
        RandomHelper.Wait(webDriver);
    }

    @AfterEach
    public void closeTab() {
        webDriver.close();
    }

    @AfterAll
    public static void closeWebDriver() {
        webDriver.quit();
    }



    @Test
    public void browseProject() {
        //TODO wait for TOUCAN, JETI, COALA parametrization
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);
        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        RandomHelper.Wait(webDriver);
        webDriver.get("https://jira-auto.codecool.metastage.net/projects/MTP/summary");
        BrowseProjectModel browseProjectModel = new BrowseProjectModel(webDriver);

        Assertions.assertTrue(browseProjectModel.getProjectKey().contains("MTP"));
    }
}

