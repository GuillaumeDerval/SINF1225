package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation_manager);
		
		TableLayout rsv_tab = (TableLayout)findViewById(R.id.rsv_manager);
		
		TableRow row = (TableRow) ( (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reservation_manager_row, null);
		rsv_tab.addView(row);
		((TextView)row.findViewById(R.id.reservationRowName)).setText("Du texte");
		
		/*TableRow rsv_item = new TableRow(this);
        rsv_item.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView name = new TextView(this);
        name.setText("Horgnies");
        name.setId(1);
        name.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView date = new TextView(this);
        date.setText("06-05-13");
        date.setId(2);
        date.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView time = new TextView(this);
        time.setText("18:30");
        time.setId(3);
        time.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView restaurant = new TextView(this);
        restaurant.setText("Le sot l'y laisse");
        restaurant.setId(4);
        restaurant.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        TextView number = new TextView(this);
        number.setText("5");
        number.setId(5);
        number.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        ((TableRow) rsv_item).addView(name);
        ((TableRow) rsv_item).addView(date);
        ((TableRow) rsv_item).addView(time);
        ((TableRow) rsv_item).addView(restaurant);
        ((TableRow) rsv_item).addView(number);
        

        ((TableLayout) rsv_tab).addView(rsv_item,new TableLayout.LayoutParams(
                  LayoutParams.MATCH_PARENT,
                  LayoutParams.WRAP_CONTENT));*/
        
        System.out.println("COUCOU !");
	}
}