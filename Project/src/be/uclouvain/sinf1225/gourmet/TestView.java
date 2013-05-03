package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TestView extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		//On défini le layout de ce fragment
		return inflater.inflate(R.layout.activity_test, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Button testRestoEdit = (Button)getActivity().findViewById(R.id.testRestoEdit);
		testRestoEdit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Fragment restaurantList = new RestaurantEditView(); //On crée le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putInt("restoId", 1);
			    restaurantList.setArguments(args); //on lui assigne le conteneur
			    
			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
			    transaction.replace(android.R.id.content, restaurantList);
			    transaction.addToBackStack(null);
			    transaction.commit();
			}	
		});
		
		Button testRestoView = (Button)getActivity().findViewById(R.id.testRestoView);
		testRestoView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Fragment restaurantList = new RestaurantView(); //On crée le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putInt("restoId", 1);
			    restaurantList.setArguments(args); //on lui assigne le conteneur
			    
			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
			    transaction.replace(android.R.id.content, restaurantList);
			    transaction.addToBackStack(null);
			    transaction.commit();
			}	
		});
	}
}
