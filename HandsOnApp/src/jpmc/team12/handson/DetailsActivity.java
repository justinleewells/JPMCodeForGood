package jpmc.team12.handson;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jpmc.team12.handson.db.Credentials;
import jpmc.team12.handson.db.Database;
import jpmc.team12.handson.db.Event;
import jpmc.team12.handson.db.OnDatabaseResultHandler;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {

	private Event event;

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
		event = Database.eventList.get(bundle.getInt("event"));

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
		if (!Credentials.getLoggedIn()) {
			Toast.makeText(this, "Please Login or Register first",
					Toast.LENGTH_LONG).show();
			CommonMenu.displayOptions(this);
		} else {
			Database.registerForEvent(event.getId(), Credentials.getUid(),
					this, new OnDatabaseResultHandler<String>() {
						public void onResult(String error) {
							if (error != null) {
								Toast.makeText(DetailsActivity.this, error,
										Toast.LENGTH_SHORT).show();
							} else {
								if (!event.getStartDate().equals("0000-00-00")) {
									new AlertDialog.Builder(
											DetailsActivity.this)
											.setTitle(
													"Success! Add to calendar?")
											.setMessage(
													"Would you like to add this event to your phone's calendar? You will still be signed up even if you skip this step.")
											.setIcon(
													android.R.drawable.ic_dialog_info)
											.setPositiveButton(
													"Add To Calendar",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int whichButton) {
															DetailsActivity.this
																	.addToCalendar();
														}
													})
											.setNegativeButton("Skip", null)
											.show();
								}

								Toast.makeText(DetailsActivity.this,
										"Successfully signed up!",
										Toast.LENGTH_SHORT).show();

								signUp.setText("Signed Up!");
								signUp.setTextColor(DetailsActivity.this
										.getResources().getColor(R.color.row2));
							}
						}
					});
		}

	}

	private void addToCalendar() {
		Pattern datePattern = Pattern.compile("/(\\d+)\\-(\\d+)\\-(\\d+)$");

		Matcher matcher = datePattern.matcher(event.getStartDate());
		matcher.matches();
		int year = Integer.parseInt(matcher.group(1));
		int month = Integer.parseInt(matcher.group(2));
		int day = Integer.parseInt(matcher.group(3));
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(year, month, day, 0, 0, 0);

		matcher = datePattern.matcher(event.getEndDate());
		matcher.matches();
		year = Integer.parseInt(matcher.group(1));
		month = Integer.parseInt(matcher.group(2));
		day = Integer.parseInt(matcher.group(3));
		Calendar endTime = Calendar.getInstance();
		endTime.set(year, month, day, 23, 59, 59);

		CalendarHelper.addToCalendar(DetailsActivity.this, beginTime, endTime,
				event.getName(), event.getDescription(),
				event.getFullLocation());
	}

	private void performExpressInterest() {
		if (!Credentials.getLoggedIn()) {
			Toast.makeText(this, "Please Login or Register first",
					Toast.LENGTH_LONG).show();
			CommonMenu.displayOptions(this);
		} else {
			Toast.makeText(DetailsActivity.this, "TODO! :)", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
