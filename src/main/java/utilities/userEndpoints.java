package utilities;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.user;

public class userEndpoints {

    public static RequestSpecification req;

    // Method to build request specification
    public static RequestSpecification requestSpecification() throws FileNotFoundException {
        if (req == null) {
            // Log request details to both the console and a file
            PrintStream logFile = new PrintStream(new FileOutputStream(new File("logging.txt")));

            req = new RequestSpecBuilder()
                    .setBaseUri(routes.base_url)
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .addFilter(RequestLoggingFilter.logRequestTo(logFile))
                    .addFilter(ResponseLoggingFilter.logResponseTo(logFile))
                    .build();
        }
        return req;
    }

    // Create a new user
    public static Response createUser(user payload) {
        try {
            return given().spec(requestSpecification()) // Use request specification
                    .body(payload)
                    .when()
                    .post(routes.createUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null; // Handle the exception as appropriate
        }
    }

    // Read a user by userName
    public static Response readUser(String userName) {
        try {
            return given().spec(requestSpecification())
                    .pathParam("username", userName)
                    .when()
                    .get(routes.getUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing user
    public static Response updateUser(String userName, user payload) {
        try {
            return given().spec(requestSpecification())
                    .pathParam("username", userName)
                    .body(payload)
                    .when()
                    .put(routes.updateUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Delete a user by username
    public static Response deleteUser(String userName) {
        try {
            return given().spec(requestSpecification())
                    .pathParam("username", userName)
                    .when()
                    .delete(routes.deleteUser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
