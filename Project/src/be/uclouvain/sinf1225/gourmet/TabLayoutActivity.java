package be.uclouvain.sinf1225.gourmet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Tab activity
 * @author Google, Guillaume Derval
 * @link {http://developer.android.com/guide/topics/ui/actionbar.html}
 */
public class TabLayoutActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    // Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment

	    // setup action bar for tabs
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    //actionBar.setDisplayShowTitleEnabled(false);

	    actionBar.addTab(actionBar.newTab()
	            .setText(R.string.action_search_restaurant)
	            .setTabListener(new TabListener<CityListView>(this, "restaurant", CityListView.class)));
	    
	    actionBar.addTab(actionBar.newTab()
	            .setText(R.string.action_reservation)
	            .setTabListener(new TabListener<ReservationManagerView>(this, "reservation", ReservationManagerView.class)));
	    
	    actionBar.addTab(actionBar.newTab()
	            .setText(R.string.action_user)
	            .setTabListener(new TabListener<PreferenceManagerView>(this, "preference", PreferenceManagerView.class)));
	}
	
	/**
	 * Listener for tab changes
	 * @author Google
	 * @link {http://developer.android.com/guide/topics/ui/actionbar.html}
	 * @param <T> Fragment used in this tab
	 */
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener
	{
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;
	    private boolean wasActive;
	    
	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz)
	    {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	        wasActive = false;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft)
	    {
	        if (mFragment == null)
	        {
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        }
	        else
	        {
	            ft.attach(mFragment);
	        }
	        wasActive = true;
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft)
	    {
	        if(wasActive)
	        {
	        	wasActive = false;
	        	mFragment = mActivity.getFragmentManager().findFragmentById(android.R.id.content);
	        	ft.detach(mFragment);
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft)
	    {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}
}
