package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	static RequestSpecification req;
	public RequestSpecification requestBuilder() throws IOException
		
	{
		if(req==null) //to make sure not deleting the first test on log file
		{
		
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		//RestAssured.baseURI="https://rahulshettyacademy.com";
			req =new RequestSpecBuilder()
			.setBaseUri(getGlobalValue("baseUrl"))
				//.setBaseUri("https://rahulshettyacademy.com")
			.addQueryParam("key", "qaclick123")
			.addFilter(RequestLoggingFilter.logRequestTo(log))
			.addFilter(ResponseLoggingFilter.logResponseTo(log))
			.setContentType(ContentType.JSON).build();
		return req;
		}
		return req;
	}
	
	public static String getGlobalValue(String str) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fin = new FileInputStream("C:\\Users\\Oras\\eclipse-workspace\\APIFramework\\src\\test\\java\\resources\\global.properties");
		prop.load(fin);
		return prop.getProperty(str);
	}
	
	public String getJsonPath(Response response, String key) {
		String resp = response.asString();
	    JsonPath js = new JsonPath(resp);
	    return js.get(key).toString();
		
	}

}
