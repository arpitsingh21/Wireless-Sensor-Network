package com.example.wsn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Data extends Activity{

	TextView v1,v2,v3,v4,v5,v6,v7,v8;
	TextView value1,value2,value3,value4,value5,value6,value7,value8;
	private StringBuilder recDataString = new StringBuilder();
	TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
	static int i = 0;
	static int x=1;
	Sheet sheet1 = null;
	File file;
	Workbook wb;
	String name="qwertyui";

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (Bluetooth.connectedThread != null) {
			Bluetooth.connectedThread.write("Q");}//Stop streaming
		super.onBackPressed();
	}

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Bluetooth.SUCCESS_CONNECT:
				Log.i("debug", "fragmentsuccessconnect-");
				Bluetooth.connectedThread = new Bluetooth.ConnectedThread((BluetoothSocket) msg.obj);
				Toast.makeText(getApplicationContext(), "Connected!", 0).show();
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
				if(endOfLineIndex==23)
					Log.i("eos", "121211");

				if (endOfLineIndex > 1) {                                           // make sure there data before ~
					Log.i("debug", "4");
					String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
					// 	Log.i("debug", dataInPrint);
					//   	txtString.setText("Data Received = " + dataInPrint);
					Log.i("dataInprint", dataInPrint);
					int dataLength = dataInPrint.length();                          //get length of data received
					//        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

					if (recDataString.charAt(0) == 'T')                             //if it starts with # we know it is what we are looking for
					{
						String sensor0 = recDataString.substring(4, 7);             //get sensor value from string between indices 1-5
						String sensor1 = recDataString.substring(11, 14);            //same again...
						String sensor2 = recDataString.substring(17, 20);
						String sensor3 = recDataString.substring(22, 23);
						Log.i("temp", sensor0);
						Log.i("humid", sensor1);
						Log.i("ldr", sensor2);
						Log.i("pir", sensor3);
						
//						if(i%10==0)
						{	
							if(sensor0.equals("000"))
								value1.setText("N");
							else
								value1.setText("F");
							if(sensor1.equals("000"))
								value2.setText("N");
							else
								value2.setText("F");
							if(sensor2.equals("000"))
								value3.setText("N");
							else
								value3.setText("F");
							if(sensor3.equals("N"))
								value4.setText("N");
							else
								value4.setText("F");
							//							if(v5.equals("000"))
							//								value5.setText("N");
							//							else
							//								value5.setText("F");
							//							if(v6.equals("000"))
							//								value6.setText("N");
							//							else
							//								value6.setText("F");
							//							if(v7.equals("000"))
							//								value7.setText("N");
							//							else
							//								value7.setText("F");
							//							if(v8.equals("000"))
							//								value8.setText("N");
							//							else
							//								value8.setText("F");
						}
						v1.setText(sensor0+" C");
						v2.setText(sensor1+" C");
						v3.setText(sensor2);
						v4.setText(sensor3);
						if(!"qwertyui".equals(name))
							saveExcelFile(this,name+".xls",sensor0,sensor1,sensor2);
						//			saveExcelFile(this,name,sensor0,sensor1,sensor2);
					}
					recDataString.delete(0, recDataString.length());                    //clear all string data
					// strIncom =" ";
					dataInPrint = " ";
					readMessage="";
				}
				i++;
				break;
			}
		}
		private boolean saveExcelFile(Handler handler, String fileName, String v1, String v2, String v3) {
			// TODO Auto-generated method stub
			// check if available and not read only 
			if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) { 
				Log.w("FileUtils", "Storage not available or read only"); 
				return false; 
			} 

			boolean success = false; 



			Cell c = null;


			Row rows = sheet1.createRow(x);
			x++;
			c = rows.createCell(0);
			c.setCellValue(v1);
			//	        c.setCellStyle(cs);

			c = rows.createCell(1);
			c.setCellValue(v2);
			//	        c.setCellStyle(cs);

			c = rows.createCell(2);
			c.setCellValue(v3);
			//	        c.setCellStyle(cs);
			// Create a path where we will place our List of objects on external storage 
			//	        File file = new File(getExternalFilesDir(null), fileName); 
			FileOutputStream os = null; 

			try { 
				os = new FileOutputStream(file);
				wb.write(os);
				Log.w("FileUtils", "Writing file" + file); 
				success = true; 
			} catch (IOException e) { 
				Log.w("FileUtils", "Error writing " + file, e); 
			} catch (Exception e) { 
				Log.w("FileUtils", "Failed to save file", e); 
			} finally { 
				try { 
					if (null != os) 
						os.close(); 
				} catch (Exception ex) { 
				} 
			} 

			return success;
		}
		public boolean isExternalStorageReadOnly() { 
			String extStorageState = Environment.getExternalStorageState(); 
			if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) { 
				return true; 
			} 
			return false; 
		} 

		public boolean isExternalStorageAvailable() { 
			String extStorageState = Environment.getExternalStorageState(); 
			if (Environment.MEDIA_MOUNTED.equals(extStorageState)) { 
				return true; 
			} 
			return false; 
		}


	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);
		init();
		Button();
	}

	private void Button() {
		// TODO Auto-generated method stub
		value1 = (TextView) findViewById(R.id.value1);
		value2 = (TextView) findViewById(R.id.value2);
		value3 = (TextView) findViewById(R.id.value3);
		value4 = (TextView) findViewById(R.id.value4);
		value5 = (TextView) findViewById(R.id.value5);
		value6 = (TextView) findViewById(R.id.value6);
		value7 = (TextView) findViewById(R.id.value7);
		value8 = (TextView) findViewById(R.id.value8);

		v1 = (TextView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		v3 = (TextView) findViewById(R.id.v3);
		v4 = (TextView) findViewById(R.id.v4);
		v5 = (TextView) findViewById(R.id.v5);
		v6 = (TextView) findViewById(R.id.v6);
		v7 = (TextView) findViewById(R.id.v7);
		v8 = (TextView) findViewById(R.id.v8);
	}


	void init(){
		Log.i("debug", "inittab");
		Bluetooth.gethandler(mHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_connect:
			startActivity(new Intent("android.intent.action.BT1"));
			Log.i("deebug",""+"qwertyui".equals(name));
			if(!"qwertyui".equals(name))
				excel();
			return true;
		case R.id.action_disconnect:
			Bluetooth.disconnect();
			name="qwertyui";
			return true;
		case R.id.action_save:
			Intent add_mem = new Intent(this, SaveRecords.class);
			startActivity(add_mem);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean excel() {
		// TODO Auto-generated method stub

		if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) { 
			Log.w("FileUtils", "Storage not available or read only"); 
			return false; 
		} 

		boolean success = false; 
		name=SaveRecords.name+".xls";
		//		name = "myExcels.xls";
		x=1;	
		//New Workbook
		wb = new HSSFWorkbook();

		Cell c = null;

		//Cell style for header row
		CellStyle cs = wb.createCellStyle();
		cs.setFillForegroundColor(HSSFColor.LIME.index);
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		sheet1 = wb.createSheet("myOrders");

		// Generate column headings
		Row row = sheet1.createRow(0);

		c = row.createCell(0);
		c.setCellValue("Device 1");
		c.setCellStyle(cs);

		c = row.createCell(1);
		c.setCellValue("Device 2");
		c.setCellStyle(cs);

		c = row.createCell(2);
		c.setCellValue("Device 3");
		c.setCellStyle(cs);

		sheet1.setColumnWidth(0, (10 * 500));
		sheet1.setColumnWidth(1, (10 * 500));
		sheet1.setColumnWidth(2, (10 * 500));

		File sdCard = Environment.getExternalStorageDirectory();

		File directory = new File(sdCard.getAbsolutePath() + "/wsns");

		Log.i("debug","" + directory);
		Log.i("debug","" + directory.isDirectory());
		Log.i("debug","" + directory.isFile());
		//create directory if not exist

		if(!directory.isDirectory()){
			directory.mkdir();
			Log.i("debug","4");
			file = new File(directory, name);
		}
		Log.i("debug","" + directory.isDirectory());
		// Create a path where we will place our List of objects on external storage 
		if(!directory.isDirectory())
		{
			Log.i("debug","3");
			file = new File(getExternalFilesDir(null), name); 
		}
		else
		{
			file = new File(directory, name);
		}

		Log.i("file name",""+file);

		FileOutputStream os = null; 

		try { 
			os = new FileOutputStream(file);
			wb.write(os);
			Log.w("FileUtils", "Writing file" + file); 
			success = true; 
		} catch (IOException e) { 
			Log.w("FileUtils", "Error writing " + file, e); 
		} catch (Exception e) { 
			Log.w("FileUtils", "Failed to save file", e); 
		} finally { 
			try { 
				if (null != os) 
					os.close(); 
			} catch (Exception ex) { 
			} 
		} 

		return success; 
	}
	public boolean isExternalStorageReadOnly() { 
		String extStorageState = Environment.getExternalStorageState(); 
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) { 
			return true; 
		} 
		return false; 
	} 

	public boolean isExternalStorageAvailable() { 
		String extStorageState = Environment.getExternalStorageState(); 
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) { 
			return true; 
		} 
		return false; 
	}
}