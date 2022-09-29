package Model.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePageModel extends LoginPageModel  {

    public ProfilePageModel(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "up-d-fullname")
    private WebElement fullName;

    public String getFullName(){
        return fullName.getText();
    }
}
