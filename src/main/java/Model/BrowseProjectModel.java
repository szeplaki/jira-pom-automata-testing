package Model;

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

    @FindBy(xpath = "//*[@id=\"sidebar\"]/div/div[1]/div/div/div[2]/h1/div/div/a")
    private WebElement projectKey;


    public String getProjectKey() {
        return projectKey.getAttribute("href");
    }
}
