package be.uclouvain.sinf1225.gourmet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

public class AddDishView extends Activity
{
	int restaurantId;
	Dish dish;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		GourmetUtils.createMenu(menu, this, R.id.search);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_dish_edit);

		restaurantId = getIntent().getExtras().getInt("restaurantId");
		final Restaurant resto = Restaurant.getRestaurant(restaurantId);
		dish = new Dish();
		// button's creation
		final EditText EditName = (EditText) findViewById(R.id.EditDishName);
		final Spinner EditCategory = (Spinner) findViewById(R.id.dish_category_edit);
		final EditText EditPrice = (EditText) findViewById(R.id.EditDishPrice);
		final EditText EditAvailable = (EditText) findViewById(R.id.EditDishAvailable);
		final EditText EditDescription = (EditText) findViewById(R.id.EditDishDescription);

		final CheckBox EditSpicy = (CheckBox) findViewById(R.id.EditDishSpicy);
		final CheckBox EditVegan = (CheckBox) findViewById(R.id.EditDishVegan);
		final EditText EditAllergen = (EditText) findViewById(R.id.EditDishAllergen);

		final Button ApplyButton = (Button) findViewById(R.id.buttonDishApply);
		final Button DeleteButton = (Button) findViewById(R.id.buttonDishDelete);
		final Button imageButton = (Button) findViewById(R.id.dishImageButton);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dish_category, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		EditCategory.setAdapter(adapter);

		ApplyButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dish.setName(EditName.getText().toString());
				dish.setCategory(EditCategory.getSelectedItem().toString());
				dish.setPrice(Double.parseDouble(EditPrice.getText().toString()));
				dish.setAvailable(Integer.parseInt(EditAvailable.getText().toString()));
				dish.setDescription(EditDescription.getText().toString());
				dish.setRestoId(restaurantId);

				dish.setSpicy(EditSpicy.isChecked());

				dish.setVegan(EditVegan.isChecked());

				dish.setAllergen(EditAllergen.getText().toString());
				dish.setRestoId(resto.getId());
				dish.addDish();
				finish();
			}
		});
		DeleteButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dish.deleteDish();
				finish();
			}
		});
		imageButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dish.setName(EditName.getText().toString());
				dish.setCategory(EditCategory.getSelectedItem().toString());
				dish.setPrice(Double.parseDouble(EditPrice.getText().toString()));
				dish.setAvailable(Integer.parseInt(EditAvailable.getText().toString()));
				dish.setDescription(EditDescription.getText().toString());
				dish.setRestoId(restaurantId);
				dish.setSpicy(EditSpicy.isChecked());

				dish.setVegan(EditVegan.isChecked());

				dish.setAllergen(EditAllergen.getText().toString());

				dish.addDish();
				String name = dish.getName();
				dish = Dish.getDish(name, restaurantId);
				Intent intent = new Intent(AddDishView.this, ViewDishImage.class);
				intent.putExtra("dishId", dish.getDishId());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onPause()
	{
		super.onStop();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

}
