package com.mao.work.layout;
import android.support.v4.view.*;
import android.content.*;
import android.util.*;

public class MyViewPager extends ViewPager
{
	public MyViewPager(Context context)
	{
		super(context);
	}
	
	public MyViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void setOffscreenPageLimit(int limit)
	{
		// TODO: Implement this method
//		super.setOffscreenPageLimit(limit);
	}
}
