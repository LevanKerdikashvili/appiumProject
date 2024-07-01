/**
 * The Utils class provides various helper methods for logging, waiting for element visibility, interacting with web
 * elements, and retrieving attribute values and text.
 */
package uz.tbcBank.Helpers;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uz.tbcBank.test.BaseTest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uz.tbcBank.test.BaseTest.*;

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


    public static String encodeFileToBase64Binary(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStream.read(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }


    /**
     * Method to restart the application on both Android and iOS platforms.
     */
    public static void restartApp() {
        try {
            String platformName = getPlatform();
            if (platformName.equalsIgnoreCase("android")) {
                ((AndroidDriver) getDriver()).terminateApp(conf.read("appPackage"));
                ((AndroidDriver) getDriver()).activateApp(conf.read("appPackage"));
            } else if (platformName.equalsIgnoreCase("ios")) {
                ((IOSDriver) getDriver()).terminateApp(conf.read("iOSBundleId"));
                ((IOSDriver) getDriver()).activateApp(conf.read("iOSBundleId"));
            }
            log(Status.INFO, "Application restarted successfully on " + platformName);
        } catch (Exception e) {
            log(Status.FAIL, "Failed to restart the application: " + e.getMessage());
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
        switch (BaseTest.getPlatform()) {
            case "android":
                txt = getAttribute(e, "text");
                break;
            case "ios":
                txt = getAttribute(e, "label");
                break;
        }

        log(Status.INFO, msg + txt);
        return txt;
    }


    /**
     * The `launchApp` function activates the specified mobile app based on the platform (Android or iOS) retrieved from
     * `BaseTest`.
     */
    public void launchApp() {
        log(Status.INFO, "launch App");
        switch (BaseTest.getPlatform()) {
            case "android":
                ((InteractsWithApps) getDriver()).activateApp(conf.read("appPackage"));
                break;
            case "ios":
                ((InteractsWithApps) getDriver()).activateApp(conf.read("iOSBundleId"));
        }
    }

    /**
     * The `closeApp` function terminates the currently running mobile application based on the platform (Android or iOS)
     * specified in the test configuration.
     */
    public void closeApp() {
        log(Status.INFO, "close app");
        switch (BaseTest.getPlatform()) {
            case "android":
                ((InteractsWithApps) getDriver()).terminateApp(conf.read("appPackage"));
                break;
            case "ios":
                ((InteractsWithApps) getDriver()).terminateApp(conf.read("iOSBundleId"));
        }
    }


    /**
     * The function `scrollToEndAndroid` scrolls to the end of a page on an Android device using a scroll gesture until it
     * can no longer scroll further.
     */
    public static void scrollToEndAndroid() {
        log(Status.INFO, "Android scrollToEnd");
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) getDriver().executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0,
                    "speed", 1000

            ));
        } while (canScrollMore);
    }

    /**
     * The function `scrollToTextAndroid` scrolls to a specific text element on an Android app using Appium.
     *
     * @param text The `text` parameter in the `scrollToTextAndroid` method represents the text that you want to scroll to
     *             within the Android application. This text is used to locate the element within the UI and scroll it into view so
     *             that it becomes visible on the screen.
     */
    public static void scrollToTextAndroid(String text) {
        log(Status.INFO, "android scrollToText - text is: " + text);
        getDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
    }


    /**
     * The `swipeActionAndroid` function uses JavaScript to perform a swipe gesture on a specified WebElement in a given
     * direction with a 75% swipe distance.
     *
     * @param ele       The `ele` parameter in the `swipeActionAndroid` method is the element on which you want to perform the
     *                  swipe action. It is of type `WebElement` and represents the element on the Android app screen that you want to
     *                  swipe.
     * @param direction The `direction` parameter in the `swipeActionAndroid` method specifies the direction in which the
     *                  swipe gesture should be performed. It could be values like "up", "down", "left", or "right" based on the desired
     *                  swipe direction on the Android device.
     */
    public static void swipeActionAndroid(WebElement ele, String direction) {
        log(Status.INFO, "android swipe - direction is: '" + direction + "' and element is " + ele);
        ((JavascriptExecutor) getDriver()).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) ele).getId(),
                "direction", direction,
                "percent", 0.75
        ));
    }

    /**
     * The function `swipeActionIos` takes a direction as input and performs a swipe action on an iOS device using Appium.
     *
     * @param direction The `direction` parameter in the `swipeActionIos` method specifies the direction in which the swipe
     *                  action should be performed. It could be values like "up", "down", "left", or "right" indicating the direction of the
     *                  swipe gesture on an iOS device.
     */
    public static void swipeActionIos(String direction) {
        log(Status.INFO, "ios swipe - direction is: " + direction);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("direction", direction);
        getDriver().executeScript("mobile:swipe", params);
    }


    /**
     * The function `parseStringXML` reads an XML file containing string elements, extracts the key-value pairs, and stores
     * them in a HashMap.
     *
     * @param file The `file` parameter in the `parseStringXML` method is an `InputStream` that represents an XML file
     *             containing string elements with attributes "name" and text content. This method parses the XML file and extracts the
     *             string elements along with their corresponding attribute values and text content, storing them in a
     * @return The method `parseStringXML` returns a `HashMap<String, String>` containing key-value pairs parsed from an
     * XML file input stream. The keys are extracted from the "name" attribute of the "string" elements in the XML, and the
     * corresponding values are the text content of those elements.
     */
    public static HashMap<String, String> parseStringXML(InputStream file) throws Exception {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(file);

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();

        //Get all elements
        NodeList nList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                // Store each element key value in map
                stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
            }
        }
        return stringMap;
    }


    public static void receiveSMS(String phoneNumber, String message) throws IOException {
        // Construct the adb command
        String command = String.format("adb emu sms send \"%s\" %s", phoneNumber, message);

        // Execute the command
        Process process = Runtime.getRuntime().exec(command);
        log(Status.INFO, "receive SMS command: " + command);
        try {
            process.waitFor();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
