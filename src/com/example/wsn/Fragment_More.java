package com.example.wsn;

import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_More extends Fragment {
	
	ListView list;
	static Integer p;
    static String[] web = {
        "Wireless Sensor Network",
            "Robot Car",
            "Robotic Hand",
//            "Bing",
//            "Itunes",
//            "Wordpress",
//            "Drupal"
    } ;
    static Integer[] imageId = {
            R.drawable.wsn,
            R.drawable.robocar,
            R.drawable.robohand,
//            R.drawable.robocar,
//            R.drawable.robohand,
//            R.drawable.robocar,
//            R.drawable.robohand,
            

    };
	
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {
		
		View android = inflater.inflate(R.layout.more_products, container, false);
			Fragment_More_ListAdapter adapter = new
                Fragment_More_ListAdapter(getActivity(), web, imageId);
			list=(ListView)android.findViewById(R.id.list);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                    	
                    //	View a = inflater.inflate(R.layout.info_products, container, false);
                    	p=position;
                    	Intent i = new Intent(getActivity(),Info_product.class);
    					startActivity(i);
    				//	Toast.makeText(getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                    }
                });
                return android;
    }
	
}