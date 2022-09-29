package Model.BrowseProject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BrowseProjectModel {
    private final WebDriver webDriver;


    public BrowseProjectModel(WebDriver webDriver) {
        this.webDriver = webDriver;
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
