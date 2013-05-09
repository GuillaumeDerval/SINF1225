package be.uclouvain.sinf1225.gourmet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationListener;
import be.uclouvain.sinf1225.gourmet.utils.GourmetLocationReceiver;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RestaurantListView extends Activity implements GourmetLocationReceiver
{
	static City city = null;
	static GourmetLocationListener locationListener = null;
	static RestaurantAdapter adapter;
	static ListView RestaurantList;
	static List<Restaurant> restaurants;

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
		//Recuperation de la ville sur laquelle on a clique et les restaurant qui lui appartiennent
		city = City.getCity(getIntent().getExtras().getString("name"), getIntent().getExtras().getString("country"));

		restaurants = Restaurant.getAllRestaurants(city);
		//On recupere les boutons pour le tri
		final Spinner sortType = (Spinner) findViewById(R.id.RestaurantListSort);
		final RadioGroup sortDirection = (RadioGroup) findViewById(R.id.RestaurantListSortDirection);

		// On recupere la vue "liste"
		if (restaurants == null || restaurants.size()==0)
		{
			Toast toast = Toast.makeText(getApplicationContext(),"Il n'y a pas de restaurants dans le ville sélectionnée",Toast.LENGTH_LONG);
			toast.show();
		}
		RestaurantList = (ListView) this.findViewById(R.id.RestaurantListView);

		// On cree un adapter qui va mettre dans la liste les donnes adequates des villes
		adapter = new RestaurantAdapter(this, R.layout.restaurant_list_row, restaurants,locationListener.getLastLocation());
		if(locationListener.getLastLocation() == null) //si on a pas de position GPS
		{
			adapter.setSort("name"); //on trie par nom
			sortType.setSelection(0); //le premier est le nom
			sortDirection.check(R.id.RestaurantListSortDirectionAsc); //asc
		}
		else //si on en a une
		{
			adapter.setSort("distance", true); //on trie par distance
			sortType.setSelection(1); //le second est la distance
			sortDirection.check(R.id.RestaurantListSortDirectionAsc); //desc
		}
		RestaurantList.setAdapter(adapter);
		RestaurantList.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView RestaurantList = (ListView) findViewById(R.id.RestaurantListView);
				final RestaurantAdapter adapter = (RestaurantAdapter) RestaurantList.getAdapter();

				Restaurant restaurant = adapter.getItem(position);

				Intent intent = new Intent(RestaurantListView.this, RestaurantView.class);
				intent.putExtra("restoId", restaurant.getId());
				startActivity(intent);
			}
		});


	
	//Les filtres
	final EditText filter = (EditText) findViewById(R.id.RestaurantListFilter);
	filter.addTextChangedListener(new TextWatcher()
	{
		@Override
		public void afterTextChanged(Editable s) { }
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			final ListView restaurantList = (ListView) findViewById(R.id.RestaurantListView);
			final RestaurantAdapter adapter = (RestaurantAdapter)restaurantList.getAdapter();
			adapter.getFilter().filter(s);
		}
	});

	final ToggleButton sortActivate = (ToggleButton) findViewById(R.id.RestaurantListSortActivate);
	sortActivate.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			final LinearLayout layout = (LinearLayout) findViewById(R.id.RestaurantListSortContainer);
			layout.setVisibility(isChecked ? ToggleButton.VISIBLE : ToggleButton.GONE);
		}
	});

	final Button mapActivate = (Button) findViewById(R.id.RestaurantListMapActivate);
	if(!GourmetUtils.hasGooglePlayServicesLib())
		mapActivate.setVisibility(View.GONE);

	mapActivate.setOnClickListener(new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(RestaurantListView.this, RestaurantMapView.class);
			startActivity(intent);
		}
	});

	sortType.setOnItemSelectedListener(new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
		{
			final ListView restaurantList = (ListView) findViewById(R.id.RestaurantListView);
			final RestaurantAdapter adapter = (RestaurantAdapter)restaurantList.getAdapter();
			if(pos == 0) adapter.setSort("name");
			else if(pos == 1) adapter.setSort("distance");
			else if(pos == 2) adapter.setSort("pricecat");
			else if(pos == 3) adapter.setSort("seats");
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) { }
	});

	sortDirection.setOnCheckedChangeListener(new OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			final ListView restaurantList = (ListView) findViewById(R.id.RestaurantListView);
			final RestaurantAdapter adapter = (RestaurantAdapter)restaurantList.getAdapter();
			if(checkedId == R.id.RestaurantListSortDirectionAsc) adapter.setSortOrder(true);
			else if(checkedId == R.id.RestaurantListSortDirectionDesc) adapter.setSortOrder(false);
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
public void onLocationUpdate(Location loc)
{
	final ListView restaurantList = (ListView) findViewById(R.id.RestaurantListView);
	RestaurantAdapter a = (RestaurantAdapter)restaurantList.getAdapter();
	a.updateLocation(loc);
}
}
