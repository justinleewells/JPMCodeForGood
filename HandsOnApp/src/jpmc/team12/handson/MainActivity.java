package jpmc.team12.handson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_account) {
			View menuItemView = findViewById(R.id.menu_account);
			PopupMenu popupMenu = new PopupMenu(this, menuItemView);

			popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getItemId() == R.id.menu_register) {
						Intent goToNextActivity = new Intent(
								getApplicationContext(), Register.class);
						startActivity(goToNextActivity);
					}
					return true;
				}
			});

			popupMenu.inflate(R.menu.logged_out);
			popupMenu.show();
		}
		return true;
	}

}
