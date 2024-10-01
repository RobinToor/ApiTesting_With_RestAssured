package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configReader {

	private static Properties properties;

	// Static block to load the properties when the class is first accessed
	static {
		try {
			FileInputStream fileInput = new FileInputStream("src/main/java/config.properties");
			properties = new Properties();
			properties.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to get a property by key
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
