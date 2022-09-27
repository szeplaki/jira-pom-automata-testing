package BrowseProjectTest;

import Model.BrowseProject.BrowseProjectModel;
import Model.Login.LoginPageModel;
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
    }

    @AfterEach
    public void closeTab() {
        webDriver.close();
    }

    @AfterAll
    public static void closeWebDriver() {
        webDriver.quit();
    }


    @ParameterizedTest
    @ValueSource(strings = {"MTP", "JETI", "TOUCAN", "COALA"})
    public void browseProject(String projectType) {
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);
        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/projects/%s/summary", projectType));
        BrowseProjectModel browseProjectModel = new BrowseProjectModel(webDriver);

        Assertions.assertTrue(browseProjectModel.getProjectKey().contains(projectType));
    }

    @ParameterizedTest
    @ValueSource(strings = {"DUMMYDATA"})
    public void browseNonExistingProject(String projectType) {
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);
        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        webDriver.get(String.format("https://jira-auto.codecool.metastage.net/projects/%s/summary", projectType));
        BrowseProjectModel browseProjectModel = new BrowseProjectModel(webDriver);

        Assertions.assertTrue(browseProjectModel.getErrorMessage().contains("You can't view this project"));
    }
}

