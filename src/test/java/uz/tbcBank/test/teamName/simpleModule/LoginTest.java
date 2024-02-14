package uz.tbcBank.test.teamName.simpleModule;

import org.testng.Assert;
import org.testng.annotations.Test;
import uz.tbcBank.pages.teamName.simpleModule.LoginPage;
import uz.tbcBank.test.BaseTest;

import java.net.MalformedURLException;

public class LoginTest extends BaseTest {


    @Test
    public void invalidUserName() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserName("invalidusername");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        String actualErrTxt = loginPage.getErrTxt();
        Assert.assertEquals(actualErrTxt, "Username and password do not match any user in this service.");
    }

    @Test
    public void invalidUserNameSecond() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserName("invalidusername");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        String actualErrTxt = loginPage.getErrTxt();
        Assert.assertEquals(actualErrTxt, "Username and password do not match any user in this service");
    }

    @Test
    public void invalidUserNameOther() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserName("invalidusername");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        Assert.assertEquals(loginPage.getErrTxt(), "Username and password do not match any user in this service");
    }

    @Test
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        Assert.assertEquals(loginPage.getTitle(),"PRODUCTS");
    }
}