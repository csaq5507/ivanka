/**
 * 
 */
package uibk.sup.ivanka.event;

import uibk.sup.ivanka.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

/**
 * @author Daniel
 *
 */
public class AddNewEventActivity extends FragmentActivity {

	private FragmentTabHost mTabHost;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        
        
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        
        mTabHost.addTab(mTabHost.newTabSpec("General").setIndicator("General"),
        		EventFragmentTab.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Comments").setIndicator("Comments"),
               EventFragmentTab.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Photos").setIndicator("Photos"),
                EventFragmentTab.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Description").setIndicator("Description"),
                EventFragmentTab.class, null);
	}
}
