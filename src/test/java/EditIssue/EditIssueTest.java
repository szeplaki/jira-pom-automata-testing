package EditIssue;

import Model.EditIssue.EditIssueModel;
import User.UserMethods;
import com.codecool.FileReader;
import com.codecool.RandomHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class EditIssueTest {

    static WebDriver webDriver;
    private static ChromeOptions browserOptions;

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
        UserMethods.login(webDriver);

        webDriver.manage().window().maximize();
        editIssueModel = new EditIssueModel(webDriver);
    }

    @AfterEach
    public static void closeWebDriver() {
        webDriver.quit();
    }



    @Test
    public void successfulEditIssue(){
        JavascriptExecutor jse = (JavascriptExecutor)webDriver;
        webDriver.get("https://jira-auto.codecool.metastage.net/browse/MTP-2245");

        Assertions.assertTrue(editIssueModel.getIssueID().contains("MTP-2245"));

        jse.executeScript("arguments[0].click()", editIssueModel.getEditBtn());

        RandomHelper.waitUntilVisibleOrClickable(webDriver, "xpath", "//*[@id=\"edit-issue-dialog\"]/header/h2");
        Assertions.assertTrue(editIssueModel.getEditModelTitle().contains("Edit Issue : MTP-2245"));

        editIssueModel.setModalSummaryField("Allopenissues");

        editIssueModel.clickUpdateBtn();

        RandomHelper.waitUntilVisibleOrClickable(webDriver, "id", "summary-val");

        webDriver.navigate().refresh();

        jse.executeScript("arguments[0].click()", editIssueModel.getEditBtn());

        RandomHelper.waitUntilVisibleOrClickable(webDriver, "xpath", "//*[@id=\"edit-issue-dialog\"]/header/h2");

        editIssueModel.setModalSummaryField("Jira Test Project");

        editIssueModel.clickUpdateBtn();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueIds.csv")
    public void editIssueWithSpecificId(String issueId){
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/browse/%s", issueId));
        Assertions.assertDoesNotThrow(() -> webDriver.findElement(By.id("edit-issue")));
    }
}
