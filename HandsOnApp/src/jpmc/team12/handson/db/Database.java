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

import android.os.AsyncTask;

public class Database {

	private static final String URL = "http://ec2-184-73-92-152.compute-1.amazonaws.com/gateway.php";

	private static final Integer SEARCH = 0;
	private static final Integer USER = 1;
	private static final Integer EVENT = 2;

	private static final Integer ZIPCODE = 0;
	private static final Integer KEYWORD = 1;

	public static void getEvents(String search,
			final OnDatabaseResultHandler<List<Event>> handler) {
		Map<String, Object> jsonValues = new HashMap<String, Object>();
		jsonValues.put("value", search);
		JSONObject data = new JSONObject(jsonValues);

		final List<Event> events = new ArrayList<Event>();
		Database.sendRequest(URL, SEARCH, KEYWORD, data,
				new OnDatabaseResultHandler<JSONObject>() {
					public void onResult(JSONObject result) {
						events.add(new Event("1", "2", "3", "4"));
						handler.onResult(events);
					}
				});
	}

	private static void sendRequest(String url, Integer domain,
			Integer function, JSONObject data,
			OnDatabaseResultHandler<JSONObject> handler) {
		HttpPost httpPost = new HttpPost(url);
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("domain", domain
					.toString()));
			nameValuePairs.add(new BasicNameValuePair("function", function
					.toString()));
			nameValuePairs.add(new BasicNameValuePair("data", data.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			new DatabaseRequestTask(handler).execute(httpPost);
		} catch (Exception e) {
		}
	}

	private static class DatabaseRequestTask extends
			AsyncTask<HttpPost, Void, JSONObject> {

		private OnDatabaseResultHandler<JSONObject> handler;

		public DatabaseRequestTask(OnDatabaseResultHandler<JSONObject> handler) {
			this.handler = handler;
		}

		@Override
		protected JSONObject doInBackground(HttpPost... httpPost) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(httpPost[0]);
				HttpEntity httpEntity = response.getEntity();
				String output = EntityUtils.toString(httpEntity);
				return new JSONObject(output);
			} catch (Exception e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			this.handler.onResult(result);
		}

	}

}
