package jpmc.team12.handson;

import java.util.ArrayList;
import java.util.List;

import jpmc.team12.handson.db.Database;
import jpmc.team12.handson.db.Event;
import jpmc.team12.handson.db.OnDatabaseResultHandler;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	private ListView listView;
	private ResultListAdapter adapter;
	private List<Event> results = new ArrayList<Event>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();
		String search = bundle.getString("search");

		Database.getEvents(search, new OnDatabaseResultHandler<List<Event>>() {
			public void onResult(List<Event> result) {
				results.addAll(result);
				adapter.notifyDataSetChanged();
			}
		});

		listView = (ListView) findViewById(R.id.resultsListView);
		adapter = new ResultListAdapter(this,
				android.R.layout.simple_list_item_1, results);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ResultOnItemClickListener());
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

	private static class ResultListAdapter extends ArrayAdapter<Event> {

		public ResultListAdapter(Context context, int resource,
				List<Event> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.search_result_item, parent,
					false);

			Event item = this.getItem(position);

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

	private class ResultOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent goToNextActivity = new Intent(
					ResultsActivity.this.getApplicationContext(),
					DetailsActivity.class);
			ResultsActivity.this.startActivity(goToNextActivity);
		}
	}

}
