package be.uclouvain.sinf1225.gourmet.models;


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
	 * Images are define by a legend , a path, an object type ( restaurant / dish) and the id of this object
	 * 
	 * @param legend
	 * @param path
	 * @param objectType
	 * @param objectId
	 */
	public Image(String legend, String path, String objectType, int objectId)
	{
		super();
		this.legend = legend;
		this.path = path;
		this.objectType = objectType;
		this.objectId = objectId;
	}

	/**
	 * add an image in the database
	 * 
	 * @param img
	 */
	public static void addImage(Image img)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.addImage(img);
		db.close();
	}

	/**
	 * delete image with this path from the database
	 * 
	 * @param path
	 */
	public static void deleteImage(String path)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteImage(path);
		db.close();
	}

	/**
	 * Delete image from database
	 */
	public void deleteImage()
	{
		GourmetDatabase db = new GourmetDatabase();
		db.deleteImage(this.path);
		db.close();
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
