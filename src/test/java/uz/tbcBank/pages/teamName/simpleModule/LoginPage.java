package uz.tbcBank.pages.teamName.simpleModule;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uz.tbcBank.Helpers.Utils;
import uz.tbcBank.test.BaseTest;

import java.io.IOException;

public class LoginPage {

    protected AppiumDriver driver;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "etInputField")
    @iOSXCUITFindBy(id = "user_phone_number_textField")
    private WebElement usernameTxtFld;

    @AndroidFindBy(id = "pinEntryEditText")
    @iOSXCUITFindBy(id = "○○○○")
    private WebElement passwordTxtFld;

    @AndroidFindBy(id = "introStartButton")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Log in\"]")
    private WebElement loginBtn;


    public void enterUserName(String username) {
        Utils.clear(usernameTxtFld);
        Utils.sendKeys(usernameTxtFld, username, "login with " + username);
    }

    public void enterPassword(String password) {
        Utils.clear(passwordTxtFld);
        Utils.sendKeys(passwordTxtFld, password, "password is " + password);
    }

    public void pressLoginBtn() {
        Utils.click(loginBtn, "press login button");
    }


}

