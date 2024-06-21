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


public class Listener implements ITestListener {
    public Config conf = Config.getInstance(); // load config reader


    @Override
    public void onTestFailure(ITestResult result) {
        File file = BaseTest.getDriver().getScreenshotAs(OutputType.FILE);
        String base64Image = "";
        try {
            base64Image = Utils.encodeFileToBase64Binary(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64ImageTag = "<img src='data:image/png;base64, " + base64Image + "' />";
        ExtentReport.getTest().fail(base64ImageTag);
        ExtentReport.getTest().fail(result.getThrowable());
    }


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


    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.getTest().log(Status.PASS, "Test Passed");

    }


    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");

    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.getReporter().flush();
    }

}
