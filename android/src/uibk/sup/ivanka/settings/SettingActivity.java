/**
 * 
 */
package uibk.sup.ivanka.settings;

import uibk.sup.ivanka.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

/**
 * @author Daniel
 *
 */
public class SettingActivity extends FragmentActivity {

	private FragmentTabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);

		mTabHost.addTab(
				mTabHost.newTabSpec("StandardSettings").setIndicator(
						"Profile Settings"), SettingsFragmentTab.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("MapSettings").setIndicator("Map Settings"),
				SettingsFragmentTab.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("LoginSettings").setIndicator(
						"Login Settings"), SettingsFragmentTab.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Logout").setIndicator("Log Out"),
				SettingsFragmentTab.class, null);

	}

}
