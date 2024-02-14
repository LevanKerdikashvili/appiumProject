/**
 * The Listener class is a TestNG listener that captures screenshots and videos of test failures, logs test results in an
 * Extent Report, and provides additional functionality for test execution.
 */
package uz.tbcBank.Helpers;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import uz.tbcBank.test.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Listener implements ITestListener {
    public Config conf = Config.getInstance(); // load config reader

    /**
     * The function captures a screenshot of a test failure, saves it to a specified location, and adds it to an Extent
     * Report along with a video if video recording is enabled.
     *
     * @param result The parameter "result" is of type ITestResult, which is an interface provided by TestNG. It represents
     *               the result of a test method execution. It contains information about the test method, the test class, the test
     *               status (pass/fail/skip), and any associated exceptions or failures.
     */
    @Override
    public void onTestFailure(ITestResult result) {

        File file = BaseTest.getDriver().getScreenshotAs(OutputType.FILE);

        String imagePath = "report/screenshots" + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

        try {
            FileUtils.copyFile(file, new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageUrl = "screenshots" + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
        String videoUrl = "/Videos" + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".mp4";


        if (Objects.equals(conf.read("recordVideo"), "true")) {
            ExtentReport.getTest().fail("<video width=\"320\" height=\"240\" controls>\n" +
                    "  <source src=\"" + videoUrl + "\" type=\"video/mp4\">\n" +
                    "  Your browser does not support the video tag.\n" +
                    "</video>");
        } else {
            ExtentReport.getTest().fail("if you want to also video record - please enable it, from config file ", MediaEntityBuilder.createScreenCaptureFromPath(imageUrl).build());
        }
        ExtentReport.getTest().fail(result.getThrowable());
    }

    /**
     * The function retrieves the operating system name and the user name, and then starts a test in an Extent Report with
     * the test name and author assigned as the user name.
     *
     * @param result The parameter "result" is of type ITestResult, which is an interface provided by TestNG. It represents
     *               the result of a test method execution.
     */
    @Override
    public void onTestStart(ITestResult result) {
        String osName = System.getProperty("os.name");
        String userName;

        if (osName.startsWith("Windows")) {
            // For Windows
            userName = System.getProperty("user.name");
        } else if (osName.startsWith("Mac")) {
            // For Mac
            userName = System.getProperty("user.home").substring(System.getProperty("user.home").lastIndexOf("/") + 1);
        } else {
            // For other operating systems, assuming Unix-like
            userName = System.getProperty("user.name");
        }
        ExtentReport.startTest(result.getName(), result.getMethod().getDescription()).assignAuthor(userName);
    }

    /**
     * The function logs a pass status for a test in an Extent Report.
     *
     * @param result The parameter "result" is an object of type ITestResult. It represents the result of a test execution.
     *               It contains information about the test method, the test class, the test status (pass or fail), and any additional
     *               details or attributes associated with the test.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.getTest().log(Status.PASS, "Test Passed");

    }

    /**
     * The function logs a test as skipped in an Extent Report.
     *
     * @param result The parameter "result" is an object of type ITestResult, which represents the result of a test method
     *               execution. It contains information about the test method, such as its name, status, and any associated exception or
     *               failure.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");

    }

    /**
     * The function is an override method that is called when a test fails but still falls within the success percentage.
     *
     * @param result The parameter "result" is an object of type ITestResult. It represents the result of a test method
     *               execution. It contains information about the test method, such as its name, status (pass/fail), and any associated
     *               exception or error.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    /**
     * The onStart method is a callback method that is called before the start of a test context.
     *
     * @param context The context parameter in the onStart method is an object of type ITestContext. It represents the test
     *                context, which provides information about the current test run. It contains methods and properties to access
     *                information such as test suite name, test name, test parameters, test results, etc.
     */
    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }


    // The `onFinish` method is called when the execution of all the test methods in a test class is completed. In this
    // method, the `ExtentReport.getReporter().flush()` is called to flush the ExtentReports reporter and write all the
    // test information to the report file. This ensures that all the test results are properly recorded and saved in the
    // report.
    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.getReporter().flush();
    }

}
