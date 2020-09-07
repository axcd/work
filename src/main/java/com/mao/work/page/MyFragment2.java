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
import java.math.*;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class MyFragment2 extends Fragment
{

	private View view;
	private float[] data = new float[21];
	private boolean isCreated = false;

    public MyFragment2()
	{
		//空构造函数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.page_two, container, false);
		isCreated = true;
		
        return view;
    }

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);

		if (isCreated && isVisibleToUser)
		{
			//数组置零,获取数据
			String[] companies = new String[] {
				"平时加班", "周末加班", "节假日加班", "中班天数", "夜班天数" ,
				"调休(小时)", "事假(小时)", "病假(小时)","年假(小时)",
				"绩效", "岗位补贴", "高温补贴", 
				"社会保险", "公积金", "其他补贴", "其他扣款", "平时加班费", "周末加班费", "节假日加班费",
				"本月应发", "本月实发"};
			ListAdapter adapter = new MyAdapter(getActivity(), companies);
			getData();

			//添加适配器
			ListView listView = (ListView) view.findViewById(R.id.pagetwoListView);
			listView.setAdapter(adapter);
		}
	}

	public void getData()
	{
		//数组置零
		for (int i=0;i < data.length;i++)
		{
			data[i] = 0;
		}

		//把两月组合成一个月
		Month month = Config.getPreMonth();
		for (int i=1; i < Config.getStartDay(); i++)
		{
			month.getDays()[i-1] = Config.getNextMonth().getDay(i);
		}

		//遍历该周期
		for (int i=1; i <= 31; i++)
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
			//绩效
			data[9] = 1200;
			//岗位补贴
			data[10] = 100;
			//高温补贴
			data[11] = 0;
			//社会保险
			data[12] = 600;
			//公积金
			data[13] = 600;
			//其他补贴
			data[14] = 0;
			//其他扣款
			data[15] = 0;
			//基本工资
			int base =2200;
			//平时加班费
			data[16] = F(base / 21.75 / 8 * 1.5 * data[0]);
			//周末加班费
			data[17] = F(base / 21.75 / 8 * 2 * data[1]);
			//节假日加班费
			data[18] = F(base / 21.75 / 8 * 3 * data[2]);
			//应发工资
			data[19] = F(base + data[3] * 0 + data[4] * 15 + data[16] + data[17] + data[18] + data[9] + data[10] + data[11] + data[14]);
			//实发工资
			data[20] = F(data[19] - (float)(base / 21.75 / 8 * 1.5 * data[5]) - (float)(base / 21.75 / 8 * data[6]) - (float)(base / 21.75 / 8 * 0.3 * data[7])-data[12] - data[13] - data[15]);
		}
	}

	//设置保留位数
	public float F(double num)
	{
		BigDecimal bg = new BigDecimal(num);
		double num1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return((float)num1);
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
