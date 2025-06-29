package tests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pojo.Pet.pet;
import pojo.Pet.tag;
import utilities.configReader;
import utilities.payLoads;
import utilities.scenarioContext;
import utilities.userEndpoints;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PetStoreTest extends payLoads {

    static Faker faker;
    static pet petPayLoad;
    static String temp_filePath = configReader.getProperty("temp_petPayload_filePath");
    static scenarioContext context;

    @BeforeClass
    public static void SetupData(){
        faker = new Faker();
        petPayLoad = new pet();
        context = new scenarioContext();
    }

    @Test
    public void testA_AddNewPet(){
        petPayLoad = createPetPayload("available");
        Response response = userEndpoints.addNewPet(petPayLoad);
        if (response == null) throw  new AssertionError();
        response.then().log().all();
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(petPayLoad.getId(), response.jsonPath().getInt("id"));
        Assert.assertEquals(petPayLoad.getTags().get(0).getId(), response.jsonPath().getInt("tags[0].id"));
        Assert.assertEquals(petPayLoad.getTags().get(0).getName(), response.jsonPath().getString("tags[0].name"));
        //Write response body in the temp file
        context.writeDataToFile(petPayLoad,temp_filePath);
    }

    @Test
    public void testB_UpdateExistingPetDetails(){
        String tagName = "home friendly";
        int tagID = 798699;
        String petStatus = "sold";

        //insert a new Pet details first
        petPayLoad = createPetPayload("available");
        Response response = userEndpoints.addNewPet(petPayLoad);
        if (response == null) throw  new AssertionError();
        response.then().log().all();

        //update status and tag[0] of the Pet
        petPayLoad.setStatus(petStatus);
        tag tag1 = new tag();
        tag1.setId(tagID);
        tag1.setName(tagName);
        petPayLoad.getTags().set(0,tag1);

        //Act
        response = userEndpoints.updateExistingPetDetails(petPayLoad);
        if (response == null) throw  new AssertionError();
        response.then().log().all();
        //Assert
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(petPayLoad.getId(), response.jsonPath().getInt("id"));
        Assert.assertEquals(tagID, petPayLoad.getTags().get(0).getId());
        Assert.assertEquals(tagName, petPayLoad.getTags().get(0).getName());
        Assert.assertEquals(petStatus, petPayLoad.getStatus());


    }

}
