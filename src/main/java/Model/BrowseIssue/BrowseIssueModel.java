package Model.BrowseIssue;

import com.codecool.RandomHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    @FindBy(xpath = "//*[@id='main']//form//button[text() = 'Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"issue-content\"]/div/div/h1")
    private WebElement errorMessageField;

    @FindBy(id = "type-val")
    private WebElement typeValueField;
    @FindBy(id = "summary-val")
    private WebElement summaryValueField;
    @FindBy(id = "key-val")
    private WebElement keyValField;

    public String getIssueId() {
        return issueId.getText();
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public String getErrorMessageField() {
        return errorMessageField.getText();
    }

    public void deleteIssue()
    {
        webDriver.findElement(By.id("opsbar-operations_more")).click();
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        WebElement deleteButton = webDriver.findElement(By.id("delete-issue"));
        jse.executeScript("arguments[0].click()", deleteButton);
        RandomHelper.waitUntilVisibleOrClickable(webDriver,"click","delete-issue-submit");
        webDriver.findElement(By.id("delete-issue-submit")).click();
    }

    public String getIssueType()
    {
        return typeValueField.getText();
    }

    public String getSummary()
    {
        return summaryValueField.getText();
    }

    public String getProjectKey()
    {
        return keyValField.getText();
    }
}
