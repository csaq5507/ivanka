/**
 * 
 */
package uibk.sup.ivanka.connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Daniel
 *
 */
public class Connection {

	public Connection() {
	}

	public JSONObject getJSON(String link) {

		URL url;
		HttpURLConnection urlConnection = null;
		JSONObject ret = null;
		try {
			url = new URL(link);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream input = new BufferedInputStream(
					urlConnection.getInputStream());
			ret = readStream(input);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// urlConnection.disconnect();
		}
		return ret;
	}

	private JSONObject readStream(InputStream in) {

		JSONObject ret = null;

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(sb.toString());
		try {
			ret = new JSONObject(sb.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

}
