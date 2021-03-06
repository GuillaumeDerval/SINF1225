package be.uclouvain.sinf1225.gourmet;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;
import be.uclouvain.sinf1225.gourmet.utils.GourmetUtils;

/**
 * 
 * @author qeggerickx
 * 
 */
@SuppressLint("SimpleDateFormat")
public class RestaurantImageView extends Activity
{
	private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_DELETE_IMAGE = 2;
	private String filePath;
	static Restaurant resto;
	static ImageAdapter adapter;
	static Image imageOnClic;
	ListView imageList;

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
	 * Called on the creation of the activity(*after* the creation of fragment's view) bundle contains restoId
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list_view);
		int restoId = getIntent().getExtras().getInt("restoId");
		resto = Restaurant.getRestaurant(restoId);

		imageList = (ListView) findViewById(R.id.restaurantImageList);
		// getting button
		final Button addButton = (Button) findViewById(R.id.addRestaurantImage); // à changer
		final Button okButton = (Button) findViewById(R.id.okRestaurantImageButton);
		// get images

		adapter = new ImageAdapter(this, R.layout.restaurant_image_list_row, resto.getImages());
		imageList.setAdapter(adapter);
		imageList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final ListView imageList = (ListView) findViewById(R.id.restaurantImageList);
				final ImageAdapter adapter = (ImageAdapter) imageList.getAdapter();

				imageOnClic = adapter.getItem(position);
				/*
				 * On change de fragment
				 */
				Intent intent = new Intent(RestaurantImageView.this, ViewRestaurantImage.class);
				intent.putExtra("path", imageOnClic.getPath());
				intent.putExtra("legend", imageOnClic.getLegend());
				startActivityForResult(intent, RESULT_DELETE_IMAGE);
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
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaColumns.DATA };

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			filePath = cursor.getString(columnIndex);
			cursor.close();
			final String objectType = "restaurant";
			final int objectId = resto.getId();

			Date d = new Date();
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
			String s = f.format(d);
			String finalFilePath = "img/" + objectType + "_" + objectId + "_" + s + ".png";
			GourmetFiles.copyImageToDisk(finalFilePath, filePath);

			Image img = new Image(null, finalFilePath, objectType, objectId); // create a new image

			Image.addImage(img); // ajoute l'image dans la DB
			resto.addImage(img);
			adapter = new ImageAdapter(this, R.layout.restaurant_image_list_row, resto.getImages());
			imageList.setAdapter(adapter);
		}
		if (requestCode == RESULT_DELETE_IMAGE)
		{
			adapter = new ImageAdapter(this, R.layout.restaurant_image_list_row, resto.getImages());
			imageList.setAdapter(adapter);
		}
	}
}