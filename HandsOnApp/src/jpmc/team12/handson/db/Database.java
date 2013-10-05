package jpmc.team12.handson.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class Database {

	private static final String URL = "http://ec2-184-73-92-152.compute-1.amazonaws.com/gateway.php";

	private static final Integer SEARCH = 0;
	private static final Integer USER = 1;
	private static final Integer EVENT = 2;

	private static final Integer ZIPCODE = 0;
	private static final Integer KEYWORD = 1;
	private static final Integer ACTIVITIES = 2;
	private static final Integer START_DATE = 3;

	private static final Pattern ZIPCODE_PATTERN = Pattern.compile("^\\d+$");
	private static final Pattern DATE_PATTERN = Pattern
			.compile("^\\d+/\\d+/\\d+$");

	private static int getSearchType(String search) {
		Matcher matcher = ZIPCODE_PATTERN.matcher(search);
		if (matcher.matches())
			return ZIPCODE;

		matcher = DATE_PATTERN.matcher(search);
		if (matcher.matches())
			return START_DATE;

		return KEYWORD;
	}

	public static void getEvents(String search,
			final OnDatabaseResultHandler<List<Event>> handler) {
		Map<String, Object> jsonValues = new HashMap<String, Object>();
		jsonValues.put("value", search);
		JSONObject data = new JSONObject(jsonValues);

		Database.sendRequest(URL, SEARCH, getSearchType(search), data,
				new OnDatabaseResultHandler<JSONArray>() {
					public void onResult(JSONArray result) {
						List<Event> events = new ArrayList<Event>();

						for (int i = 0; i < result.length(); i++) {
							try {
								JSONObject json = result.getJSONObject(i);
								events.add(new Event(json));
							} catch (JSONException e) {
								throw new RuntimeException(e);
							}
						}

						handler.onResult(events);
					}
				});
	}

	private static void sendRequest(String url, Integer domain,
			Integer function, JSONObject data,
			OnDatabaseResultHandler<JSONArray> handler) {
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
			throw new RuntimeException(e);
		}
	}

	private static class DatabaseRequestTask extends
			AsyncTask<HttpPost, Void, JSONArray> {

		private OnDatabaseResultHandler<JSONArray> handler;

		public DatabaseRequestTask(OnDatabaseResultHandler<JSONArray> handler) {
			this.handler = handler;
		}

		@Override
		protected JSONArray doInBackground(HttpPost... httpPost) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(httpPost[0]);
				HttpEntity httpEntity = response.getEntity();
				String output = EntityUtils.toString(httpEntity);

				return new JSONArray(output);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			this.handler.onResult(result);
		}

	}

}
