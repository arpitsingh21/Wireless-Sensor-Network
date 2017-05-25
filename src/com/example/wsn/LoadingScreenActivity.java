package com.example.wsn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class LoadingScreenActivity extends Activity {
//	//Introduce an delay
//    private final int WAIT_TIME = 1500;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//	// TODO Auto-generated method stub
//	super.onCreate(savedInstanceState);
//	System.out.println("LoadingScreenActivity  screen started");
//	setContentView(R.layout.loadingscreen);
//	findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);
//
//	new Handler().postDelayed(new Runnable(){ 
//	@Override 
//	    public void run() { 
//              //Simulating a long running task
//              try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	     System.out.println("Going to Profile Data");
//	  /* Create an Intent that will start the ProfileData-Activity. */
////        Intent mainIntent = new Intent(LoadingScreenActivity.this,TabLayout.class); 
////	    LoadingScreenActivity.this.startActivity(mainIntent); 
//	    LoadingScreenActivity.this.finish(); 
//	} 
//	}, WAIT_TIME);
//      }
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("LoadingScreenActivity  screen started");
		setContentView(R.layout.load);
//		findViewById(R.id.mainSpinner1).setVisibility(View.VISIBLE);

		ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				  LoadingScreenActivity.this.finish(); 
			}
		});
		
	      }
}
