package stepDefinitions;
import static org.hamcrest.Matchers.equalTo;
import com.github.javafaker.Faker;
import static org.junit.Assert.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.user;
import pojo.Pet.category;
import pojo.Pet.pet;
import pojo.Pet.tags;
import utilities.scenarioContext;
import utilities.userEndpoints;
import io.cucumber.java.en.Then;


public class stepDefinitions {
	
	Faker faker = new Faker();
	user userPayload = new user();
	user updateUserPayloadUser = new user();
	pet petpayload = new pet();
	Response response;
	scenarioContext context = new scenarioContext();
	public static String userPayload_filePath = "src/test/resources/TempData/userPayload.json";
	public static String petPayload_filePath = "src/test/resources/TempData/petPayload.json";
	
	
	@Given("The create new user payload is created with dummy data")
	public void the_create_new_user_payload_is_created_with_dummy_data() {
				
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(7, 10, false));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		context.writeDataToFile(userPayload, userPayload_filePath);		
	}
	
	@Given("The Add new pet to the store payload is created with dummy data")
	public void the_add_new_pet_to_the_store_payload_is_created_with_dummy_data() {
	    
		//set Pet Id
		petpayload.setId(faker.idNumber().hashCode());
		
		//set Pet Category  
	    category category1 = new category(); 
	    category1.setId(faker.number().randomDigit());
	    category1.setName(faker.funnyName().name());	    
	    petpayload.setCategory(category1);
	    
	    //set Pet Name
	    petpayload.setName(faker.animal().name());
	    
	    //set PhotoUrls
	    List<String> photoUrList = new ArrayList<>();
	    photoUrList.add("");
	    petpayload.setPhotoUrls(photoUrList);
	    
	    //set Pet Tags
	    List<tags> tagsList = new ArrayList<>();	    
	    tags tag1 = new tags(); 
	    tag1.setId(faker.number().randomDigit());
	    tag1.setName(faker.funnyName().name());
	    
	    tags tag2 = new tags();
	    tag2.setId(faker.number().randomDigit());
	    tag2.setName(faker.funnyName().name());
	    
	    tagsList.add(tag1);
	    tagsList.add(tag2);	    
	    petpayload.setTag(tagsList);
	    
	    //set Pet Status
	    petpayload.setStatus("available");
	    context.writeDataToFile(petpayload, petPayload_filePath);
	}
	
	@When("The user trigger Api call {string} with {string} http request")
	public void the_user_trigger_api_call_with_http_request(String callType, String requestType) {
		callType = callType.toLowerCase();
		if(callType.contains("create new user")) {
			response = userEndpoints.createUser(userPayload);
		}
		else if (callType.contains("update user")) {
			response = userEndpoints.updateUser(updateUserPayloadUser.getUsername(), updateUserPayloadUser);
		}	
		else if (callType.contains("delete user")) {
			response = userEndpoints.deleteUser(updateUserPayloadUser.getUsername());
		}
		else if (callType.contains("add new pet")) {
			response = userEndpoints.addNewPet(petpayload);
		}
    	response.then().log().all();
	}
	
	@Then("The Api call should be succeeded with status code {int}")
	public void the_api_call_should_be_succeeded_with_status_code(Integer int1) {
    	assertEquals(response.getStatusCode(), 200);
	}
	
	@Then("{string} in response body should be {string}")
	public void in_response_body_should_be(String key, String value) {
	    
		//if value is username  then get its value from json data
		if(value.toLowerCase().contains("username")) {
	    	value = updateUserPayloadUser.getUsername(); 
	    }
		if(value.toLowerCase().contains("petname")) {
	    	value = petpayload.getName(); 
	    }
		response.then().assertThat().body(key, equalTo(value));
	}
	
	@Given("The details of the previous user are noted")
	public void the_details_of_the_previous_user_are_noted() {
		updateUserPayloadUser = context.readDataFromFile(user.class, userPayload_filePath);
		System.out.print(updateUserPayloadUser.getLastName());
	}

	@Given("Update {string} and {string} in the request body")
	public void update_and(String input1, String input2) {
		updateUserPayloadUser.setLastName(input1);
		updateUserPayloadUser.setEmail(input2);
		System.out.print(updateUserPayloadUser.getLastName());
	    System.out.print(updateUserPayloadUser.getEmail());
	}

	@Then("{string} and {string} should be updated")
	public void and_should_be_updated(String value1, String value2) {
	    response = userEndpoints.readUser(updateUserPayloadUser.getUsername());
	    response.then().assertThat().body("lastName", equalTo(value1));
	    response.then().assertThat().body("email", equalTo(value2));  
	    System.out.print(updateUserPayloadUser.getLastName());
	    System.out.print(updateUserPayloadUser.getEmail());
	}
	
	@Then("User record should be deleted")
	public void user_record_should_be_deleted() {
		response = userEndpoints.readUser(updateUserPayloadUser.getUsername());
		assertEquals(response.getStatusCode(), 404);
		System.out.print(response.getStatusCode());
		response.then().assertThat().body("message", equalTo("User not found"));
	}


}
