/**
 * 
 */
package uibk.sup.ivanka.map;

import java.util.ArrayList;

import uibk.sup.ivanka.R;
import uibk.sup.ivanka.data.ActivityStack;
import uibk.sup.ivanka.data.EventData;
import uibk.sup.ivanka.data.UserSettings;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Daniel
 *
 */
public class MapActions implements OnMapReadyCallback {

	private MapData mapData;
	private EventData eventData;
	private GeoLocation geoLocation;
	private int numberPic = 0;;

	/**
	 * @param map
	 */
	public MapActions(MapFragment map, ActionBarActivity mainActivity,
			EventData eventData) {
		this.geoLocation = new GeoLocation(mapData, this);
		MarkerOptions currentOptions = new MarkerOptions().title("home").icon(
				BitmapDescriptorFactory
						.fromResource(R.drawable.current_position));
		this.mapData = new MapData(this, map.getMap(), geoLocation,
				mainActivity, currentOptions, new ArrayList<MarkerOptions>(),
				new ArrayList<Marker>(), eventData);

		map.getMapAsync(this);

	}

	public void onMapCreated() {

		GeoLocation geoLocation = new GeoLocation(mapData, this);
		LatLng coordinates = geoLocation.getCurrentPosition();
		if (coordinates != null) {
			mapData.getGoogleMap().moveCamera(
					CameraUpdateFactory.newLatLngZoom(coordinates, 15f));
			mapData.setCurrentPositionMarker(mapData.getGoogleMap().addMarker(
					mapData.getCurrentPositionMarkerOptions()));
		}
		mapData.getGoogleMap().setOnMarkerClickListener(markerListener);
		mapData.getGoogleMap().setInfoWindowAdapter(infoViewAdapter);

		geoLocation.startShortGeoLocationTracking();
		// UpdateMapAsyncTask asyncMapUpdate = new UpdateMapAsyncTask(mapData);
		// asyncMapUpdate.execute(mapData.getGoogleMap());
	}

	public void updateCompleteMap() {

		updateMapTyp();
		updateMarkers();
		mapData.getGoogleMap().setInfoWindowAdapter(infoViewAdapter);
	}

	public void updateMapTyp() {

		mapData.getGoogleMap().setMapType(UserSettings.getMapType());
		mapData.getGoogleMap().setInfoWindowAdapter(infoViewAdapter);
	}

	public void updateMarkers() {

		deleteAllObjects();
		for (int i = 0; i < mapData.getMarkersOptions().size(); i++) {
			Marker marker = mapData.getGoogleMap().addMarker(
					mapData.getMarkersOptions().get(i));
			addInfoToMarker(marker);
		}
	}

	public void updateMarkers(Integer pic) {
		this.numberPic = pic;
		deleteAllObjects();
		for (int i = 0; i < mapData.getMarkersOptions().size(); i++) {
			Marker marker = mapData.getGoogleMap().addMarker(
					mapData.getMarkersOptions().get(i));
			addInfoToMarker(marker);
		}
		updateRoute();
	}

	public void updateCurrentPositionMarker() {

		if (mapData.getCurrentPositionMarker() != null)
			mapData.getCurrentPositionMarker().remove();
		mapData.setCurrentPositionMarker(mapData.getGoogleMap().addMarker(
				mapData.getCurrentPositionMarkerOptions()));

	}

	public void updateRoute() {

	}

	public void addMarkerToMap(LatLng coordinates, String title) {

		MarkerOptions newMarker = new MarkerOptions()
				.position(coordinates)
				.title(title)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

		mapData.getMarkersOptions().add(newMarker);
		mapData.getMarker().add(mapData.getGoogleMap().addMarker(newMarker));
		addInfoToMarker(mapData.getMarker().get(mapData.getMarker().size() - 1));
	}

	public void addMarkersToMap(LatLng[] coordinates, String[] titles) {

		for (int i = 0; i < coordinates.length; i++) {
			addMarkersToMap(coordinates, titles);
		}

	}

	public void deleteMarker(LatLng coordinates, String title) {

		for (int i = 0; i < mapData.getMarker().size(); i++) {
			if (mapData.getMarker().get(i).getPosition().equals(coordinates)
					&& mapData.getMarker().get(i).getTitle().equals(title)) {
				mapData.getMarker().get(i).remove();
				return;
			}
		}

	}

	public void deleteAllObjects() {

		mapData.getGoogleMap().clear();

	}

	public void addInfoToMarker(Marker marker) {
		marker.showInfoWindow();

	}

	public void drawRoute(LatLng start, LatLng destination) {

		CalculateRouteAsyncTask drawRoute = new CalculateRouteAsyncTask(
				this.mapData);

		drawRoute
				.execute("http://maps.googleapis.com/maps/api/directions/json?origin="
						+ start.latitude
						+ ","
						+ start.longitude
						+ "&destination="
						+ destination.latitude
						+ ","
						+ destination.longitude + "&mode=walking&sensor=false");

	}

	/**
	 * This function will update asynchronous the position of the user and the
	 * pictures displayed at the info text near the markers
	 */
	public void startAsyncMapUpdating() {

	}

	private InfoWindowAdapter infoViewAdapter = new InfoWindowAdapter() {

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}

		@Override
		public View getInfoContents(Marker marker) {

			View view = ActivityStack.mainActivity.getLayoutInflater().inflate(
					R.layout.fragment_info_window_layout, (ViewGroup) null);
			ImageView image = (ImageView) view.findViewById(R.id.imageView1);
			switch (numberPic) {
			case 1:
				image.setImageResource(R.drawable.android);
				break;

			case 2:
				image.setImageResource(R.drawable.android2);
				break;
			case 3:
				image.setImageResource(R.drawable.android3);
				break;

			default:
				break;
			}
			TextView title = (TextView) view.findViewById(R.id.textViewTitle);
			RatingBar ratingBar = (RatingBar) view
					.findViewById(R.id.ratingBarEvent);

			title.setText(marker.getTitle());
			ratingBar.setRating(2.5f);
			// TODO
			// ratingBar.setRating(eventData.getRating());

			return view;
		}
	};

	private OnMarkerClickListener markerListener = new OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			if (marker.equals(mapData.getCurrentPositionMarker())) {

				marker.hideInfoWindow();
				return true;
			}

			else {
				marker.showInfoWindow();
				return false;
			}
		}
	};

	@Override
	public void onMapReady(GoogleMap arg0) {
		onMapCreated();

	}

}
