/**
 * 
 */
package uibk.sup.ivanka.map;

import java.util.ArrayList;

import uibk.sup.ivanka.data.EventData;
import uibk.sup.ivanka.util.List;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author Daniel
 *
 */
public class MapData {

	private MapActions mapActions;
	private GoogleMap googleMap;
	private GeoLocation geoLocation;
	private Location currentLocation;
	private MarkerOptions currentPositionMarkerOptions;
	private Marker currentPositionMarker;
	private ArrayList<MarkerOptions> markersOptions;
	private ArrayList<Marker> marker;
	private EventData eventData;
	private List<Polyline> route;
	private List<PolylineOptions> routeOptions;
	private boolean routeSet = false;

	/**
	 * @param mapActions
	 * @param googleMap
	 * @param mGPS
	 * @param mainActivity
	 * @param currentPosition
	 * @param markersOptions
	 * @param marker
	 * @param eventData
	 * @param route
	 */
	public MapData(MapActions mapActions, GoogleMap googleMap,
			GeoLocation geoLocation, ActionBarActivity mainActivity,
			MarkerOptions currentPositionOptions,
			ArrayList<MarkerOptions> markersOptions, ArrayList<Marker> marker,
			EventData eventData) {
		super();
		this.mapActions = mapActions;
		this.googleMap = googleMap;
		this.geoLocation = geoLocation;
		this.currentPositionMarkerOptions = currentPositionOptions;
		this.markersOptions = markersOptions;
		this.marker = marker;
		this.eventData = eventData;
		this.route = new List<Polyline>();
		this.routeOptions = new List<PolylineOptions>();
	}

	/**
	 * @return the mapActions
	 */
	public MapActions getMapActions() {
		return mapActions;
	}

	/**
	 * @param mapActions
	 *            the mapActions to set
	 */
	public void setMapActions(MapActions mapActions) {
		this.mapActions = mapActions;
	}

	/**
	 * @return the googleMap
	 */
	public GoogleMap getGoogleMap() {
		return googleMap;
	}

	/**
	 * @param googleMap
	 *            the googleMap to set
	 */
	public void setGoogleMap(GoogleMap googleMap) {
		this.googleMap = googleMap;
	}

	/**
	 * @return the mGPS
	 */
	public GeoLocation getmGPS() {
		return geoLocation;
	}

	/**
	 * @param mGPS
	 *            the mGPS to set
	 */
	public void setmGPS(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	/**
	 * @return the currentLocation
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation
	 *            the currentLocation to set
	 */
	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the currentPositionInfo
	 */
	public MarkerOptions getCurrentPositionMarkerOptions() {
		return currentPositionMarkerOptions;
	}

	/**
	 * @param currentPositionInfo
	 *            the currentPositionInfo to set
	 */
	public void setCurrentPositionMarkerOptions(
			MarkerOptions currentPositionMarkerOptions) {
		this.currentPositionMarkerOptions = currentPositionMarkerOptions;
	}

	/**
	 * @return the currentPosition
	 */
	public Marker getCurrentPositionMarker() {
		return currentPositionMarker;
	}

	/**
	 * @param currentPosition
	 *            the currentPosition to set
	 */
	public void setCurrentPositionMarker(Marker currentPositionMarker) {
		this.currentPositionMarker = currentPositionMarker;
	}

	/**
	 * @return the markersOptions
	 */
	public ArrayList<MarkerOptions> getMarkersOptions() {
		return markersOptions;
	}

	/**
	 * @param markersOptions
	 *            the markersOptions to set
	 */
	public void setMarkersOptions(ArrayList<MarkerOptions> markersOptions) {
		this.markersOptions = markersOptions;
	}

	/**
	 * @return the marker
	 */
	public ArrayList<Marker> getMarker() {
		return marker;
	}

	/**
	 * @param marker
	 *            the marker to set
	 */
	public void setMarker(ArrayList<Marker> marker) {
		this.marker = marker;
	}

	/**
	 * @return the eventData
	 */
	public EventData getEventData() {
		return eventData;
	}

	/**
	 * @param eventData
	 *            the eventData to set
	 */
	public void setEventData(EventData eventData) {
		this.eventData = eventData;
	}

	/**
	 * @return the route
	 */
	public List<Polyline> getRoute() {
		return route;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(List<Polyline> route) {
		this.route = route;
	}

	/**
	 * @return the routeOptions
	 */
	public List<PolylineOptions> getRouteOptions() {
		return routeOptions;
	}

	/**
	 * @param routeOptions
	 *            the routeOptions to set
	 */
	public void setRouteOptions(List<PolylineOptions> routeOptions) {
		this.routeOptions = routeOptions;
	}

	/**
	 * @return the routeSet
	 */
	public boolean isRouteSet() {
		return routeSet;
	}

	/**
	 * @param routeSet
	 *            the routeSet to set
	 */
	public void setRouteSet(boolean routeSet) {
		this.routeSet = routeSet;
	}

	public void setCurrentPositionMarkerOptions(Location location) {

		if (location != null)
			this.currentPositionMarkerOptions.position(new LatLng(location
					.getLatitude(), location.getLongitude()));

	}

}
