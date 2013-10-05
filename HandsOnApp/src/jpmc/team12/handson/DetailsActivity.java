package jpmc.team12.handson;

import jpmc.team12.handson.db.Database;
import jpmc.team12.handson.db.Event;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle bundle = getIntent().getExtras();
		Event event = Database.eventList.get(bundle.getInt("event"));

		TextView detailOpp = (TextView) findViewById(R.id.detailOpp);
		detailOpp.setText(event.getName());

		TextView detailDate = (TextView) findViewById(R.id.detailDate);
		detailDate.setText(event.getDate());

		TextView detailOrg = (TextView) findViewById(R.id.detailOrg);
		detailOrg.setText(event.getOrganization());

		TextView detailLoc = (TextView) findViewById(R.id.detailLoc);
		detailLoc.setText(event.getFullLocation());

		TextView detailDescr = (TextView) findViewById(R.id.detailDescr);
		detailDescr.setText(Html.fromHtml(event.getDescription()));
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

}
