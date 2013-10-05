package jpmc.team12.handson.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Database {

	private static final String URL = "http://ec2-184-73-92-152.compute-1.amazonaws.com/gateway.php";

	public List<Event> getEvents() {
		Map<String, Object> jsonValues = new HashMap<String, Object>();
		jsonValues.put("value", "ralph");
		JSONObject data = new JSONObject(jsonValues);

		JSONObject result = Database
				.sendRequest(URL, "search", "keyword", data);
		if (result == null)
			return null;

		// Turn null into List<Event>
		List<Event> events = new ArrayList<Event>();

		return events;
	}

	/*
	 * user search event
	 * 
	 * keyword
	 */
	private static JSONObject sendRequest(String url, String domain,
			String function, JSONObject data) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		JSONObject json = null;

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("domain", domain));
			nameValuePairs.add(new BasicNameValuePair("function", function));
			nameValuePairs.add(new BasicNameValuePair("data", data.toString()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			String output = EntityUtils.toString(httpEntity);
			json = new JSONObject(output);
		} catch (Exception e) {
		}

		return json;
	}

}
