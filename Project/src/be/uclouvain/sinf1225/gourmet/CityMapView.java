package be.uclouvain.sinf1225.gourmet;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationListener;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationReceiver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CityMapView extends MapFragment implements GourmetLocationReceiver
{
	private GourmetLocationListener locationListener;
	private HashMap<Marker,City> markerToCity = null;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// Initialisation des services de localisation
		locationListener = new GourmetLocationListener(getActivity(),this).init();
				
		if(markerToCity == null)
		{
			markerToCity = new HashMap<Marker,City>();
			this.getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
			List<City> cities = City.getAllCities();
			for(City city : cities)
			{
				Marker marker = getMap().addMarker(new MarkerOptions()
		        .position(new LatLng(city.getLocation().getLatitude(), city.getLocation().getLongitude()))
		        .title(city.getName()));
				markerToCity.put(marker, city);
			}
			
			this.getMap().setOnMarkerClickListener(new OnMarkerClickListener()
			{
				@Override
				public boolean onMarkerClick(Marker arg0)
				{
					City city = markerToCity.get(arg0);
					
					Intent intent = new Intent(CityMapView.this.getActivity(), RestaurantListView.class);
				    intent.putExtra("name", city.getName());
				    intent.putExtra("country", city.getCountry());
				    startActivity(intent);
				    
					return true;
				}
			});
		}
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
