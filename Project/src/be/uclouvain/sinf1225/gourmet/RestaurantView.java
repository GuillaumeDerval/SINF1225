package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;

/**
	 * RestaurantView activity
	 * @author Olivia Bleeckx
	 */

public class RestaurantView extends Fragment 
{
	private Restaurant restaurant = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On d�fini le layout de ce fragment
        return inflater.inflate(R.layout.activity_restaurant_view, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
				
		restaurant = Restaurant.getRestaurant(getArguments().getInt("restoId"));
		
		((TextView)getActivity().findViewById(R.id.RestaurantViewName)).setText(restaurant.getName());
		((TextView)getActivity().findViewById(R.id.RestaurantViewAddress)).setText(restaurant.getAddress());
		//((TextView)findViewById(R.id.RestaurantListDistance)).setText(new DecimalFormat("#.##").format(city.getLocation().distanceTo(locationListener.getLastLocation())/1000));
		
		final Button button = (Button)getActivity().findViewById(R.id.RestaurantViewReturn);
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				getFragmentManager().popBackStack(); //return to CityListView
			}
		});
	}


}
