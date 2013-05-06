package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReservationManagerView extends Activity
{	
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.reservations);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		TableLayout rsv_tab = (TableLayout)findViewById(R.id.rsv_manager);
		/*
		
		TableRow rsv_item = new TableRow(this);
        rsv_item.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView name = new TextView(this);
        name.setText("Horgnies");
        name.setId(5);
        name.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView date = new TextView(this);
        date.setText("06-05-13");
        date.setId(5);
        date.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView time = new TextView(this);
        time.setText("18:30");
        time.setId(5);
        time.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView restaurant = new TextView(this);
        restaurant.setText("Le sot l'y laisse");
        restaurant.setId(5);
        restaurant.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView number = new TextView(this);
        number.setText("5");
        number.setId(5);
        number.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        rsv_item.addView(name);
        rsv_item.addView(date);
        rsv_item.addView(time);
        rsv_item.addView(restaurant);
        rsv_item.addView(number);
        

        rsv_tab.addView(rsv_item,new TableLayout.LayoutParams(
                  LayoutParams.MATCH_PARENT,
                  LayoutParams.WRAP_CONTENT));*/
        

		setContentView(R.layout.activity_reservation_manager);
	}
}