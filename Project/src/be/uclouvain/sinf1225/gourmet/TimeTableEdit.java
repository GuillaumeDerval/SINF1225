package be.uclouvain.sinf1225.gourmet;

import java.text.SimpleDateFormat;
import java.util.Date;

import be.uclouvain.sinf1225.gourmet.models.TimeTable;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressWarnings("unused")
public class TimeTableEdit extends Activity{

	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.search);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.restaurant_timesheet_edit);
		final TextView day = (TextView) findViewById(R.id.day_clic_timesheet);
		final CheckBox close = (CheckBox) findViewById(R.id.check_ferme);
		final EditText morning1 = (EditText) findViewById(R.id.text_morning_open);
		final EditText morning2 = (EditText) findViewById(R.id.text_morning_close);
		final EditText evening1 = (EditText) findViewById(R.id.text_evening_open);
		final EditText evening2 = (EditText) findViewById(R.id.text_evening_close);
		final Button apply = ( Button) findViewById(R.id.apply_time_sheet_edit);
		
		day.setText(TimeSheetRestaurantEdit.onClic.getDay());
		morning1.setText(TimeSheetRestaurantEdit.onClic.getMorningOpening());
		morning2.setText(TimeSheetRestaurantEdit.onClic.getMorningClosing());
		evening1.setText(TimeSheetRestaurantEdit.onClic.getEveningOpening());
		evening2.setText(TimeSheetRestaurantEdit.onClic.getEveningClosing());
		
		if (TimeSheetRestaurantEdit.onClic.getClose() == 1)
			close.setChecked(true); // to be checked, true equals 1 or 0
		else
			close.setChecked(false);
		apply.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if (close.isChecked()){
					TimeSheetRestaurantEdit.onClic.setClose(1);
					finish();
				}
				else {
					SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
				
				try { 
					Date d = sdf.parse(morning1.getText().toString());
					d = sdf.parse(morning2.getText().toString());
					d = sdf.parse(evening1.getText().toString());
					d = sdf.parse(evening1.getText().toString());
					TimeSheetRestaurantEdit.onClic.setMorningOpening(morning1.getText().toString());
					TimeSheetRestaurantEdit.onClic.setMorningClosing(morning2.getText().toString());
					TimeSheetRestaurantEdit.onClic.setEveningOpening(evening1.getText().toString());
					TimeSheetRestaurantEdit.onClic.setEveningClosing(evening1.getText().toString());
					TimeSheetRestaurantEdit.onClic.setClose(0);
					finish();
				}
				catch (Exception e){ 
					Toast toast = Toast.makeText(getApplicationContext(), "Veuillez introduire une heure au format hh:mm", Toast.LENGTH_LONG);
					toast.show();}
				

				}
			}
		});


	}
}
