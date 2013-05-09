package be.uclouvain.sinf1225.gourmet;

import java.util.List;

import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.models.TimeTable;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TimeSheetRestaurant extends Activity{
	
	static Restaurant resto;
	static TimeTableAdapter adapter;
	ListView timeTableList;

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
		setContentView(R.layout.activity_restaurant_timesheet_view);
		int restoId = getIntent().getExtras().getInt("restoId");
		resto = Restaurant.getRestaurant(restoId);

		timeTableList = (ListView) findViewById(R.id.restaurant_time_sheet_list);
		List<TimeTable> timeTable = Restaurant.getTimeTable(restoId);
		if ( timeTable != null && timeTable.size()>0)
		{
			adapter = new TimeTableAdapter(this, R.layout.restaurant_timesheet_row, timeTable);
			timeTableList.setAdapter(adapter);
		}
		else {
			Toast toast = Toast.makeText(getApplicationContext(), "Aucun horaire disponible", Toast.LENGTH_LONG);
			toast.show();
			finish();
		}
	}

}
