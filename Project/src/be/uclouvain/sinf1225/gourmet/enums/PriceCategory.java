package be.uclouvain.sinf1225.gourmet.enums;

/**
 * Represents price categories
 * 
 * @author guillaumederval
 */
public enum PriceCategory
{
	PRICECAT_UNKNOWN("Autre"), PRICECAT_VERYCHEAP("Trés bon marché"), PRICECAT_CHEAP("Bon marché"), PRICECAT_MIDDLE("Abordable"), PRICECAT_EXPENSIVE("Cher"), PRICECAT_VERYEXPENSIVE("Trés cher");
	private String name = "";

	// Constructeur
	PriceCategory(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public static PriceCategory fromString(String text)
	{
		if (text != null)
		{
			for (PriceCategory b : PriceCategory.values())
			{
				if (text.equalsIgnoreCase(b.name))
				{
					return b;
				}
			}
		}
		return null;
	}
}
