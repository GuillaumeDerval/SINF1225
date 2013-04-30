package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import android.widget.Button;
/**
 * 
 * @author qeggerickx
 *
 */
public class RestaurantEditView extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //define layout
        return inflater.inflate(R.layout.activity_restaurant_edit, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		int restoId = Integer.valueOf(getArguments().getString("restoId"));
		final Restaurant resto = Restaurant.getRestaurant(restoId);
		
		// création des boutons
		final EditText EditName = (EditText) getActivity().findViewById(R.id.EditName);
		final EditText EditCity = (EditText) getActivity().findViewById(R.id.EditCity);
		final EditText EditPhone = (EditText) getActivity().findViewById(R.id.EditPhoneNumber);
		final EditText EditAdress = (EditText) getActivity().findViewById(R.id.EditAdress);
		final EditText EditPriceCat = (EditText) getActivity().findViewById(R.id.EditPriceCat);
		final EditText EditEmail = (EditText) getActivity().findViewById(R.id.EditEmail);
		final EditText EditSeats = (EditText) getActivity().findViewById(R.id.EditSeats);
		final EditText EditDescription = (EditText) getActivity().findViewById(R.id.EditDescription);
		final EditText EditWebsite = (EditText) getActivity().findViewById(R.id.EditWebsite);
		final RatingBar Stars = (RatingBar) getActivity().findViewById(R.id.restaurantStars);
		
		final Button ApplyButton = (Button) getActivity().findViewById(R.id.buttonApply);
		final Button imageButton = (Button) getActivity().findViewById(R.id.seeImageRestaurant);
		
		// field's init
		EditName.setText(resto.getName());
		EditCity.setText(resto.getCity().getName());
		EditPhone.setText(resto.getPhone());
		EditAdress.setText(resto.getAddress());
		//EditPriceCat.setText(resto.getPriceCate());
		EditEmail.setText(resto.getEmail());
		EditSeats.setText(resto.getSeats());
		EditDescription.setText(resto.getDescription());
		EditWebsite.setText(resto.getWebsite());
		
		Stars.setMax(5); // set max =5! Just to be sure
		Stars.setNumStars(resto.getStars());
		
		imageButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * perform action on click
			 */
            public void onClick(View v) 
            {
            	Fragment restaurantImageView = new RestaurantImageView();
 			    Bundle args = new Bundle(); //un conteneur pour ses arguments
 			    args.putString("restoId", ""+resto.getId()); // bundle contains restoId
 			    restaurantImageView.setArguments(args); //on lui assigne le conteneur
 			    
 			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
 			    transaction.replace(android.R.id.content, restaurantImageView);
 			    transaction.addToBackStack(null);
 			    transaction.commit();
            }
        });
		
		ApplyButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * perform action on click
			 */
            public void onClick(View v) 
            {
            	resto.setName(EditName.getText().toString());
            	//resto.setCity(EditCity.getText().toString());
            	resto.setPhone(EditPhone.getText().toString());
            	resto.setAddress(EditAdress.getText().toString());
            	//resto.setPriceCate(Integer.parseInt(EditPriceCat.getText().toString()));
            	resto.setEmail(EditEmail.getText().toString());
            	resto.setSeats(Integer.parseInt(EditSeats.getText().toString()));
            	resto.setDescription(EditDescription.getText().toString());
            	resto.setWebsite(EditWebsite.getText().toString());
            	resto.setName(EditName.getText().toString());
            	resto.setStars((int)Double.parseDouble(""+Stars.getRating())); // to be checked
            	
            	Restaurant.updateRestaurant(resto); // to be checked
            	getFragmentManager().popBackStack(); // back to the previous view 
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
