package tests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.Pet.pet;
import utilities.payLoads;
import utilities.userEndpoints;

public class PetStoreTest extends payLoads {

    static Faker faker;
    static pet petPayLoad;

    @BeforeClass
    public static void SetupData(){
        faker = new Faker();
        petPayLoad = new pet();
    }

    @Test
    public void testA_AddNewPet(){
        petPayLoad = createPetPayload("available");
        Response response = userEndpoints.addNewPet(petPayLoad);
        if (response == null) throw  new AssertionError();
        response.then().log().all();
        Assert.assertEquals(200, response.getStatusCode());
    }

}
