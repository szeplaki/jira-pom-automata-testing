package Model.BrowseProject;

import Model.BaseModel;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;

public class BrowseProjectModel extends BaseModel {

    public BrowseProjectModel() throws MalformedURLException {
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
