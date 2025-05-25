package tests;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WireMockTest {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final WireMockServer server = new WireMockServer(PORT);

    @BeforeClass
    public static void setup() {
        server.start();
        WireMock.configureFor(HOST, PORT);

        ResponseDefinitionBuilder mockResponseBuilder = new ResponseDefinitionBuilder();
        mockResponseBuilder.withStatus(200);
        mockResponseBuilder.withHeader("status", "success");
        mockResponseBuilder.withStatusMessage("This is a mock response");
        mockResponseBuilder.withHeader("content-type", "application/json");
        mockResponseBuilder.withBody("{\"message\":\"Hello World, This is a mock response \"}");

        server.stubFor(WireMock.get(WireMock.urlEqualTo("/api/v1/users/1")).willReturn(mockResponseBuilder));

    }

    @Test
    public void testcode(){
        String testApi = "http://" + HOST + ":" + PORT + "/api/v1/users/1";
        System.out.println("System to hit Test API: " + testApi);

        Response response = RestAssured
                .given()
                .get(testApi)
                .then()
                .statusCode(200)
                .extract().response();
        Assert.assertEquals("Hello World, This is a mock response ", response.getBody().jsonPath().getString("message"));
        System.out.println("Response: " + response.getBody().jsonPath().getString("message"));
    }

    @AfterClass
    public static void teardown() {
        if(server.isRunning() && server != null) {
            System.out.println("WireMock server is shutting down");
            server.shutdown();
        }
    }


}
