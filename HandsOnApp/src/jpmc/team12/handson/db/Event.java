package jpmc.team12.handson.db;

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
