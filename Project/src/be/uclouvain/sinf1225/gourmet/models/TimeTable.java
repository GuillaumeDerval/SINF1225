package be.uclouvain.sinf1225.gourmet.models;

import java.util.ArrayList;
import java.util.List;

public class TimeTable
{
	private String morningOpening;
	private String morningClosing;
	private String eveningOpening;
	private String eveningClosing;
	private String day;
	private int close;
	private int restoId;

	public TimeTable(String morningOpening, String morningClosing, String eveningOpening, String eveningClosing, String day, int close, int restoId)
	{
		super();
		this.morningOpening = morningOpening;
		this.morningClosing = morningClosing;
		this.eveningOpening = eveningOpening;
		this.eveningClosing = eveningClosing;
		this.day = day;
		this.close = close;
		this.restoId = restoId;
	}

	public static void updateTimeTable(TimeTable table)
	{
		GourmetDatabase db = new GourmetDatabase();
		db.updateTimeTable(table);
		db.close();
	}

	public static void addTimeTable(List<TimeTable> table)
	{
		GourmetDatabase db = new GourmetDatabase();
		for (TimeTable timetable : table)
		{
			System.out.println("helo");
			db.addTimeTable(timetable);
		}
		db.close();
	}

	public String getMorningOpening()
	{
		return morningOpening;
	}

	public void setMorningOpening(String morningOpening)
	{
		this.morningOpening = morningOpening;
	}

	public String getMorningClosing()
	{
		return morningClosing;
	}

	public void setMorningClosing(String morningClosing)
	{
		this.morningClosing = morningClosing;
	}

	public String getEveningOpening()
	{
		return eveningOpening;
	}

	public void setEveningOpening(String eveningOpening)
	{
		this.eveningOpening = eveningOpening;
	}

	public String getEveningClosing()
	{
		return eveningClosing;
	}

	public void setEveningClosing(String eveningClosing)
	{
		this.eveningClosing = eveningClosing;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}

	public int getClose()
	{
		return close;
	}

	public void setClose(int close)
	{
		this.close = close;
	}

	public int getRestoId()
	{
		return restoId;
	}

	public void setRestoId(int restoId)
	{
		this.restoId = restoId;
	}

	public static List<TimeTable> newTimeTable(int restoId)
	{
		List<TimeTable> timeTable = new ArrayList<TimeTable>();
		String[] semaine = { "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche" };
		for (int i = 0; i < 7; i++)
		{
			timeTable.add(new TimeTable("00:00", "00:00", "00:00", "00:00", semaine[i], 1, restoId));
		}
		return timeTable;
	}

	public static List<TimeTable> getFullTimeTable(int restoId)
	{
		GourmetDatabase db = new GourmetDatabase();
		List<TimeTable> table = db.getFullTimeTable(restoId);
		db.close();
		return table;
	}

}
