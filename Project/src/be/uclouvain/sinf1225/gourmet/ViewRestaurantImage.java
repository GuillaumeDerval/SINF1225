package be.uclouvain.sinf1225.gourmet;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

/**
 * 
 * @author qeggerickx
 * 
 */
public class ViewRestaurantImage extends Activity
{
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		GourmetUtils.createMenu(menu, this, R.id.search);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}

	/**
	 * bundle = path & legend
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);

		final Button deleteButton = (Button) findViewById(R.id.delete_view_restaurant_image);
		final ImageView myImage = (ImageView) findViewById(R.id.image_view_restaurant_image);

		File imgFile = new File(GourmetFiles.getRealPath(getIntent().getExtras().getString("path")));
		if (imgFile.exists())
		{

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			myImage.setImageBitmap(myBitmap);
		}
		deleteButton.setOnClickListener(new View.OnClickListener()
		{
			/**
			 * perform action on click -> delete image
			 */
			@Override
			public void onClick(View v)
			{
				File imgFile = new File(GourmetFiles.getRealPath(getIntent().getExtras().getString("path")));
				if (imgFile.exists())
				{
					imgFile.delete(); // delete the file
				}
				Image.deleteImage(getIntent().getExtras().getString("path")); // delete the image in the database
				GourmetFiles.deleteExternalStoragePrivateFile(getIntent().getExtras().getString("path"));
				RestaurantImageView.resto.deleteImage(RestaurantImageView.imageOnClic);
				finish();
			}
		});
	}
}
