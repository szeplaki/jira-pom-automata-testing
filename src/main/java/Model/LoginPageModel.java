package Model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageModel {
    private final WebDriver webDriver;

    public LoginPageModel(WebDriver webDriver) {
        this.webDriver = webDriver;
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
    @FindBy(xpath = "//*[@id=\"login-form\"]/div[1]/div[1]/p")
    private WebElement invalidLoginMsg;

    public String getErrorMsg(){
        return invalidLoginMsg.getText();
    }

    public String getTitle(){
        return title.getText();
    }

    public void setUsername(String username){
        this.usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        this.passwordField.sendKeys(password);
    }

    public void clickOnLoginButton(){
        loginButton.click();
    }

    public void login(String username, String password){
        setUsername(username);
        setPassword(password);
        clickOnLoginButton();
    }
}
