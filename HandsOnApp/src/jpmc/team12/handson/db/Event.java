package jpmc.team12.handson.db;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {

	private String opportunityName;

	private String organization;

	private String location;

	private String date;

	public Event(String opportunityName, String organization, String location,
			String date) {
		super();
		this.opportunityName = opportunityName;
		this.organization = organization;
		this.location = location;
		this.date = date;
	}

	public Event(JSONObject json) {
		try {
			this.opportunityName = json.getString("name");
			this.organization = json.getString("organization_managing_name");
			this.location = json.getString("city");
			this.date = json.getString("start_date");

			if (this.date == "0000-00-00")
				this.date = "Ongoing";
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public String getOpportunityName() {
		return opportunityName;
	}

	public String getOrganization() {
		return organization;
	}

	public String getLocation() {
		return location;
	}

	public String getDate() {
		return date;
	}

}
