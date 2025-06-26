package tests;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import pojo.user.user;
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
		if (response == null) throw new AssertionError();
		response.then().log().all();
		System.out.println(userPayload.getUsername());
    	Assert.assertEquals(200, response.getStatusCode());
    }
    
	@Test
    public void testB_GetUser2() throws InterruptedException {
		try{
			Thread.sleep(4000);
			Response response = userEndpoints.readUser(userPayload.getUsername());
			if (response == null) throw new AssertionError();
			response.then().log().all();
			System.out.println(userPayload.getUsername());
			Assert.assertEquals(200, response.getStatusCode());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
    }

	@Test
	public void testC_UpdateUser(){
		faker = new Faker();
		String previousEmail = userPayload.getEmail();
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Response response = userEndpoints.updateUser(userPayload.getUsername(),userPayload);
        if (response == null) throw new AssertionError();
		response.then().log().all();
		Assert.assertEquals(200, response.statusCode());
		Assert.assertNotEquals(previousEmail,userPayload.getEmail());
		System.out.println(previousEmail);
		System.out.println(userPayload.getEmail());
	}


	@Test
	public void testD_DeleteUser()
	{

		Response response = userEndpoints.deleteUser(userPayload.getUsername());
		if (response == null) throw  new AssertionError();
		response.then().log().all();
		Assert.assertEquals("Assertion failed",200, response.getStatusCode());
		Assert.assertEquals(userPayload.getUsername(),response.jsonPath().getString("message"));

		System.out.println("Now validate by calling get user to check the record exists");
		response = userEndpoints.readUser(userPayload.getUsername());
		if (response == null) throw  new AssertionError();
		response.then().log().all();
        Assert.assertEquals(404,response.getStatusCode());
		Assert.assertEquals("User not found",response.jsonPath().getString("message"));
	}

}
