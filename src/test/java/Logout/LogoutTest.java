package Logout;

import Model.Logout.LogoutModel;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;

public class LogoutTest {
    private static LogoutModel logoutModel;

    @BeforeAll
    public static void beforeAll() throws MalformedURLException {
        logoutModel = new LogoutModel();
    }

    @BeforeEach
    public void beforeEach(){
        logoutModel.doLogin();
    }

    @AfterEach
    public void closeWebDriver(){
        WebDriverService.getInstance().quitWebDriver();
    }


    @Test
    public void successfulLogout(){
        logoutModel.waitUntilLoggedIn();
        logoutModel.logout();

        Assertions.assertTrue(logoutModel.getLogoutMsg().contains("You are now logged out. Any automatic login has also been stopped."));
    }
}
