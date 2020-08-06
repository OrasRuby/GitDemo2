package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@DeletePlace")
	
	public void beforeScenario() throws IOException
	{
		StepDefinition sd = new StepDefinition();
		if(StepDefinition.place_id==null)
		{
		sd.add_place_payload_with("Shetty", "India", "Asia");
		sd.user_calls_with_http_request("AddPlaceAPI", "Post");
		sd.verify_place_id_created_maps_to_using("Shetty", "getPlaceAPI");
		}
	}
}
