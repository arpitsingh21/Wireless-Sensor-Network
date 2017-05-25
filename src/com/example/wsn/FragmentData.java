package com.example.wsn;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentData extends Fragment {
	
	TextView v1,v2,v3,v4,v5,v6,v7,v8;
	TextView value1,value2,value3,value4,value5,value6,value7,value8;
	private StringBuilder recDataString = new StringBuilder();
	TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
	
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Bluetooth.SUCCESS_CONNECT:
				Log.i("debug", "fragmentsuccessconnect-");
				Bluetooth.connectedThread = new Bluetooth.ConnectedThread((BluetoothSocket) msg.obj);
				Toast.makeText(getActivity(), "Connected!", 0).show();
				String s = "successfully connected";
				Bluetooth.connectedThread.start();
				break;
			case Bluetooth.MESSAGE_READ:

				Log.i("debug", "1");
			//	byte[] readBuf = (byte[]) msg.obj;
				Log.i("debug", "2");
//				TextView txt = (TextView) findViewById(R.id.textView1);
				String readMessage = (String) msg.obj; 
		//		String strIncom = new String(readBuf, 0, 5);
				Log.i("readMessage", readMessage);// msg.arg1 = bytes from connect thread
                recDataString.append(readMessage);                                      //keep appending to string until ~
                Log.i("debug", "3");
                int endOfLineIndex = recDataString.indexOf(".");                  // determine the end-of-line
                Log.i("eos", "eos");
              //  Toast.makeText(getApplication(), endOfLineIndex , Toast.LENGTH_SHORT).show();
                if(endOfLineIndex==21)
                	Log.i("eos", "121211");
                
                if (endOfLineIndex > 1) {                                           // make sure there data before ~
                	Log.i("debug", "4");
                	String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
               // 	Log.i("debug", dataInPrint);
             //   	txtString.setText("Data Received = " + dataInPrint);
                    Log.i("dataInprint", dataInPrint);
                    int dataLength = dataInPrint.length();                          //get length of data received
            //        txtStringLength.setText("String Length = " + String.valueOf(dataLength));
 
                    if (recDataString.charAt(0) == 't')                             //if it starts with # we know it is what we are looking for
                    {
                        String sensor0 = recDataString.substring(4, 7);             //get sensor value from string between indices 1-5
                        String sensor1 = recDataString.substring(10, 13);            //same again...
                        String sensor2 = recDataString.substring(15, 18);
                        String sensor3 = recDataString.substring(20, 21);
                        Log.i("temp", sensor0);
                        Log.i("humid", sensor1);
                        Log.i("ldr", sensor2);
                        Log.i("pir", sensor3);
            
                        v1.setText(sensor0);
                        v2.setText(sensor1);
                        v3.setText(sensor2);
                        v4.setText(sensor3);
                    }
                    recDataString.delete(0, recDataString.length());                    //clear all string data
                   // strIncom =" ";
                    dataInPrint = " ";
                    readMessage="";
                }

				break;
			}
		}


	};
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View android = inflater.inflate(R.layout.mains, container, false);
        Log.i("check", "helooooooooooooooooo");
        value1 = (TextView) android.findViewById(R.id.value1);
        value2 = (TextView) android.findViewById(R.id.value2);
        value3 = (TextView) android.findViewById(R.id.value3);
        value4 = (TextView) android.findViewById(R.id.value4);
        value5 = (TextView) android.findViewById(R.id.value5);
        value6 = (TextView) android.findViewById(R.id.value6);
        value7 = (TextView) android.findViewById(R.id.value7);
        value8 = (TextView) android.findViewById(R.id.value8);
        
        v1 = (TextView) android.findViewById(R.id.v1);
        v2 = (TextView) android.findViewById(R.id.v2);
        v3 = (TextView) android.findViewById(R.id.v3);
        v4 = (TextView) android.findViewById(R.id.v4);
        v5 = (TextView) android.findViewById(R.id.v5);
        v6 = (TextView) android.findViewById(R.id.v6);
        v7 = (TextView) android.findViewById(R.id.v7);
        v8 = (TextView) android.findViewById(R.id.v8);
        
   //     TabLayout t = new TabLayout();
 //       Bundle b= getActivity().getIntent().getExtras();
 //       String s = b.getString("");
       // init();
      //  showData();
      //  v1.setText("1");
        return android;
    }
	
	void init(){
		Log.i("debug", "init");
		Bluetooth.gethandler(mHandler);

		
	}
//	public void setTextViewText(String value) {
//		// TODO Auto-generated method stub
//		v1.setText(value);
//	}
		public void updateTextValue(CharSequence newText) {
		// TODO Auto-generated method stub
			Log.i("debug", "update");
		v1.setText(newText);
	}
}
