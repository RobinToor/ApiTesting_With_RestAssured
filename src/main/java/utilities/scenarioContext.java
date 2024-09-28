package utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class scenarioContext {
	
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> void writeDataToFile(T data, String filePath) {
        try 
        {
            File file = new File(filePath);
            // Create the file and its parent directories if they do not exist
            file.getParentFile().mkdirs();
            objectMapper.writeValue(file, data);
        } 
        catch (IOException e) 
        {      	
            e.printStackTrace(); // Handle exception
        }
    }

    public <T> T readDataFromFile(Class<T> clazz, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return objectMapper.readValue(file, clazz);
            } else {
                System.out.println("File not found: " + filePath);
                return null;
            }
        } catch (IOException e) 
        {
        	// Handle exception
            e.printStackTrace(); 
            return null;
        }
    }
}
