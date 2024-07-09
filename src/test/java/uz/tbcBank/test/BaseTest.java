package uz.tbcBank.test;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;
import uz.tbcBank.Helpers.Config;
import uz.tbcBank.Helpers.Utils;
import uz.tbcBank.Helpers.VideoRecordUtils;

import java.net.URL;

import static uz.tbcBank.Helpers.Utils.closeApp;
import static uz.tbcBank.Helpers.Utils.log;

public class BaseTest {

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> platform = new ThreadLocal<>();
    private static final ThreadLocal<String> env = new ThreadLocal<>();

    public static Config conf = Config.getInstance(); // load config reader
    private static VideoRecordUtils videoRecordUtils;

    /**
     * Gets the current Appium driver instance.
     *
     * @return the Appium driver
     */
    public static AppiumDriver getDriver() {
        return driver.get();
    }

    /**
     * Sets the Appium driver instance.
     *
     * @param drv the Appium driver to set
     */
    public void setDriver(AppiumDriver drv) {
        System.out.println("set driver: " + drv.getCapabilities().getCapability("platformName").toString());
        driver.set(drv);
    }

    /**
     * Gets the platform name (android or ios).
     *
     * @return the platform name
     */
    public static String getPlatform() {
        return platform.get().toLowerCase();
    }

    /**
     * Sets the platform name.
     *
     * @param platformValue the platform name to set
     */
    public void setPlatform(String platformValue) {
        System.out.println("set platform: " + platformValue);
        platform.set(platformValue);
    }

    /**
     * Sets the environment name.
     *
     * @param envValue the environment name to set
     */
    public void setEnv(String envValue) {
        System.out.println("set env: " + envValue);
        env.set(envValue);
    }

    /**
     * Gets the environment name.
     *
     * @return the environment name
     */
    public static String getEnv() {
        return env.get().toLowerCase();
    }

    /**
     * Sets up the Appium driver with desired capabilities.
     *
     * @param platformParam the platform parameter from TestNG XML
     * @param envParam      the environment parameter from TestNG XML
     * @param udid          the udid parameter from TestNG XML
     * @throws Exception if the platform is invalid or driver initialization fails
     */
    @Parameters({"platform", "env", "udid"})
    @BeforeClass
    public void setUp(@Optional String platformParam, @Optional String envParam, @Optional String udid) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String projectPath = System.getProperty("user.dir");
        String platformName = (platformParam != null) ? platformParam.toLowerCase() : conf.read("platform").toLowerCase();
        String envName = (envParam != null) ? envParam.toLowerCase() : conf.read("env").toLowerCase();

        setEnv(envName);
        setPlatform(platformName);

        AppiumDriver driver;
        Utils.setStrings(Utils.parseStringXML(getClass().getClassLoader().getResourceAsStream("env/" + getEnv() + ".xml")));

        try {
            switch (platformName) {
                case "android":
                    capabilities.setCapability("appium:automationName", conf.read("androidAutomationName"));
                    capabilities.setCapability("appium:platformName", platformName);
                    capabilities.setCapability("appium:udid", (udid != null) ? udid : conf.read("androidUdid"));
                    capabilities.setCapability("appium:appPackage", conf.read("appPackage"));
                    capabilities.setCapability("appium:appActivity", conf.read("appActivity"));
                    capabilities.setCapability("appium:autoGrantPermissions", true);
                    capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/" + getEnv() + "/" + conf.read("androidApk"));
                    driver = new AndroidDriver(new URL(conf.read("appiumURL")), capabilities);
                    break;
                case "ios":
                    capabilities.setCapability("appium:automationName", conf.read("iOSAutomationName"));
                    capabilities.setCapability("appium:platformName", platformName);
                    capabilities.setCapability("appium:udid", (udid != null) ? udid : conf.read("iosUdid"));
                    capabilities.setCapability("appium:printPageSourceOnFindFailure", true);
                    capabilities.setCapability("appium:bundleId", conf.read("iOSBundleId"));
                    capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/" + getEnv() + "/" + conf.read("iosApp"));
                    capabilities.setCapability("autoAcceptAlerts", true);
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
     * Closes the Appium driver after the test class execution.
     */
    @AfterClass
    public void tearDown() {
        closeApp();
        getDriver().quit();
        System.out.println("driver quit");
    }

    /**
     * Starts video recording before each test method.
     */
    @BeforeMethod
    public void beforeMethod() {
        videoRecordUtils = new VideoRecordUtils(getDriver());
        videoRecordUtils.startRecording();
    }

    /**
     * Stops video recording after each test method.
     *
     * @param result the test result
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        videoRecordUtils = new VideoRecordUtils(getDriver());
        videoRecordUtils.stopRecording(result);
    }
}
