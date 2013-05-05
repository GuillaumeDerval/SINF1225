package be.uclouvain.sinf1225.gourmet;

import java.util.ArrayList;
import java.util.List;

import be.uclouvain.sinf1225.gourmet.models.Dish;
import be.uclouvain.sinf1225.gourmet.models.Restaurant;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter for displaying Dishes in DishesListActivity Hello
 */
public class DishAdapter extends ArrayAdapter<Dish>
{
  static class ViewIds
    {
      TextView name;
    }
  
    private Context context; 
    private int layoutResourceId;    
    protected List<Dish> dishes = null;

	
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
            row.setTag(viewIds);
        }
        else
        {
        	viewIds = (ViewIds)row.getTag();
        }
        
        Dish dish = dishes.get(position);
        viewIds.name.setText(dish.getName());
        return row;
    }
}
