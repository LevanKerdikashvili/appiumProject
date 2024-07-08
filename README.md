# Appium Test Automation Project

This project is designed for automated testing of mobile applications on Android and iOS platforms using Appium.

## Project Structure

- **.gitignore:** Specifies intentionally untracked files to ignore.
- **README.md:** Project documentation.
- **config.properties:** Configuration file for setting up test parameters.
- **defaultNG.xml:** TestNG configuration file.
- **pom.xml:** Maven project file containing project dependencies and build configuration.
- **src/test/java/uz/tbcBank:**
    - **Helpers:** Contains helper classes like `Config.java`, `ExtentReport.java`, `Listener.java`, `Utils.java`, `VideoRecordUtils.java`.
    - **pages:** Contains page classes representing different screens of the application.
        - **teamName/simpleModule:**
            - `LoginPage.java`: Represents the login page.
    - **test:** Contains test classes.
        - **teamName/simpleModule:**
            - `LoginTest.java`: Contains tests for the login functionality.
- **src/test/resources:**
    - **app:** Contains application files for different environments (dev, qa).
    - **env:** Contains environment-specific configuration files (dev.xml, qa.xml).

## Technologies and Design Patterns

- **Programming Language:** Java
- **Build Tool:** Maven
- **Test Framework:** TestNG
- **Automation Tool:** Appium
- **Design Patterns Used:**
    - **Page Object Model (POM):** Helps in creating an object repository for web elements.
    - **Singleton Pattern:** Ensures a class has only one instance and provides a global point of access to it.
    - **Factory Pattern:** Used for creating instances of classes.

## Prerequisites

1. **Java Development Kit (JDK):** Ensure JDK is installed and JAVA_HOME is set.
2. **Maven:** Ensure Maven is installed and MAVEN_HOME is set.
3. **Appium:** Install Appium globally using Node.js:

    ```bash
    npm install -g appium
    ```

4. **Android SDK:** Ensure Android SDK is installed and ANDROID_HOME is set.
5. **Xcode (for iOS):** Ensure Xcode is installed and properly configured.

## Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/LevanKerdikashvili/appiumProject.git
    cd appiumProject
    ```

2. **Install dependencies:**

    ```bash
    mvn install
    ```

## Configuration

Edit the `config.properties` file to set up your test parameters:

    ```properties
    appium.server.url=http://localhost:4723/wd/hub
    android.app.path=src/test/resources/app/dev/androidApp.apk
    ios.app.path=src/test/resources/app/dev/iosApp.app
    ```

- **appium.server.url:** The URL of the Appium server.
- **android.app.path:** Path to the Android application file.
- **ios.app.path:** Path to the iOS application file.

## Running Tests

You can run the tests using the following command:

    ```bash
    mvn test
    ```

### Running Tests with Parameters

You can specify parameters such as platform (Android or iOS) when running tests:

    ```bash
    mvn test -Dplatform=Android
    ```

## Test Reports

Test reports will be generated in the `test-output` directory of the project.

## Helper Classes

- **Config.java:** Contains methods for reading configuration properties.
- **ExtentReport.java:** Manages test reporting using ExtentReports.
- **Listener.java:** Implements TestNG listeners for logging and reporting.
- **Utils.java:** Utility methods for common test tasks.
- **VideoRecordUtils.java:** Handles video recording of test sessions.

## Page Classes

- **LoginPage.java:** Represents the login page of the application and contains methods for interacting with the login screen.

## Test Classes

- **BaseTest.java:** Base class for all test classes. Initializes the Appium driver and manages test setup and teardown.
- **LoginTest.java:** Contains tests for the login functionality, verifying various login scenarios.

## Contributing

To contribute to this project, follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
