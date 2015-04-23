/**
 * 
 */
package uibk.sup.ivanka.start;

import uibk.sup.ivanka.R;
import uibk.sup.ivanka.connection.ServerConnection;
import uibk.sup.ivanka.data.ActivityStack;
import uibk.sup.ivanka.data.UserSettings;
import uibk.sup.ivanka.main.MainActivity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Daniel
 *
 */
public class StartActivity extends ActionBarActivity {

	private EditText username;
	private TextView usernameInfo;
	private EditText password;
	private TextView passwordInfoOne;
	private EditText passwordRepeat;
	private TextView passwordInfoTwo;
	private CheckBox acceptASGB;
	private TextView readASGB;
	private Button createAccount;
	private CheckBox autoLogin;
	private CheckBox autoFill;
	private Button login;

	private boolean usernameCorrect = false;
	private boolean passwordCorrect = false;

	private String saveUsername;
	private String savePassword;
	private ServerConnection con;

	// number of the mobile device the user is using to make the user
	// identifiable in case of abuse
	// private String mobilePhoneNumber;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		ActivityStack.startAcivity = this;

		con = ServerConnection.getInstance();

		SharedPreferences settings = getSharedPreferences("Settings",
				MODE_PRIVATE);
		UserSettings.initUserSettings(settings);

		if (!UserSettings.isBasicDataSet()) {

			setContentView(R.layout.activity_register);

			username = (EditText) findViewById(R.id.editTextUsername);
			usernameInfo = (TextView) findViewById(R.id.textViewUsername);
			password = (EditText) findViewById(R.id.editTextPassowrdFirst);
			passwordInfoOne = (TextView) findViewById(R.id.textViewPasswordOne);
			passwordRepeat = (EditText) findViewById(R.id.editTextPasswordSecond);
			passwordInfoTwo = (TextView) findViewById(R.id.textViewPasswordTwo);
			acceptASGB = (CheckBox) findViewById(R.id.checkBoxASGB);
			readASGB = (TextView) findViewById(R.id.textViewASGB);
			createAccount = (Button) findViewById(R.id.buttonCreateAccount);

			username.addTextChangedListener(usernameWatcher);
			password.addTextChangedListener(passwordWatcher);
			passwordRepeat.addTextChangedListener(passwordControllWatcher);
			readASGB.setOnClickListener(buttonhandler);
			createAccount.setOnClickListener(buttonhandler);

		} else if (!UserSettings.isAutoLogin()) {

			setContentView(R.layout.activity_login);

			username = (EditText) findViewById(R.id.editTextUsername);
			password = (EditText) findViewById(R.id.editTextPassword);
			autoLogin = (CheckBox) findViewById(R.id.checkBoxAutoLogin);
			autoFill = (CheckBox) findViewById(R.id.checkBoxAutoFill);
			login = (Button) findViewById(R.id.buttonLogin);

			if (UserSettings.isAutoFill()) {
				// TODO register to Server
				autoFill.setChecked(true);
				username.setText(UserSettings.getUsername());
				password.setText(UserSettings.getPassword());
			}

			autoLogin.setChecked(false);

			login.setOnClickListener(buttonhandler);

		} else {

			startMainActivity();

		}

	}

	private OnClickListener buttonhandler = new OnClickListener() {

		public void onClick(View view) {

			if (view.getId() == R.id.buttonCreateAccount) {

				if (!usernameCorrect) {
					Toast.makeText(getApplicationContext(),
							"Please enter a correct username",
							Toast.LENGTH_LONG).show();
				} else if (!passwordCorrect) {
					Toast.makeText(getApplicationContext(),
							"Please enter a correct password",
							Toast.LENGTH_LONG).show();
				} else if (acceptASGB.isActivated()) {
					Toast.makeText(getApplicationContext(),
							"Please read and accept the ASGB",
							Toast.LENGTH_LONG).show();
				} else {

					saveUsername = username.getText().toString();
					savePassword = password.getText().toString();

					UserSettings.setUsername(saveUsername);
					UserSettings.setPassword(savePassword);

					UserSettings.update();

					startMainActivity();

				}

			} else if (view.getId() == R.id.textViewASGB) {

				LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				View popupView = layoutInflater.inflate(
						R.layout.popup_asgb_info_layout, (ViewGroup) null);
				final PopupWindow popupWindow = new PopupWindow(popupView,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				popupWindow.showAsDropDown(view, 50, -30);

				Button readASGBPopUP = (Button) popupView
						.findViewById(R.id.buttonRead);

				readASGBPopUP.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						popupWindow.dismiss();
						acceptASGB.setChecked(true);
						;
					}
				});
			} else if (view.getId() == R.id.buttonLogin) {

				if (!username.getText().toString()
						.equals(UserSettings.getUsername()))
					Toast.makeText(getApplicationContext(),
							"Username not correct", Toast.LENGTH_LONG).show();
				else if (!password.getText().toString()
						.equals(UserSettings.getPassword()))
					Toast.makeText(getApplicationContext(),
							"Password not correct", Toast.LENGTH_LONG).show();
				else {
					UserSettings.setAutoFill(autoFill.isChecked());
					UserSettings.setAutoLogin(autoLogin.isChecked());
					startMainActivity();
				}

			}

		}

	};

	// TODO to check if username already exists
	private TextWatcher usernameWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// Not needed
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// Not needed
		}

		@Override
		public void afterTextChanged(Editable s) {

			if (s.toString().length() < 4) {
				usernameInfo.setText("The username is to short");
				usernameCorrect = false;
			} else if (s.toString().length() > 12) {
				usernameInfo.setText("The username is to long");
				usernameCorrect = false;
			} else {
				usernameInfo.setText("");
				usernameCorrect = true;
			}

		}
	};

	private TextWatcher passwordWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// Not needed
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// Not needed
		}

		@Override
		public void afterTextChanged(Editable s) {

			if (s.toString().length() < 6)
				passwordInfoOne.setText("The username is to short");
			else if (s.toString().length() > 16)
				passwordInfoOne.setText("The username is to long");
			else
				passwordInfoOne.setText("Password length is correct");
			// TODO checking if password is strong

		}
	};

	private TextWatcher passwordControllWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// Not needed
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// Not needed
		}

		@Override
		public void afterTextChanged(Editable s) {

			if (!s.toString().equals(password.getText().toString())) {
				passwordInfoTwo.setText("The two passwords are not the same");
				passwordCorrect = false;
			} else {
				passwordInfoTwo.setText("");
				passwordCorrect = true;
			}

		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void startMainActivity() {

		if (con.login(UserSettings.getUsername(), UserSettings.getPassword())) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else
			Toast.makeText(getApplicationContext(), "Login Failure!",
					Toast.LENGTH_LONG).show();

	}

}
