/**
 * 
 */
package uibk.sup.ivanka.map;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * @author Daniel
 *
 */
public class UpdateMapAsyncTask extends AsyncTask<GoogleMap, Float, GoogleMap> {

	private MapData mapData;
	private int i = 0;
	private Float[] toPublish;

	/**
	 * @param mapData
	 */
	public UpdateMapAsyncTask(MapData mapData) {
		super();
		this.mapData = mapData;
		this.toPublish = new Float[3];
	}

	@Override
	protected GoogleMap doInBackground(GoogleMap... params) {

		int i = 1;
		GoogleMap googleMap = mapData.getGoogleMap();

		while (true) {

			if (i % 5 == 0) {

				changeInfoImages(googleMap);

			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (i == 1000000)
				i = 0;
			else
				i++;
		}

	}

	@Override
	protected void onProgressUpdate(Float... hotspot) {

		if (hotspot[0] == 0) {
			double lat = hotspot[1];
			double lng = hotspot[2];
			mapData.getCurrentPositionMarkerOptions().position(
					new LatLng(lat, lng));
			mapData.getCurrentPositionMarker()
					.setPosition(new LatLng(lat, lng));
			mapData.getMapActions().updateCurrentPositionMarker();
			// updateRoute(lat, lng);
		} else if (hotspot[0] >= 1) {

			float help = hotspot[0];
			int save = (int) help;
			mapData.getMapActions().updateMarkers(save);

		}

	}

	private void updateRoute(double lat, double lng) {
		if (mapData.getRoute().size() > 0) {
			CalculateRouteAsyncTask update = new CalculateRouteAsyncTask(
					mapData, true);
			update.execute("http://maps.googleapis.com/maps/api/directions/json?origin="
					+ lat
					+ ","
					+ lng
					+ "&destination="
					+ mapData
							.getRoute()
							.getElement()
							.getPoints()
							.get(mapData.getRoute().getElement().getPoints()
									.size() - 1).latitude
					+ ","
					+ mapData
							.getRoute()
							.getElement()
							.getPoints()
							.get(mapData.getRoute().getElement().getPoints()
									.size() - 1).longitude
					+ "&mode=walking&sensor=false");
		}
	}

	private void changeInfoImages(GoogleMap googleMap) {

		switch (i) {
		case 0:
			publishProgress((float) 1);
			i++;
			break;
		case 1:
			publishProgress((float) 2);
			i++;
			break;
		case 2:
			publishProgress((float) 3);
			i = 0;
			break;
		default:
			break;
		}

	}

}
