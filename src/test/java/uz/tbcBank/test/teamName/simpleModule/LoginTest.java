package uz.tbcBank.test.teamName.simpleModule;

import org.testng.Assert;
import org.testng.annotations.Test;
import uz.tbcBank.pages.teamName.simpleModule.LoginPage;
import uz.tbcBank.test.BaseTest;

import java.io.IOException;

public class LoginTest extends BaseTest {


    @Test
    public void invalidUserName() throws IOException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName("invalidusername");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        String actualErrTxt = loginPage.getErrTxt();
        Assert.assertEquals(actualErrTxt, "1Username and password do not match any user in this service.");
    }

    @Test
    public void successLogin() throws InterruptedException, IOException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.pressLoginBtn();
        Thread.sleep(5000);

    }


}
