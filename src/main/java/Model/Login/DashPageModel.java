package Model.Login;

import Model.BaseModel;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;

public class DashPageModel extends BaseModel {

    public DashPageModel() throws MalformedURLException {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "login")
    private WebElement loginButtonOnDash;
    @FindBy(xpath = "//*[@id='dashboard-content']//div[@class='aui-page-header-main']/h1")
    private WebElement dashPageTitle;

    public String getDashPageTitle(){
        return dashPageTitle.getText();
    }

    private void clickLoginButtonOnDash(){
        this.loginButtonOnDash.click();
    }

    public void openDashboardLoginPage()
    {
        webDriver.get(System.getProperty("baseurl") + "/secure/Dashboard.jspa");
    }

    public void loginOnDashPage(String username, String password){
        setUsername(username);
        setPassword(password);
        clickLoginButtonOnDash();
    }
}
