package jpmc.team12.handson;

import java.util.List;

import jpmc.team12.handson.db.Database;
import jpmc.team12.handson.db.Event;
import jpmc.team12.handson.db.OnDatabaseResultHandler;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	private ListView listView;
	private ResultListAdapter adapter;

	private EditText searchText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();
		String search = bundle.getString("search");

		searchText = (EditText) findViewById(R.id.searchText);
		searchText.setText(search);

		ImageView searchButton = (ImageView) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				performSearch();
			}
		});

		searchText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					performSearch();
					return true;
				}
				return false;
			}
		});

		Database.getEvents(search, this,
				new OnDatabaseResultHandler<List<Event>>() {
					public void onResult(List<Event> result) {
						adapter.notifyDataSetChanged();
					}
				});

		listView = (ListView) findViewById(R.id.resultsListView);
		adapter = new ResultListAdapter(this,
				android.R.layout.simple_list_item_1, Database.eventList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ResultOnItemClickListener());
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		invalidateOptionsMenu();
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

	private void performSearch() {
		Intent intent = new Intent(getApplicationContext(),
				ResultsActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString("search", searchText.getText().toString());
		intent.putExtras(bundle);

		startActivity(intent);
		finish();
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
			opportunity.setText(item.getName());

			TextView organizaton = (TextView) view
					.findViewById(R.id.organizationLabel);
			organizaton.setText(item.getOrganization());

			TextView location = (TextView) view
					.findViewById(R.id.locationLabel);
			location.setText(item.getCity());

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
			Intent intent = new Intent(
					ResultsActivity.this.getApplicationContext(),
					DetailsActivity.class);

			Bundle bundle = new Bundle();
			bundle.putInt("event", position);
			intent.putExtras(bundle);

			ResultsActivity.this.startActivity(intent);
		}
	}

}
