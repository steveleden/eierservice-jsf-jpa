package scheduledTasks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ScheduledTask implements Runnable {

	@Override
	public void run() {

		// Execute task.
	    System.out.println("Job trigged by scheduler");
		HttpURLConnection connection = null;
	    try {
	        //System.out.println("Testing URL...");
	        URL u = new URL("http://test-bank.azurewebsites.net/");
	        connection = (HttpURLConnection) u.openConnection();
	        connection.setRequestMethod("HEAD");
	        connection.getResponseCode();
	        //int code = connection.getResponseCode();
	        //System.out.println("" + code);
	        // You can determine on HTTP return code received. 200 is success.
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (connection != null) {
	            connection.disconnect();
	        }
	    }
	    
	}
}	
	
