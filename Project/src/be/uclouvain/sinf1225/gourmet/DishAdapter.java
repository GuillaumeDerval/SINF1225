package be.uclouvain.sinf1225.gourmet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import be.uclouvain.sinf1225.gourmet.models.City;
import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
 * Adapter for displaying Dishes in DishesListActivity Hello
 */
public class DishAdapter extends ArrayAdapter<Dish>
{
	static class ViewIds
	{
		TextView name;
		TextView price;
	}
	private class DishComparator implements Comparator<Dish>
	{
		@Override
		public int compare(Dish dish1, Dish dish2)
		{
			int ret = 0;
			if(orderby.equals("name"))
				ret = dish1.getName().compareTo(dish2.getName());
			
			if(orderby.equals("price"))
				ret = Double.valueOf(dish1.getPrice()).compareTo((double)dish2.getPrice());
			
			if(!orderasc) ret = -ret;
			return ret;
		}
	}

	/**
	 * Filter dishes
	 * @author Guillaume Derval alias l'homme au mille visages
	 */
	private class DishFilter extends Filter
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults results = new FilterResults();
			String prefix = constraint.toString().toLowerCase(Locale.FRENCH);

			if (prefix == null || prefix.length() == 0)
			{
				ArrayList<Dish> list = new ArrayList<Dish>(dishes);
				results.values = list;
				results.count = list.size();
			}
			else
			{
				final ArrayList<Dish> list = new ArrayList<Dish>(dishes);
				final ArrayList<Dish> nlist = new ArrayList<Dish>();

				for (int i = 0; i < list.size(); i++)
				{
					final Dish dish = list.get(i);

					if (dish.getName().toLowerCase(Locale.FRENCH).startsWith(prefix))
					{
						nlist.add(dish);
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
			filteredDishes = (ArrayList<Dish>)results.values;

			clear();
			int count = filteredDishes.size();
			for (int i=0; i<count; i++)
				add(filteredDishes.get(i));

			sort();
		}

	}
	private Context context; 
	private int layoutResourceId;    
	protected List<Dish> dishes = null;
	protected List<Dish> filteredDishes = null;
	private Filter filter;
	protected String orderby = "name";
	protected boolean orderasc = true;

	/**
	 * Create a new DishAdapter
	 * @param context Context associated with this Adapter
	 * @param layoutResourceId Ressource of the layout of a line
	 * @param dishes dishes to display
	 */
	public DishAdapter(Context context, int layoutResourceId, List<Dish> dishes)
	{
		super(context, layoutResourceId, new ArrayList<Dish>(dishes));
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.dishes = dishes;
		this.filteredDishes = new ArrayList<Dish>(dishes);
	}

    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new DishFilter();

        return filter;
    }
    
    /**
     * re-sort lines
     */
    public void sort()
    {
    	Collections.sort(filteredDishes, new DishComparator());
    	clear();
        int count = filteredDishes.size();
        for (int i=0; i<count; i++)
            add(filteredDishes.get(i));
    }
    
    /**
     * Sort lines
     * @param sort Type of sort. Must be "distance","restaurants" or "name"
     */
    public void setSort(String sort)
    {
    	orderby = sort;
    	sort();
    }
    
    /**
     * Sort lines
     * @param sort Type of sort. Must be "distance","restaurants" or "name"
     * @param sortorder true if order is Asc
     */
    public void setSort(String sort, boolean sortorder)
    {
    	orderby = sort;
    	orderasc = sortorder;
    	sort();
    }
    
    /**
     * Sort lines
     * @param sort true if order is Asc
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

		//Si il n'y a pas encore de lignes
		if(row == null)
		{
			//Créons une nouvelle ligne
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, p, false);

			//Et une correspondance entre cette ligne et les champs de texte
			viewIds = new ViewIds();
			viewIds.name = (TextView)row.findViewById(R.id.DishListName);
			viewIds.price = (TextView)row.findViewById(R.id.DishListPrice);
			row.setTag(viewIds);
		}
		else
		{
			viewIds = (ViewIds)row.getTag();
		}

		Dish dish = filteredDishes.get(position);
		viewIds.name.setText(dish.getName());
		viewIds.price.setText("Prix: " + String.valueOf(dish.getPrice()));
		return row;
	}
}
