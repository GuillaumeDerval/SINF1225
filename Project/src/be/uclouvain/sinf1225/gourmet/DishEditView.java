package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Image;
import android.widget.CheckBox;
/**
 * 
 * @author qeggerickx
 *
 */
public class DishEditView extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //define the layout
        return inflater.inflate(R.layout.activity_dish_edit, container, false);//Todo
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		int dishId = Integer.valueOf(getArguments().getString("dishId"));
		final Dish dish = Dish.getDish(dishId);
		final Image img = dish.getImg();
		
		//button's creation
		final EditText EditName = (EditText) getActivity().findViewById(R.id.EditDishName);
		final EditText EditCategory = (EditText) getActivity().findViewById(R.id.EditDishCategory);
		final EditText EditPrice = (EditText) getActivity().findViewById(R.id.EditDishPrice);
		final EditText EditAvailable = (EditText) getActivity().findViewById(R.id.EditDishAvailable);
		final EditText EditDescription = (EditText) getActivity().findViewById(R.id.EditDishDescription);
		
		final CheckBox EditSpicy = ( CheckBox) getActivity().findViewById(R.id.EditDishSpicy);
		final CheckBox EditVegan = ( CheckBox) getActivity().findViewById(R.id.EditDishVegan);
		final CheckBox EditAllergen = ( CheckBox) getActivity().findViewById(R.id.EditDishAllergen);
		
		final Button ApplyButton = (Button) getActivity().findViewById(R.id.buttonDishApply);
		final Button DeleteButton = (Button) getActivity().findViewById(R.id.buttonDishDelete);
		final Button imageButton = ( Button) getActivity().findViewById(R.id.dishImageButton);
		
		//field's init
		EditName.setText(dish.getName());
		EditCategory.setText(dish.getCategory());
		EditPrice.setText(""+dish.getPrice());
		EditAvailable.setText(dish.getAvailable());
		EditDescription.setText(dish.getDescription());
		
		if( dish.getSpicy() ==1) EditSpicy.setChecked(true); // to be checked, true equals 1 or 0
		else EditSpicy.setChecked( false);

		if( dish.getVegan() ==1) EditVegan.setChecked(true);
		else EditVegan.setChecked( false);
		
		if( dish.getAllergen() ==1) EditAllergen.setChecked(true);
		else EditAllergen.setChecked( false);
		
		ApplyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	dish.setName(EditName.getText().toString());
            	dish.setCategory(EditCategory.getText().toString());
            	dish.setPrice(Double.parseDouble(EditPrice.getText().toString()));
            	dish.setAvailable(Integer.parseInt(EditAvailable.getText().toString()));
            	dish.setDescription(EditDescription.getText().toString());
            	
            	if( EditSpicy.isChecked()) dish.setSpicy(1);
            	else dish.setSpicy(0);
            	
            	if( EditVegan.isChecked()) dish.setVegan(1);
            	else dish.setVegan(0);
            	
            	if( EditAllergen.isChecked()) dish.setAllergen(1);
            	else dish.setAllergen(0);
            	
            	(new Dish()).updateDish(dish); // to be checked
            	getFragmentManager().popBackStack(); // back to the previous view
            	}
		});
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	(new Dish()).deleteDish(dish);
            	getFragmentManager().popBackStack();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
			    /*
			     * On change de fragment
			     */
			    Fragment imageView = new ViewDishImage(); //On crée le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putString("path", img.getPath());
			    args.putString("legend", img.getLegend());
			    args.putInt("dishId", dish.getDishId());
			    imageView.setArguments(args); //on lui assigne le conteneur
			    
			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
			    transaction.replace(android.R.id.content, imageView);
			    transaction.addToBackStack(null);
			    transaction.commit();
        	}
        });
	}
	public void onPause()
	{
		super.onStop();
	}
	public void onResume()
	{
		super.onResume();
	}

}
