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

public class WireMockTest2 {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final WireMockServer server = new WireMockServer(PORT);

    @BeforeClass
    public static void setup() {
        server.start();
        WireMock.configureFor(HOST, PORT);

        ResponseDefinitionBuilder mockResponseBuilder = new ResponseDefinitionBuilder();
        mockResponseBuilder.withStatus(200);
        mockResponseBuilder.withHeader("Content-Type", "application/json");
        mockResponseBuilder.withBodyFile("json/productsMock.json");

        server.stubFor(WireMock.get(WireMock.urlEqualTo("/api/products")).willReturn(mockResponseBuilder));

    }

    @Test
    public void testcode1(){
        String testApi = "http://" + HOST + ":" + PORT + "/api/products";
        System.out.println("System to hit Test API: " + testApi);

        Response response = RestAssured
                .given()
                .get(testApi)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Response: " + response.getBody().jsonPath().getString("response.jsonBody.name"));
        Assert.assertEquals("application/json", response.header("Content-Type"));
        Assert.assertEquals("Hello World, This is a mock response", response.getBody().jsonPath().getString("response.message"));
        Assert.assertTrue(response.getBody().jsonPath().getString("response.jsonBody.name").contains("Wireless Mouse"));

    }

    @AfterClass
    public static void teardown() {
        if(server.isRunning() && server != null) {
            System.out.println("WireMock server is shutting down");
            server.shutdown();
        }
    }


}
