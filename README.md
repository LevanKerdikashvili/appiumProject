# appium - test automation (Front - Android)

-----------------------------------------------------------
## Technology: <br>
* Automation Framework: Appium <br>
* Build tool: Maven <br>
* Bundled Tools: TestNG
* Language: Java <br>
* Report: extent <br>
* Project Structure: Page object Model(POM)<br>


----------------------------------------------------------

## Prerequisite:
* Intellij idea (Or use any IDE you like that supports Java language programming)
* Java 11+
* npm or other package manager
* Appium Server (2.x) for installation [check installation instruction](#appium-installation)
* Appium Inspector (https://github.com/appium/appium-inspector/releases)
* Android studio Emulator or Real Device  [check installation instruction](#android-studio-setup-emulators--real-device)
----------------------------------------------------------


## Android Studio Setup (emulators + Real Device)
<a name="androidStudio"></a>
Download and install [Android Studio](https://developer.android.com/studio) based on your preferred OS. Then set your ANDROID_HOME by follow this steps:

# Setting ANDROID_HOME Variable

This guide will walk you through setting the `ANDROID_HOME` variable on both Windows and macOS. The `ANDROID_HOME` variable is used by various tools and IDEs for locating the Android SDK installation directory.

1. Locate your Android SDK installation directory. By default, it's typically installed in `C:\Users\YourUsername\AppData\Local\Android\Sdk`.

2. Right-click on "This PC" or "My Computer" and select "Properties".

3. Click on "Advanced system settings" on the left side.

4. In the System Properties window, go to the "Advanced" tab and click on the "Environment Variables" button at the bottom.

5. In the Environment Variables window, under "System variables", click on "New".

6. For the Variable Name, enter `ANDROID_HOME`.

7. For the Variable Value, enter the path to your Android SDK installation directory (e.g., `C:\Users\YourUsername\AppData\Local\Android\Sdk`).

8. Click "OK" to save the variable.

9. Click "OK" again to close the Environment Variables window.

10. You may need to restart your system for the changes to take effect, although restarting your command prompt or IDE may be sufficient.

 **Setting `ANDROID_HOME` on macOS**

1. Open Terminal.

2. Run the command `nano ~/.bash_profile` to open your bash profile (you can also use `~/.zshrc` if you are using Zsh).

3. Add the following line at the end of the file: 

`export ANDROID_HOME=/Users/YourUsername/Library/Android/sdk`


Replace `/Users/YourUsername/Library/Android/sdk` with the path to your Android SDK installation directory.

4. Save the file by pressing `Ctrl` + `X`, then `Y`, and finally press `Enter`.

5. To make the changes take effect, either restart Terminal or run `source ~/.bash_profile` in the terminal.

Remember to replace `YourUsername` with your actual username in the paths specified above.

### Connecting a Real Device for Mobile App Testing with Appium

### Enable Developer Mode on Your Device:
1. **For Android**: Go to Settings > About phone > Software info, then tap "Build number" 7 times to enable Developer Options. Once enabled, go to Settings > Developer options and enable USB debugging.

### Connect Your Device to the Computer:
- Use a USB cable to connect your device to your computer.

### Check Device Connection:
- Open a terminal or command prompt.
    - Run `adb devices`. Your device should be listed.


### Additional variables

For more comfortable, you will also need it during the work process set next variables
    platform-tools <br>
    cmdline-tools<br>
    cmdline-tools/bin <br>
    emulator </br>


### for MAC
`export PATH=$ANDROID_HOME/platform-tools:$PATH` </br>
`export PATH=$ANDROID_HOME/cmdline-tools:$PATH` </br>
`export PATH=$ANDROID_HOME/cmdline-tools/bin:$PATH` </br>
`export PATH=$ANDROID_HOME/emulator:$PATH` </br>

### for Windows 
Use the process which is described above about how to set ANDROID_HOME

warning!
if you use old android studio version, you needs: <br>
`platform-tools` <br>
`tools` <br>
`tools/bin` <br>
`emulator` </br>
instead of<br>
`platform-tools` <br>
`cmdline-tools` <br>
`cmdline-tools/bin` <br>
`emulator` </br>



----------------------------------------------------------
## Setup Android Emulator
Open Android Studio -> More Actions -> Virtual Device Manager -> Create device -> Choose any type of device you want (in this tutorial I use Pixel 4 with Google Play) -> Choose your Android version which we have installed in previous steps (API 33 with Google Play).

<img src="https://miro.medium.com/v2/resize:fit:1200/format:webp/0*7uCXSld03RWzzcuy.gif">

Run your previously created Android emulator.

<img src="https://miro.medium.com/v2/resize:fit:1200/format:webp/0*vQT6IWl7yZtJ8X-c.gif">



----------------------------------------------------------
## Appium installation
<a name="appium"></a>
Open your terminal or cmd then enter command and wait for installation to finish<br/>
`npm install -g appium@next` or will be better `npm install -g appium@2.5`

Install Appium 2 driver for Android using terminal<br/>
`appium driver install uiautomator2`

Check if uiautomator2 driver is installed<br/>
`appium driver list`


Install the plugin using Appium's plugin CLI, either as a named plugin or via NPM:<br/>
`appium plugin install --source=npm appium-device-farm`


`appium plugin install --source=npm appium-dashboard`

The plugin will not be active unless turned on when invoking the Appium server. See "Argument options" below<br/>
`appium server -ka 800 --use-plugins=device-farm,appium-dashboard  -pa /wd/hub --plugin-device-farm-platform=android`
<br/> You can define as well device type `"android" , "ios" or "both"`

<img src="https://github.com/AppiumTestDistribution/appium-device-farm/blob/main/docs/assets/images/demo.gif?raw=true" width="500" />
<br/> you can block/unblock devices from Dashboard manually. These devices will not be picked up for automation.
Once automation picks the device user cannot manually unblock, it's responsible for the automation script.
dashboard will be: <a href="http://localhost:4723/device-farm/" target="_blank">localhost:4723/device-farm</a> 
for more information check <a href="https://appium-device-farm-eight.vercel.app/" target="_blank">Appium Device Farm Doc.</a>

----------------------------------------------------------
## How to use appium inspector:
<img src="https://miro.medium.com/v2/resize:fit:1400/format:webp/0*2kkCMk9fkmouiXNb.png">

**Set Driver Capabilities**

In order for Appium to be able to connect to your device and app, you need to fill in some information about your testing environment such as device and application information.
Find your Android emulator UDID by using terminal and enter this command:

`adb devices` // but if you use device-farm you don't need device UDID

Copy this JSON capabilities into your Appium Inspector

{
"platformName": "Android",<br>
"appium:udid": "yourudid", // if you need this<br>
"appium:appPackage": "com.google.android.youtube",<br>
"appium:appActivity": "com.google.android.youtube.app.honeycomb.Shell$HomeActivity",<br>
"appium:deviceName": "yourdevicename",<br>
"appium:automationName": "UiAutomator2",<br>
"appium:platformVersion": "13",<br>
"appium:autoGrantPermissions": true<br>
}

<img src="https://miro.medium.com/v2/resize:fit:1400/format:webp/0*sYAx27RpKj5ciL4w.png">

Replace “yourudid” in “appium:udid” with your UDID which you found in this previous step. <br>
Replace “yourdevicename” in “appium:deviceName” with you Android emulator name. You can find information about your device name in Android Studio -> Device Manager.

<img src="https://miro.medium.com/v2/resize:fit:1400/format:webp/0*L83WiM9i6uulT7iN.png">

Pixel 4 API 33 is the name of my emulator device.<br>
Actually there are 2 ways to set which application you will automate:<br/>
By using capability of “appium:app”, with this your apk is not pre-installed on your device and will be installed each time your test run. If you are using this type of capability then you don’t need to use “appium:appPackage” and “appium:appActivity” as we used in the previous steps.

<img src="https://miro.medium.com/v2/resize:fit:1200/format:webp/0*W7_CfzJbX4TxW_X8.gif">


----------------------------------------------------------
## Run the Automation Script:
1. Prepare Emulator or real device
2. Run Appium server
3. check config.properties before execute
4. Run the testng.xml file or use mvn command `mvn clean test`.
5. After Complete the test execution Report will generate to "report" Folder if it will be enabled from config


----------------------------------------------------------

## Folder Structure

- `src/`: Contains the source code of the project.
    - `main/`: Contains the main source code files.
        - `commonSteps`: common methods that can be used in different projects
            - `Auth`: class of auth
        - `DateBase`: classes of db connection
        - `Helpers`: classes of Helpers
            - `AllureEnvironmentWritter`: The AllureEnvironmentWriter class contains methods for creating and saving an XML file containing environment variables
            - `Config`: The Config class loads a configuration file and provides a way to read its properties, with a singleton pattern
              implementation
            - `LanguageReader`: The LanguageReader class reads language strings from a JSON file and provides a method to retrieve a specific string based on a given language code and string name
            - `Listener`: It implements the ITestListener interface and overrides all of its methods
            - `ResourceReader`: The ResourceReader class reads a JSON file and returns its contents as a JSONObject
            - `Utils`: The Utils class contains various utility methods
            - `Page`: Java class that contains locators and methods (Page Object)
    - `test/`: Contains the test source code files.
        - `java/`: Contains the Java source files.
          - `uz.tbcBank`: main package 
            - `Helpers`: classes of Helpers
            - `Page/{teamName}/{moduleName}`: Java class that contains locators and methods (Page Object)
            - `test/{teamName}/{moduleName}`: Java class that contains test cases as methods and BaseTest.java
        - `resources/app/app.apk`: .apk file for testing 

- `pom.xml`: Maven configuration file for your project.
- `target/`: Contains compiled classes and packaged artifacts (created after building the project).
- `README.md`: This README file providing an overview of the project.
- `config.properties`: config data.
- `report`: contains reporting files index.html (you can open it in any browser) + screenshots
- `videos`: contains video files when test case is fail
