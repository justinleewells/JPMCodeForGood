package jpmc.team12.handson;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;

public class LogIn extends Activity {
	private static final String TAG = "NewUserActivity";

	private EditText mUsernameEdit = null;
	private EditText mPasswordEdit = null;
	private Button mLogInButton = null;
	ImageView usernamee;
	ImageView password1;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		mUsernameEdit = (EditText) findViewById(R.id.usernameEdit);
		mPasswordEdit = (EditText) findViewById(R.id.passwordEdit);
		mLogInButton = (Button) findViewById(R.id.logInButton);

		// **Initialize the error images**//
		//usernamee = (ImageView) findViewById(R.id.error2);
		//password1 = (ImageView) findViewById(R.id.error3);

		//usernamee.setVisibility(View.INVISIBLE);
		//password1.setVisibility(View.INVISIBLE);

		mLogInButton.setOnClickListener(new CreateUserClickListener(this));
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

			usernamee.setVisibility(View.INVISIBLE);
			password1.setVisibility(View.INVISIBLE);
			String username = mUsernameEdit.getText().toString();
			String password = mPasswordEdit.getText().toString();
			String result = "";

			boolean keepGoing = true;
			/*
			 * if(firstName.equals("")||lastName.equals("")){
			 * name.setVisibility(View.VISIBLE); keepGoing = false; }
			 */
			if (username.equals("")) {
				keepGoing = false;
				result = result + "-Username is Empty";
				usernamee.setVisibility(View.VISIBLE);
			}
			if (password.equals("")) {
				keepGoing = false;
				result = result + "-Password is Null";
				password1.setVisibility(View.VISIBLE);
			}

			if (keepGoing == false) {
				Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
				return;
			}

			/** Database persistance code here **/

			/*
			 * User user; try { user = new User(mContext);
			 * user.setUsername(username); user.setPassword(password);
			 * user.setFirstName(firstName); user.setLastName(lastName);
			 * user.setEmail(email); user.save(); } catch
			 * (DatabaseConstraintException e) { Log.i(TAG,
			 * String.format("User %s already exists", username));
			 * Toast.makeText(mContext, R.string.user_exists,
			 * Toast.LENGTH_SHORT).show();
			 * usernamee.setVisibility(View.VISIBLE); return; }
			 * 
			 * Intent resultIntent = new Intent();
			 * resultIntent.putExtra("USER_ID", user.getID());
			 * setResult(Activity.RESULT_OK, resultIntent);
			 */
			finish();
		}
	}

}
