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
* Android studio Emulator or Real Device
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
for more information check <a href="https://appium-device-farm-eight.vercel.app/" target="_blank">Appium Device Farm Doc.</a>

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
