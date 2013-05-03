package be.uclouvain.sinf1225.gourmet.models;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author qeggerickx
 *
 */
public class Image 
{
	
	private String legend; // legend
	private String path;
	private String objectType;
	private int objectId;
	
	/**
	 *  Images are define by a legend , a path, an object type ( restaurant / dish) and the id of this object
	 * @param name
	 * @param path
	 * @param objetType
	 * @param objetId
	 */
	public Image(String legend, String path, String objectType, int objectId) 
	{
		super();
		this.legend = legend;
		this.path = path;
		this.objectType = objectType;
		this.objectId = objectId;
	}
	public Image() 
	{
		super();
	}
	/**
	 * add an image in the database
	 * @param img
	 */
	public static void addImage(Image img)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.addImage(img);
		db.close();
	}
	/**
	 * delete one image from the type and the id
	 * YOU CAN ONLY USE THIS METHOD WITH A DISH! A RESTAURANT CAN HAVE MORE THAN ONE IMAGE
	 * @param type
	 * @param id
	 */
	public static void deleteImage(String type, int id)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteImage(type, id);
		db.close();
	}
	/**
	 * delete one image from the path
	 * @param path
	 */
	public static void deleteImage(String path)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteImage(path);
		db.close();
	}
	/**
	 * return images link to type and the id
	 * @return all images
	 */
	public static List<Image> getAllImages(String type, int Id)
	{
		GourmetDatabase db = new GourmetDatabase();
		List<Image> images = new ArrayList<Image>();
		images = db.getAllImages(type, Id);
		db.close();
		return images;
	}
	public String getLegend() 
	{
		return legend;
	}
	public void setLegend(String legend) 
	{
		this.legend = legend;
	}
	public String getPath() 
	{
		return path;
	}
	public void setPath(String path) 
	{
		this.path = path;
	}
	public String getObjectType() 
	{
		return objectType;
	}
	public void setObjectType(String objectType) 
	{
		this.objectType = objectType;
	}
	public int getObjectId() 
	{
		return objectId;
	}
	public void setObjectId(int objectId) 
	{
		this.objectId = objectId;
	}
}
