/**
 * The Utils class provides various helper methods for logging, waiting for element visibility, interacting with web
 * elements, and retrieving attribute values and text.
 */
package uz.tbcBank.Helpers;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uz.tbcBank.test.BaseTest;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    private static final String LOG_FILE_NAME = "app.log";

    static Date date = new Date();



    /**
     * The log function logs a message to the ExtentReport, console, and a log file with the current date and time.
     *
     * @param status The "status" parameter is of type "Status" and represents the log level or status of the log message.
     *               It is used to categorize the log message based on its severity or importance. The "Status" class is likely an
     *               enumeration or a custom class that defines different log levels such as INFO
     * @param txt    The "txt" parameter is a string that represents the log message that you want to log. It can be any text
     *               that you want to include in the log.
     */
    public static void log(Status status, String txt) {
        if (ExtentReport.getTest() != null) {
            ExtentReport.getTest().log(status, txt);
        }
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Log to console
        LOGGER.log(Level.INFO, txt);
        // Append log to file with caller information
        try (FileWriter fw = new FileWriter(LOG_FILE_NAME, true)) {
            fw.write("[" + dateFormat.format(date) + "] : " + txt + "\n");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not log to file", e);
        }
    }


    /**
     * The function waits for a web element to become visible on the page.
     *
     * @param e The parameter "e" is a WebElement object, which represents an element on a web page. It is used to specify
     *          the element for which we want to wait for visibility.
     */
    public static void waitForVisibility(WebElement e) {
        Config conf = Config.getInstance(); // load config reader
        WebDriverWait wait = new WebDriverWait(BaseTest.getDriver(), Duration.ofSeconds(Long.parseLong(conf.read("wait"))));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    /**
     * The function clears the text input of a given WebElement after waiting for it to be visible.
     *
     * @param e The parameter "e" is of type WebElement. It represents the web element that you want to clear.
     */
    public static void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    /**
     * The function waits for the visibility of a web element and then clicks on it.
     *
     * @param e The parameter "e" is of type WebElement. It represents the web element that needs to be clicked.
     */
    public static void click(WebElement e) {
        waitForVisibility(e);
        e.click();
    }

    /**
     * The function "click" waits for the visibility of a web element, logs an informational message, and then clicks on
     * the element.
     *
     * @param e   The parameter "e" is a WebElement, which represents an element on a web page that can be interacted with,
     *            such as a button or a link.
     * @param msg The "msg" parameter is a string that represents the message or description of the click action. It is
     *            used to provide additional information or context about the click operation being performed on the WebElement.
     */
    public static void click(WebElement e, String msg) {
        waitForVisibility(e);
        log(Status.INFO, msg);
        e.click();
    }

    /**
     * The function sends a given text to a web element after waiting for it to be visible.
     *
     * @param e   The parameter "e" is a WebElement, which represents an element on a web page. It can be any HTML element
     *            such as input field, button, dropdown, etc.
     * @param txt The "txt" parameter is a string that represents the text that you want to send to the WebElement.
     */
    public static void sendKeys(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
        log(Status.INFO, "text is: " + txt);
    }

    /**
     * The function sends keys to a web element after waiting for it to be visible and logs an information message.
     *
     * @param e   The parameter "e" is a WebElement, which represents an element on a web page. It can be any HTML element
     *            such as input field, button, dropdown, etc.
     * @param txt The "txt" parameter is a string that represents the text that you want to enter into the WebElement.
     * @param msg The "msg" parameter is a string that represents a message or description of the action being performed.
     *            It is used for logging purposes to provide additional information or context about the action being performed.
     */
    public static void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        log(Status.INFO, msg);
        e.sendKeys(txt);
    }


    /**
     * The function returns the value of the specified attribute of a web element after waiting for it to be visible.
     *
     * @param e         The parameter "e" is of type WebElement, which represents an element on a web page. It can be any HTML
     *                  element such as a button, input field, or a div.
     * @param attribute The attribute parameter is a string that represents the name of the attribute you want to retrieve
     *                  from the WebElement.
     * @return The method is returning the value of the specified attribute of the given WebElement.
     */
    public static String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    /**
     * The function getText retrieves the text from a WebElement, logs an informational message with the text, and returns
     * the text.
     *
     * @param e   The parameter "e" is a WebElement, which represents an element on a web page. It can be any HTML element
     *            such as a button, input field, or paragraph.
     * @param msg The "msg" parameter is a string that represents a message or description of the action being performed.
     *            It is used to provide additional context or information when logging the status of the action.
     * @return The method is returning the text value of the WebElement.
     */
    public static String getText(WebElement e, String msg) {
        String txt = null;
        txt = getAttribute(e, "text");
        log(Status.INFO, msg + txt);
        return txt;
    }


}
