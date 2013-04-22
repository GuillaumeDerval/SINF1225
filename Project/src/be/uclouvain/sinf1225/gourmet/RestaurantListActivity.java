package be.uclouvain.sinf1225.gourmet;

import java.text.DecimalFormat;

import be.uclouvain.sinf1225.gourmet.utils.GourmetDatabase;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationListener;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationReceiver;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantListActivity extends Activity implements GourmetLocationReceiver
{
	private GourmetLocationListener locationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Initialisation des services de localisation
		locationListener = new GourmetLocationListener(this,this).init();
				
		Intent intent = getIntent();
		GourmetDatabase db = new GourmetDatabase(this);
		City city = db.getCity(intent.getStringExtra("name"), intent.getStringExtra("country"));
		
		setContentView(R.layout.activity_restaurant_list);
		((TextView)findViewById(R.id.RestaurantListName)).setText(city.getName());
		((TextView)findViewById(R.id.RestaurantListCountry)).setText(city.getCountry());
		((TextView)findViewById(R.id.RestaurantListDistance)).setText(new DecimalFormat("#.##").format(city.getLocation().distanceTo(locationListener.getLastLocation())/1000));
		
		final Button button = (Button)findViewById(R.id.RestaurantListRetour);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent i = getIntent();
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}

	@Override
	protected void onStop()
	{
		locationListener.close();
		locationListener = null;
		super.onStop();
	}
	
	@Override
	protected void onRestart()
	{
		if(locationListener == null)
			locationListener = new GourmetLocationListener(this,this);
		super.onRestart();
	}
	
	@Override
	public void onLocationUpdate(Location loc)
	{
	}
}
