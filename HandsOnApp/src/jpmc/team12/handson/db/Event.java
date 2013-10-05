package jpmc.team12.handson.db;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {

	private String id;
	private String name;
	private String organization;
	private String street;
	private String city;
	private String state_province;
	private String zip_postal_code;
	private String start_date;
	private String end_date;
	private String date;
	private String description;

	public Event(JSONObject json) {
		try {
			this.id = json.getString("id");
			this.name = json.getString("name");
			this.organization = json.getString("organization_managing_name");
			this.street = json.getString("street");
			this.city = json.getString("city");
			this.state_province = json.getString("state_province");
			this.zip_postal_code = json.getString("zip_postal_code");
			this.start_date = json.getString("start_date");
			this.end_date = json.getString("end_date");
			this.description = json.getString("description");

			if (this.start_date.equals("0000-00-00")
					|| this.end_date.equals("0000-00-00"))
				this.date = "Ongoing";
			else if (!this.start_date.equals(this.end_date))
				this.date = this.start_date + " - " + this.end_date;
			else
				this.date = this.start_date;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOrganization() {
		return organization;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getStateProvince() {
		return state_province;
	}

	public String getZipPostalCode() {
		if (zip_postal_code.equals("0")) {
			return "";
		} else {
			return zip_postal_code;
		}
	}

	public String getFullLocation() {
		if (street.equals("") | city.equals("") | state_province.equals("")
				| zip_postal_code.equals("0"))
			return "";
		else {
			return street + " " + city + ", " + state_province + " "
					+ zip_postal_code;
		}
	}

	public String getStartDate() {
		if (start_date.equals("0"))
			return "";
		else {
			return start_date;
		}
	}

	public String getEndDate() {
		if (end_date.equals("0"))
			return "";
		else {
			return end_date;
		}

	}

	public String getDate() {
		if (date.equals("0"))
			return "";
		else {
			return date;
		}
	}

	public String getDescription() {
		return description;
	}

}
