package be.uclouvain.sinf1225.gourmet;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
	
	@SuppressWarnings("unused")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation_manager);
		
		//linking current layout
		TableLayout rsv_tab = (TableLayout)findViewById(R.id.rsv_manager_table);
		
		//TODO each row much contains one reservation and be able to get the focus and show it got the focus.
		//TODO clicklistener must open reservation edit view and fills informations with the row which has focus.
		User currentUser = User.getUserConnected();
		List<Reservation> userReservations = Reservation.getReservationByUser(currentUser);
		System.out.println("SIZE OF RSV LIST : " + userReservations.size());

		Iterator<Reservation> it = userReservations.iterator();
		while(it.hasNext()) {
          Reservation oneRsv=(Reservation) it.next();
          System.out.println("LOADING INFORMATION OF ONE RESERVATION");
          
          String initial = User.getUserConnected().getName().substring(0,1)+". ";
          String name = initial + User.getUserConnected().getSurname(); //Initial of firstname + name (F. Name)
          name = name.length() <= 10 ? name : name.substring(0,10)+"..." ; //Droping last letters if too long, not to destroy GUI
          
          Date date = oneRsv.getDate();
          String[] dateTime = parseDateTime(date);
          
          String restoName = oneRsv.getRestaurant().getName();
          restoName = restoName.length() <= 18 ? restoName : restoName.substring(0,18)+"..." ; //Droping last letters if too long, not to destroy GUI
          String nbPeople = ""+oneRsv.getnbrReservation();

          TableRow row = (TableRow) ( (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reservation_manager_row, null);
          setRow(row, name ,dateTime[1], dateTime[0], restoName, nbPeople);
          rsv_tab.addView(row);
        }
		
		//button to edit the selection
		Button edit = (Button) findViewById(R.id.rsvEdit);
		edit.setOnClickListener(handler_edit);
	}
	
	View.OnClickListener handler_edit = new View.OnClickListener() {
		public void onClick(View v) {
			Context context = getApplicationContext();
			
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
	

	
	/**
	 * Give one string represenation for the time (HH:MM) and (DD-MM-YY)
	 * It makes the assumption that you know what century you're in.
	 * 
	 * @param date the date which you want a string representation
	 * @return [0] is the time and [1] is the date
	 * */
	private static String[] parseDateTime(Date date){
		String[] dateAndTime = new String[2];
		String source = date.toString();
		String[] parsedSource = source.split(" ");
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