/**
 * It reads a key from a properties file and returns the value
 */
package uz.tbcBank.Helpers;

import java.io.*;
import java.util.Properties;

public class Config extends Properties {
    private static volatile Config instance;

    // The `private Config()` constructor is a private constructor of the `Config` class. It is called when an instance of
    // the `Config` class is created.
    private Config() {
        load();
    }

    /**
     * The load() function loads the properties from a config file named "config.properties".
     */
    private void load() {
        try (InputStream inputStream = new FileInputStream("config.properties")) { // config file location
            load(inputStream);
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework
        }
    }

    /**
     * Retrieves the value associated with the specified key from the properties file.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key
     */
    public String read(String key) {
        return getProperty(key);
    }

    /**
     * The function returns the instance of the Config class, creating it if it doesn't already exist.
     *
     * @return The instance of the Config class.
     */
    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }
}
