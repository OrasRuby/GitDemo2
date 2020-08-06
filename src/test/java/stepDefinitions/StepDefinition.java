package stepDefinitions;

import static io.restassured.RestAssured.given;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.DataBuild;
import resources.Utils;

public class StepDefinition extends Utils {
	Response response;
	RequestSpecification res;
	ResponseSpecification resspec;
	DataBuild data = new DataBuild();
	static String place_id;
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address)throws IOException {
	  				 
		res=given().spec(requestBuilder())
		.body(data.addPlacePayload(name, language, address));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httMethod) {
		
		//constructor will be called with value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resspec =new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		if(httMethod.equalsIgnoreCase("Post"))
		{
		response =res.when().post(resourceAPI.getResource()); //using post method
		}
				//then().spec(resspec).extract().response();
		else if(httMethod.equalsIgnoreCase("GET"))
			response =res.when().get(resourceAPI.getResource()); //using get method
	}
	
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(),200);
	}
	
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
	    // Write code here that turns the phrase above into concrete actions
	    
	    assertEquals(getJsonPath(response, key),value);
	   
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expected_name, String httpMethod) throws IOException {
	    
		// requestSpec
		place_id = getJsonPath(response,"place_id");
		res=given().spec(requestBuilder()).queryParam("place_id",place_id);
		user_calls_with_http_request(httpMethod, "GET"); //new response will be created
		String actual_name = getJsonPath(response,"name");
		assertEquals(actual_name, expected_name);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		res=given().spec(requestBuilder()).body(data.deletePlaceAPI(place_id));
		
	}

	
	
}
