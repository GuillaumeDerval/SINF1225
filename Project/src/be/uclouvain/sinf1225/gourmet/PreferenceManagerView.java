package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class PreferenceManagerView extends Activity
{
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.preferences);
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
		setContentView(R.layout.activity_preference_manager);
		
		//Ajout des options sur le budget_spinner
		final Spinner budgetSpinner = (Spinner) findViewById(R.id.budget_spinner);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pref_budget_list, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		budgetSpinner.setAdapter(adapter1);
		
		final Button saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				
				
				final CheckBox allergenBox = (CheckBox) findViewById(R.id.allergen_checkbox);
				final CheckBox vegBox = (CheckBox) findViewById(R.id.veg_checkbox);
				final CheckBox spicyBox = (CheckBox) findViewById(R.id.spicy_checkbox);
				boolean allergenBool = allergenBox.isChecked();
				boolean vegBool = vegBox.isChecked();
				boolean spicyBool = spicyBox.isChecked();
				String budgetText = budgetSpinner.getSelectedItem().toString();
				
				Context context = getApplicationContext();
				Toast toast = Toast.makeText(context, "budget = "+budgetText+"\nallergen = "+allergenBool+"\nvegetarien = "+vegBool+"\nspicy = "+spicyBool, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
	}
}