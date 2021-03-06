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
import android.widget.*;
import android.app.*;
import android.content.*;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class MyFragment3 extends Fragment
{

	private View view;
	private static float[] data = new float[13];

    public MyFragment3()
	{
		//空构造函数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.page_three, container, false);
		setView();
        return view;
    }

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		// TODO: Implement this method
//		super.setUserVisibleHint(isVisibleToUser);
	}

	public void setView()
	{
		//数组置零,获取数据
		String[] companies = new String[] {
			"基本工资", "周期开始日", "本月绩效", "中班补贴", "夜班补贴" ,
			"岗位补贴", "高温补贴","交通补贴", "社会保险", "公积金",
			"其他补贴", "其他扣款", "专项扣除" };
		ListAdapter adapter = new MyAdapter(getActivity(), companies);
		getData();
		
		//添加适配器
		ListView listView = (ListView) view.findViewById(R.id.pagethreeListView);
		listView.setAdapter(adapter);

	}

	public void getData()
	{
		data[0] = Config.getSettings().getBasePay();
		data[1] = Config.getSettings().getStartDay();
		data[2] = Config.getSettings().getPerformance();
		data[3] = Config.getSettings().getMiddleShiftSubsidy();
		data[4] = Config.getSettings().getNightShiftSubsidy();
		data[5] = Config.getSettings().getPostSubsidy();
		data[6] = Config.getSettings().getTemperatureSubsidy();
		data[7] = Config.getSettings().getTransportationSubsidy();
		data[8] = Config.getSettings().getSocialInsurance();
		data[9] = Config.getSettings().getHousingFund();
		data[10] = Config.getSettings().getOtherSubsidy();
		data[11] = Config.getSettings().getOtherDeductions();
		data[12] = Config.getSettings().getSpecialDeduction();
	}
	
	public void saveSettings()
	{
		Config.getSettings().setBasePay(data[0]);
		Config.getSettings().setStartDay((int)data[1]);
		Config.getSettings().setPerformance(data[2]);
		Config.getSettings().setMiddleShiftSubsidy(data[3]);
		Config.getSettings().setNightShiftSubsidy(data[4]);
		Config.getSettings().setPostSubsidy(data[5]);
		Config.getSettings().setTemperatureSubsidy(data[6]);
		Config.getSettings().setTransportationSubsidy(data[7]);
		Config.getSettings().setSocialInsurance(data[8]);
		Config.getSettings().setHousingFund(data[9]);
		Config.getSettings().setOtherSubsidy(data[10]);
		Config.getSettings().setOtherDeductions(data[11]);
		Config.getSettings().setSpecialDeduction(data[12]);
		
		Config.save();
	}
	//设置保留位数
	public float F(double num, int n)
	{
		BigDecimal bg = new BigDecimal(num);
		double num1 = bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return((float)num1);
	}

	class MyAdapter extends ArrayAdapter<String>
	{
		public MyAdapter(Context context, String[] values) 
		{
			super(context, R.layout.page_three_entry, values);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.page_three_entry, parent, false);

			final String text = getItem(position);

			TextView textView1 = (TextView) view.findViewById(R.id.entryTextView1);
			textView1.setText(text);

			final EditText textView2 = (EditText) view.findViewById(R.id.entryEditText);
			
			textView2.setText(data[position] + "");
			if(position==1)textView2.setText((int)data[position] + "");
			//textView2.setTag(position+"");

			textView2.setOnClickListener(new View.OnClickListener(){
					public void onClick(View view){
						AlertDialog aldg;
						AlertDialog.Builder adBd=new AlertDialog.Builder(getContext());

						final LinearLayout dialogv = (LinearLayout)LayoutInflater.from(getContext()).inflate(R.layout.dialog,null);
						adBd.setTitle(text);
						adBd.setView(dialogv);
						
						adBd.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									EditText ett = (EditText)(dialogv.getChildAt(0));
									if(!"".equals(ett.getText().toString())){
										if(position!=1){
											data[position] = Float.parseFloat(ett.getText().toString());
											textView2.setText(data[position] + "");
										}else if(Float.parseFloat(ett.getText().toString())>=1 && Float.parseFloat(ett.getText().toString())<=31)
										{
											data[position] = Float.parseFloat(ett.getText().toString());
											textView2.setText((int)data[position] + "");
										}
										
									}
									
									saveSettings();
									
								}
							});
						adBd.setNegativeButton("取消",null);// new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									//saveSettings();
//								}
//							});
						aldg=adBd.create();
						aldg.show();
					}
				});

			return view;
		}
	}
}

