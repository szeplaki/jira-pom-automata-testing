package User;

import com.codecool.FileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserMethods {

    public static void login(WebDriver driver){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/login.jsp?os_destination=%2Fsecure%2FMyJiraHome.jspa");

        driver.findElement(By.id("login-form-username")).sendKeys(FileReader.getValueByKey("jira.username"));
        driver.findElement(By.id("login-form-password")).sendKeys(FileReader.getValueByKey("jira.password"));

        driver.findElement(By.id("login-form-submit")).click();
    }

    public static void logout(WebDriver driver){
        driver.findElement(By.className("aui-avatar-inner")).click();
        driver.findElement(By.id("log_out")).click();
    }
}
