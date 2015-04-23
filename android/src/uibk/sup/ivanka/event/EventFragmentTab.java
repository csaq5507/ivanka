/**
 * 
 */
package uibk.sup.ivanka.event;

import uibk.sup.ivanka.R;
import uibk.sup.ivanka.connection.ServerConnection;
import uibk.sup.ivanka.map.GeoLocation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

/**
 * @author Daniel
 *
 */
public class EventFragmentTab extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view;
		if (this.getTag().equals("General")) {

			view = inflater.inflate(R.layout.fragment_event_general_new,
					container, false);

			final EditText title = (EditText) view
					.findViewById(R.id.editTextEventName);
			RatingBar rating = (RatingBar) view
					.findViewById(R.id.ratingBarEvent);
			Button takeFoto = (Button) view.findViewById(R.id.buttonTakePhoto);
			Button addEvent = (Button) view
					.findViewById(R.id.buttonCreateNewEvent);

			addEvent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (title.getText().toString().length() > 0) {

						GeoLocation geo = new GeoLocation(null, null);
						ServerConnection.getInstance().addEvent(
								title.getText().toString(),
								geo.getCurrentPosition());

					}

				}
			});

		} else if (this.getTag().equals("Comments")) {

		} else if (this.getTag().equals("Photos")) {

		} else if (this.getTag().equals("Description")) {

		}

		return null;

	}

}
