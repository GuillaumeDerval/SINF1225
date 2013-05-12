package be.uclouvain.sinf1225.gourmet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;

/**
 * Adapter for displaying Restaurants in RestauransListActivity Hello
 */
public class RestaurantAdapter extends ArrayAdapter<Restaurant>
{
	static class ViewIds
	{
		TextView name;
		TextView distance;
		TextView pricecat;
		TextView seats;
	}

	private class RestaurantComparator implements Comparator<Restaurant>
	{
		@Override
		public int compare(Restaurant restaurant1, Restaurant restaurant2)
		{
			int ret = 0;
			if (orderby.equals("name"))
				ret = restaurant1.getName().compareTo(restaurant2.getName());
			else if (orderby.equals("distance"))
			{
				if (lastLocation == null)
					return 0;
				ret = Double.valueOf(restaurant1.getLocation().distanceTo(lastLocation)).compareTo((double) restaurant2.getLocation().distanceTo(lastLocation));
			}
			else if (orderby.equals("pricecat"))
			{
				ret = restaurant1.getPriceCategory().compareTo(restaurant2.getPriceCategory());
			}
			else if (orderby.equals("seats"))
			{
				ret = Integer.valueOf(restaurant1.getSeats()).compareTo(restaurant2.getSeats());
			}
			if (!orderasc)
				ret = -ret;
			return ret;
		}
	}

	/**
	 * Filter restaurants
	 * 
	 * @author Guillaume Derval alias Nicolas Cage
	 */
	private class RestaurantFilter extends Filter
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults results = new FilterResults();
			String prefix = constraint.toString().toLowerCase(Locale.FRENCH);

			if (prefix == null || prefix.length() == 0)
			{
				ArrayList<Restaurant> list = new ArrayList<Restaurant>(restaurants);
				results.values = list;
				results.count = list.size();
			}
			else
			{
				final ArrayList<Restaurant> list = new ArrayList<Restaurant>(restaurants);
				final ArrayList<Restaurant> nlist = new ArrayList<Restaurant>();

				for (int i = 0; i < list.size(); i++)
				{
					final Restaurant restaurant = list.get(i);

					if (restaurant.getName().toLowerCase(Locale.FRENCH).startsWith(prefix))
					{
						nlist.add(restaurant);
					}
				}

				results.values = nlist;
				results.count = nlist.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			filteredRestaurants = (ArrayList<Restaurant>) results.values;

			clear();
			int count = filteredRestaurants.size();
			for (int i = 0; i < count; i++)
				add(filteredRestaurants.get(i));

			sort();
		}

	}

	private Context context;
	private int layoutResourceId;
	protected List<Restaurant> restaurants = null;
	protected List<Restaurant> filteredRestaurants = null;
	protected Location lastLocation = null;
	private Filter filter;
	protected String orderby = "name";
	protected boolean orderasc = true;

	/**
	 * Create a new RestaurantAdapter
	 * 
	 * @param context
	 *            Context associated with this Adapter
	 * @param layoutResourceId
	 *            Ressource of the layout of a line
	 * @param restaurants
	 *            restaurants to display
	 */
	public RestaurantAdapter(Context context, int layoutResourceId, List<Restaurant> restaurants, Location loc)
	{
		super(context, layoutResourceId, new ArrayList<Restaurant>(restaurants));
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.restaurants = restaurants;
		this.filteredRestaurants = new ArrayList<Restaurant>(restaurants);
		this.lastLocation = loc;
	}

	public void updateLocation(Location loc)
	{
		lastLocation = loc;
		notifyDataSetChanged();
		// sort(); //For filtering
	}

	@Override
	public Filter getFilter()
	{
		if (filter == null)
			filter = new RestaurantFilter();

		return filter;
	}

	/**
	 * re-sort lines
	 */
	public void sort()
	{
		Collections.sort(filteredRestaurants, new RestaurantComparator());
		clear();
		int count = filteredRestaurants.size();
		for (int i = 0; i < count; i++)
			add(filteredRestaurants.get(i));
	}

	/**
	 * Sort lines
	 * 
	 * @param sort
	 *            Type of sort. Must be "distance","restaurants" or "name"
	 */
	public void setSort(String sort)
	{
		orderby = sort;
		sort();
	}

	/**
	 * Sort lines
	 * 
	 * @param sort
	 *            Type of sort. Must be "distance","restaurants" or "name"
	 * @param sortorder
	 *            true if order is Asc
	 */
	public void setSort(String sort, boolean sortorder)
	{
		orderby = sort;
		orderasc = sortorder;
		sort();
	}

	/**
	 * Sort lines
	 * 
	 * @param sort
	 *            true if order is Asc
	 */
	public void setSortOrder(boolean sort)
	{
		orderasc = sort;
		sort();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup p)
	{
		View row = convertView;
		ViewIds viewIds = null;

		// Si il n'y a pas encore de lignes
		if (row == null)
		{
			// CrÃ©ons une nouvelle ligne
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, p, false);

			// Et une correspondance entre cette ligne et les champs de texte
			viewIds = new ViewIds();
			viewIds.name = (TextView) row.findViewById(R.id.RestaurantListName);
			viewIds.pricecat = (TextView) row.findViewById(R.id.RestaurantListPriceCat);
			viewIds.seats = (TextView) row.findViewById(R.id.RestaurantListSeats);
			viewIds.distance = (TextView) row.findViewById(R.id.RestaurantListDistance);
			row.setTag(viewIds);
		}
		else
		{
			viewIds = (ViewIds) row.getTag();
		}

		Restaurant restaurant = filteredRestaurants.get(position);
		viewIds.name.setText(restaurant.getName());
		viewIds.pricecat.setText("CatŽgorie de prix: " + restaurant.getPriceCategory().toString());
		viewIds.seats.setText("Places disponibles: " + String.valueOf(restaurant.getSeats()));
		double dist = -1;
		if (lastLocation != null)
			dist = restaurant.getLocation().distanceTo(lastLocation);
		if (dist >= 0)
			viewIds.distance.setText("Distance: " + new DecimalFormat("#.##").format(dist / 1000000) + " Km");
		else
			viewIds.distance.setText("");
		return row;
	}
}
