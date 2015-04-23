/**
 * 
 */
package uibk.sup.ivanka.settings;

/**
 * @author Daniel
 *
 */

import uibk.sup.ivanka.R;
import uibk.sup.ivanka.data.ActivityStack;
import uibk.sup.ivanka.data.UserSettings;
import uibk.sup.ivanka.start.StartActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

public class SettingsFragmentTab extends Fragment {

	private View view;

	// objects for the StandardSettings menu: fragment_standard_settings
	private ImageView profilePicture;
	private TextView username;
	private EditText newUsername;
	private EditText confirmPassword;
	private EditText date;
	private EditText smallMessage;
	private Button changeUsername;
	private Button saveChanges;

	// objects for the MapSettings menu: fragment_map_settings
	private TextView showSearchRadius;
	private SeekBar selectRadius;
	private Button saveRadius;
	private RadioGroup mapType;
	private Button saveMapType;

	// objects for LoginSettings menu: fragment_login_settings
	private EditText oldPassword;
	private EditText newPassword;
	private TextView newPasswordInfo;
	private EditText confirmNewPassword;
	private TextView confirmPasswordInfo;
	private Button saveNewPassword;
	boolean newPasswordCorrect = false;

	// objexts for SignOut menu: fragment_log_out
	private Button logOut;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (this.getTag().equals("StandardSettings")) {

			view = inflater.inflate(R.layout.fragment_standard_settings,
					container, false);

			profilePicture = (ImageView) view
					.findViewById(R.id.imageViewProfilePicture);
			username = (TextView) view.findViewById(R.id.textViewUsername);
			newUsername = (EditText) view
					.findViewById(R.id.editTextNewUsername);
			confirmPassword = (EditText) view
					.findViewById(R.id.editTextConfirmPassword);
			date = (EditText) view.findViewById(R.id.editTextDate);
			smallMessage = (EditText) view
					.findViewById(R.id.editTextSmallMessage);
			changeUsername = (Button) view
					.findViewById(R.id.buttonChangeUsername);
			saveChanges = (Button) view.findViewById(R.id.buttonSaveChanges);

			username.setText(UserSettings.getUsername());

			if (UserSettings.getDate().length() > 0)
				date.setHint(UserSettings.getDate());

			if (UserSettings.getSmallMessage().length() > 0)
				smallMessage.setHint(UserSettings.getSmallMessage());

			profilePicture.setOnClickListener(buttonhandlerStandardSettings);
			changeUsername.setOnClickListener(buttonhandlerStandardSettings);
			saveChanges.setOnClickListener(buttonhandlerStandardSettings);

			return view;

		} else if (this.getTag().equals("MapSettings")) {

			view = inflater.inflate(R.layout.fragment_map_settings, container,
					false);

			showSearchRadius = (TextView) view
					.findViewById(R.id.textViewSearchRadius);
			selectRadius = (SeekBar) view
					.findViewById(R.id.seekBarSearchRadius);
			saveRadius = (Button) view
					.findViewById(R.id.buttonChangeSearchRadius);
			mapType = (RadioGroup) view.findViewById(R.id.radioGroupMapType);
			saveMapType = (Button) view.findViewById(R.id.buttonSaveMapType);

			RadioButton standardMap = new RadioButton(getActivity());
			standardMap.setText("Standard map");
			RadioButton hybridMap = new RadioButton(getActivity());
			hybridMap.setText("Hybrid map");
			RadioButton satelliteMap = new RadioButton(getActivity());
			satelliteMap.setText("Satellite map");
			RadioButton terrainMap = new RadioButton(getActivity());
			terrainMap.setText("Terrain map");

			mapType.addView(standardMap, 0);
			mapType.addView(terrainMap, 1);
			mapType.addView(satelliteMap, 2);
			mapType.addView(hybridMap, 3);

			mapType.check(UserSettings.getMapType());

			showSearchRadius.setText("Actual search radius: "
					+ UserSettings.getSearchRadius());
			selectRadius.setProgress(UserSettings.getSearchRadius() - 500);

			selectRadius.setOnSeekBarChangeListener(seekBarListener);
			saveRadius.setOnClickListener(buttonhandlerMapSettings);
			saveMapType.setOnClickListener(buttonhandlerMapSettings);

			return view;

		} else if (this.getTag().equals("LoginSettings")) {

			view = inflater.inflate(R.layout.fragment_login_settings,
					container, false);

			oldPassword = (EditText) view
					.findViewById(R.id.editTextOldPassword);
			newPassword = (EditText) view
					.findViewById(R.id.editTextNewPassword);
			newPasswordInfo = (TextView) view
					.findViewById(R.id.textViewNewPassword);
			confirmNewPassword = (EditText) view
					.findViewById(R.id.editTextConfirmNewPassword);
			confirmPasswordInfo = (TextView) view
					.findViewById(R.id.textViewConfirmPassword);
			saveNewPassword = (Button) view
					.findViewById(R.id.buttonChangePassword);

			newPasswordInfo
					.setText("Enter a password between 6 and 16 characters");

			newPassword.addTextChangedListener(passwordWatcher);
			confirmNewPassword.addTextChangedListener(passwordControllWatcher);

			saveNewPassword.setOnClickListener(buttonhandlerLoginSettings);

			return view;

		} else if (this.getTag().equals("Logout")) {

			view = inflater
					.inflate(R.layout.fragment_log_out, container, false);

			logOut = (Button) view.findViewById(R.id.buttonLogOut);

			logOut.setOnClickListener(buttonhandlerSignout);

			return view;

		} else {

			View v = inflater.inflate(R.layout.fragment_layout, container,
					false);
			TextView tv = (TextView) v.findViewById(R.id.text);
			tv.setText(this.getTag() + " Content");
			return null;
		}

	}

	private OnClickListener buttonhandlerStandardSettings = new OnClickListener() {

		@Override
		public void onClick(View view) {

			if (view.getId() == R.id.imageViewProfilePicture) {

				// TODO open camera or saved images on mobile phone
				Toast.makeText(getActivity(), "Do nothing now!",
						Toast.LENGTH_LONG).show();

			} else if (view.getId() == R.id.buttonChangeUsername) {

				if (!confirmPassword.getText().toString()
						.equals(UserSettings.getPassword())) {

					Toast.makeText(getActivity(), "Wrong password!",
							Toast.LENGTH_LONG).show();

				} else if (newUsername.getText().toString().length() < 4
						|| newUsername.getText().toString().length() > 12) {

					Toast.makeText(
							getActivity(),
							"Username is to long or to short. Length between 4 and 12 characters.",
							Toast.LENGTH_LONG).show();

				} else {

					username.setText(newUsername.getText().toString());
					UserSettings.setUsername(newUsername.getText().toString());
					newUsername.setText("");
					confirmPassword.setText("");

				}

			} else if (view.getId() == R.id.buttonSaveChanges) {

				if (smallMessage.getText().toString().length() > 0) {

					UserSettings.setSmallMessage(smallMessage.getText()
							.toString());
					smallMessage.setText("");
					smallMessage.setHint(UserSettings.getSmallMessage());

				}

				if (date.getText().toString().length() > 0) {

					UserSettings.setDate(date.getText().toString());
					date.setText("");
					date.setHint(UserSettings.getDate());

				}

			}

		}
	};

	private OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// not needed

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// not needed

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			int newRadius = selectRadius.getProgress() + 500;
			// TODO runden auf hunderter ...

			showSearchRadius.setText("Actual search radius: " + newRadius);

		}
	};

	private OnClickListener buttonhandlerMapSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.buttonChangeSearchRadius) {
				int newRadius = selectRadius.getProgress() + 500;
				// TODO runden auf hunderter ...
				UserSettings.setSearchRadius(newRadius);
			} else if (v.getId() == R.id.buttonSaveMapType) {

				switch (mapType.getCheckedRadioButtonId()) {
				case 1:
					UserSettings.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					break;

				case 2:
					UserSettings.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
					break;

				case 3:
					UserSettings.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
					break;

				case 4:
					UserSettings.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					break;

				default:
					UserSettings.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					break;
				}

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
				newPasswordInfo.setText("The username is to short");
			else if (s.toString().length() > 16)
				newPasswordInfo.setText("The username is to long");
			else
				newPasswordInfo.setText("Password length is correct");
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

			if (!s.toString().equals(newPassword.getText().toString())) {
				confirmPasswordInfo
						.setText("The two passwords are not the same");
				newPasswordCorrect = false;
			} else {
				confirmPasswordInfo.setText("");
				newPasswordCorrect = true;
			}

		}
	};

	private OnClickListener buttonhandlerLoginSettings = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (!newPasswordCorrect)
				Toast.makeText(getActivity(), "Passwords not equal",
						Toast.LENGTH_LONG).show();
			else if (!oldPassword.getText().toString()
					.equals(UserSettings.getPassword()))
				Toast.makeText(getActivity(), "Password not correct",
						Toast.LENGTH_LONG).show();
			else {

				UserSettings.setPassword(newPassword.getText().toString());

				oldPassword.setText("");
				newPassword.setText("");
				newPasswordInfo
						.setText("Enter a password between 6 and 16 characters");
				confirmNewPassword.setText("");
				confirmPasswordInfo.setText("");

			}

		}
	};

	private OnClickListener buttonhandlerSignout = new OnClickListener() {

		@Override
		public void onClick(View v) {

			// TODO awian schianer zu mochn
			UserSettings.setAutoLogin(false);
			getActivity().finish();
			ActivityStack.mainActivity.finish();
			Intent intent = new Intent(getActivity(), StartActivity.class);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}
	};
}
