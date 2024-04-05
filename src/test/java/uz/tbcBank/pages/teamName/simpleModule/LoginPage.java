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

public class LoginPage {

    protected AppiumDriver driver;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Username\"]")
    @iOSXCUITFindBy(id = "test-Username")
    private WebElement usernameTxtFld;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Password\"]")
    @iOSXCUITFindBy(id = "test-Password")
    private WebElement passwordTxtFld;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]")
    @iOSXCUITFindBy(id = "test-LOGIN")
    private WebElement loginBtn;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    @iOSXCUITFindBy(id = "test-Error message")
    private WebElement errTxt;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement productTitleTxt;


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

    public String getErrTxt() {
        return Utils.getText(errTxt, "error text is - ");
    }

    public String getTitle() {
        return Utils.getText(productTitleTxt, "product page title is - ");
    }

}

