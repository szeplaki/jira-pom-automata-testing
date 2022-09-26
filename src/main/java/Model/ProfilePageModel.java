package Model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePageModel {
    private WebDriver webDriver;

    public ProfilePageModel(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "up-d-fullname")
    private WebElement fullName;

    public String getFullName(){
        return fullName.getText();
    }
}
