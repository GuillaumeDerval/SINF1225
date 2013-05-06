package be.uclouvain.sinf1225.gourmet;

import java.util.ArrayList;
import java.util.List;

import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ToggleButton;

public class DishListView extends Activity 
{
	private Restaurant restaurant = null;

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
		setContentView(R.layout.activity_dish_list);

		// Initialisation des services de localisation
		// locationListener = new GourmetLocationListener(this,this).init();
		// Récupération du restaurant sur lequel on a cliqué et les plats qui lui appartiennent
		restaurant = Restaurant.getRestaurant(getIntent().getExtras().getInt("restoId"));

		List<Dish> dishes = restaurant.getDishes();
		//MARKER

		List<Dish> dishesEntree = new ArrayList<Dish>();
		List<Dish> dishesPlat = new ArrayList<Dish>();
		List<Dish> dishesDessert = new ArrayList<Dish>();
		List<Dish> dishesAutre = new ArrayList<Dish>();
		for(int i=0; i<dishes.size();i++)
		{
			if(dishes.get(i).getCategory().equals("Entree")) 
			{
				dishesEntree.add(dishes.get(i));
			}
			else if(dishes.get(i).getCategory().equals("Plat"))
			{
				dishesPlat.add(dishes.get(i));;
			}
			else if(dishes.get(i).getCategory().equals("Dessert"))
			{
				dishesDessert.add(dishes.get(i));;
			}
			else
			{
				dishesAutre.add(dishes.get(i));;
			}
		}
		//On recupere les boutons pour le tri
		final Spinner sortType = (Spinner) findViewById(R.id.DishListSort);
		final RadioGroup sortDirection = (RadioGroup) findViewById(R.id.DishListSortDirection);

		// On recupere la vue "liste"
		ListView DishListEntree = (ListView) this.findViewById(R.id.DishListEntreeView);
		ListView DishListPlat = (ListView) this.findViewById(R.id.DishListPlatView);
		ListView DishListDessert = (ListView) this.findViewById(R.id.DishListDessertView);
		ListView DishListAutre = (ListView) this.findViewById(R.id.DishListAutreView);

