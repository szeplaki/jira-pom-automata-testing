package Model.Login;

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

public class LoginPageModel {
    protected final WebDriver webDriver;
    protected final WebDriverWait driverWait;

    public LoginPageModel() {
        this.webDriver = WebDriverService.getInstance().getWebDriver();
        driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "login-form-username")
    private WebElement usernameField;
    @FindBy(id = "login-form-password")
    private WebElement passwordField;
    @FindBy(id = "login-form-submit")
    private WebElement loginButton;
    @FindBy(className = "aui-page-header-main")
    private WebElement title;
    @FindBy(xpath = "//*[@id='login-form']//p")
    private WebElement invalidLoginMsg;
    @FindBy(id = "login")
    private WebElement loginButtonOnDash;

    private void clickLoginButtonOnDash(){
        loginButtonOnDash.click();
    }

    public String getErrorMsg(){
        return invalidLoginMsg.getText();
    }

    public String getTitle(){
        return title.getText();
    }

    private void setUsername(String username){
        this.usernameField.sendKeys(username);
    }

    private void setPassword(String password) {
        this.passwordField.sendKeys(password);
    }

    private void clickOnLoginButton(){
        loginButton.click();
    }

    public void login(String username, String password){
        setUsername(username);
        setPassword(password);
        clickOnLoginButton();
    }

    public void loginOnDashPage(String username, String password){
        setUsername(username);
        setPassword(password);
        clickLoginButtonOnDash();
    }

    public void doLogin(){
        webDriver.navigate().to("https://jira-auto.codecool.metastage.net/login.jsp?os_destination=%2Fsecure%2FMyJiraHome.jspa");

        login(FileReader.getValueByKey("jira.username"), FileReader.getValueByKey("jira.password"));
    }

    public void getLoginPage()
    {
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/login.jsp?os_destination=%2Fsecure%2FTests.jspa#/design?projectId=10101");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-form-submit")));
    }

    public void openUrlWithSpecificEndingAndMaximizeWindowSize(String ending){
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + ending);
        webDriver.manage().window().maximize();
    }
    public void openUrlWithEnding(String ending){
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + ending);
    }

    public void waitUntilErrorAppears()
    {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-form']//p")));
    }
    public void waitUntilLoggedIn()
    {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-details-user-fullname")));
    }

    public void openProfilePageAndWaitForLoadIn(){
        openUrlWithSpecificEndingAndMaximizeWindowSize("/secure/ViewProfile.jspa");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("up-d-fullname")));
    }

    public void invalidLoginTry(String username, String password){
        login(username, password);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='login-form']//p")));
    }

    public void waitUntil(String type, String id){
        switch (type){
            case "id":
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
                break;
            case "xpath":
                break;
        }
    }
}
