package com.example.wsn;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Info_product extends Activity{
	private Integer position;
	private String[] web;
	private Integer[] imageId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_products);
		web= Fragment_More.web;
		imageId = Fragment_More.imageId;
		position= Fragment_More.p;
		TextView txtTitle = (TextView) findViewById(R.id.title);
		TextView info = (TextView) findViewById(R.id.info);
		TextView function = (TextView) findViewById(R.id.func);
		TextView component = (TextView) findViewById(R.id.comp);
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		txtTitle.setText(web[position]);
		imageView.setImageResource(imageId[position]);
		if(position == 0){

			String i = getString(R.string.wsn_info);
			info.setText(i);
			String f = getString(R.string.wsn_func);
			function.setText(f);
			String c = getString(R.string.wsn_comp);
			component.setText(c);
		}
		if(position == 1){

			String i = getString(R.string.robocontrol_info);
			info.setText(i);
			String f = getString(R.string.robocontrol_func);
			function.setText(f);
			String c = getString(R.string.robocontrol_comp);
			component.setText(c);
		}
		if(position == 2){

			String i = getString(R.string.robohand_info);
			info.setText(i);
			String f = getString(R.string.robohand_func);
			function.setText(f);
			String c = getString(R.string.robohand_comp);
			component.setText(c);
		}
		if(position == 3){

			String i = getString(R.string.hello_world);
			info.setText(i);
			String f = getString(R.string.hello_world);
			function.setText(f);
			String c = getString(R.string.hello_world);
			component.setText(c);
		}
		if(position == 4){

			String i = getString(R.string.hello_world);
			info.setText(i);
			String f = getString(R.string.hello_world);
			function.setText(f);
			String c = getString(R.string.hello_world);
			component.setText(c);
		}
		if(position == 5){

			String i = getString(R.string.hello_world);
			info.setText(i);
			String f = getString(R.string.hello_world);
			function.setText(f);
			String c = getString(R.string.hello_world);
			component.setText(c);
		}
		if(position == 6){

			String i = getString(R.string.hello_world);
			info.setText(i);
			String f = getString(R.string.hello_world);
			function.setText(f);
			String c = getString(R.string.hello_world);
			component.setText(c);
		}

	}
	public void sendPlainTextEmail(View button) {

		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		String aEmailList[] = { getResources()
				.getString(R.string.email_address) };
		String aEmailCCList[] = { getResources().getString(
				R.string.email_address_cc) };
		String aEmailBCCList[] = { getResources().getString(
				R.string.email_address_bcc) };

		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
		emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
		emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);
//		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//				getResources().getString(R.string.email_subject));

		emailIntent.setType("plain/text");
//		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources()
//				.getString(R.string.email_message));

		startActivity(emailIntent);
	}

}