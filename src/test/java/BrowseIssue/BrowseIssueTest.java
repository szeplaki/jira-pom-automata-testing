package BrowseIssue;

import Model.BrowseIssue.BrowseIssueModel;
import User.UserMethods;
import com.codecool.FileReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowseIssueTest {
    static WebDriver webDriver;
    private static ChromeOptions browserOptions;
    private WebDriverWait driverWait;
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
        driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        UserMethods.login(webDriver);

        webDriver.manage().window().maximize();
        browseIssueModel = new BrowseIssueModel(webDriver);
    }

    @AfterEach
    public void closeWebDriver() {
        webDriver.quit();
    }



    @Test
    public void browseExistingIssue() {
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/browse/MTP-2253");
        Assertions.assertEquals("MTP-2253", browseIssueModel.getIssueId());
    }

    @Test
    public void checkPossibilityOfBrowsing() {
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/issues/?jql=");
        browseIssueModel.getSearchField().click();
        browseIssueModel.getSearchField().sendKeys("Jira Test Project");
        browseIssueModel.getSearchButton().click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("key-val")));
        Assertions.assertEquals("MTP-2245", browseIssueModel.getIssueId());
    }

    @Test
    public void browseNonExistingIssue() {
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/browse/MTP-99999999999");
        Assertions.assertEquals("You can't view this issue", browseIssueModel.getErrorMessageField());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueIds.csv")
    public void browseIssueWithSpecificId(String issueId) {

        webDriver.get(String.format(FileReader.getValueByKey("jira.baseurl") + "/browse/%s", issueId));
        Assertions.assertDoesNotThrow(() -> browseIssueModel.getIssueId());
        Assertions.assertEquals(issueId, browseIssueModel.getIssueId());
    }
}
