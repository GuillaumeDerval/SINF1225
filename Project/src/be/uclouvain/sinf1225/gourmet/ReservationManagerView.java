package be.uclouvain.sinf1225.gourmet;

import java.util.List;

import be.uclouvain.sinf1225.gourmet.models.Reservation;
import be.uclouvain.sinf1225.gourmet.models.User;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
		
		//linking current layout
		TableLayout rsv_tab = (TableLayout)findViewById(R.id.rsv_manager_table);

		//TODO make a cursor to get the reservations of the user
		//TODO load all the reservations and put them in the GUI 
		//TODO each row much contains one reservation and be able to get the focus and show it got the focus.
		TableRow row = (TableRow) ( (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reservation_manager_row, null);
		rsv_tab.addView(row);
		setRow(row,"Horgnies","12-05-13", "18:30", "Le sot l'y laisse", "5");
		TableRow row2 = (TableRow) ( (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reservation_manager_row, null);
		rsv_tab.addView(row2);
		setRow(row2,"Legrand","11-05-13", "20:30", "L'o à la bouche", "2");
		
		//button to edit the selection
		Button edit = (Button) findViewById(R.id.rsvEdit);
		edit.setOnClickListener(handler_edit);
	}
	
	View.OnClickListener handler_edit = new View.OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			//TODO clicklistener must open reservation edit view and fills informations with the row which has focus.
			User currentUser = User.getUserConnected();
			List<Reservation> userReservations = Reservation.getReservationByUser(currentUser);
			
			
			Toast toast = Toast.makeText(context, " Handler not implemented", Toast.LENGTH_SHORT);
			toast.show();
		}
		
	};
	
	private void setRow(TableRow row, String name, String date, String time, String restaurant, String effectif){
		((TextView)row.findViewById(R.id.rsvName)).setText(name);
		((TextView)row.findViewById(R.id.rsvDate)).setText(date);
		((TextView)row.findViewById(R.id.rsvTime)).setText(time);
		((TextView)row.findViewById(R.id.rsvRest)).setText(restaurant);
		((TextView)row.findViewById(R.id.rsvNbPeople)).setText(effectif);
	}
}