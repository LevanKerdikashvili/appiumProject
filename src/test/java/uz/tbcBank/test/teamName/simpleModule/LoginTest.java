package uz.tbcBank.test.teamName.simpleModule;

import org.testng.Assert;
import org.testng.annotations.Test;
import uz.tbcBank.Helpers.Utils;
import uz.tbcBank.pages.teamName.simpleModule.LoginPage;
import uz.tbcBank.test.BaseTest;

import java.io.IOException;

public class LoginTest extends BaseTest {


    @Test
    public void successLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.pressLoginBtn();
        loginPage.enterUserName(Utils.getStrings().get("phone_number"));
        loginPage.enterPassword(Utils.getStrings().get("password"));
        Thread.sleep(5000);
    }

}
