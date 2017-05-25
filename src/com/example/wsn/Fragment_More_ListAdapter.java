package com.example.wsn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment_More_ListAdapter extends ArrayAdapter<String>{

	private final Activity context;
	private final String[] web;
	private final Integer[] imageId;
	public Fragment_More_ListAdapter(Activity context,String[] web, Integer[] imageId) {
		super(context, R.layout.more_products_list_row, web);
		this.context = context;
		this.web = web;
		this.imageId = imageId;

	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.more_products_list_row, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(web[position]);

		imageView.setImageResource(imageId[position]);
		return rowView;
	}
}