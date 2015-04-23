package uibk.sup.ivanka.main;

import java.util.ArrayList;

import uibk.sup.ivanka.R;
import uibk.sup.ivanka.connection.ServerConnection;
import uibk.sup.ivanka.data.ActivityStack;
import uibk.sup.ivanka.data.EventData;
import uibk.sup.ivanka.data.UserSettings;
import uibk.sup.ivanka.event.AddNewEventActivity;
import uibk.sup.ivanka.map.MapActions;
import uibk.sup.ivanka.models.Event;
import uibk.sup.ivanka.settings.SettingActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends ActionBarActivity {

	private MapFragment map;
	private int mapType;
	private ImageButton settings;
	private ImageButton addEvent;
	private EventData eventData;
	private ServerConnection con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActivityStack.mainActivity = this;

		con = ServerConnection.getInstance();

		map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapType = UserSettings.getMapType();

		MapActions mapActions = new MapActions(map, this, eventData);

		ArrayList<Event> events = con.getEventStream(100);

		for (Event event : events) {
			mapActions.addMarkerToMap(
					new LatLng(event.getLatitude(), event.getLongitude()),
					event.getName());
		}

		// draw route from meran to portugal
		// mapActions.drawRoute(new LatLng(46.657592, 11.165797), new LatLng(
		// 40.118815, -7.871342));
		settings = (ImageButton) findViewById(R.id.buttonSettings);
		addEvent = (ImageButton) findViewById(R.id.buttonAddElement);
		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				Intent intent = new Intent(getApplicationContext(),
						SettingActivity.class);
				startActivity(intent);

			}
		});

		addEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						AddNewEventActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRestart() {
		super.onRestart();
		if (mapType != UserSettings.getMapType()) {
			map.getMap().setMapType(UserSettings.getMapType());
			mapType = UserSettings.getMapType();
		}
	}

	// TODO catch Settings button and add our settings menu there

}
