/**
 * The VideoRecordUtils class is a helper class in Java that provides methods for starting and stopping video recording
 * during test execution and saving the recorded video file.
 */
package uz.tbcBank.Helpers;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.codec.binary.Base64;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


public class VideoRecordUtils {
    private final AndroidDriver driver;
    public static Config conf = Config.getInstance(); // load config reader


    public VideoRecordUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * The startRecording() function starts recording the screen if the "recordVideo" configuration value is set to "true".
     */
    public void startRecording() {
        if (Objects.equals(conf.read("recordVideo"), "true")) {
            driver.startRecordingScreen();
        }
    }


    /**
     * The function "stopRecording" checks if video recording is enabled and if a test has failed, and if so, it stops the
     * recording and saves the video file in a specified directory.
     *
     * @param result The "result" parameter is an object of type ITestResult, which is typically used in testing frameworks
     *               like TestNG or JUnit to represent the result of a test method execution. It contains information about the test
     *               method, its status (passed, failed, skipped, etc.), and any associated data
     */
    public void stopRecording(ITestResult result) {
        if (Objects.equals(conf.read("recordVideo"), "true")) {
            /* Do whatever only when Test is failed */
            if (result.getStatus() == 2) {
                String media = driver.stopRecordingScreen();
                String dir = "videos" + File.separator
                        + result.getTestClass().getRealClass().getSimpleName();
                createDirectoryAndCopyFile(result, media, dir);
            }
        }
    }

    /**
     * The function creates a directory if it doesn't exist and copies a file to that directory.
     *
     * @param result The "result" parameter is an object of type ITestResult, which is typically used in testing frameworks
     *               like TestNG or JUnit to represent the result of a test method execution. It contains information about the test
     *               method, such as its name, status, and any associated data.
     * @param media  The "media" parameter is a Base64 encoded string representing the video file that needs to be copied.
     * @param dir    The "dir" parameter is the directory path where the file will be created.
     */
    private void createDirectoryAndCopyFile(ITestResult result, String media, String dir) {
        File videoDir = new File(dir);
        if (!videoDir.exists()) {
            videoDir.mkdirs();
        }
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            stream.write(Base64.decodeBase64(media));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
