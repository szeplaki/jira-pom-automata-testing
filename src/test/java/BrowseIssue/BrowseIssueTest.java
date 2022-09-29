package BrowseIssue;

import Model.BrowseIssue.BrowseIssueModel;
import com.codecool.WebDriverService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BrowseIssueTest {
    private BrowseIssueModel browseIssueModel;

    @BeforeEach
    public void openNewTab() {
        browseIssueModel = new BrowseIssueModel();
        browseIssueModel.doLogin();
    }

    @AfterAll
    public void closeWebDriver() {
        WebDriverService.getInstance().quitWebDriver();
    }



    @Test
    public void browseExistingIssue() {
        browseIssueModel.openUrlWithSpecificEndingAndMaximizeWindowSize("/browse/MTP-2253");
        Assertions.assertEquals("MTP-2253", browseIssueModel.getIssueId());
    }

    @Test
    public void checkPossibilityOfBrowsing() {
        browseIssueModel.openUrlWithSpecificEndingAndMaximizeWindowSize("/issues/?jql=");
        browseIssueModel.getSearchField().click();
        browseIssueModel.getSearchField().sendKeys("Jira Test Project");
        browseIssueModel.getSearchButton().click();
        browseIssueModel.waitUntilKeyIsVisible();
        Assertions.assertEquals("MTP-2245", browseIssueModel.getIssueId());
    }

    @Test
    public void browseNonExistingIssue() {
        browseIssueModel.openUrlWithSpecificEndingAndMaximizeWindowSize("/browse/MTP-99999999999");
        Assertions.assertEquals("You can't view this issue", browseIssueModel.getErrorMessageField());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/issueIds.csv")
    public void browseIssueWithSpecificId(String issueId) {
        browseIssueModel.openUrlWithSpecificEndingAndMaximizeWindowSize(String.format("/browse/%s", issueId));
        Assertions.assertDoesNotThrow(() -> browseIssueModel.getIssueId());
        Assertions.assertEquals(issueId, browseIssueModel.getIssueId());
    }
}
