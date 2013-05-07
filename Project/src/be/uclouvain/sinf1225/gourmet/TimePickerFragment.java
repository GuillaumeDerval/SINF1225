package be.uclouvain.sinf1225.gourmet;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
	private static final String TIME = "time";
	private static Calendar LOW;
	private static Calendar HIGH;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Check whether we're recreating a previously destroyed instance
		if (savedInstanceState != null)
		{
			try
			{
				ReservationCreateView.dateTime.setTime(ReservationCreateView.timeFormatter.parse(savedInstanceState.getString(TIME)));
			}
			catch (Exception e)
			{
				return Default();
			}
		}
		return Default();
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		
		//TODO to implement more seriously ^
		
		/* low bound */
		LOW = ReservationCreateView.dateTime;
		LOW.set(Calendar.HOUR_OF_DAY, 10);
		LOW.set(Calendar.AM_PM, Calendar.AM);
		
		/* High bound */
		HIGH = ReservationCreateView.dateTime;
		HIGH.set(Calendar.HOUR_OF_DAY, 11);
		HIGH.set(Calendar.AM_PM, Calendar.PM);
		
		if (cal.after(Calendar.getInstance()) && cal.after(LOW) && cal.before(HIGH))
		{
			ReservationCreateView.dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			ReservationCreateView.dateTime.set(Calendar.MINUTE, minute);
			ReservationCreateView.timePicker.setText(ReservationCreateView.timeFormatter.format(ReservationCreateView.dateTime.getTime()));

		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		// Save the user's current time
		savedInstanceState.putString(TIME, ReservationCreateView.timeFormatter.format(ReservationCreateView.dateTime.getTime()));
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	public Dialog Default()
	{
		int hour = ReservationCreateView.dateTime.get(Calendar.HOUR_OF_DAY);
		int minute = ReservationCreateView.dateTime.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}
}