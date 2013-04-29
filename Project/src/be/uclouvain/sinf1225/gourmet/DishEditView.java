package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;

public class DishEditView extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On défini le layout de ce fragment
        return inflater.inflate(R.layout.activity_dish_edit, container, false);//Todo
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		int dishId = Integer.valueOf(getArguments().getString("restoId"));
		Dish resto = Dish.getDish(dishId);
	}

}
