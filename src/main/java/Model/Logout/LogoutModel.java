package Model.Logout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutModel {
    private WebDriver webDriver;

    public LogoutModel(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "header-details-user-fullname")
    private WebElement profileIcon;
    @FindBy(id = "log_out")
    private WebElement logoutButton;
    @FindBy(xpath = "//*[@id=\"main\"]/div/div/p[1]")
    private WebElement logoutMsg;

    public String getLogoutMsg(){
        return this.logoutMsg.getText();
    }

    public void logout(){
        profileIcon.click();
        logoutButton.click();
    }
}
