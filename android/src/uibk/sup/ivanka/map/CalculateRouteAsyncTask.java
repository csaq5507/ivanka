/**
 * 
 */
package uibk.sup.ivanka.map;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uibk.sup.ivanka.connection.Connection;
import uibk.sup.ivanka.data.ActivityStack;
import uibk.sup.ivanka.util.List;
import uibk.sup.ivanka.util.PolyUtil;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author Daniel
 *
 */
public class CalculateRouteAsyncTask extends
		AsyncTask<String, String, JSONObject> {

	private Connection conn;
	private MapData mapData;
	private ProgressDialog progressDialog;
	private boolean onlyPart = false;

	public CalculateRouteAsyncTask(MapData mapData) {
		this.mapData = mapData;
		this.conn = new Connection();
	}

	public CalculateRouteAsyncTask(MapData mapData, boolean onlyPart) {
		this.mapData = mapData;
		this.onlyPart = onlyPart;
	}

	@Override
	protected void onPreExecute() {
		if (!onlyPart) {

			progressDialog = new ProgressDialog(ActivityStack.mainActivity);
			progressDialog.setTitle("Route Calculating");
			progressDialog.setMessage("Downloading data from server...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							progressDialog.dismiss();
							cancel(true);
						}
					});

			progressDialog.show();
		}
	}

	@Override
	protected void onCancelled() {
		// TODO may delete allready saved data from mapData
	}

	@Override
	protected JSONObject doInBackground(String... params) {

		return conn.getJSON(params[0]);

	}

	@Override
	public void onPostExecute(JSONObject result) {

		if (onlyPart)
			drawPartOfRoute(getEncodedPoints(result));
		else
			drawRoute(getEncodedPoints(result));
		progressDialog.dismiss();
	}

	private ArrayList<java.util.List<LatLng>> getEncodedPoints(
			JSONObject jsonObject) {

		ArrayList<java.util.List<LatLng>> ret = new ArrayList<java.util.List<LatLng>>();

		JSONArray legs;
		try {
			legs = jsonObject.getJSONArray("routes").getJSONObject(0)
					.getJSONArray("legs");

			for (int j = 0; j < legs.length(); j++) {

				JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");

				for (int i = 0; i < steps.length(); i++) {
					JSONObject polyline = steps.getJSONObject(i);
					String pol = polyline.getJSONObject("polyline").getString(
							"points");
					java.util.List<LatLng> help = PolyUtil.decode(pol);
					ret.add(help);

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	private void drawRoute(
			ArrayList<java.util.List<LatLng>> arrayListCoordinates) {
		PolylineOptions rectLine = new PolylineOptions().width(10).color(
				Color.BLUE);
		List<PolylineOptions> polylineOptionsSaver = new List<PolylineOptions>();
		List<Polyline> polylineSaver = new List<Polyline>();
		for (int i = 0; i < arrayListCoordinates.size(); i++) {
			java.util.List<LatLng> help = arrayListCoordinates.get(i);
			for (int j = 0; j < help.size(); j++) {
				rectLine.add(help.get(j));
			}
			polylineOptionsSaver.addElement(rectLine);
			rectLine = new PolylineOptions().width(10).color(Color.BLUE);
		}
		for (int i = 0; i < polylineOptionsSaver.size(); i++) {

			if (i < polylineOptionsSaver.size() - 1) {
				polylineOptionsSaver.get(i).add(
						polylineOptionsSaver.get(i + 1).getPoints().get(0));
				polylineOptionsSaver.get(i).add(
						polylineOptionsSaver.get(i + 1).getPoints().get(1));

			}

			polylineSaver.addElement(mapData.getGoogleMap().addPolyline(
					polylineOptionsSaver.get(i)));
		}
		mapData.setRouteOptions(polylineOptionsSaver);
		mapData.setRoute(polylineSaver);
	}

	private void drawPartOfRoute(ArrayList<java.util.List<LatLng>> arrayList) {
		PolylineOptions rectLine = new PolylineOptions().width(10).color(
				Color.BLUE);
		List<PolylineOptions> polylineOptionsSaver = new List<PolylineOptions>();
		List<Polyline> polylineSaver = new List<Polyline>();
		for (int i = 0; i < arrayList.size(); i++) {
			java.util.List<LatLng> help = arrayList.get(i);
			for (int j = 0; j < arrayList.size(); j++) {
				rectLine.add(help.get(j));
			}
			polylineOptionsSaver.addElement(rectLine);
			rectLine = new PolylineOptions().width(10).color(Color.BLUE);
		}

		PolylineOptions[] newPolylineOptionsParts = new PolylineOptions[polylineOptionsSaver
				.size()];
		Polyline[] newPolylineParts = new Polyline[polylineSaver.size()];

		for (int i = 0; i < polylineOptionsSaver.size(); i++) {

			if (i < polylineOptionsSaver.size() - 1) {
				polylineOptionsSaver.get(i).add(
						polylineOptionsSaver.get(i + 1).getPoints().get(0));
				polylineOptionsSaver.get(i).add(
						polylineOptionsSaver.get(i + 1).getPoints().get(1));

			}
			newPolylineParts[i] = mapData.getGoogleMap().addPolyline(
					polylineOptionsSaver.get(i));
			newPolylineOptionsParts[i] = polylineOptionsSaver.get(i);
		}

		mapData.getRouteOptions().addElementsAtBegin(newPolylineOptionsParts);
		mapData.getRoute().addElementsAtBegin(newPolylineParts);
	}

}
