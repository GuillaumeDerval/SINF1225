package be.uclouvain.sinf1225.gourmet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.models.Restaurator;
import be.uclouvain.sinf1225.gourmet.models.User;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

/**
 * RestaurantView activity
 * 
 * @author Olivia Bleeckx
 */

public class RestaurantView extends Activity
{
	private Restaurant restaurant = null;
	private static int RESULT_EDIT_RESTO = 1;
	
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
		
		// On récupère le restaurant
		restaurant = Restaurant.getRestaurant(getIntent().getExtras().getInt("restoId"));
		
		// On affiche les informations du restaurant
		((TextView) findViewById(R.id.RestaurantViewName)).setText(restaurant.getName());
		((TextView) findViewById(R.id.RestaurantViewAddress)).setText(restaurant.getAddress());
		((TextView) findViewById(R.id.RestaurantViewDescription)).setText(restaurant.getDescription());
		// ((TextView)findViewById(R.id.RestaurantListDistance)).setText(new
		// DecimalFormat("#.##").format(city.getLocation().distanceTo(locationListener.getLastLocation())/1000));
		
		// Bouton pour afficher le menu du restaurant
		Button menu = (Button) findViewById(R.id.RestaurantViewMenu);
		menu.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(RestaurantView.this, DishListView.class);
			    intent.putExtra("restoId", restaurant.getId());
			    startActivity(intent);
			}
		});
		
		// Bouton pour créer une réservation dans ce restaurant
		Button book = (Button) findViewById(R.id.RestaurantViewReservation);
		book.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(RestaurantView.this, ReservationCreateView.class);
			    intent.putExtra("restoId", restaurant.getId());
			    startActivity(intent);
			}
		});
		
		// Bouton pour modifier le restaurant
		Button editresto = (Button) findViewById(R.id.RestaurantViewEdit);
		User user = User.getUserConnected();
		// Visible seulement par le restaurateur du restaurant
		if(user instanceof Restaurator && ((Restaurator)user).hasRightsForRestaurant(restaurant))
			editresto.setVisibility(View.VISIBLE);	
		else
			editresto.setVisibility(View.INVISIBLE);
		
		editresto.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(RestaurantView.this, RestaurantEditView.class);
			    intent.putExtra("restoId", restaurant.getId());
			    startActivityForResult(intent, RESULT_EDIT_RESTO);
			}
		});
		
		// On affiche les images du restaurant si elles existent
		Point p = new Point();
		getWindowManager().getDefaultDisplay().getSize(p);
		int width = p.x;
		int height = p.y;
		
		final LinearLayout imageContainer = (LinearLayout)this.findViewById(R.id.RestaurantViewImageContainer);
		
		for(Image img:restaurant.getImages())
		{
			View v = getLayoutInflater().inflate(R.layout.image_gallery_element, null);
			
			ImageView image = (ImageView)v.findViewById(R.id.galleryImage);
			TextView legend = (TextView)v.findViewById(R.id.galleryLegend);
			
	        image.setAdjustViewBounds(true);
			image.setMaxWidth(width/2);
			image.setMaxHeight(height/2);
			
			if(img != null)
				image.setImageBitmap(BitmapFactory.decodeFile(GourmetFiles.getRealPath(img.getPath())));
			else
				image.setVisibility(ImageView.GONE);
			
			legend.setText(img.getLegend()); 
			imageContainer.addView(v);
		}
		
		// Bouton pour lancer le service de navigation
		Button beginNav = (Button) findViewById(R.id.beginNav);
		beginNav.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				try
				{
					String addr = URLEncoder.encode(RestaurantView.this.restaurant.getAddress() + ", " + RestaurantView.this.restaurant.getCity().getName() + ", " + RestaurantView.this.restaurant.getCity().getCountry(), "utf-8");
					final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "daddr=" + addr));
					intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
					startActivity(intent);
				}
				catch (UnsupportedEncodingException e)
				{
					
				}
			}
		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_EDIT_RESTO)
		{
			 super.onRestart();
			 Intent i = new Intent(this, RestaurantView.class);  //your class
			 i.putExtra("restoId", restaurant.getId());
			 startActivity(i);
			 finish();
		}
	}
	 public void onBackPressed() {
		 	RestaurantListView.restaurants = Restaurant.getAllRestaurants(RestaurantListView.city);
			RestaurantListView.adapter = new RestaurantAdapter(this, R.layout.restaurant_list_row, RestaurantListView.restaurants,RestaurantListView.locationListener.getLastLocation());
			RestaurantListView.RestaurantList.setAdapter(RestaurantListView.adapter);
			finish();
		 }
}
