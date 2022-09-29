package Login;

import Model.Login.DashPageModel;
import Model.Login.LoginPageModel;
import Model.Login.ProfilePageModel;
import com.codecool.FileReader;
import com.codecool.RandomHelper;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTest {
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
        webDriver.get("https://jira-auto.codecool.metastage.net/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
        RandomHelper.Wait(webDriver);
    }

    @AfterEach
    public void closeTab(){ webDriver.quit(); }


    @Test
    public void successfulLoginOnLoginPage(){
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);
        ProfilePageModel profilePageModel = new ProfilePageModel(webDriver);

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));

        webDriver.get("https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa");
        webDriver.manage().window().maximize();
        RandomHelper.Wait(webDriver);

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }

    @Test
    public void successfulLoginOnDashPage(){
        //TODO if we have time separate dash from login page and do all test all over again
        DashPageModel dashPageModel = new DashPageModel(webDriver);
        ProfilePageModel profilePageModel = new ProfilePageModel(webDriver);

        webDriver.get("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");

        Assertions.assertTrue(dashPageModel.getDashPageTitle().contains("System Dashboard"));

        dashPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        RandomHelper.Wait(webDriver);

        webDriver.get("https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa");
        RandomHelper.Wait(webDriver);

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }

    @Test
    public void loginWithInvalidUserName(){
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login("whatever", FileReader.getValueByKey("jira.password"));
        RandomHelper.Wait(webDriver);

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }

    @Test
    public void loginWithInvalidPassword(){
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), "whatever");
        RandomHelper.Wait(webDriver);

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }
}
