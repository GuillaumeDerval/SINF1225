package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationListener;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationReceiver;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantListView extends Activity implements GourmetLocationReceiver
{
	private GourmetLocationListener locationListener;
	private City city = null;
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.search);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		
		// Initialisation des services de localisation
		locationListener = new GourmetLocationListener(this,this).init();
				
		city = City.getCity(getIntent().getExtras().getString("name"), getIntent().getExtras().getString("country"));
		
		((TextView)findViewById(R.id.RestaurantListName)).setText(city.getName());
		((TextView)findViewById(R.id.RestaurantListCountry)).setText(city.getCountry());
		
		final Button button = (Button)findViewById(R.id.RestaurantListRetour);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});
	}

	@Override
	public void onPause()
	{
		if(locationListener != null)
			locationListener.close();
		locationListener = null;
		super.onPause();
	}
	
	@Override
	public void onStop()
	{
		if(locationListener != null)
			locationListener.close();
		locationListener = null;
		super.onStop();
	}
	
	@Override
	public void onResume()
	{
		if(locationListener == null)
			locationListener = new GourmetLocationListener(this,this);
		super.onResume();
	}
	
	@Override
	public void onLocationUpdate(Location loc)
	{
	}
}
