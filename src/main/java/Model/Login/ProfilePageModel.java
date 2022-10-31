package Model.Login;

import Model.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.MalformedURLException;

public class ProfilePageModel extends BaseModel {

    public ProfilePageModel() throws MalformedURLException {
        PageFactory.initElements(webDriver, this);
    }

    public void openProfilePage()
    {
        webDriver.get(System.getenv("baseurl") + "/secure/ViewProfile.jspa");
        webDriver.manage().window().maximize();
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("up-d-fullname")));
    }

    @FindBy(id = "up-d-fullname")
    private WebElement fullName;

    public String getFullName(){
        return fullName.getText();
    }
}
