package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationListener;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationReceiver;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantListView extends Fragment implements GourmetLocationReceiver
{
	private GourmetLocationListener locationListener;
	private City city = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On défini le layout de ce fragment
        return inflater.inflate(R.layout.activity_restaurant_list, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// Initialisation des services de localisation
		locationListener = new GourmetLocationListener(getActivity(),this).init();
				
		city = City.getCity(getArguments().getString("name"), getArguments().getString("country"));
		
		((TextView)getActivity().findViewById(R.id.RestaurantListName)).setText(city.getName());
		((TextView)getActivity().findViewById(R.id.RestaurantListCountry)).setText(city.getCountry());
		//((TextView)findViewById(R.id.RestaurantListDistance)).setText(new DecimalFormat("#.##").format(city.getLocation().distanceTo(locationListener.getLastLocation())/1000));
		
		final Button button = (Button)getActivity().findViewById(R.id.RestaurantListRetour);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				getFragmentManager().popBackStack(); //return to CityListView
			}
		});
	}

	@Override
	public void onPause()
	{
		locationListener.close();
		locationListener = null;
		super.onStop();
	}
	
	@Override
	public void onResume()
	{
		if(locationListener == null)
			locationListener = new GourmetLocationListener(getActivity(),this);
		super.onResume();
	}
	
	@Override
	public void onLocationUpdate(Location loc)
	{
	}
}
