package uz.tbcBank.pages.teamName.simpleModule;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uz.tbcBank.Helpers.Utils;

public class LoginPage {

    protected AppiumDriver driver;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Username\"]")
    private WebElement usernameTxtFld;

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Password\"]")
    private WebElement passwordTxtFld;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]")
    private WebElement loginBtn;
   @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    private WebElement errTxt;
   @FindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
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

