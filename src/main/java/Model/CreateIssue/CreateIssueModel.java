package Model.CreateIssue;

import Model.BaseModel;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

public class CreateIssueModel extends BaseModel {

    WebDriverWait shortWait;
    public CreateIssueModel() throws MalformedURLException {
        PageFactory.initElements(webDriver, this);
        shortWait = new WebDriverWait(webDriver, Duration.ofMillis(1500));
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
    // Érdemes találni egy id-vel rendelkező div-et a h2 hierarchia felett
    @FindBy(xpath = "//h2[text() = 'Create Issue']")
    private WebElement modalHeader;

    @FindBy(xpath = "//*[@id = 'create-issue-dialog']//div[@class = 'error']")
    private WebElement summaryErrorField;


    public void openCreateIssueModal()
    {
        createButton.click();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Create Issue')]")));
    }

    public void selectProject(String projectName)
    {
        try {
            shortWait.until(CreateIssueModel.absenceOfElementLocated(By.id("project-field")));
        }
        catch (TimeoutException ignored){}
        finally {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        }
//        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        projectField.click();
        projectField.sendKeys(Keys.BACK_SPACE);
        projectField.sendKeys(projectName);
        projectField.sendKeys(Keys.ENTER);
        modalHeader.click();
    }

    public void selectIssueType(String issueType)
    {
        try {
            shortWait.until(CreateIssueModel.absenceOfElementLocated(By.id("issuetype-field")));
        }
        catch (TimeoutException ignored){}
        finally {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("issuetype-field")));
        }
//        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
        issueField.click();
        issueField.sendKeys(Keys.BACK_SPACE);
        issueField.sendKeys(issueType);
        issueField.sendKeys(Keys.ENTER);
        modalHeader.click();
    }

    public void setSummary(String summary)
    {
        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("summary")));
        summaryField.sendKeys(summary);
    }

    public void submitIssue()
    {
        submitButton.click();
    }

    public void openSubmittedIssue() {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("issue-created-key")));
        newIssueLink.click();
    }

    public String getProjectFieldValue()
    {
        try {
            shortWait.until(CreateIssueModel.absenceOfElementLocated(By.id("project-field")));
        }
        catch (TimeoutException ignored){}
        finally {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("project-field")));
        }
//        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("project-field")));
        return extractValueFrom(projectField);
    }

    public String getIssueFieldValue()
    {
        try {
            shortWait.until(CreateIssueModel.absenceOfElementLocated(By.id("issuetype-field")));
        }
        catch (TimeoutException ignored){}
        finally {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("issuetype-field")));
        }
//        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("issuetype-field")));
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
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id = 'create-issue-dialog']//div[@class = 'error']")));
        return summaryErrorField.getText();
    }

    public static ExpectedCondition<Boolean> absenceOfElementLocated(final By locator) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                try {
                    driver.findElement(locator);
                    return false;
                } catch (NoSuchElementException var3) {
                    return true;
                } catch (StaleElementReferenceException err)
                {
                    return false;
                }
            }
            public String toString() {
                return "element to no longer be visible: " + locator;
            }
        };
    }




}