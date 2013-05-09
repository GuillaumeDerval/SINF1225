package be.uclouvain.sinf1225.gourmet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import be.uclouvain.sinf1225.gourmet.ImageAdapter.ViewIds;
import be.uclouvain.sinf1225.gourmet.models.Image;
import be.uclouvain.sinf1225.gourmet.models.TimeTable;
import be.uclouvain.sinf1225.gourmet.utils.GourmetFiles;

public class TimeTableAdapter extends ArrayAdapter<TimeTable>{
	
	static class ViewIds
	{
		TextView day;
		TextView opening1;
		TextView opening2;
		TextView closing1;
		TextView closing2;
	}
	private Context context;
	private int layoutResourceId;
	protected List<TimeTable> timeTable = null;
	public TimeTableAdapter(Context context, int layoutResourceId, List<TimeTable> timeTable)
	{
		super(context, layoutResourceId, new ArrayList<TimeTable>(timeTable));
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.timeTable = timeTable;
	}
	public View getView(int position, View convertView, ViewGroup p)
	{
		View row = convertView;
		ViewIds viewIds = null;
		TimeTable table = timeTable.get(position);

		// Si il n'y a pas encore de lignes
		if (row == null)
		{
			// Créons une nouvelle ligne
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, p, false);
			viewIds = new ViewIds();
			viewIds.day= (TextView) row.findViewById(R.id.date_day);
			viewIds.opening1 = (TextView) row.findViewById(R.id.date_time1);
			viewIds.closing1 = (TextView) row.findViewById(R.id.date_time2);
			viewIds.opening2 = (TextView) row.findViewById(R.id.date_time3);
			viewIds.closing2 = (TextView) row.findViewById(R.id.date_time4);
			row.setTag(viewIds);
		}
		else
		{
			viewIds = (ViewIds) row.getTag();
		}
		if( table.getClose() != 0) {viewIds.day.setTextColor(0xffff0000); Log.d("",table.getDay()+"<----------------------" );}
		viewIds.day.setText(table.getDay());
		viewIds.opening1.setText(table.getMorningOpening());
		viewIds.closing1.setText(table.getMorningClosing());
		viewIds.opening2.setText(table.getEveningOpening());
		viewIds.closing2.setText(table.getEveningClosing());
		return row;
		
	}

}
