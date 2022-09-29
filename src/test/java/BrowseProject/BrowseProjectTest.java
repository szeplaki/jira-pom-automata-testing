package BrowseProject;

import Model.BrowseProject.BrowseProjectModel;
import com.codecool.FileReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowseProjectTest {
    static WebDriver webDriver;
    private static ChromeOptions browserOptions;
    private BrowseProjectModel browseProjectModel;

    @BeforeAll
    public static void setProperty() {
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
    }

    @BeforeEach
    public void openNewTab() {
        browserOptions = new ChromeOptions();
        browserOptions.addArguments("--incognito");
        webDriver = new ChromeDriver(browserOptions);
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
        webDriver.manage().window().maximize();
        browseProjectModel = new BrowseProjectModel();
        browseProjectModel.doLogin();
    }

    @AfterEach
    public void closeWebDriver() {
        webDriver.quit();
    }


    @ParameterizedTest
    @ValueSource(strings = {"MTP", "JETI", "TOUCAN", "COALA"})
    public void browseProject(String projectType) {
        webDriver.get(String.format(FileReader.getValueByKey("jira.baseurl") + "/projects/%s/summary", projectType));


        Assertions.assertTrue(browseProjectModel.getProjectKey().contains(projectType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"DUMMYDATA"})
    public void browseNonExistingProject(String projectType) {
        webDriver.get(String.format(FileReader.getValueByKey("jira.baseurl") + "/projects/%s/summary", projectType));

        Assertions.assertTrue(browseProjectModel.getErrorMessage().contains("You can't view this project"));
    }
}

