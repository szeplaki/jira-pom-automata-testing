package Model.BrowseIssue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BrowseIssueModel {
    private final WebDriver webDriver;


    public BrowseIssueModel(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "key-val")
    private WebElement issueId;

    @FindBy(id = "searcher-query")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div[1]/form/div[1]/div[1]/div[1]/div[1]/div/div[1]/ul/li[7]/button")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"key-val\"]")
    private WebElement issueNumber;
    public String getIssueId() {
        return issueId.getText();
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getIssueNumber() {
        return issueNumber;
    }
}
