package Test;

import Model.LoginPageModel;
import Model.ProfilePageModel;
import com.codecool.FileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    static WebDriver webDriver;
    @BeforeAll
    public static void setProperty(){
        System.setProperty("webdriver.chrome.driver", FileReader.getValueByKey("driver.location"));

        webDriver = new ChromeDriver();
        webDriver.get("https://jira-auto.codecool.metastage.net/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
    }

    @AfterEach
    public void closeTab(){ webDriver.close(); }

    @Test
    public void successfulLogin(){
        LoginPageModel loginPageModel = new LoginPageModel(webDriver);
        ProfilePageModel profilePageModel = new ProfilePageModel(webDriver);

        Assertions.assertTrue(loginPageModel.getTitle().contains("Welcome to Jira Auto"));

        loginPageModel.login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));

        webDriver.get("https://jira-auto.codecool.metastage.net/secure/ViewProfile.jspa");

        Assertions.assertTrue(profilePageModel.getFullName().contains(FileReader.getValueByKey("jira.displayname")));
    }
}
