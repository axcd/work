package com.mao.work.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mao.work.*;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import com.mao.work.config.*;
import com.mao.work.bean.*;
import com.mao.work.enum.*;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class MyFragment2 extends Fragment
{

	private float[] data = { 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public MyFragment2()
	{
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.page_two, container, false);

		String[] companies = new String[] {
			"平时加班", "周末加班", "节假日加班", "中班天数", "夜班天数" ,
			"调休(小时)", "事假(小时)", "病假(小时)","年假(小时)",
			"绩效", "岗位补贴", "高温补贴", 
			"社会保险", "公积金", "其他补贴", "其他扣款", 
			"本月应发", "本月实发"};

		setZero();
        ListAdapter adapter = new MyAdapter(getActivity(), companies);
		getData();

        ListView listView = (ListView) view.findViewById(R.id.pagetwoListView);
		listView.setAdapter(adapter);

        return view;
    }

	public void setZero()
	{
		for (int i=0;i < data.length;i++)
		{
			data[i] = 0;
		}
	}

	public void getData()
	{
		//把两月组合成一个月
		Month month = Config.getPreMonth();
		for (int i=0; i < Config.getStartDay(); i++)
		{
			month.getDays()[i] = null;
			month.getDays()[i] = Config.getNextMonth().getDay(i);
		}
		for (int i=0; i <= 31; i++)
		{

			if (null != month.getDay(i))
			{
				//获取小时
				String shour = month.getDay(i).getHour().getHourName();
				int length = shour.length();
				float hour = Float.parseFloat(shour.substring(0, length - 1));
				//不是休班
				if (!Shift.REST.equals(month.getDay(i).getShift()))
				{
					//加班
					if (Fake.NORMAL.equals(month.getDay(i).getFake()))
					{
						//平时加班
						if (Rate.ONE_AND_HALF.equals(month.getDay(i).getRate()))
						{

							data[0] += hour;
						}
						//周末加班
						else if (Rate.TWO.equals(month.getDay(i).getRate()))
						{
							data[1] += hour;
						}
						//节假日加班
						else if (Rate.THREE.equals(month.getDay(i).getRate()))
						{

							data[2] += hour;
						}
					}
					else
					{
						if (hour > 4)
						{

							//中班天数
							if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
							{
								data[3] -= 1;
							}
							//夜班天数
							if (Shift.NIGHT.equals(month.getDay(i).getShift()))
							{
								data[4] -= 1;
							}
						}
					}

					//中班天数
					if (Shift.MIDDLE.equals(month.getDay(i).getShift()))
					{
						data[3] += 1;
					}
					//夜班天数
					if (Shift.NIGHT.equals(month.getDay(i).getShift()))
					{
						data[4] += 1;
					}
					//调休
					if (Fake.TAKEOFF.equals(month.getDay(i).getFake()))
					{
						data[5] += hour;
					}
					//事假
					if (Fake.LEAVE.equals(month.getDay(i).getFake()))
					{
						data[6] += hour;
					}
					//病假
					if (Fake.SICK.equals(month.getDay(i).getFake()))
					{
						data[7] += hour;
					}
					//年假
					if (Fake.SICK.equals(month.getDay(i).getFake()))
					{
						data[8] += hour;
					}
				}
			}
		}
	}

    class MyAdapter extends ArrayAdapter<String>
    {
		public MyAdapter(Context context, String[] values) 
		{
			super(context, R.layout.entry, values);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.entry, parent, false);

			String text = getItem(position);

			TextView textView1 = (TextView) view.findViewById(R.id.entryTextView1);
			textView1.setText(text);

			TextView textView2 = (TextView) view.findViewById(R.id.entryTextView2);
			textView2.setText(data[position] + "");

			return view;
		}
    }
}
