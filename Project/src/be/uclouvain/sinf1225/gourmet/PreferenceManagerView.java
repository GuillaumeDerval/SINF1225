package be.uclouvain.sinf1225.gourmet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import be.uclouvain.sinf1225.gourmet.models.Preference;
import be.uclouvain.sinf1225.gourmet.models.User;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

public class PreferenceManagerView extends Activity
{
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		GourmetUtils.createMenu(menu, this, R.id.preferences);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preference_manager);

		// Ajout des options sur le budget_spinner
		final Spinner budgetSpinner = (Spinner) findViewById(R.id.budget_spinner);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.pref_budget_list, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		budgetSpinner.setAdapter(adapter1);

		// loading existing preference if there is.
		final String userEmail = User.getUserConnected().getEmail();
		if (Preference.isTherePref(userEmail))
		{
			Preference pref = Preference.getPrefByUserEmail(userEmail);

			final EditText allergenEdit = (EditText) findViewById(R.id.allergen_edit);
			final CheckBox vegBox = (CheckBox) findViewById(R.id.veg_checkbox);

			budgetSpinner.setSelection(pref.getBudget() - 1);
			allergenEdit.setText(pref.getAllergensText());
			vegBox.setChecked(pref.isVegeterian());
		}

		final Button saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				final String userEmail = User.getUserConnected().getEmail();

				int budgetOption = budgetSpinner.getSelectedItemPosition() + 1;

				final EditText allergenEdit = (EditText) findViewById(R.id.allergen_edit);
				String allergenValue = allergenEdit.getText().toString();

				final CheckBox vegBox = (CheckBox) findViewById(R.id.veg_checkbox);
				boolean vegBool = vegBox.isChecked();

				Preference currentPreference = new Preference(userEmail, budgetOption, allergenValue, vegBool);

				Context context = getApplicationContext();
				Toast toast;

				if (Preference.isTherePref(userEmail))
				{
					Preference.updatePreference(currentPreference);
					toast = Toast.makeText(context, "Vos préférences ont été modifiées.", Toast.LENGTH_SHORT);

				}
				else
				{
					Preference.addPreference(currentPreference);
					toast = Toast.makeText(context, "Vos préférences ont été sauvées.", Toast.LENGTH_SHORT);
				}

				toast.show();

			}
		});

	}
}