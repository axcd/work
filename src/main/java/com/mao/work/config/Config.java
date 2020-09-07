package com.mao.work.config;

import java.util.*;
import com.mao.work.bean.*;
import com.mao.work.view.*;
import com.mao.work.enum.*;
import com.mao.work.io.*;
import java.text.*;
import com.mao.work.*;

public class Config
{
	private static int startDay = 24;
	private static Date today;
	private static Date selectedDate;
	private static Date startDate;
	private static Date endDate;
	private static Month preMonth;
	private static Month nextMonth;
	private static DayView selectedView;
	private static boolean weekend;
	private static Calendar calendar;
	
	public static void initCalendar()
	{

		Config.calendar = Calendar.getInstance();
		Config.setToday(Config.calendar.getTime());


		//如果大于开始日期显示在下一月
		if (calendar.get(Calendar.DATE) > Config.getStartDay() && Config.getStartDay() != 1)
		{
			Config.calendar.add(Calendar.MONTH, 1);
		}
		
	}
	
	//设置Config
	public static void setConfig()
	{
		setStartDate(getCalendar());
		setEndDate(getCalendar());

		Calendar cal = (Calendar)getCalendar().clone();
		ObjectIO<Month> io = new ObjectIO<Month>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		String nMonth = sdf.format(cal.getTime());
		setNextMonth(io.inObject(nMonth));
		if(null == getNextMonth())
		{
			setNextMonth(new Month(nMonth));
		}

		cal.add(Calendar.MONTH,-1);
		String pMonth = sdf.format(cal.getTime());    
		setPreMonth(io.inObject(pMonth));
		if(null == getPreMonth())
		{
			setPreMonth(new Month(pMonth));
		}
	}
	
	//设置开始日期
	public static void setStartDate(Calendar calendar)
	{
		Calendar start_cal = (Calendar)calendar.clone();
	    if(Config.getStartDay()!=1)start_cal.add(Calendar.MONTH, -1);
		int maxDay = start_cal.getActualMaximum(Calendar.DATE);
		
		if(maxDay<startDay){
			start_cal.set(Calendar.DATE, maxDay);
		}else{
			start_cal.set(Calendar.DATE, startDay-1);
		}
		Config.startDate = start_cal.getTime();
	}

	public static Date getStartDate()
	{
		return startDate;
	}

	//设置结束日期
	public static void setEndDate(Calendar calendar)
	{
		Calendar end_cal = (Calendar)calendar.clone();
	    if(Config.getStartDay()==1)end_cal.add(Calendar.MONTH,1);
		int maxDay = end_cal.getActualMaximum(Calendar.DATE);
		
		if(maxDay<startDay){
			end_cal.set(Calendar.DATE,maxDay+1);
		}else{
			end_cal.set(Calendar.DATE, startDay);
		}
		Config.endDate = end_cal.getTime();
	}

	public static Date getEndDate()
	{
		return endDate;
	}

	public static void setSelectedDate(Date selectedDate)
	{
		Config.selectedDate = selectedDate;
	}

	public static Date getSelectedDate()
	{
		return selectedDate;
	}

	public static void setSelectedView(DayView selectedView)
	{
		Config.selectedView = selectedView;
	}

	public static DayView getSelectedView()
	{
		return selectedView;
	}

	public static void setToday(Date today)
	{
		Config.today = today;
	}

	public static Date getToday()
	{
		return today;
	}

	public static void setStartDay(int startDay)
	{
		Config.startDay = startDay;
	}

	public static int getStartDay()
	{
		return startDay;
	}

	public static void setPreMonth(Month preMonth)
	{
		Config.preMonth = preMonth;
	}

	public static Month getPreMonth()
	{
		return preMonth;
	}

	public static void setNextMonth(Month nextMonth)
	{
		Config.nextMonth = nextMonth;
	}

	public static Month getNextMonth()
	{
		return nextMonth;
	}

	public static void setWeekend(boolean weekend)
	{
		Config.weekend = weekend;
	}

	public static boolean isWeekend()
	{
		return weekend;
	}
	
	public static void setCalendar(Calendar calendar)
	{
		Config.calendar = calendar;
	}

	public static Calendar getCalendar()
	{
		return calendar;
	}
	
}
