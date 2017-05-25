package com.example.wsn;


import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Fragment_Controller extends Fragment {
	
	Button play1,play2;
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View android = inflater.inflate(R.layout.info_controller, container, false);
	        play1= (Button) android.findViewById(R.id.data);
	        play2= (Button) android.findViewById(R.id.graph);
	        
	        play1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(),Data.class);
					startActivity(i);
				}
			});
	        play2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(),Graph.class);
					startActivity(i);
				}
			});
	        return android;
	    }
}
