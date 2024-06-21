package uz.tbcBank.test.teamName.simpleModule;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import uz.tbcBank.pages.teamName.simpleModule.LoginPage;
import uz.tbcBank.test.BaseTest;

public class LoginTest extends BaseTest {


    @Test
    public void invalidUserName() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName("invalidusername");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        String actualErrTxt = loginPage.getErrTxt();
        Assert.assertEquals(actualErrTxt, "1Username and password do not match any user in this service.");
    }
    
}