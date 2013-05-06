package be.uclouvain.sinf1225.gourmet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Reservation;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.models.User;

public class ReservationCreateView extends Activity
{

	/* date and time */
	protected static Calendar dateTime = Calendar.getInstance();
	// TODO check if we should use SimpleDateFormat.getDate/TimeInstance() or not.
	protected static SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
	protected static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a", Locale.getDefault());
	protected static Button datePicker;
	protected static Button timePicker;

	/* input Controls */
	private ListView list;
	private TextView restaurant;
	private EditText nbrReservation;
	private AutoCompleteTextView addDish;
	private ArrayList<String> listDish = new ArrayList<String>(); // list of dishes booked by the user

	/* adapters */
	private ArrayAdapter<String> adapter; // list of dishes
	private ArrayAdapter<String> adapterAddDishes; // auto-completion

	/* intent keys */
	private static final String RESTAURANT = "restoId"; // intent's key - restoID
	private static final String DISH = "dishId"; // intent's key - dishID

	/* intent values */
	private static Restaurant resto;
	private static Dish dish;
	// private static int resvID;

	/* list of dishes from resto */
	private List<String> dishesList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation_edit);

		/* date picker - choose the date of the reservation */
		datePicker = (Button) findViewById(R.id.datepicker);
		datePicker.setText(dateFormatter.format(dateTime.getTime()));

		/* date picker - choose the hour of the reservation */
		timePicker = (Button) findViewById(R.id.timepicker);
		timePicker.setText(timeFormatter.format(dateTime.getTime()));

		/* input items */
		list = (ListView) findViewById(R.id.listDish);
		restaurant = (TextView) findViewById(R.id.restaurant);
		nbrReservation = (EditText) findViewById(R.id.nbrReservation);

		/* Receive the data */
		getDataTransfer(getIntent());

		/* auto-completion - make the choose of the dish easier */
		/* adapter for the auto-completion */
		addDish = (AutoCompleteTextView) findViewById(R.id.dish);
		dishesList = new ArrayList<String>();
		for (Dish dish : resto.getDishes())
			dishesList.add(dish.getName());

		adapterAddDishes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dishesList);
		addDish.setAdapter(adapterAddDishes);

		/* adapter for the list of dishes */
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, listDish);
		list.setAdapter(adapter);
	}

	public void showTimePickerDialog(View v)
	{
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public void showDatePickerDialog(View v)
	{
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	/**
	 * OnClick Behavior - button ADD
	 * 
	 * @param v
	 */
	public void addItems(View v)
	{
		/* Variables for the Toast */
		Context context = getApplicationContext();
		String text;

		/* get the text from the addDish */
		String message = addDish.getText().toString();

		/* check if the dish is contained into the list of dishes from resto */
		if (!dishesList.contains(message))
		{
			text = "dish unknown";
			addDish.setText("");
		}
		else
		{
			/* add the dish to the listView */
			listDish.add(message);
			adapter.notifyDataSetChanged();
			text = "dish added";
		}

		/* display the toast */
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * onClick Behavior - button SEND
	 * 
	 * @param view
	 */
	public void sendReservation(View view)
	{
		/* variables for the toast */
		Context context = getApplicationContext();
		String text = "";
		boolean correctValues = true;
		int nbrResv = 0;

		/* Variables for the reservation */
		String email = User.getUserConnected().getEmail();
		Date date = dateTime.getTime();
		int restoID = resto.getId();

		/* Manage Exception */
		try
		{
			nbrResv = Integer.parseInt(nbrReservation.getText().toString());
		}
		catch (Exception e)
		{
			correctValues = false;
			text = "Nbr unfilled";
		}

		/* Check if the reservation does not exists yet */
		
		if (correctValues && Reservation.getReservation(email, restoID, date) != null)
		{
			correctValues = false;
			text = "Vous avez déjà une réservation à cette date";
		}

		/* Create reservation */
		if (correctValues)
		{
			Reservation resv = new Reservation(email, Restaurant.getRestaurant(restoID), nbrResv, date);
			
			//TODO correct listDish to be a real list of Dish.
			/*for(Reservation.DishNode dishNode : listDish)
			{
				resv.addDishNode(dishNode.dish, dishNode.nbrDishes);
			}*/
			correctValues = false;
			text = "Reservation Failed";
		}

		/* if all the insertion into the dataBase passed */
		if (correctValues)
		{
			text = "Reservation sent";
		}

		/* display the toast */
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * Get the data from the intent
	 * 
	 * @param intent
	 */
	public void getDataTransfer(Intent intent)
	{
		/* get intent values */
		int restoID = intent.getIntExtra(RESTAURANT, 0);
		int dishID = intent.getIntExtra(DISH, 0);

		if ((dishID == 0 && restoID == 0) || (dishID != 0 && restoID != 0))
		{
			finish();
		}
		else if (dishID != 0)
		{
			dish = Dish.getDish(dishID);

			/* add item to the listDish */
			listDish.add(dish.getName());
			adapter.notifyDataSetChanged();

			/* get restoID */
			restoID = dish.getRestoId();
		}

		/* display the restaurant's name */
		resto = Restaurant.getRestaurant(restoID);
		restaurant.setText(resto.getName());
	}

	/**
	 * Insert the dishes into the database
	 * 
	 * @param list
	 * @param reference
	 * @return success : true error (the insertion failed) : false
	 */

	/*public static boolean InsertIntoDatabase(List<String> list)
	{
		// variables
		int recurrence, index, check;
		String dish;

		// continue until the list is empty
		while (!list.isEmpty())
		{
			// get the first element from the remaining list
			recurrence = 0;
			index = 0;
			dish = list.get(index);

			// remove the element from the list, and increment the recurrence of this one
			while (index >= 0)
			{
				list.remove(index);
				recurrence += 1;
				index = list.indexOf(dish);
			}
			// Check if the reservation passed
			check = Reservation.addReservationDish(resvID, dish, recurrence);
			if (check == -1)
			{
				return false;
			}
		}
		return true;
	}*/
}
