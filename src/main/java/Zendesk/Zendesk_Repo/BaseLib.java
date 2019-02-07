package Zendesk.Zendesk_Repo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

public class BaseLib {

	public Response baseURI(String method,String body,int record) throws ParseException
	{
		try
		{
			//Declaration
			String methodName=method.toLowerCase();
			String URI="https://Sam319.zendesk.com/api/v2/tickets.json";
			Response response=null;
			String userName= "syedsuhailg@gmail.com";
			String passWord="Mazher2@";
			String content= "application/json";
			String APIKey="GpdqsNkBn5ut9z1cEf6CHRFiTE3C0HGk4r7t2dce";
			
			// Building the response
			RequestSpecification request=RestAssured.given();
			request.header("apiKey",APIKey);
			request.header("Content-Type",content);
			request.auth().basic(userName,passWord);		

			//Appending Body for Post and Put Methods 
			if(body !="") {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
				request.body(jsonObject);
			}

			//Updating the End point for Delete and Put Methods
			if(record!=0)
				URI="https://Sam319.zendesk.com/api/v2/tickets/"+record+".json";
			// https://Sam319.zendesk.com/api/v2/tickets?record=1

			if(methodName.equals("get"))
			{
				 response= request.get(URI);
			}

			else if(methodName.equals("post"))
			{
				 response= request.post(URI);
			}

			else if(methodName.equals("put"))
			{
				 response= request.put(URI);
			}

			else if(methodName.equals("delete"))
			{
				 response= request.delete(URI);
			}
			
			//return errorMessage(statusCode);
			return response;

		}
		catch (Exception e) 
		{
			Response res=null;
		    errorMessage(res.getStatusCode());
		    return res;
		}

	}
	
	public String errorMessage(int code)
	{
		//Custom the status response status code
		switch(code)
		{
			case 200:
				return "200 Sucessful";
			case 201:
				return "Ticket Creation was sucessful";
			case 204:
				return "204 Record Deleted";
			case 400:
				return "Bad Request - the HTTP request that was sent to the server has invalid syntax";
			case 401:
				return "Unauthorized - the user trying to access the resource has not been authenticated";
			case 403:
				return "Forbidden - the user made a valid request but the server is refusing to serve the request";
			case 404:
				return "Not Found - unable to locate the requested file or resource";
			case 500:
				return "Internal Server Error - the server cannot process the request for an unknown reason";
			case 503:
				return "Service Unavailable - the server is overloaded or under maintenance";
			case 504:
				return "Gateway Timeout -  the server is a not receiving a response from the backend servers within the allowed time period";
			default:
				return "Unknown error occured";
		}
	}

}
