package be.uclouvain.sinf1225.gourmet;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import be.uclouvain.sinf1225.gourmet.models.City;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class CityMapView extends Activity
{
	private HashMap<Marker,City> markerToCity = null;
	
	protected GoogleMap getMap()
	{
		return ((MapFragment) getFragmentManager().findFragmentById(R.id.CityListMap)).getMap();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_list_map);
		
		GoogleMap map = getMap();
		map.setMyLocationEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		if(markerToCity == null)
		{
			markerToCity = new HashMap<Marker,City>();
		
			List<City> cities = City.getAllCities();
			for(City city : cities)
			{
				Marker marker = getMap().addMarker(new MarkerOptions()
		        .position(new LatLng(city.getLocation().getLatitude(), city.getLocation().getLongitude()))
		        .title(city.getName()));
				markerToCity.put(marker, city);
			}
			
			map.setOnMarkerClickListener(new OnMarkerClickListener()
			{
				@Override
				public boolean onMarkerClick(Marker arg0)
				{
					City city = markerToCity.get(arg0);
					
					Intent intent = new Intent(CityMapView.this, RestaurantListView.class);
				    intent.putExtra("name", city.getName());
				    intent.putExtra("country", city.getCountry());
				    startActivity(intent);
				    
					return true;
				}
			});
		}
	}
}
