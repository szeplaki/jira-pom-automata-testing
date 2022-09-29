package CreateIssue;

import Model.BrowseIssue.BrowseIssueModel;
import Model.CreateIssue.CreateIssueModel;
import com.codecool.FileReader;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.UUID;

public class CreateIssueTest {
    private CreateIssueModel createIssueModel;

//    @BeforeAll
//    public static void setProperty() {
//        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
//    }

//    public void openNewTab() {
//        ChromeOptions browserOptions = new ChromeOptions();
  //      browserOptions.addArguments("--incognito");
    //    webDriver = new ChromeDriver(browserOptions);

//        webDriver.manage().window().maximize();
      //  createIssueModel  = new CreateIssueModel();
        //createIssueModel.doLogin();
//    }

@AfterEach
public void closeTab(){
    WebDriverService.getInstance().quitWebDriver();
}

    @Test
    public void createIssueGeneralTest()
    {
        String expectedSummary = UUID.randomUUID().toString();
        String expectedProjectKey = "MTP";
        String expectedIssueType = "Story";

        createIssueModel.openCreateIssueModal();
        createIssueModel.selectProject(expectedProjectKey);
        String modalProject =  createIssueModel.getProjectFieldValue();
        createIssueModel.selectIssueType(expectedIssueType);
        String modalIssueType = createIssueModel.getIssueFieldValue();

        Assertions.assertTrue(modalProject.contains(expectedProjectKey));
        Assertions.assertEquals(expectedIssueType,modalIssueType);

        createIssueModel.setSummary(expectedSummary);

        createIssueModel.submitIssue();
        createIssueModel.openSubmittedIssue();

        BrowseIssueModel browseIssueModel = new BrowseIssueModel();

        String actualType = browseIssueModel.getIssueType();
        String actualSummary = browseIssueModel.getSummary();
        String actualKey = browseIssueModel.getProjectKey();

        browseIssueModel.deleteIssue();

        Assertions.assertEquals(expectedIssueType,actualType);
        Assertions.assertEquals(expectedSummary, actualSummary);
        Assertions.assertTrue(actualKey.contains(expectedProjectKey));
    }

    @Test
    public void createIssueEmptySummary()
    {
        createIssueModel.openCreateIssueModal();
        createIssueModel.submitIssue();

        final String[] errorMessage = new String[1];
        Assertions.assertDoesNotThrow(() -> {
            errorMessage[0] = createIssueModel.getSummaryErrorMessage();
        });

        Assertions.assertTrue(errorMessage[0].contains("You must specify a summary of the issue"));
    }



    @ParameterizedTest
    @CsvFileSource(resources = "/createTests.csv", numLinesToSkip = 1)
    public void specificIssueCreateTest(String expectedProjectKey, String expectedIssueType)
    {
        createIssueModel.openCreateIssueModal();

        createIssueModel.selectProject(expectedProjectKey);
        String actualProject = createIssueModel.getProjectFieldValue();
        createIssueModel.selectIssueType(expectedIssueType);
        String actualIssueType = createIssueModel.getIssueFieldValue();

        Assertions.assertTrue(actualProject.contains(expectedProjectKey));
        Assertions.assertEquals(expectedIssueType,actualIssueType);
    }
}
