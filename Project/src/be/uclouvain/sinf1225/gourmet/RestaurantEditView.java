package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
public class RestaurantEditView extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On défini le layout de ce fragment
        return inflater.inflate(R.layout.activity_restaurant_edit, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		int restoId = Integer.valueOf(getArguments().getString("restoId"));
		Restaurant resto = Restaurant.getRestaurant(restoId);
	}
}
