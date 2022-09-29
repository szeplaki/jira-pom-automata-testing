package EditIssue;

import Model.EditIssue.EditIssueModel;
import com.codecool.FileReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditIssueTest {

    static WebDriver webDriver;
    private static ChromeOptions browserOptions;
    private WebDriverWait driverWait;
    private EditIssueModel editIssueModel;

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

        webDriver.manage().window().maximize();
        editIssueModel = new EditIssueModel(webDriver);
        editIssueModel.doLogin();
    }

    @AfterEach
    public void closeWebDriver() {
        webDriver.quit();
    }



    @Test
    public void successfulEditIssue(){
        JavascriptExecutor jse = (JavascriptExecutor)webDriver;
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/browse/MTP-2245");

        Assertions.assertTrue(editIssueModel.getIssueID().contains("MTP-2245"));

        jse.executeScript("arguments[0].click()", editIssueModel.getEditBtn());

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"edit-issue-dialog\"]/header/h2")));
        Assertions.assertTrue(editIssueModel.getEditModelTitle().contains("Edit Issue : MTP-2245"));

        editIssueModel.setModalSummaryField("Allopenissues");

        editIssueModel.clickUpdateBtn();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-val")));

        webDriver.navigate().refresh();

        jse.executeScript("arguments[0].click()", editIssueModel.getEditBtn());

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"edit-issue-dialog\"]/header/h2")));

        editIssueModel.setModalSummaryField("Jira Test Project");

        editIssueModel.clickUpdateBtn();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueIds.csv")
    public void editIssueWithSpecificId(String issueId){
        webDriver.get(String.format(FileReader.getValueByKey("jira.baseurl") + "/browse/%s", issueId));
        Assertions.assertDoesNotThrow(() -> editIssueModel.clickEditBtn());
    }
}
