/**
 * 
 */
package uibk.sup.ivanka.map;

import uibk.sup.ivanka.data.ActivityStack;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Daniel
 *
 */
public class GeoLocation {

	private MapData mapData;
	private MapActions mapActions;
	private LocationManager locationManager;
	private boolean isGPSTrackingActivated;
	private String locationProvider;
	private boolean isShortInterval;
	private Criteria criteria;
	private final int TIME_INTERVAL_SHORT = 1000;
	private final int TIME_INTERVAL_LONG = 1000 * 60 * 2;
	private final float DISTANCE_BETWEEN_UPDATE = 0.5f;

	public GeoLocation(MapData mapData, MapActions mapActions) {

		this.mapData = mapData;
		this.mapActions = mapActions;
		this.locationManager = (LocationManager) ActivityStack.mainActivity
				.getSystemService(Context.LOCATION_SERVICE);
		this.isGPSTrackingActivated = false;

		this.criteria = new Criteria();
		this.criteria.setAccuracy(Criteria.ACCURACY_FINE);
		this.criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		this.criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

	}

	public LatLng getCurrentPosition() {
		LatLng ret = null;
		Location save = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (save == null)
			save = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (save != null) {
			ret = new LatLng(save.getLatitude(), save.getLongitude());
		}
		return ret;
	}

	public void startShortGeoLocationTracking() {

		isShortInterval = true;
		isGPSTrackingActivated = true;
		locationProvider = locationManager.getBestProvider(this.criteria, true);
		// TODO testing with best provider, by test only Network provider
		// worked, may write own getBestProvider ...
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, TIME_INTERVAL_SHORT,
				DISTANCE_BETWEEN_UPDATE, locationListener);
	}

	/**
	 * 
	 * this method starts a GeoLocation with a higher time interval between the
	 * position updating, which saves battery energy
	 */
	public void startLongGeoLocationTracking() {

		isShortInterval = false;
		isGPSTrackingActivated = true;
		locationProvider = locationManager.getBestProvider(this.criteria, true);
		locationManager.requestLocationUpdates(locationProvider,
				TIME_INTERVAL_LONG, DISTANCE_BETWEEN_UPDATE, locationListener);

	}

	public void stopGeoLoacationTracking() {

		if (isGPSTrackingActivated)
			locationManager.removeUpdates(locationListener);

	}

	private LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("Provider enabled");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("Provider disabled");
		}

		@Override
		public void onLocationChanged(Location location) {
			if (isBetterLocation(location, mapData.getCurrentLocation())) {
				System.out.println(location.toString());
				updateCurrentPosition(location);
				if (mapData.isRouteSet())
					updateRoute();
			}
		}

		private void updateCurrentPosition(Location location) {
			mapData.setCurrentLocation(location);
			mapData.setCurrentPositionMarkerOptions(location);
			mapActions.updateCurrentPositionMarker();
		}

		private void updateRoute() {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * 
	 * Function from Android Developers page:
	 * http://developer.android.com/guide/topics/location/strategies.html
	 * 
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentLocation) {
		if (currentLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentLocation.getTime();
		boolean isSignificantlyNewer;
		boolean isSignificantlyOlder;
		if (isShortInterval) {
			isSignificantlyNewer = timeDelta > TIME_INTERVAL_SHORT;
			isSignificantlyOlder = timeDelta < -TIME_INTERVAL_SHORT;
		} else {
			isSignificantlyNewer = timeDelta > TIME_INTERVAL_LONG;
			isSignificantlyOlder = timeDelta < -TIME_INTERVAL_LONG;
		}

		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
