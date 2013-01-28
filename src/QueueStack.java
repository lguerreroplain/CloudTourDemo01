
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.table.client.*;
import com.microsoft.windowsazure.services.table.client.TableQuery.QueryComparisons;
import com.microsoft.windowsazure.services.queue.*;
import com.microsoft.windowsazure.services.queue.client.*;

import org.json.simple.*;

/**
 * Servlet implementation class QueueStack
 */
@WebServlet("/QueueStack")
public class QueueStack extends HttpServlet {
	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
			+ "AccountName=;"
			+ "AccountKey=";

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueueStack() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CloudStorageAccount account;
		try {

			account = CloudStorageAccount.parse(storageConnectionString);
			CloudQueueClient client = account.createCloudQueueClient();
			response.setContentType("application/json");
			CloudQueue queue = client.getQueueReference("cloudtour");
			queue.createIfNotExist();
			CloudQueueMessage message = queue.retrieveMessage();
			if(message != null){
				String messageValue =message.getMessageContentAsString();
				queue.deleteMessage(message);
				CloudTableClient tableClient = account.createCloudTableClient();
				String queryFilter = TableQuery.generateFilterCondition("Id", QueryComparisons.EQUAL, messageValue);
				TableQuery<TwitterItem> query = TableQuery.from("TwitterItem", TwitterItem.class).where(queryFilter);
				PrintWriter writer = response.getWriter();	
				Iterable<TwitterItem> iterable = tableClient.execute(query);
				JSONArray result = new JSONArray();
				
				for(TwitterItem entity : iterable){
					JSONObject obj = new JSONObject();
					obj.put("QueueName", "cloudtour");
					obj.put("date", new java.util.Date().toString());
					
					JSONObject twitter = new JSONObject();
					twitter.put("CreatedAt",entity.getCreatedAt().toString());
					twitter.put("FromUser",entity.getFromUser());
					twitter.put("FromUserName",entity.getFromUserName());
					twitter.put("ProfileImage",entity.getProfileImage());
					twitter.put("Text",entity.getText());
					twitter.put("Id",entity.getId());
					
					obj.put("value",twitter);
					result.add(obj);		
					
				}
				result.writeJSONString(writer);
				
				/*JSONObject obj = new JSONObject();
				obj.put("QueueName", "cloudtour");
				obj.put("date", new java.util.Date().toString());
				obj.put("value",messageValue);
				
						
				obj.writeJSONString(writer);*/
			}

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		catch(StorageException e){
			e.printStackTrace();
		}
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
