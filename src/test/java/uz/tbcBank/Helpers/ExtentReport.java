/**
 * The ExtentReport class is a helper class that provides methods for creating and managing ExtentReports objects for
 * generating test reports.
 */
package uz.tbcBank.Helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import static uz.tbcBank.test.BaseTest.getEnv;

public class ExtentReport {
    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private static ExtentReports extent;


    /**
     * The function returns an instance of the ExtentReports class, creating it if it doesn't already exist.
     *
     * @return The method is returning an instance of the ExtentReports class.
     */
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            ExtentSparkReporter html = new ExtentSparkReporter("report/index.html");

            // CSS style
            String css = ".nav-logo {"
                    + "display: none;"  // remove current logo
                    + "}";

            html.config().setCss(css);
            html.config().setDocumentTitle("Mobile Testing Report");
            html.config().setReportName("Mobile App Testing");
            html.config().setTheme(Theme.DARK);
            extent = new ExtentReports();
            extent.attachReporter(html);
        }

        return extent;
    }

    /**
     * The function returns the current ExtentTest object in a thread-safe manner.
     *
     * @return The method is returning an object of type ExtentTest.
     */
    public static synchronized ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }


    /**
     * The function "startTest" creates a synchronized ExtentTest object with a given testName and desc, sets it in a
     * thread-local variable, and returns the created object.
     *
     * @param testName The name of the test case or scenario. It is used to identify the test in the test report.
     * @param desc     The "desc" parameter is a string that represents the description or details of the test being started.
     *                 It provides additional information about the purpose or objective of the test.
     * @return The method is returning an object of type ExtentTest.
     */
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = getReporter().createTest(testName, desc);
        extentTestThreadLocal.set(test);
        return test;
    }
}