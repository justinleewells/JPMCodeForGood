package jpmc.team12.handson;

import jpmc.team12.handson.db.Credentials;
import jpmc.team12.handson.db.Database;
import jpmc.team12.handson.db.Event;
import jpmc.team12.handson.db.OnDatabaseResultHandler;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class DetailsActivity extends Activity {
	
	private Button signUp;
	private Button expressInterest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		signUp = (Button) findViewById(R.id.signupButton);
		expressInterest = (Button) findViewById(R.id.expressInterestButton);
		
		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				performSignUp();
			}
		});
		expressInterest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				performExpressInterest();
			}
		});


		
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
	private void performSignUp() {
		/*Intent intent = new Intent(getApplicationContext(),
				ResultsActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString("search", searchText.getText().toString());
		intent.putExtras(bundle);
		startActivity(intent);*/
		
		String username = findViewById(R.id.menu_account).getTag().toString();
		if( username.equals("Guest")){
			Toast.makeText(this, "Please Login or Register First",
					Toast.LENGTH_LONG).show();
		}else{
			//Perform signUp functionality here
		}

	}
	private void performExpressInterest() {
		/*Intent intent = new Intent(getApplicationContext(),
				ResultsActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString("search", searchText.getText().toString());
		intent.putExtras(bundle);

		startActivity(intent);*/
		Toast.makeText(this, "Interest Expressed",
				Toast.LENGTH_SHORT).show();
	}
}
