package Login;

import Model.Login.DashPageModel;
import Model.Login.LoginPageModel;
import Model.Login.ProfilePageModel;
import com.codecool.FileReader;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest {
    @BeforeAll
    public static void setProperty(){
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));
    }

//    @BeforeEach
//    public void openNewTab(){
//        browserOptions = new ChromeOptions();
//        browserOptions.addArguments("--incognito");
//        webDriver = new ChromeDriver(browserOptions);
//        this.driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
//        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
//        webDriver.manage().window().maximize();
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-form-submit")));
//    }

    @AfterEach
    public void closeTab(){
        WebDriverService.getInstance().quitWebDriver();
    }


    @Test
    public void successfulLoginOnLoginPage(){
        LoginPageModel loginPageModel = new LoginPageModel();
        ProfilePageModel profilePageModel = new ProfilePageModel();

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        loginPageModel.waitUntilLoggedIn();
//
//        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/secure/ViewProfile.jspa");
//        webDriver.manage().window().maximize();
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("up-d-fullname")));

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }

    @Test
    public void successfulLoginOnDashPage(){
        //TODO if we have time separate dash from login page and do all test all over again
        DashPageModel dashPageModel = new DashPageModel();
        ProfilePageModel profilePageModel = new ProfilePageModel();

//        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/secure/Dashboard.jspa");

        Assertions.assertTrue(dashPageModel.getDashPageTitle().contains("System Dashboard"));

        dashPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        dashPageModel.waitUntilLoggedIn();
        profilePageModel.openProfilePage();
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
//
//        webDriver.get(FileReader.getValueByKey("jira.baseurl") +  "/secure/ViewProfile.jspa");
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("up-user-title")));

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }

    @Test
    public void loginWithInvalidUserName(){
        LoginPageModel loginPageModel = new LoginPageModel();

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login("whatever", FileReader.getValueByKey("jira.password"));
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-form']//p")));
        loginPageModel.waitUntilErrorAppears();

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }

    @Test
    public void loginWithInvalidPassword(){
        LoginPageModel loginPageModel = new LoginPageModel();

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), "whatever");
        loginPageModel.waitUntilErrorAppears();
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-form']//p")));

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }
}
