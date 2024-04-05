/**
 * The BaseTest class is a Java class that sets up the desired capabilities for an Android driver, initializes the driver,
 * and handles video recording before and after each test method.
 */
package uz.tbcBank.test;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import uz.tbcBank.Helpers.Config;

import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

import static uz.tbcBank.Helpers.Utils.log;


public class BaseTest {

    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<Properties> props = new ThreadLocal<>();
    protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<>();
    protected static ThreadLocal<String> platform = new ThreadLocal<>();

    public static Config conf = Config.getInstance(); // load config reader


    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver drv) {
        driver.set(drv);
    }

    public static String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platformValue) {
        platform.set(platformValue);
    }


    /**
     * The setUp() function sets up the desired capabilities for an Android driver and initializes the driver with the
     * specified capabilities.
     */
    @BeforeClass
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String projectPath = System.getProperty("user.dir");
        String platformName = System.getProperty("platform", conf.read("platform")).toLowerCase();
        setPlatform(platformName);
        AppiumDriver driver;
        try {
            switch (platformName) {
                case "android":
                    // Set Android-specific capabilities
                    capabilities.setCapability("appium:automationName", "UiAutomator2");
                    capabilities.setCapability("appium:platformName", platformName);
                    if (!Objects.equals(conf.read("randomDevice"), "true")) {
                        capabilities.setCapability("appium:udid", conf.read("androidUdid"));
                    }
                    capabilities.setCapability("appium:appPackage", conf.read("appPackage"));
                    capabilities.setCapability("appium:appActivity", conf.read("appActivity"));
                    capabilities.setCapability("appium:autoGrantPermissions", true);
                    capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/app.apk");
                    driver = new AndroidDriver(new URL(conf.read("appiumURL")), capabilities);
                    break;
                case "ios":
                    // Set ios-specific capabilities
                    capabilities.setCapability("appium:automationName", "XCUITest");
                    capabilities.setCapability("appium:platformName", platformName);
                    if (!Objects.equals(conf.read("randomDevice"), "true")) {
                        capabilities.setCapability("appium:udid", conf.read("iosUdid"));
                    }
                    capabilities.setCapability("appium:forceEspressoRebuild", true);
                    capabilities.setCapability("appium:printPageSourceOnFindFailure", true);
                    capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/iosApp.app");
                    driver = new IOSDriver(new URL(conf.read("appiumURL")), capabilities);
                    break;
                default:
                    throw new Exception("Invalid platform! - " + platformName);
            }
            setDriver(driver);
            log(Status.INFO, "driver initialization: " + driver);
        } catch (Exception e) {
            log(Status.INFO, "driver initialization failure");
            throw e;
        }
    }


    /**
     * The above function is a teardown method in Java that quits the driver if it is not null.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            getDriver().quit();
        }
    }

}