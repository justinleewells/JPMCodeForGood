package jpmc.team12.handson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView searchButton = (ImageView) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent goToNextActivity = new Intent(getApplicationContext(),
						ResultsActivity.class);
				startActivity(goToNextActivity);
			}
		});
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
