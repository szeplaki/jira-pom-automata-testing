package Login;

import Model.Login.DashPageModel;
import Model.Login.LoginPageModel;
import Model.Login.ProfilePageModel;
import com.codecool.FileReader;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {
    private LoginPageModel loginPageModel;
    private ProfilePageModel profilePageModel;
    private DashPageModel dashPageModel;

    @BeforeEach
    public void setProperty() {
        loginPageModel = new LoginPageModel();
        profilePageModel = new ProfilePageModel();
        dashPageModel = new DashPageModel();
        loginPageModel.openUrlWithSpecificPathAndMaximizeWindowSize("/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
    }

    @AfterEach
    public void closeTab() {
        WebDriverService.getInstance().quitWebDriver();
    }

    @Test
    public void successfulLoginOnLoginPage() {
        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        loginPageModel.waitUntilLoggedIn();

        loginPageModel.openProfilePageAndWaitForLoadIn();

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }

    @Test
    public void successfulLoginOnDashPage() {
        // TODO külön method a webpage megnyitás és ellenőrzése
        loginPageModel.openUrlWithSpecificPathAndMaximizeWindowSize("/secure/Dashboard.jspa");
        loginPageModel.waitUntilWebElementIsVisible("id", "login");

        Assertions.assertTrue(dashPageModel.getDashPageTitle().contains("System Dashboard"));

        dashPageModel.loginOnDashPage(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
        dashPageModel.waitUntilLoggedIn();
        profilePageModel.openProfilePage();

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }

    @Test
    public void loginWithInvalidUserName() {
        loginPageModel.waitUntilWebElementIsVisible("", "login-form-submit");
        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.invalidLoginTry("whatever", FileReader.getValueByKey("jira.password"));
        loginPageModel.waitUntilErrorAppears();

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }

    @Test
    public void loginWithInvalidPassword() {
        loginPageModel.waitUntilWebElementIsVisible("", "login-form-submit");
        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.invalidLoginTry(FileReader.getValueByKey("jira.username"), "whatever");
        loginPageModel.waitUntilErrorAppears();

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }
}
