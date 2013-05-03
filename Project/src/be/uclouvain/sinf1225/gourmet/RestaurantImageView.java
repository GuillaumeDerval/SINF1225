package be.uclouvain.sinf1225.gourmet;

import java.util.List;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
/**
 * 
 * @author qeggerickx
 *
 */
public class RestaurantImageView extends Fragment
{
	
	/**
	 * Called on the creation of the view; Inflate xml
	 */
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On dŽfini le layout de ce fragment
        return inflater.inflate(R.layout.activity_image_list_view, container, false);
    }
	/**
	 * Called on the creation of the activity(*after* the creation of fragment's view)
	 * bundle contains restoId
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		int restoId = Integer.valueOf(getArguments().getString("restoId"));
		final Restaurant resto = Restaurant.getRestaurant(restoId);

		//On rŽcup�re la vue "liste"
		final ListView imageList = (ListView) getActivity().findViewById(R.id.restaurantImageList);
		// getting button
		final Button addButton = (Button) getActivity().findViewById(R.id.addRestaurantImage); // à changer
		final Button okButton = (Button) getActivity().findViewById(R.id.okRestaurantImageButton);
		//get images
		List<Image> images = Image.getAllImages("restaurant", restoId);
		ImageAdapter adapter = new ImageAdapter(getActivity(), R.layout.restaurant_image_list_row, images);
		imageList.setAdapter(adapter);
		imageList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView imageList = (ListView) getActivity().findViewById(R.id.restaurantImageList);
				final ImageAdapter adapter = (ImageAdapter)imageList.getAdapter();
				
			    Image image = adapter.getItem(position);
			    /*
			     * On change de fragment
			     */
			    Fragment imageView = new ViewRestaurantImage(); //On crŽe le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putString("path", image.getPath());
			    args.putString("legend", image.getLegend());
			    imageView.setArguments(args); //bundle = path & legend
			    
			    FragmentTransaction transaction = getFragmentManager().beginTransaction(); //et on change de fragment.
			    transaction.replace(android.R.id.content, imageView);
			    transaction.addToBackStack(null);
			    transaction.commit();
			    
			}
		});
		addButton.setOnClickListener(new View.OnClickListener() 
		{
			/**
			 * perform action on click
			 */
            @Override
			public void onClick(View v) 
            {
			    //Activity galleryView = new ImageGalleryActivity(); //On crŽe le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putString("objectType", "restaurant");//type
			    args.putString("id", ""+resto.getId());// id
			    Intent intent = new Intent(getActivity(), ImageGalleryActivity.class);
			    
			    intent.putExtras(args); //On affecte à l'Intent le Bundle que l'on a créé
			    startActivityForResult(intent, R.layout.activity_gallery_view);// not sure of this	
            }
        });
		okButton.setOnClickListener(new View.OnClickListener() 
		{
			/**
			 * perform action on click
			 */
            @Override
			public void onClick(View v) 
            {
            	getFragmentManager().popBackStack(); // back to the previous view 
            }
        });
	}
	@Override
	public void onPause()
	{
		super.onStop();
	}
	@Override
	public void onResume()
	{
		super.onResume();
	}	
}