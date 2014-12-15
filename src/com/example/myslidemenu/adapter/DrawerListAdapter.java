package com.example.myslidemenu.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myslidemenu.R;
import com.example.myslidemenu.model.Laptop;

public class DrawerListAdapter extends BaseAdapter {
	Context context;
	ArrayList<Laptop> laptopList;

	public DrawerListAdapter(Context context, ArrayList<Laptop> laptopList) {
		this.context = context;
		this.laptopList = laptopList;
	}

	@Override
	public int getCount() {
		return laptopList.size();
	}

	@Override
	public Object getItem(int position) {
		return laptopList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, parent, false);
		}
		ImageView list_item_icon = (ImageView) convertView.findViewById(R.id.list_item_icon);
		TextView list_item_name = (TextView) convertView.findViewById(R.id.list_item_name);
		list_item_icon.setImageResource(laptopList.get(position).getIcon());
		list_item_name.setText(laptopList.get(position).getName());
		return convertView;
	}

}
