package BrowseIssue;

import Model.BrowseIssue.BrowseIssueModel;
import User.UserMethods;
import com.codecool.FileReader;
import com.codecool.RandomHelper;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowseIssueTest {
    static WebDriver webDriver;
    private static ChromeOptions browserOptions;

    private BrowseIssueModel browseIssueModel;

    @BeforeAll
    public static void setProperty() {
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
    }

    @BeforeEach
    public void openNewTab() {
        browserOptions = new ChromeOptions();
        browserOptions.addArguments("--incognito");
        webDriver = new ChromeDriver(browserOptions);
        UserMethods.login(webDriver);

        webDriver.manage().window().maximize();
        browseIssueModel = new BrowseIssueModel(webDriver);
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
    public void browseExistingIssue() {
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-2253");
        Assertions.assertEquals("MTP-2253",browseIssueModel.getIssueId());
    }

    @Test
    public void checkPossibilityOfBrowsing(){
        webDriver.get("https://jira-auto.codecool.metastage.net/issues/?jql=");
        browseIssueModel.getSearchField().click();
        browseIssueModel.getSearchField().sendKeys("Jira Test Project");
        browseIssueModel.getSearchButton().click();
        RandomHelper.waitUntilVisibleOrClickable(webDriver, "id", "key-val");
        Assertions.assertEquals("MTP-2245", browseIssueModel.getIssueId());
    }
}