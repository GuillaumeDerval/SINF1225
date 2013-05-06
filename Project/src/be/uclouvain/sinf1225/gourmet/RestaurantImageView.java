package be.uclouvain.sinf1225.gourmet;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;
/**
 * 
 * @author qeggerickx
 *
 */
public class RestaurantImageView extends Activity
{
	private static int RESULT_LOAD_IMAGE = 1;
	private String filePath;
	static Restaurant resto;
	static ImageAdapter adapter;
	static Image imageOnClic;
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.search);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return GourmetUtils.onMenuItemSelected(item, this);
	}
	
	/**
	 * Called on the creation of the activity(*after* the creation of fragment's view)
	 * bundle contains restoId
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list_view);
		int restoId = getIntent().getExtras().getInt("restoId");
		resto = Restaurant.getRestaurant(restoId);

		final ListView imageList = (ListView) findViewById(R.id.restaurantImageList);
		// getting button
		final Button addButton = (Button) findViewById(R.id.addRestaurantImage); // à changer
		final Button okButton = (Button) findViewById(R.id.okRestaurantImageButton);
		//get images
		//List<Image> images = Image.getAllImages("restaurant", restoId);
		adapter = new ImageAdapter(this, R.layout.restaurant_image_list_row, resto.getImages());//images);
		imageList.setAdapter(adapter);
		imageList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView imageList = (ListView) findViewById(R.id.restaurantImageList);
				final ImageAdapter adapter = (ImageAdapter)imageList.getAdapter();
				
			    imageOnClic = adapter.getItem(position);
			    /*
			     * On change de fragment
			     */
			    Intent intent = new Intent(RestaurantImageView.this, ViewRestaurantImage.class);
			    intent.putExtra("path", imageOnClic.getPath());
			    intent.putExtra("legend", imageOnClic.getLegend());
			    startActivity(intent);
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
			    /*Bundle args = new Bundle(); //un conteneur pour ses arguments
			    args.putString("objectType", "restaurant");//type
			    args.putString("id", ""+resto.getId());// id
			    Intent intent = new Intent(RestaurantImageView.this, ImageGalleryActivity.class);
			    
			    intent.putExtras(args); //On affecte à l'Intent le Bundle que l'on a créé
			    startActivityForResult(intent, R.layout.activity_gallery_view);// not sure of this	*/
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			    
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
            	finish(); // back to the previous view 
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			filePath =cursor.getString(columnIndex);
			cursor.close();
			Log.d("", filePath);
			final String objectType = "restaurant";
			final int objectId = resto.getId();
			String finalFilePath = "img/" + objectType + "_" + objectId+ "_"+filePath+ ".png";
			GourmetFiles.copyImageToDisk(finalFilePath, filePath);
			
			Image img = new Image(null, finalFilePath, objectType, objectId); // create a new image

			Image.addImage(img); // ajoute l'image dans la DB
			resto.addImage(img);
			adapter.notifyDataSetChanged();
			//myImage.setImageBitmap(BitmapFactory.decodeFile(GourmetFiles.getRealPath(finalFilePath)));
		}
	}
}