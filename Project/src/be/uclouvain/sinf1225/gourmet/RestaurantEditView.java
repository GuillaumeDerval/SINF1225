package be.uclouvain.sinf1225.gourmet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import be.uclouvain.sinf1225.gourmet.enums.PriceCategory;

/**
 * 
 * @author qeggerickx
 * 
 */
public class RestaurantEditView extends Activity
{
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
		setContentView(R.layout.activity_restaurant_edit);

		int restoId = getIntent().getExtras().getInt("restoId");
		final Restaurant resto = Restaurant.getRestaurant(restoId);

		// creation of the buttons
		final EditText EditName = (EditText) this.findViewById(R.id.EditName);
		final EditText EditPhone = (EditText) this.findViewById(R.id.EditPhoneNumber);
		final EditText EditAdress = (EditText) this.findViewById(R.id.EditAdress);
		final Spinner EditPriceCat =(Spinner) findViewById(R.id.edit_restaurant_price_cat);
		final EditText EditEmail = (EditText) this.findViewById(R.id.EditEmail);
		final EditText EditSeats = (EditText) this.findViewById(R.id.EditSeats);
		final EditText EditDescription = (EditText) this.findViewById(R.id.EditDescription);
		final EditText EditWebsite = (EditText) this.findViewById(R.id.EditWebsite);
		final RatingBar Stars = (RatingBar) this.findViewById(R.id.restaurantStars);
		final Button ApplyButton = (Button) this.findViewById(R.id.buttonApply);
		final Button imageButton = (Button) this.findViewById(R.id.seeImageRestaurant);
		final Button timeTable = (Button) this.findViewById(R.id.editTableTableButton);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.restaurant_price_category, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		EditPriceCat.setAdapter(adapter);
		int position = adapter.getPosition(resto.getPriceCategory().toString());
		// field's init
		EditName.setText(resto.getName());
		EditPhone.setText(resto.getPhone());
		EditAdress.setText(resto.getAddress());
		EditPriceCat.setSelection(position);
		EditEmail.setText(resto.getEmail());
		EditSeats.setText("" + resto.getSeats());
		EditDescription.setText(resto.getDescription());
		EditWebsite.setText(resto.getWebsite());

		Stars.setNumStars(5);
		Stars.setMax(5); // set max just to be sure
		Stars.setRating(resto.getStars());
		Stars.setStepSize(1);
		timeTable.setOnClickListener(new View.OnClickListener()
		{
			/**
			 * perform action on click
			 */
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(RestaurantEditView.this, TimeSheetRestaurantEdit.class);
				intent.putExtra("restoId", resto.getId());
				startActivity(intent);
			}
		});

		imageButton.setOnClickListener(new View.OnClickListener()
		{
			/**
			 * perform action on click
			 */
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(RestaurantEditView.this, RestaurantImageView.class);
				intent.putExtra("restoId", resto.getId());
				startActivity(intent);
			}
		});

		ApplyButton.setOnClickListener(new View.OnClickListener()
		{
			/**
			 * perform action on click
			 */
			@Override
			public void onClick(View v)
			{
				resto.setName(EditName.getText().toString());
				resto.setPhone(EditPhone.getText().toString());
				resto.setAddress(EditAdress.getText().toString());
				resto.setEmail(EditEmail.getText().toString());
				resto.setSeats(Integer.parseInt(EditSeats.getText().toString()));
				resto.setDescription(EditDescription.getText().toString());
				resto.setWebsite(EditWebsite.getText().toString());
				resto.setStars((int) Stars.getRating()); // to be checked
				resto.setPriceCategory(PriceCategory.fromString(EditPriceCat.getSelectedItem().toString()));
				resto.updateRestaurant(); // to be checked
				finish();
			}
		});

	}
}
