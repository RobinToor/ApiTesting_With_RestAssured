package stepDefinitions;
import static org.hamcrest.Matchers.equalTo;
import com.github.javafaker.Faker;
import static org.junit.Assert.*;

import java.io.Console;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.user;
import utilities.scenarioContext;
import utilities.userEndpoints;
import io.cucumber.java.en.Then;


public class stepDefinitions {
	
	Faker faker = new Faker();
	user userPayload = new user();
	Response response;
	scenarioContext context = new scenarioContext();
	
	
	@Given("The create new user payload is created with dummy data")
	public void the_create_new_user_payload_is_created_with_dummy_data() {
				
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(7, 10, false));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		context.writeDataToFile(userPayload, "src/test/resources/TempData/userPayload.json");
		
	}
	@When("The user trigger Api call {string} with {string} http request")
	public void the_user_trigger_api_call_with_http_request(String string, String string2) {
		
		response = userEndpoints.createUser(userPayload);
    	response.then().log().all();
	}
	@Then("The Api call should be succeeded with status code {int}")
	public void the_api_call_should_be_succeeded_with_status_code(Integer int1) {
    	assertEquals(response.getStatusCode(), 200);
	}
	@Then("{string} in response body should be {string}")
	public void in_response_body_should_be(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
		response.then().assertThat().body("type", equalTo("unknown"));
		user pUser = context.readDataFromFile(user.class, "src/test/resources/TempData/userPayload.json");
		System.out.print(pUser.getLastName());
	}
	
	@Given("The details of the previous user are noted")
	public void the_details_of_the_previous_user_are_noted() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	@Given("Update <lastname> and <email>")
	public void update_lastname_and_email() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

}
