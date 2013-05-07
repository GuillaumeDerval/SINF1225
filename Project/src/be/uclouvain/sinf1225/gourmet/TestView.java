package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestView extends Activity
{
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.test);
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
		setContentView(R.layout.activity_test);
		Button testRestoEdit = (Button) findViewById(R.id.testRestoEdit);
		testRestoEdit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(TestView.this, RestaurantEditView.class);
			    intent.putExtra("restoId", 1);
			    startActivity(intent);
			}
		});

		Button testRestoView = (Button) findViewById(R.id.testRestoView);
		testRestoView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(TestView.this, RestaurantView.class);
			    intent.putExtra("restoId", 1);
			    startActivity(intent);
			}
		});
		Button testDishEdit = (Button) findViewById(R.id.testDishEditView);
		testDishEdit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(TestView.this, DishEditView.class);
			    intent.putExtra("dishId", 1);
			    startActivity(intent);
			}
		});
		Button testDishView = (Button) findViewById(R.id.testDishView);
		testDishView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(TestView.this, DishView.class);
			    intent.putExtra("dishId", 1);
			    startActivity(intent);
			}
		});
		
		Button testReservationCreateView = (Button) findViewById(R.id.testReservationCreateView);
		testReservationCreateView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(TestView.this, ReservationCreateView.class);
			    intent.putExtra("restoId", 1);
			    startActivity(intent);
			}
		});
		Button addDish = (Button) findViewById(R.id.testAddDishView);
		addDish.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent(TestView.this, AddDishView.class);
			    intent.putExtra("restaurantId", 1);
			    startActivity(intent);
			}
		});
		
	}
}
