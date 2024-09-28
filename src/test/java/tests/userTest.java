package tests;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.hamcrest.Matchers.equalTo;
import com.github.javafaker.Faker;
import static org.junit.Assert.*;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import pojo.user;
import utilities.userEndpoints;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class userTest {
	
	static Faker faker;
	static user userPayload;
	
	@BeforeClass
	public static void setupData() 
	{
		faker = new Faker();
		userPayload = new user();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(7, 10, false));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}
	
	@Test
    public void testA_PostUser1() 
    {
    	Response response = userEndpoints.createUser(userPayload);
    	response.then().log().all();
    	
    	assertEquals(response.getStatusCode(), 200);
    }
    
	@Test
    public void testB_GetUser2() 
    {
    	Response response = userEndpoints.readUser(this.userPayload.getUsername());
    	response.then().log().all();
    	assertEquals(response.getStatusCode(), 200);
    }
}
