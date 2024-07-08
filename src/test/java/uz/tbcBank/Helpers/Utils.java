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
    private static final ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<>();
    static Date date = new Date();


    /**
     * Retrieves the strings HashMap for the current thread.
     *
     * @return The strings HashMap.
     */
    public static HashMap<String, String> getStrings() {
        return strings.get();
    }

    /**
     * Sets the strings HashMap for the current thread.
     *
     * @param str The strings HashMap to be set.
     */
    public static void setStrings(HashMap<String, String> str) {
        strings.set(str);
    }

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
     * Encodes the given file to a Base64 encoded string.
     *
     * @param file the file to be encoded
     * @return the Base64 encoded string representation of the file
     */
    public static String encodeFileToBase64Binary(File file) throws IOException {
        // Using try-with-resources to ensure FileInputStream is closed after use
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            // Create a byte array to hold the file data
            byte[] bytes = new byte[(int) file.length()];

            // Read the file data into the byte array
            int bytesRead = fileInputStream.read(bytes);

            // Check if the entire file was read
            if (bytesRead != file.length()) {
                // Log an info message and throw an exception if the entire file could not be read
                log(Status.INFO, "Could not read the entire file");
                throw new IOException("Could not read the entire file");
            }

            // Encode the byte array to a Base64 encoded string and return it
            return Base64.getEncoder().encodeToString(bytes);
        }
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
        log(Status.INFO, "clear element: " + e);
    }

    /**
     * The function waits for the visibility of a web element and then clicks on it.
     *
     * @param e The parameter "e" is of type WebElement. It represents the web element that needs to be clicked.
     */
    public static void click(WebElement e) {
        waitForVisibility(e);
        e.click();
        log(Status.INFO, "click to element: " + e);
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
        e.click();
        log(Status.INFO, msg);
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
        log(Status.INFO, "Send key with text: " + txt);
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
        e.sendKeys(txt);
        log(Status.INFO, msg);
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
        log(Status.INFO, "try to get attribute [" + attribute + "] from element [" + e + " ]");
        return e.getAttribute(attribute);

    }

    /**
     * The function getText retrieves the text from a WebElement, logs an informational message with the text, and returns
     * the text.
     *
     * @param e The parameter "e" is a WebElement, which represents an element on a web page. It can be any HTML element
     *          such as a button, input field, or paragraph.
     * @return The method is returning the text value of the WebElement.
     */
    public static String getText(WebElement e) {
        String txt = switch (BaseTest.getPlatform()) {
            case "android" -> getAttribute(e, "text");
            case "ios" -> getAttribute(e, "label");
            default -> null;
        };
        log(Status.INFO, "try to get text from element [" + e + " ]");
        return txt;
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
        String txt = switch (BaseTest.getPlatform()) {
            case "android" -> getAttribute(e, "text");
            case "ios" -> getAttribute(e, "label");
            default -> null;
        };
        log(Status.INFO, msg + txt);
        return txt;
    }


    /**
     * The `launchApp` function activates the specified mobile app based on the platform (Android or iOS) retrieved from
     * `BaseTest`.
     */
    public void launchApp() {
        switch (BaseTest.getPlatform()) {
            case "android":
                ((InteractsWithApps) getDriver()).activateApp(conf.read("appPackage"));
                break;
            case "ios":
                ((InteractsWithApps) getDriver()).activateApp(conf.read("iOSBundleId"));
        }
        log(Status.INFO, "launch App where appPackage is [" + conf.read("appPackage") + "] and platform is [" + BaseTest.getPlatform() + "]");
    }

    /**
     * The `closeApp` function terminates the currently running mobile application based on the platform (Android or iOS)
     * specified in the test configuration.
     */
    public static void closeApp() {
        String platform = BaseTest.getPlatform();
        String appPackage = conf.read("appPackage");
        log(Status.INFO, "Attempting to close app on platform [" + platform + "] with appPackage [" + appPackage + "]");

        switch (platform) {
            case "android":
                try {
                    ((InteractsWithApps) getDriver()).terminateApp(appPackage);
                    log(Status.INFO, "Successfully terminated app with appPackage [" + appPackage + "]");
                } catch (Exception e) {
                    log(Status.FAIL, "Failed to terminate app on Android. Error: " + e.getMessage());
                }
                break;
            case "ios":
                try {
                    ((InteractsWithApps) getDriver()).terminateApp(conf.read("iOSBundleId"));
                    log(Status.INFO, "Successfully terminated app with iOSBundleId [" + conf.read("iOSBundleId") + "]");
                } catch (Exception e) {
                    log(Status.FAIL, "Failed to terminate app on iOS. Error: " + e.getMessage());
                }
                break;
            default:
                log(Status.FAIL, "Unsupported platform [" + platform + "]");
        }
    }


    /**
     * The function `scrollToEnd` scrolls to the end of a page  using a scroll gesture until it
     * can no longer scroll further.
     */
    public static void scrollToEnd() {
        log(Status.INFO, "Scroll to end on platform: " + BaseTest.getPlatform());

        int screenHeight = getDriver().manage().window().getSize().getHeight();
        int screenWidth = getDriver().manage().window().getSize().getWidth();

        boolean canScrollMore;

        do {
            try {
                if (BaseTest.getPlatform().equalsIgnoreCase("android")) {
                    // get screenSize for android
                    int scrollStartX = screenWidth / 2;
                    int scrollStartY = (int) (screenHeight * 0.1);
                    int scrollEndY = (int) (screenHeight * 0.2);

                    // android
                    Object result = getDriver().executeScript("mobile: scrollGesture", ImmutableMap.of(
                            "left", scrollStartX,
                            "top", scrollStartY,
                            "width", scrollStartX,
                            "height", scrollEndY,
                            "direction", "down",
                            "percent", 3.0,
                            "speed", 2000
                    ));
                    canScrollMore = result != null && (Boolean) result;
                } else if (BaseTest.getPlatform().equalsIgnoreCase("ios")) {
                    // get screen size for IOS
                    int scrollStartX = screenWidth / 2;
                    int scrollStartY = (int) (screenHeight * 0.8);
                    int scrollEndY = (int) (screenHeight * 0.2);

                    // ios
                    Object result = getDriver().executeScript("mobile: scroll", ImmutableMap.of(
                            "direction", "down",
                            "left", scrollStartX,
                            "top", scrollStartY,
                            "width", screenWidth,
                            "height", screenHeight
                    ));
                    canScrollMore = result != null && (Boolean) result;
                } else {
                    throw new UnsupportedOperationException("Unsupported platform: " + BaseTest.getPlatform());
                }
            } catch (Exception e) {
                log(Status.FAIL, "Scroll attempt failed. Error: " + e.getMessage());
                canScrollMore = false;
            }
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


    /**
     * The function `receiveSMS` simulates receiving an SMS on an Android emulator using an ADB command.
     *
     * @param phoneNumber The phone number from which the SMS is being sent.
     * @param message     The message content of the SMS.
     */
    public static void receiveSMSonAndroid(String phoneNumber, String message) throws IOException {
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
