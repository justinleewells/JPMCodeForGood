package jpmc.team12.handson;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class CommonMenu {

	public static boolean onCreateOptionsMenu(Activity activity, Menu menu) {
		activity.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static boolean onOptionsItemSelected(final Activity activity,
			MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(activity);
			return true;
		case R.id.menu_account:
			View menuItemView = activity.findViewById(R.id.menu_account);
			PopupMenu popupMenu = new PopupMenu(activity, menuItemView);

			popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					if (item.getItemId() == R.id.menu_register) {
						Intent goToNextActivity = new Intent(activity
								.getApplicationContext(), Register.class);
						activity.startActivity(goToNextActivity);
					}
					return true;
				}
			});

			popupMenu.inflate(R.menu.logged_out);
			popupMenu.show();
			return true;
		}

		return false;
	}
}