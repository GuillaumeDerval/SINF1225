package be.uclouvain.sinf1225.gourmet;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

/**
 * 
 * @author qeggerickx
 * 
 */
public class ViewDishImage extends Activity
{
	Dish dish;
	private static int RESULT_LOAD_IMAGE = 1;
	private String filePath;
	private ImageView myImage;
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    GourmetUtils.createMenu(menu, this, R.id.search);
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
		setContentView(R.layout.activity_image_dish_view);
		int dishId = Integer.valueOf(getIntent().getExtras().getInt("dishId"));
		dish = Dish.getDish(dishId);
		final Image img = dish.getImg();

		final Button deleteButton = (Button) findViewById(R.id.delete_image_dish);
		final Button changeButton = (Button) findViewById(R.id.change_image_dish);
		myImage = (ImageView) findViewById(R.id.dish_image_icon);

		if (img != null)
		{
			File imgFile = new File(GourmetFiles.getRealPath(img.getPath()));
			if (imgFile.exists())
			{

				myImage.setImageBitmap( BitmapFactory.decodeFile(GourmetFiles.getRealPath(dish.getImg().getPath())));
			}
		}
		else 
		{
			deleteButton.setVisibility(View.GONE);
			changeButton.setText("Add");
			Toast toast = Toast.makeText(getApplicationContext(), "Aucune image pour ce plat", Toast.LENGTH_LONG);
			toast.show();
		}
		deleteButton.setOnClickListener(new View.OnClickListener()
		{
			/**
			 * perform action on click -> delete image
			 */
			public void onClick(View v)
			{
				img.deleteImage(); // delete the image in the database
				finish();
			}
		});
		changeButton.setOnClickListener(new View.OnClickListener()
		{
			/**
			 * perform action on click
			 */
			public void onClick(View v)
			{
				//Bundle args = new Bundle();
				//args.putString("objectType", "dish");
				//args.putInt("id", dish.getDishId());
				//Intent intent = new Intent(ViewDishImage.this, ImageGalleryActivity.class);
				//intent.putExtras(args);
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//i.putExtras(args);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
				
				//startActivityForResult(intent, R.layout.activity_gallery_view);
				
			}
		});
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
			
			final String objectType = "dish";
			final int objectId = dish.getDishId();
			String finalFilePath = "img/" + objectType + "_" + objectId + ".png";
			GourmetFiles.copyImageToDisk(finalFilePath, filePath);
			
			Image img = new Image(null, finalFilePath, objectType, objectId); // create a new image
			
			if (dish.getImg() != null)
			{
				Toast toast = Toast.makeText(getApplicationContext(), "Previous image will be deleted", Toast.LENGTH_LONG);
				toast.show();
				GourmetFiles.deleteExternalStoragePrivateFile(dish.getImg().getPath());
				dish.getImg().deleteImage();

			}
			Image.addImage(img); // ajoute l'image dans la DB
			dish.setImg(img);
			myImage.setImageBitmap(BitmapFactory.decodeFile(GourmetFiles.getRealPath(finalFilePath)));
			finish(); // end the activity
		}
	}

}
