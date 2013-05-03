package be.uclouvain.sinf1225.gourmet;

import java.io.File;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Dish;
/**
 * 
 * @author qeggerickx
 *
 */
public class ViewDishImage extends Fragment
{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On dŽfini le layout de ce fragment
        return inflater.inflate(R.layout.activity_image_dish_view, container, false);
    }
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		int dishId = Integer.valueOf(getArguments().getString("dishId"));
		final Dish dish = Dish.getDish(dishId);
		
		final Button deleteButton = (Button) getActivity().findViewById(R.id.delete_image_dish);
		final Button changeButton = (Button) getActivity().findViewById(R.id.change_image_dish);
		final ImageView myImage = (ImageView) getActivity().findViewById(R.id.dish_image_icon);
		
		File imgFile = new  File(getArguments().getString("path"));
		if(imgFile.exists())
		{

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    myImage.setImageBitmap(myBitmap);
		}
		deleteButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * perform action on click -> delete image
			 */
            public void onClick(View v) 
            {
            	File imgFile = new  File(getArguments().getString("path"));
        		if(imgFile.exists())
        		{
            	imgFile.delete(); // delete the file
        		}
        		Image.deleteImage(getArguments().getString("path")); // delete the image in the database
        		getFragmentManager().popBackStack();
            }
        });
		changeButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * perform action on click
			 */
            public void onClick(View v) 
            {
			    //Activity galleryView = new ImageGalleryActivity(); //On crŽe le fragment
			    Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putString("objectType", "dish");//type
			    args.putString("id", ""+dish.getDishId());// id
			    Intent intent = new Intent(getActivity(), ImageGalleryActivity.class);
			    
			    intent.putExtras(args); //On affecte à l'Intent le Bundle que l'on a créé
			    startActivityForResult(intent, R.layout.activity_gallery_view);// not sure of this	
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
