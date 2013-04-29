package be.uclouvain.sinf1225.gourmet;

import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationListener;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationReceiver;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.ToggleButton;

/**
 * CityList activity.
 * @author Guillaume Derval
 */
public class CityListView extends Fragment implements GourmetLocationReceiver
{
	GourmetLocationListener locationListener = null;
	
	/**
	 * Called on the creation of the view; Inflate xml
	 */
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On défini le layout de ce fragment
        return inflater.inflate(R.layout.activity_city_list, container, false);
    }
	
	/**
	 * Called on the creation of the activity(*after* the creation of fragment's view)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		// Initialisation des services de localisation
		locationListener = new GourmetLocationListener(this.getActivity(),this).init();
		
		//Recuperons les villes.
		List<City> cities = City.getAllCities();
		
		//On recupere les boutons pour le tri
		final Spinner sortType = (Spinner) getActivity().findViewById(R.id.CityListSort);
		final RadioGroup sortDirection = (RadioGroup) getActivity().findViewById(R.id.CityListSortDirection);
		
		//On récupère la vue "liste"
		final ListView cityList = (ListView) getActivity().findViewById(R.id.CityListView);
		//On crée un adapter qui va mettre dans la liste les donnes adequates des villes
		CityAdapter adapter = new CityAdapter(getActivity(), R.layout.city_list_row, cities, locationListener.getLastLocation());
		if(locationListener.getLastLocation() == null) //si on a pas de position GPS
		{
			adapter.setSort("name"); //on trie par nom
			sortType.setSelection(0); //le premier est le nom
			sortDirection.check(R.id.CityListSortDirectionAsc); //asc
		}
		else //si on en a une
		{
			adapter.setSort("distance", true); //on trie par distance
			sortType.setSelection(1); //le second est la distance
			sortDirection.check(R.id.CityListSortDirectionAsc); //desc
		}
		
		cityList.setAdapter(adapter);
		cityList.setTextFilterEnabled(true);
		cityList.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView cityList = (ListView) getActivity().findViewById(R.id.CityListView);
				final CityAdapter adapter = (CityAdapter)cityList.getAdapter();
				
			    City city = adapter.getItem(position);
			    
			    /*
			     * On change de fragment
			     */
			    
			   
			    Fragment restaurantList = new RestaurantListView(); //On crée le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putString("name", city.getName());
			    args.putString("country", city.getCountry());
			    restaurantList.setArguments(args); //on lui assigne le conteneur
			    
			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
			    transaction.replace(android.R.id.content, restaurantList);
			    transaction.addToBackStack(null);
			    transaction.commit();
			    
			}
		});
		
		//Les filtres
		final EditText filter = (EditText) getActivity().findViewById(R.id.CityListFilter);
		filter.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable s) { }
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				final ListView cityList = (ListView) getActivity().findViewById(R.id.CityListView);
				final CityAdapter adapter = (CityAdapter)cityList.getAdapter();
				adapter.getFilter().filter(s);
			}
		});
		
		final ToggleButton sortActivate = (ToggleButton) getActivity().findViewById(R.id.CityListSortActivate);
		sortActivate.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				final LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.CityListSortContainer);
				layout.setVisibility(isChecked ? ToggleButton.VISIBLE : ToggleButton.GONE);
			}
		});
		
		final Button mapActivate = (Button) getActivity().findViewById(R.id.CityListMapActivate);
		if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity()) != ConnectionResult.SUCCESS)
			mapActivate.setVisibility(View.GONE);
		
		mapActivate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Fragment restaurantList = new CityMapView(); //On crée le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    restaurantList.setArguments(args); //on lui assigne le conteneur
			    
			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
			    transaction.replace(android.R.id.content, restaurantList);
			    transaction.addToBackStack(null);
			    transaction.commit();
			}
		});
		
		sortType.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
			{
				final ListView cityList = (ListView) getActivity().findViewById(R.id.CityListView);
				final CityAdapter adapter = (CityAdapter)cityList.getAdapter();
				if(pos == 0) adapter.setSort("name");
				else if(pos == 1) adapter.setSort("distance");
				else if(pos == 2) adapter.setSort("restaurants");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
		
		sortDirection.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				final ListView cityList = (ListView) getActivity().findViewById(R.id.CityListView);
				final CityAdapter adapter = (CityAdapter)cityList.getAdapter();
				if(checkedId == R.id.CityListSortDirectionAsc) adapter.setSortOrder(true);
				else if(checkedId == R.id.CityListSortDirectionDesc) adapter.setSortOrder(false);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 * Remember to close locationListener as it consumes lot of battery
	 */
	@Override
	public void onPause()
	{
		locationListener.close();
		locationListener = null;
		super.onStop();
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 * Remember to restart locationListener
	 */
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
		final ListView cityList = (ListView) getActivity().findViewById(R.id.CityListView);
		CityAdapter a = (CityAdapter)cityList.getAdapter();
		a.updateLocation(loc);
	}
}