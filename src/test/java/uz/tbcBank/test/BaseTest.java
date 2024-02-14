/**
 * The BaseTest class is a Java class that sets up the desired capabilities for an Android driver, initializes the driver,
 * and handles video recording before and after each test method.
 */
package uz.tbcBank.test;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import uz.tbcBank.Helpers.Config;
import uz.tbcBank.Helpers.VideoRecordUtils;

import java.net.URL;
import java.util.Objects;


public class BaseTest {
    public static AndroidDriver driver;
    public static Config conf = Config.getInstance(); // load config reader
    private static VideoRecordUtils videoRecordUtils;


    public static AndroidDriver getDriver() {
        return driver;
    }

    /**
     * The setUp() function sets up the desired capabilities for an Android driver and initializes the driver with the
     * specified capabilities.
     */
    @BeforeClass
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String projectPath = System.getProperty("user.dir");
        // Set Android-specific capabilities
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        if (!Objects.equals(conf.read("randomDevice"), "true")) {
            capabilities.setCapability("appium:udid", conf.read("udid"));
        }
        capabilities.setCapability("appium:appPackage", conf.read("appPackage"));
        capabilities.setCapability("appium:appActivity", conf.read("appActivity"));
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:app", projectPath + "/src/test/resources/app/app.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }


    /**
     * The above function is a teardown method in Java that quits the driver if it is not null.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * The above function is a setup method that initializes a VideoRecordUtils object and starts recording a video.
     */
    @BeforeMethod
    public void beforeMethod() {
        videoRecordUtils = new VideoRecordUtils(driver);
        videoRecordUtils.startRecording();
    }

    /**
     * The above function is an AfterMethod annotation in Java that stops video recording using the VideoRecordUtils class
     * after each test method.
     *
     * @param result The "result" parameter is an object of type ITestResult, which represents the result of a test method
     *               execution. It contains information about the test method, such as its name, status (passed, failed, skipped), and
     *               any associated exception or error.
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        videoRecordUtils = new VideoRecordUtils(driver);
        videoRecordUtils.stopRecording(result);
    }

}