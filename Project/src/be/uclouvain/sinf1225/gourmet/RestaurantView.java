package be.uclouvain.sinf1225.gourmet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.models.Restaurator;
import be.uclouvain.sinf1225.gourmet.models.User;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

/**
 * RestaurantView activity
 * 
 * @author Olivia Bleeckx
 */

public class RestaurantView extends Activity
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
		setContentView(R.layout.activity_restaurant_view);
		restaurant = Restaurant.getRestaurant(getIntent().getExtras().getInt("restoId"));

		((TextView) findViewById(R.id.RestaurantViewName)).setText(restaurant.getName());
		((TextView) findViewById(R.id.RestaurantViewAddress)).setText(restaurant.getAddress());
		((TextView) findViewById(R.id.RestaurantViewDescription)).setText(restaurant.getDescription());
		// ((TextView)findViewById(R.id.RestaurantListDistance)).setText(new
		// DecimalFormat("#.##").format(city.getLocation().distanceTo(locationListener.getLastLocation())/1000));
		
		Button menu = (Button) findViewById(R.id.RestaurantViewMenu);
		menu.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(RestaurantView.this, DishListView.class);
			    intent.putExtra("dishId", 1);
			    startActivity(intent);
			}
		});
		
		Button book = (Button) findViewById(R.id.RestaurantViewReservation);
		book.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(RestaurantView.this, ReservationCreateView.class);
			    intent.putExtra("restoId", 1);
			    startActivity(intent);
			}
		});
		
		Button editresto = (Button) findViewById(R.id.RestaurantViewEdit);
		User user = User.getUserConnected();
		if(user instanceof Restaurator && ((Restaurator)user).hasRightsForRestaurant(restaurant))
		editresto.setVisibility(View.VISIBLE);	
		else editresto.setVisibility(View.INVISIBLE);
		editresto.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(RestaurantView.this, RestaurantEditView.class);
			    intent.putExtra("restoId", 1);
			    startActivity(intent);
			}
		});
		

	}
}
