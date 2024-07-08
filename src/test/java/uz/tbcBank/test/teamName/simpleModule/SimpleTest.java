package uz.tbcBank.test.teamName.simpleModule;

import org.testng.Assert;
import org.testng.annotations.Test;
import uz.tbcBank.Helpers.Utils;
import uz.tbcBank.pages.teamName.simpleModule.LoginPage;
import uz.tbcBank.test.BaseTest;

import java.io.IOException;

public class SimpleTest extends BaseTest {


    @Test
    public void simpleTest() throws InterruptedException, IOException {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        String expectedProductTitle = Utils.getStrings().get("product_title");
        System.out.println(expectedProductTitle);
        loginPage.pressLoginBtn();

    }


}
