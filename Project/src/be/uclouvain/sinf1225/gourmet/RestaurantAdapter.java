package be.uclouvain.sinf1225.gourmet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import be.uclouvain.sinf1225.gourmet.models.Restaurant;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter for displaying Restaurants in RestauransListActivity Hello
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant>
{
  static class ViewIds
    {
    	TextView name;
    }
	
    private Context context; 
    private int layoutResourceId;    
    protected List<Restaurant> restaurants = null;
    protected Location lastLocation = null;

	
    /**
     * Create a new RestaurantAdapter
     * @param context Context associated with this Adapter
     * @param layoutResourceId Ressource of the layout of a line
     * @param restaurants restaurants to display
     */
    public RestaurantAdapter(Context context, int layoutResourceId, List<Restaurant> restaurants)
    {
        super(context, layoutResourceId, new ArrayList<Restaurant>(restaurants));
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.restaurants = restaurants;
    }

    
    @Override
	public View getView(int position, View convertView, ViewGroup p)
    {
        View row = convertView;
        ViewIds viewIds = null;
        
        //Si il n'y a pas encore de lignes
        if(row == null)
        {
        	//Créons une nouvelle ligne
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, p, false);
            
            //Et une correspondance entre cette ligne et les champs de texte
            viewIds = new ViewIds();
            viewIds.name = (TextView)row.findViewById(R.id.RestaurantListName);
            row.setTag(viewIds);
        }
        else
        {
        	viewIds = (ViewIds)row.getTag();
        }
        
        Restaurant restaurant = restaurants.get(position);
        viewIds.name.setText(restaurant.getName());
        return row;
    }
}
