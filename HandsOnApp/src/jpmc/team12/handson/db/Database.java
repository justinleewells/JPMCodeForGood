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

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class Database {

	private static final String URL = "http://ec2-184-73-92-152.compute-1.amazonaws.com/gateway.php";

	private static final Integer SEARCH = 0;
	private static final Integer USER = 1;
	private static final Integer EVENT = 2;

	// Search
	private static final Integer ZIPCODE = 0;
	private static final Integer KEYWORD = 1;
	private static final Integer ACTIVITIES = 2;
	private static final Integer START_DATE = 3;

	// User
	private static final Integer LOGIN = 0;

	// Event
	private static final Integer SUBSCRIBE = 1;
	private static final Integer UNSUBSCRIBE = 2;

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

	public static List<Event> eventList = new ArrayList<Event>();

	public static void getEvents(String search, Activity activity,
			final OnDatabaseResultHandler<List<Event>> handler) {
		eventList.clear();

		Map<String, Object> jsonValues = new HashMap<String, Object>();
		jsonValues.put("value", search);
		JSONObject data = new JSONObject(jsonValues);

		Database.sendRequest(URL, SEARCH, getSearchType(search), data,
				"Searching...", activity,
				new OnDatabaseResultHandler<JSONArray>() {
					public void onResult(JSONArray result) {
						eventList.clear();
						for (int i = 0; i < result.length(); i++) {
							try {
								JSONObject json = result.getJSONObject(i);
								eventList.add(new Event(json));
							} catch (JSONException e) {
								throw new RuntimeException(e);
							}
						}
						handler.onResult(eventList);
					}
				});
	}

	public static void submitLogin(String username, String password,
			Activity activity, final OnDatabaseResultHandler<String> handler) {
		Map<String, Object> jsonValues = new HashMap<String, Object>();
		jsonValues.put("username", username);
		jsonValues.put("password", password);
		JSONObject data = new JSONObject(jsonValues);

		Database.sendRequest(URL, USER, LOGIN, data, "Logging in...", activity,
				new OnDatabaseResultHandler<JSONArray>() {
					public void onResult(JSONArray result) {
						String user_id = null;
						try {
							boolean success = (result.getJSONObject(0).getInt(
									"logged_in") == 1);
							if (success)
								user_id = result.getJSONObject(0).getString(
										"user_id");
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						handler.onResult(user_id);
					}
				});
	}

	private static void sendRequest(String url, Integer domain,
			Integer function, JSONObject data, String message,
			Activity activity, OnDatabaseResultHandler<JSONArray> handler) {
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
			new DatabaseRequestTask(message, activity, handler)
					.execute(httpPost);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class DatabaseRequestTask extends
			AsyncTask<HttpPost, Void, JSONArray> {

		private OnDatabaseResultHandler<JSONArray> handler;

		private Activity activity;

		private String message;

		private ProgressDialog progDialog;

		public DatabaseRequestTask(String message, Activity activity,
				OnDatabaseResultHandler<JSONArray> handler) {
			this.activity = activity;
			this.message = message;
			this.handler = handler;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progDialog = new ProgressDialog(activity);
			progDialog.setMessage(message);
			progDialog.setIndeterminate(false);
			progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progDialog.setCancelable(false);
			progDialog.show();

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
			if (progDialog != null)
				progDialog.dismiss();
			this.handler.onResult(result);
		}

	}

}
