package Model;

import com.codecool.FileReader;
import com.codecool.WebDriverService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseModel {
    protected final WebDriver webDriver;
    protected final WebDriverWait driverWait;

    @FindBy(id = "login-form-username")
    protected WebElement usernameField;
    @FindBy(id = "login-form-password")
    protected WebElement passwordField;
    @FindBy(id = "login-form-submit")
    protected WebElement loginButton;

    public BaseModel() {
        this.webDriver = WebDriverService.getInstance().getWebDriver();
        driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        PageFactory.initElements(webDriver, this);
    }

    protected void setUsername(String username){
        this.usernameField.sendKeys(username);
    }

    protected void setPassword(String password) {
        this.passwordField.sendKeys(password);
    }

    protected void clickOnLoginButton(){
        loginButton.click();
    }

    /**
     * The webDriver waits until the selected webElement is visible, or 15 sec. maximum duration.
     * @param type the type of the element you are looking for: id, xpath, css or className.
     * @param id the selected element's selector.
     */
    public void waitUntilWebElementIsVisible(String type, String id){
        switch (type){
            case "id":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            case "xpath":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(id)));
                break;
            case "css":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(id)));
                break;
            case "className":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(id)));
                break;
        }
    }

    public void doLogin(){
        webDriver.navigate().to(FileReader.getValueByKey("jira.baseurl") + "/login.jsp?os_destination=%2Fsecure%2FMyJiraHome.jspa");

        setUsername(FileReader.getValueByKey("jira.username"));
        setPassword(FileReader.getValueByKey("jira.password"));
        clickOnLoginButton();
    }

    public void openUrlWithSpecificPathAndMaximizeWindowSize(String path){
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + path);
        webDriver.manage().window().maximize();
    }
}