		// On cree un adapter qui va mettre dans la liste les donnes adequates des plats
		DishAdapter adapterEntree = new DishAdapter(this, R.layout.dish_list_row, dishesEntree);
		DishAdapter adapterPlat = new DishAdapter(this, R.layout.dish_list_row, dishesPlat);
		DishAdapter adapterDessert = new DishAdapter(this, R.layout.dish_list_row, dishesDessert);
		DishAdapter adapterAutre = new DishAdapter(this, R.layout.dish_list_row, dishesAutre);
		adapterEntree.setSort("name"); //on trie par nom
		adapterPlat.setSort("name"); //on trie par nom
		adapterDessert.setSort("name"); //on trie par nom
		adapterAutre.setSort("name"); //on trie par nom
		sortType.setSelection(0); //le premier est le nom
		sortDirection.check(R.id.DishListSortDirectionAsc); //asc
		DishListEntree.setAdapter(adapterEntree);
		DishListEntree.setOnItemClickListener(new OnItemClickListener()
		//MARKER
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView DishList = (ListView) findViewById(R.id.DishListEntreeView);
				//MARKER
				final DishAdapter adapter = (DishAdapter) DishList.getAdapter();

				Dish dish = adapter.getItem(position);

				Intent intent = new Intent(DishListView.this, DishView.class);
				intent.putExtra("dishId", dish.getDishId());
				startActivity(intent);
			}
		});
		DishListPlat.setAdapter(adapterPlat);
		DishListPlat.setOnItemClickListener(new OnItemClickListener()
		//MARKER
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView DishList = (ListView) findViewById(R.id.DishListPlatView);
				//MARKER
				final DishAdapter adapter = (DishAdapter) DishList.getAdapter();

				Dish dish = adapter.getItem(position);

				Intent intent = new Intent(DishListView.this, DishView.class);
				intent.putExtra("dishId", dish.getDishId());
				startActivity(intent);
			}
		});
		DishListDessert.setAdapter(adapterDessert);
		DishListDessert.setOnItemClickListener(new OnItemClickListener()
		//MARKER
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView DishList = (ListView) findViewById(R.id.DishListDessertView);
				//MARKER
				final DishAdapter adapter = (DishAdapter) DishList.getAdapter();

				Dish dish = adapter.getItem(position);

				Intent intent = new Intent(DishListView.this, DishView.class);
				intent.putExtra("dishId", dish.getDishId());
				startActivity(intent);
			}
		});
		DishListAutre.setAdapter(adapterAutre);
		DishListAutre.setOnItemClickListener(new OnItemClickListener()
		//MARKER
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView DishList = (ListView) findViewById(R.id.DishListAutreView);
				//MARKER
				final DishAdapter adapter = (DishAdapter) DishList.getAdapter();

				Dish dish = adapter.getItem(position);

				Intent intent = new Intent(DishListView.this, DishView.class);
				intent.putExtra("dishId", dish.getDishId());
				startActivity(intent);
			}
		});

	
	//Les filtres
	final EditText filter = (EditText) findViewById(R.id.DishListFilter);
	filter.addTextChangedListener(new TextWatcher()
	{
		@Override
		public void afterTextChanged(Editable s) { }
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			final ListView dishListEntree = (ListView) findViewById(R.id.DishListEntreeView);
			final ListView dishListPlat = (ListView) findViewById(R.id.DishListPlatView);
			final ListView dishListDessert = (ListView) findViewById(R.id.DishListDessertView);
			final ListView dishListAutre = (ListView) findViewById(R.id.DishListAutreView);
			//MARKER
			final DishAdapter adapterEntree = (DishAdapter)dishListEntree.getAdapter();
			final DishAdapter adapterPlat = (DishAdapter)dishListPlat.getAdapter();
			final DishAdapter adapterDessert = (DishAdapter)dishListDessert.getAdapter();
			final DishAdapter adapterAutre = (DishAdapter)dishListAutre.getAdapter();
			adapterEntree.getFilter().filter(s);
			adapterPlat.getFilter().filter(s);
			adapterDessert.getFilter().filter(s);
			adapterAutre.getFilter().filter(s);
		}
	});

	final ToggleButton sortActivate = (ToggleButton) findViewById(R.id.DishListSortActivate);
	sortActivate.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			final LinearLayout layout = (LinearLayout) findViewById(R.id.DishListSortContainer);
			layout.setVisibility(isChecked ? ToggleButton.VISIBLE : ToggleButton.GONE);
		}
	});

	

	sortType.setOnItemSelectedListener(new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
		{
			final ListView dishListEntree = (ListView) findViewById(R.id.DishListEntreeView);
			final ListView dishListPlat = (ListView) findViewById(R.id.DishListPlatView);
			final ListView dishListDessert = (ListView) findViewById(R.id.DishListDessertView);
			final ListView dishListAutre = (ListView) findViewById(R.id.DishListAutreView);
			
			//MARKER
			final DishAdapter adapterEntree = (DishAdapter)dishListEntree.getAdapter();
			final DishAdapter adapterPlat = (DishAdapter)dishListPlat.getAdapter();
			final DishAdapter adapterDessert = (DishAdapter)dishListDessert.getAdapter();
			final DishAdapter adapterAutre = (DishAdapter)dishListAutre.getAdapter();
			if(pos == 0) adapterEntree.setSort("name");
			if(pos == 0) adapterPlat.setSort("name");
			if(pos == 0) adapterDessert.setSort("name");
			if(pos == 0) adapterAutre.setSort("name");
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) { }
	});

	sortDirection.setOnCheckedChangeListener(new OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			final ListView dishListEntree = (ListView) findViewById(R.id.DishListEntreeView);
			final ListView dishListPlat = (ListView) findViewById(R.id.DishListPlatView);
			final ListView dishListDessert = (ListView) findViewById(R.id.DishListDessertView);
			final ListView dishListAutre = (ListView) findViewById(R.id.DishListAutreView);
			//MARKER
			final DishAdapter adapterEntree = (DishAdapter)dishListEntree.getAdapter();
			final DishAdapter adapterPlat = (DishAdapter)dishListPlat.getAdapter();
			final DishAdapter adapterDessert = (DishAdapter)dishListDessert.getAdapter();
			final DishAdapter adapterAutre = (DishAdapter)dishListAutre.getAdapter();
			
			if(checkedId == R.id.DishListSortDirectionAsc) adapterEntree.setSortOrder(true);
			else if(checkedId == R.id.DishListSortDirectionDesc) adapterEntree.setSortOrder(false);
			
			if(checkedId == R.id.DishListSortDirectionAsc) adapterPlat.setSortOrder(true);
			else if(checkedId == R.id.DishListSortDirectionDesc) adapterPlat.setSortOrder(false);

			if(checkedId == R.id.DishListSortDirectionAsc) adapterDessert.setSortOrder(true);
			else if(checkedId == R.id.DishListSortDirectionDesc) adapterDessert.setSortOrder(false);
			
			if(checkedId == R.id.DishListSortDirectionAsc) adapterAutre.setSortOrder(true);
			else if(checkedId == R.id.DishListSortDirectionDesc) adapterAutre.setSortOrder(false);
		}
	});
}

@Override
public void onPause()
{
	super.onPause();
}

@Override
public void onStop()
{
	super.onStop();
}

@Override
public void onResume()
{
	super.onResume();
}
}
