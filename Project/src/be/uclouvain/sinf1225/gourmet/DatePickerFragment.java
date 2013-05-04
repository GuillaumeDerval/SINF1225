package be.uclouvain.sinf1225.gourmet;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private static final String DATE = "date";
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
	    // Check whether we're recreating a previously destroyed instance
	    if (savedInstanceState != null) {
	    	try{ReservationView.dateTime.setTime(ReservationView.dateFormatter.parse(savedInstanceState.getString(DATE)));}
	    	catch(Exception e){return Default();}
	    }
	    return Default();
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		ReservationView.dateTime.set(Calendar.YEAR, year);
		ReservationView.dateTime.set(Calendar.MONTH, month);
		ReservationView.dateTime.set(Calendar.DAY_OF_MONTH, day);
		ReservationView.datePicker.setText(ReservationView.dateFormatter.format(ReservationView.dateTime.getTime()));
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    // Save the user's current time
	    savedInstanceState.putString(DATE, ReservationView.dateFormatter.format(ReservationView.dateTime.getTime()));
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	public Dialog Default()
	{
		// Use the current date as the default date in the picker
		ReservationView.dateTime.setTimeZone(TimeZone.getTimeZone("Europe/Brussels"));
		int year = ReservationView.dateTime.get(Calendar.YEAR);
		int month = ReservationView.dateTime.get(Calendar.MONTH);
		int day = ReservationView.dateTime.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
}