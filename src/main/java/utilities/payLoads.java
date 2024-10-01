package utilities;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import pojo.Pet.category;
import pojo.Pet.pet;
import pojo.Pet.tags;
import pojo.user.user;

/**
 * 
 */
public class payLoads {

	Faker faker = new Faker();
	user userPayload = new user();
	user updateUserPayloadUser = new user();
	pet petpayload = new pet();

	public user createUserPayload() {

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(7, 10, false));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		return userPayload;
	}

	
	/**
	 * @return pet payload created to add a new pet to the store
	 */
	public pet createPetPayload() {

		// set Pet Id
		petpayload.setId(faker.idNumber().hashCode());

		// set Pet Category
		category category1 = new category();
		category1.setId(faker.number().randomDigit());
		category1.setName(faker.funnyName().name());
		petpayload.setCategory(category1);

		// set Pet Name
		petpayload.setName(faker.animal().name());

		// set PhotoUrls
		List<String> photoUrList = new ArrayList<>();
		photoUrList.add("");
		petpayload.setPhotoUrls(photoUrList);

		// set Pet Tags
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

		// set Pet Status
		petpayload.setStatus("available");

		return petpayload;
	}
}
