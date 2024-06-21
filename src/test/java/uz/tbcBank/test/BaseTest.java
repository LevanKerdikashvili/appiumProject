/**
 * The BaseTest class in Java sets up and initializes the driver with desired capabilities for Android and iOS platforms,
 * and includes methods for setting and getting driver, platform, and environment values.
 */
package uz.tbcBank.test;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import uz.tbcBank.Helpers.Config;
import uz.tbcBank.Helpers.Utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

import static uz.tbcBank.Helpers.Utils.log;


public class BaseTest {

    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<>();
    protected static ThreadLocal<String> platform = new ThreadLocal<>();
    protected static ThreadLocal<String> env = new ThreadLocal<>();

    public static Config conf = Config.getInstance(); // load config reader


    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver drv) {
        log(Status.INFO, "set driver: " + driver);
        driver.set(drv);
    }

    public static String getPlatform() {
        return platform.get();
    }

    public void setPlatform(String platformValue) {
        log(Status.INFO, "set platform: " + platformValue);
        platform.set(platformValue);
    }


    public void setEnv(String envValue) {
        log(Status.INFO, "set env: " + envValue);
        env.set(envValue);
    }

    public static String getEnv() {
        return env.get();
    }

    public HashMap<String, String> getStrings() {
        return strings.get();
    }

    public void setStrings(HashMap<String, String> str) {
        strings.set(str);
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
        String envName = System.getProperty("env", conf.read("env")).toLowerCase();

        setEnv(envName);

        setPlatform(platformName);

        AppiumDriver driver;

        setStrings(Utils.parseStringXML(getClass().getClassLoader().getResourceAsStream("env/" + getEnv() + ".xml")));


        try {
            switch (platformName) {
                case "android":
                    // Set Android-specific capabilities
                    capabilities.setCapability("appium:automationName", conf.read("androidAutomationName"));
                    capabilities.setCapability("appium:platformName", platformName);
                    if (!Objects.equals(conf.read("randomDevice"), "true")) {
                        capabilities.setCapability("appium:udid", conf.read("androidUdid"));
                    }
                    capabilities.setCapability("appium:appPackage", conf.read("appPackage"));
                    capabilities.setCapability("appium:appActivity", conf.read("appActivity"));
                    capabilities.setCapability("appium:autoGrantPermissions", true);
                    capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/" + getEnv() + "/" + conf.read("androidApk"));
                    driver = new AndroidDriver(new URL(conf.read("appiumURL")), capabilities);
                    break;
                case "ios":
                    // Set ios-specific capabilities
                    capabilities.setCapability("appium:automationName", conf.read("iOSAutomationName"));
                    capabilities.setCapability("appium:platformName", platformName);
                    if (!Objects.equals(conf.read("randomDevice"), "true")) {
                        capabilities.setCapability("appium:udid", conf.read("iosUdid"));
                    }
                    capabilities.setCapability("appium:forceEspressoRebuild", true);
                    capabilities.setCapability("appium:printPageSourceOnFindFailure", true);
                    capabilities.setCapability("appium:bundleId", conf.read("iOSBundleId"));
                    capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/" + getEnv() + "/" + conf.read("iosApp"));
                    driver = new IOSDriver(new URL(conf.read("appiumURL")), capabilities);
                    break;
                default:
                    throw new Exception("Invalid platform! - " + platformName);
            }
            setDriver(driver);
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
            log(Status.INFO, "driver quit");
            log(Status.INFO, "--- --- --- ---");
            getDriver().quit();
        }
    }

}
