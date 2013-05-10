package be.uclouvain.sinf1225.gourmet;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import be.uclouvain.sinf1225.gourmet.models.Reservation;
import be.uclouvain.sinf1225.gourmet.models.User;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

public class ReservationManagerView extends Activity
{	
	String restoName;
	
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
		User currentUser = User.getUserConnected();
		List<Reservation> userReservations = Reservation.getReservationByUser(currentUser);

		Iterator<Reservation> it = userReservations.iterator();
		while(it.hasNext()) {
          Reservation oneRsv=(Reservation) it.next();
          
          String date = oneRsv.getDateText();
          String[] dateTime = parseDateTime(date);
          
          restoName = oneRsv.getRestaurant().getName();
          restoName = restoName.length() <= 18 ? restoName : restoName.substring(0,18)+"..." ; //Droping last letters if too long, not to destroy GUI
          String nbPeople = ""+oneRsv.getnbrReservation();

          TableRow row = (TableRow) ( (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reservation_manager_row, null);
          setRow(row ,dateTime[1], dateTime[0], restoName, nbPeople);
          rsv_tab.addView(row);
          registerForContextMenu(row);
          
          View.OnClickListener handler_edit = new ReservationRowListener(oneRsv.getId());
          
          row.setOnClickListener(handler_edit);
        }
		
		
	}
	
	View.OnClickListener handler_edit = new View.OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			
			Toast toast = Toast.makeText(context, " Handler not implemented", Toast.LENGTH_SHORT);
			toast.show();
		}
		
	};
	
	private void setRow(TableRow row, String date, String time, String restaurant, String effectif){
		((TextView)row.findViewById(R.id.rsvDate)).setText(date);
		((TextView)row.findViewById(R.id.rsvTime)).setText(time);
		((TextView)row.findViewById(R.id.rsvRest)).setText(restaurant);
		((TextView)row.findViewById(R.id.rsvNbPeople)).setText(effectif);
	}
	

	
	/**
	 * Give one string represenation for the time (HH:MM) and (DD-MM-YY)
	 * It makes the assumption that you know what century you're in.
	 * 
	 * @param date the date which you want a string representation
	 * @return [0] is the time and [1] is the date
	 * */
	private static String[] parseDateTime(String dateText){
		String[] dateAndTime = new String[2];
		String[] parsedSource = dateText.split(" ");
		String day = parsedSource[2];
		String month = abrToNum(parsedSource[1]);
		String year = parsedSource[5].substring(2,4);//droping century
        
        dateAndTime[0] = parsedSource[3].substring(0,5);
        dateAndTime[1] = day+"-"+month+"-"+year;
		
		return dateAndTime;
	}
	
	private static String abrToNum(String monthAbr){
		if(monthAbr.equals("Jan")){
			return "01";
		}else if(monthAbr.equals("Feb")){
			return "02";
		}else if(monthAbr.equals("Mar")){
			return "03";
		}else if(monthAbr.equals("Apr")){
			return "04";
		}else if(monthAbr.equals("May")){
			return "05";
		}else if(monthAbr.equals("Jun")){
			return "06";
		}else if(monthAbr.equals("Jul")){
			return "07";
		}else if(monthAbr.equals("Aug")){
			return "08";
		}else if(monthAbr.equals("Sep")){
			return "09";
		}else if(monthAbr.equals("Oct")){
			return "10";
		}else if(monthAbr.equals("Nov")){
			return "11";
		}else if(monthAbr.equals("Dec")){
			return "12";
		}
		return "FAIL IN METHOD abrToNum";
	}
}