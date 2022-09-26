package Model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BrowseProjectModel {
    private final WebDriver webDriver;


    public BrowseProjectModel(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//*[@id=\"summary-body\"]/div/div[2]/dl/dd[3]")
    private WebElement projectKey;


    public String getProjectKey() {
        return projectKey.getText();
    }
}
