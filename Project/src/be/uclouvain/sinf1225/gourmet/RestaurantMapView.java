package be.uclouvain.sinf1225.gourmet;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantMapView extends Activity
{
	private HashMap<Marker, Restaurant> markerToRestaurant = null;

	protected GoogleMap getMap()
	{
		return ((MapFragment) getFragmentManager().findFragmentById(R.id.RestaurantListMap)).getMap();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list_map);

		GoogleMap map = getMap();
		map.setMyLocationEnabled(true);
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		if (markerToRestaurant == null)
		{
			markerToRestaurant = new HashMap<Marker, Restaurant>();

			List<Restaurant> restaurants = Restaurant.getAllRestaurants(City.getCity(getIntent().getExtras().getString("name"), getIntent().getExtras().getString("country")));
			for (Restaurant restaurant : restaurants)
			{
				Marker marker = getMap().addMarker(new MarkerOptions().position(new LatLng(restaurant.getLocation().getLongitude(), restaurant.getLocation().getLatitude())).title(restaurant.getName()));
				markerToRestaurant.put(marker, restaurant);
			}
			
			map.setOnMarkerClickListener(new OnMarkerClickListener()
			{
				@Override
				public boolean onMarkerClick(Marker arg0)
				{
					Restaurant restaurant = markerToRestaurant.get(arg0);

					Intent intent = new Intent(RestaurantMapView.this, RestaurantView.class);
					intent.putExtra("restoId", restaurant.getId());
					startActivity(intent);

					return true;
				}
			});
		}
	}
}
