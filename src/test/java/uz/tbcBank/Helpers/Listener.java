package uz.tbcBank.Helpers;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import uz.tbcBank.test.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static uz.tbcBank.test.BaseTest.getPlatform;

public class Listener implements ITestListener {
    public Config conf = Config.getInstance(); // load config reader

    @Override
    public void onTestFailure(ITestResult result) {
        handleTestResult(result, "fail");
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

        // Setting the suite and test names in the report
        ExtentReport.getTest().assignCategory(result.getTestContext().getSuite().getName());
        ExtentReport.getTest().assignCategory(result.getTestContext().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        handleTestResult(result, "pass");
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

    private void handleTestResult(ITestResult result, String status) {
        File file = BaseTest.getDriver().getScreenshotAs(OutputType.FILE);
        String base64Image = "";
        try {
            base64Image = Utils.encodeFileToBase64Binary(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64ImageTag = "<img src='data:image/png;base64, " + base64Image + "'  style='width:320px'/>";

        String platformPath = getPlatform() + File.separator + result.getTestClass().getRealClass().getPackage().getName().replace('.', File.separatorChar) + File.separator + result.getTestClass().getRealClass().getSimpleName();

        String videoDirPath = "report/videos" + File.separator + platformPath;
        String videoPath = "videos" + File.separator + platformPath;

        File videoDir = new File(videoDirPath);
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }

        String videoUrl = videoPath + File.separator + result.getName() + ".mp4";
        if (Objects.equals(conf.read("recordVideo"), "true")) {
            ExtentReport.getTest().log(Status.valueOf(status.toUpperCase()), "<video width=\"320\" height=\"240\" controls>\n" +
                    "  <source src=\"" + videoUrl + "\" type=\"video/mp4\">\n" +
                    "  Your browser does not support the video tag.\n" +
                    "</video>");
        } else {
            ExtentReport.getTest().log(Status.valueOf(status.toUpperCase()), base64ImageTag);
        }
    }
}
