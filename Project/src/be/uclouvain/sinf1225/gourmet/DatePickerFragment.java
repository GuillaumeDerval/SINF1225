package be.uclouvain.sinf1225.gourmet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import be.uclouvain.sinf1225.gourmet.models.TimeTable;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	private static final String DATE = "date";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Check whether we're recreating a previously destroyed instance
		if (savedInstanceState != null)
		{
			try
			{
				ReservationCreateView.dateTime.setTime(ReservationCreateView.dateFormatter.parse(savedInstanceState.getString(DATE)));
			}
			catch (Exception e)
			{
				return Default();
			}
		}
		return Default();
	}

	public void onDateSet(DatePicker view, int year, int month, int day)
	{
		/* Calendar the day choose */
		Calendar cal = Calendar.getInstance();
		cal.set(year,month,day);

		if (cal.after(Calendar.getInstance()))
		{
			ReservationCreateView.dateTime.set(Calendar.YEAR, year);
			ReservationCreateView.dateTime.set(Calendar.MONTH, month);
			ReservationCreateView.dateTime.set(Calendar.DAY_OF_MONTH, day);
			ReservationCreateView.datePicker.setText(ReservationCreateView.dateFormatter.format(ReservationCreateView.dateTime.getTime()));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		// Save the user's current time
		savedInstanceState.putString(DATE, ReservationCreateView.dateFormatter.format(ReservationCreateView.dateTime.getTime()));
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	public Dialog Default()
	{
		// Use the current date as the default date in the picker
		int year = ReservationCreateView.dateTime.get(Calendar.YEAR);
		int month = ReservationCreateView.dateTime.get(Calendar.MONTH);
		int day = ReservationCreateView.dateTime.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
}