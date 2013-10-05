package jpmc.team12.handson;

public class SearchResultItem {

	private String opportunityName;

	private String organization;

	private String location;

	public SearchResultItem(String opportunityName, String organization,
			String location) {
		super();
		this.opportunityName = opportunityName;
		this.organization = organization;
		this.location = location;
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

}
