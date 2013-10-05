package jpmc.team12.handson;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	private ListView listView;
	private ResultListAdapter adapter;
	private List<SearchResultItem> results = new ArrayList<SearchResultItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		listView = (ListView) findViewById(R.id.resultsListView);
		adapter = new ResultListAdapter(this,
				android.R.layout.simple_list_item_1, results);
		listView.setAdapter(adapter);

		// DEBUG
		for (int i = 0; i < 20; i++)
			results.add(new SearchResultItem("Opportunity " + i,
					"Organization " + i, "Location " + i, "Date " + i));
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		CommonMenu.onCreateOptionsMenu(this, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (CommonMenu.onOptionsItemSelected(this, item))
			return true;

		return super.onOptionsItemSelected(item);
	}

	private static class ResultListAdapter extends
			ArrayAdapter<SearchResultItem> {

		public ResultListAdapter(Context context, int resource,
				List<SearchResultItem> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.search_result_item, parent,
					false);

			SearchResultItem item = this.getItem(position);

			TextView opportunity = (TextView) view
					.findViewById(R.id.opportunityLabel);
			opportunity.setText(item.getOpportunityName());

			TextView organizaton = (TextView) view
					.findViewById(R.id.organizationLabel);
			organizaton.setText(item.getOrganization());

			TextView location = (TextView) view
					.findViewById(R.id.locationLabel);
			location.setText(item.getLocation());

			TextView date = (TextView) view.findViewById(R.id.dateLabel);
			date.setText(item.getDate());

			if (position % 2 != 0)
				view.setBackgroundColor(getContext().getResources().getColor(
						R.color.row2));

			return view;
		}
	}

}
