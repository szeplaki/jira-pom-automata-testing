package Model.BrowseProject;

import Model.Login.LoginPageModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BrowseProjectModel extends LoginPageModel {

    public BrowseProjectModel(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//*[@id='sidebar']//h1//a")
    private WebElement projectKey;

    @FindBy(xpath = "//*[@id='main']/h1")
    private WebElement errorMessage;


    public String getProjectKey() {
        return projectKey.getAttribute("href");
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
