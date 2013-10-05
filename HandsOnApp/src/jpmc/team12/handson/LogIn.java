package jpmc.team12.handson;

import jpmc.team12.handson.db.Credentials;
import jpmc.team12.handson.db.Database;
import jpmc.team12.handson.db.OnDatabaseResultHandler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;

public class LogIn extends Activity {
	private EditText mUsernameEdit = null;
	private EditText mPasswordEdit = null;
	private ImageView mLogInButton = null;
	ImageView usernamee;
	ImageView password1;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		mUsernameEdit = (EditText) findViewById(R.id.usernameEdit);
		mPasswordEdit = (EditText) findViewById(R.id.passwordEdit);
		mLogInButton = (ImageView) findViewById(R.id.login_Button);

		// **Initialize the error images**//
		// usernamee = (ImageView) findViewById(R.id.error2);
		// password1 = (ImageView) findViewById(R.id.error3);

		// usernamee.setVisibility(View.INVISIBLE);
		// password1.setVisibility(View.INVISIBLE);

		mLogInButton.setOnClickListener(new CreateUserClickListener(this));
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

	private class CreateUserClickListener implements OnClickListener {

		Context mContext;

		public CreateUserClickListener(Context context) {
			mContext = context;
		}

		@Override
		public void onClick(View v) {

			// usernamee.setVisibility(View.INVISIBLE);
			// password1.setVisibility(View.INVISIBLE);
			final String username = mUsernameEdit.getText().toString();
			final String password = mPasswordEdit.getText().toString();
			String result = "";

			boolean keepGoing = true;

			if (username.equals("")) {
				keepGoing = false;
				result = "Username is empty";
				// usernamee.setVisibility(View.VISIBLE);
			}
			if (password.equals("")) {
				keepGoing = false;
				result = "Password is empty";
				// password1.setVisibility(View.VISIBLE);
			}

			if (keepGoing == false) {
				Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
				return;
			}

			Database.submitLogin(username, password, LogIn.this,
					new OnDatabaseResultHandler<String>() {
						public void onResult(String result) {
							if (result != null) {
								Credentials.logIn(username, result);
								finish();
							} else {
								Toast.makeText(mContext, "Invalid credentials",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
		}
	}

}
