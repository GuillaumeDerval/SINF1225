package be.uclouvain.sinf1225.gourmet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.io.File;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import be.uclouvain.sinf1225.gourmet.models.Image;
/**
 * 
 * @author qeggerickx
 *
 */
public class ViewRestaurantImage extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        //On dŽfini le layout de ce fragment
        return inflater.inflate(R.layout.activity_image_view, container, false);
    }
    /**
     * bundle = path & legend
     */
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		final Button deleteButton = (Button) getActivity().findViewById(R.id.delete_view_restaurant_image);
		final ImageView myImage = (ImageView) getActivity().findViewById(R.id.image_view_restaurant_image);
		
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
