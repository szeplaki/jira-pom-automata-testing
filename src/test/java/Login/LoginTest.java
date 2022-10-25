package Login;

import Model.Login.DashPageModel;
import Model.Login.LoginPageModel;
import Model.Login.ProfilePageModel;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class LoginTest {
    private LoginPageModel loginPageModel;
    private ProfilePageModel profilePageModel;
    private DashPageModel dashPageModel;

    @BeforeEach
    public void setProperty() throws MalformedURLException {
        loginPageModel = new LoginPageModel();
        profilePageModel = new ProfilePageModel();
        dashPageModel = new DashPageModel();
        loginPageModel.getLoginPage();
    }

    @AfterEach
    public void closeTab() {
        WebDriverService.getInstance().quitWebDriver();
    }

    @Test
    public void successfulLoginOnLoginPage() {
        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(System.getProperty("username"), System.getProperty("password"));
        loginPageModel.waitUntilLoggedIn();

        loginPageModel.openProfilePageAndWaitForLoadIn();

        Assertions.assertTrue(profilePageModel.getFullName().contains(System.getProperty("displayname")));
    }

    @Test
    public void successfulLoginOnDashPage() {
        dashPageModel.openDashboardLoginPage();
        dashPageModel.waitUntilWebElementIsClickable("id", "login");
        Assertions.assertTrue(dashPageModel.getDashPageTitle().contains("System Dashboard"));

        dashPageModel.loginOnDashPage(System.getProperty("username"), System.getProperty("password"));
        dashPageModel.waitUntilLoggedIn();
        profilePageModel.openProfilePage();

        Assertions.assertTrue(profilePageModel.getFullName().contains(System.getProperty("displayname")));
    }

    @Test
    public void loginWithInvalidUserName() {
        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.invalidLoginTry("whatever", System.getProperty("password"));
        loginPageModel.waitUntilErrorAppearsOnLoginPage();

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(System.getProperty("username"), System.getProperty("password"));
    }

    @Test
    public void loginWithInvalidPassword() {
        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.invalidLoginTry(System.getProperty("username"), "whatever");
        loginPageModel.waitUntilErrorAppearsOnLoginPage();

        Assertions.assertTrue(loginPageModel.getErrorMsg().contains("Sorry, your username and password are incorrect - please try again."));

        loginPageModel.login(System.getProperty("username"), System.getProperty("password"));
    }
}
