package be.uclouvain.sinf1225.gourmet;

import android.widget.ArrayAdapter;
import be.uclouvain.sinf1225.gourmet.models.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author qeggerickx
 * 
 */
public class ImageAdapter extends ArrayAdapter<Image>
{

	static class ViewIds
	{
		TextView legend;
		ImageView image;
	}

	private Context context;
	private int layoutResourceId;
	protected List<Image> images = null;

	/**
	 * create new imageadapter
	 * 
	 * @param context
	 * @param layoutResourceId
	 * @param images
	 */
	public ImageAdapter(Context context, int layoutResourceId, List<Image> images)
	{
		super(context, layoutResourceId, new ArrayList<Image>(images));
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.images = images;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup p)
	{
		View row = convertView;
		ViewIds viewIds = null;
		Image imageView = images.get(position);

		// Si il n'y a pas encore de lignes
		if (row == null)
		{
			// Créons une nouvelle ligne
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, p, false);
			viewIds = new ViewIds();
			viewIds.image = (ImageView) row.findViewById(R.id.restaurantImageListRowImg);
			viewIds.legend = (TextView) row.findViewById(R.id.restaurantImageListRowLegend);
			row.setTag(viewIds);
		}
		else
		{
			viewIds = (ViewIds) row.getTag();
		}
		viewIds.legend.setText(imageView.getLegend());
		// recuperer l'image
		File imgFile = new File(imageView.getPath());
		if (imgFile.exists())
		{

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			viewIds.image.setImageBitmap(myBitmap);
		}
		return row;
	}
}
