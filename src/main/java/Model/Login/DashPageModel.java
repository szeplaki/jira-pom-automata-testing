package Model.Login;

import Model.BaseModel;
import com.codecool.FileReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashPageModel extends BaseModel {

    public DashPageModel() {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "login-form-username")
    private WebElement usernameField;
    @FindBy(id = "login-form-password")
    private WebElement passwordField;
    @FindBy(id = "login")
    private WebElement loginButton;
    @FindBy(xpath = "//*[@id='dashboard-content']//div[@class='aui-page-header-main']/h1")
    private WebElement dashPageTitle;

    public String getDashPageTitle(){
        return dashPageTitle.getText();
    }

    public void login(String username, String password){
        setUsername(username);
        setPassword(password);
        clickOnLoginButton();
    }

    public void openDashboardLogin()
    {
        webDriver.get(FileReader.getValueByKey("jira.baseurl") + "/secure/Dashboard.jspa");
    }
}
