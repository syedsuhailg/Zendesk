package Zendesk.Zendesk_Repo;

import static org.testng.Assert.assertEquals;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.restassured.response.Response;

@Listeners(Listener.class)

public class TestCase extends BaseLib{
	
	//Declaration
	Response response=null;
	int statusCode=-1;
	String status="";
	int FirstTicket=-1;
	
	@BeforeClass
	public void getTickets() throws Exception {
		//Execution
		response= baseURI("get","",0);
		//Verification
		List<Integer> ticketIDsList=response.jsonPath().getList("tickets.id");
		FirstTicket= ticketIDsList.get(1);
		
	}
	
	@Test (priority=1)
	public void getListOfTickets() throws Exception {
		//Execution
		response= baseURI("get","",0);
		//Verification
		statusCode=response.getStatusCode();
		status=errorMessage(statusCode);
		assertEquals(status, "200 Sucessful");
		
	}
	
	
	@Test (priority=2)	
	public void createTickets() throws Exception {
		
		//Declaration
		String body ="{\"ticket\": {\"subject\": \"My printer is on fire!\", \"comment\": { \"body\": \"The smoke is very color\" }}}";
		//Execution
		response = baseURI("post",body,0);
		
		//Verification
		statusCode=response.getStatusCode();
		status=errorMessage(statusCode);
		assertEquals(status, "Ticket Creation was sucessful");	
	}
	
	@Test (priority=3)	
	public void updateTicketStatus() throws Exception {
		
		//Declaration 
		String body ="{\"ticket\": {\"status\": \"closed\", \"comment\": { \"body\": \"The smoke is very colorful.\", \"author_id\": 494820284 }}}";
		
		//Execution
		response = baseURI("put",body,FirstTicket);

		//Verification
		statusCode=response.getStatusCode();
		status=errorMessage(statusCode);
		assertEquals(status, "200 Sucessful");	
	}
	
	@Test (priority=4)	
	public void deleteTicket() throws Exception {
		
		//Execution
		response = baseURI("delete","",FirstTicket);
		//Verification
		statusCode=response.getStatusCode();
		status=errorMessage(statusCode);
		assertEquals(status, "204 Record Deleted");	
	}

}
