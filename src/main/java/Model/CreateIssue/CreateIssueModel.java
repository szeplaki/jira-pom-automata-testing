package Model.CreateIssue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// page_url = https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa
public class CreateIssueModel {
    private final WebDriver driver;
    private WebDriverWait driverWait;

    public CreateIssueModel(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(id = "create_link")
    private WebElement createButton;

    @FindBy(id = "project-field")
    private WebElement projectField;

    @FindBy(id = "issuetype-field")
    private WebElement issueField;

    @FindBy(id = "summary")
    private WebElement summaryField;
    @FindBy(id = "create-issue-submit")
    private WebElement submitButton;
    @FindBy(className = "issue-created-key")
    private WebElement newIssueLink;
    @FindBy(xpath = "//h2[text() = 'Create Issue']")
    private WebElement modalHeader;

    @FindBy(xpath = "//*[@id = 'create-issue-dialog']//div[@class = 'error']")
    private WebElement summaryErrorField;


    public void openCreateIssueModal()
    {
        createButton.click();
        // RandomHelper.waitUntilVisibleOrClickable(driver,"xpath", "//h2[contains(text(),'Create Issue')]");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Create Issue')]")));
    }

    public void selectProject(String projectName)
    {
        // RandomHelper.waitUntilVisibleOrClickable(driver,"click","project-field");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        projectField.click();
        projectField.sendKeys(Keys.BACK_SPACE);
        projectField.sendKeys(projectName);
        projectField.sendKeys(Keys.ENTER);
        modalHeader.click();
    }

    public void selectIssueType(String issueType)
    {
        // RandomHelper.waitUntilVisibleOrClickable(driver,"click","issuetype-field");
        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
        issueField.click();
        issueField.sendKeys(Keys.BACK_SPACE);
        issueField.sendKeys(issueType);
        issueField.sendKeys(Keys.ENTER);
        modalHeader.click();
    }

    public void setSummary(String summary)
    {
        // RandomHelper.waitUntilVisibleOrClickable(driver,"click","summary");
        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryField.sendKeys(summary);
    }

    public void submitIssue()
    {
        submitButton.click();
    }

    public void openSubmittedIssue() {
        // RandomHelper.waitUntilVisibleOrClickable(driver, "className", "issue-created-key");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("issue-created-key")));
        newIssueLink.click();
    }

    public String getProjectFieldValue()
    {
        // RandomHelper.waitUntilVisibleOrClickable(driver,"click","project-field");
        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("project-field")));
        return extractValueFrom(projectField);
    }

    public String getIssueFieldValue()
    {
        // RandomHelper.waitUntilVisibleOrClickable(driver,"click","issuetype-field");
        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
        return extractValueFrom(issueField);
    }

    private String extractValueFrom(WebElement inputField) {
        inputField.click();
        inputField.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        inputField.sendKeys(Keys.chord(Keys.CONTROL,"c"));
        summaryField.click();
        summaryField.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        summaryField.sendKeys(Keys.chord(Keys.CONTROL,"v"));
        String value = summaryField.getAttribute("value");
        summaryField.clear();
        return value;
    }

    public String getSummaryErrorMessage()
    {
        // RandomHelper.waitUntilVisibleOrClickable(driver,"xpath","//*[@id = 'create-issue-dialog']//div[@class = 'error']");
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id = 'create-issue-dialog']//div[@class = 'error']")));
        return summaryErrorField.getText();
    }




}