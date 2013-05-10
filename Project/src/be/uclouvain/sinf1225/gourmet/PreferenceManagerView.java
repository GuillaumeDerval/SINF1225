package be.uclouvain.sinf1225.gourmet;

import be.uclouvain.sinf1225.gourmet.models.Preference;
import be.uclouvain.sinf1225.gourmet.models.User;
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
		
		//loading existing preference if there is.
		final String userEmail = User.getUserConnected().getEmail();
		if (Preference.isTherePref(userEmail)){
			Preference pref = Preference.getPrefByUserEmail(userEmail);
			
			final CheckBox allergenBox = (CheckBox) findViewById(R.id.allergen_checkbox);
			final CheckBox vegBox = (CheckBox) findViewById(R.id.veg_checkbox);
			final CheckBox spicyBox = (CheckBox) findViewById(R.id.spicy_checkbox);
			
			budgetSpinner.setSelection(pref.getBudget()-1);
			allergenBox.setChecked(pref.isAllergen());
			vegBox.setChecked(pref.isVegeterian());
			spicyBox.setChecked(pref.isSpicy());
			
		} 
		
		final Button saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				
				final String userEmail = User.getUserConnected().getEmail();
				
				int budgetOption = budgetSpinner.getSelectedItemPosition() + 1;
				
				final CheckBox allergenBox = (CheckBox) findViewById(R.id.allergen_checkbox);
				boolean allergenBool = allergenBox.isChecked();
				
				final CheckBox vegBox = (CheckBox) findViewById(R.id.veg_checkbox);
				boolean vegBool = vegBox.isChecked();

				final CheckBox spicyBox = (CheckBox) findViewById(R.id.spicy_checkbox);
				boolean spicyBool = spicyBox.isChecked();
				
				Preference currentPreference = new Preference(userEmail, budgetOption, allergenBool, spicyBool, vegBool);
				
				Context context = getApplicationContext();
				Toast toast;
				
				if (Preference.isTherePref(userEmail)){
					Preference.updatePreference(currentPreference);
					toast = Toast.makeText(context, "Vos préférences ont été modifiées.", Toast.LENGTH_SHORT);
					
				} else {
					Preference.addPreference(currentPreference);
					toast = Toast.makeText(context, "Vos préférences ont été sauvées.", Toast.LENGTH_SHORT);
				}
				
				toast.show();
				
				
			}
		});
		
	}
}