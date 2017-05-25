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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Graph extends Activity implements OnClickListener{

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (Bluetooth.connectedThread != null) {
			Bluetooth.connectedThread.write("Q");}//Stop streaming
		super.onBackPressed();
	}

	private StringBuilder recDataString = new StringBuilder();
	TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
	TextView dataview;
	
	//toggle Button
	static boolean Lock;//whether lock the x-axis to 0-5
	static boolean AutoScrollX;//auto scroll to the last x value
	static boolean Stream;//Start or stop streaming
	//Button init
	Button bXminus;
	Button bXplus;
	//	ToggleButton tbLock;
	ToggleButton tbScroll;
	//	ToggleButton tbStream;
	//GraphView init
	static LinearLayout GraphView,GraphView1;
	static GraphView graphView,graphView1;
	static GraphViewSeries Series1,Series2,Series3;;
	//graph value
	private static double graph2LastXValue = 0;
	private static int Xview=10;
	Button bConnect, bDisconnect, d1, d2, d3, d4, d5, d6, d7, d8, savedata, allgraph;
	static int i=0;
	static int key=0;

	static int x=1;
	Sheet sheet1 = null;
	File file;
	Workbook wb;
	String name="qwertyui";

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case Bluetooth.SUCCESS_CONNECT:
				Bluetooth.connectedThread = new Bluetooth.ConnectedThread((BluetoothSocket) msg.obj);
				Toast.makeText(getApplicationContext(), "Connected!", 0).show();
				String s = "successfully connected";
				Bluetooth.connectedThread.start();
				break;
			case Bluetooth.MESSAGE_READ:

				Log.i("debug", "1");

				Log.i("debug", "2");
				String readMessage = (String) msg.obj; 
				Log.i("readMessage", readMessage);// msg.arg1 = bytes from connect thread
				recDataString.append(readMessage);                                      //keep appending to string until ~
				Log.i("debug", "3");
				int endOfLineIndex = recDataString.indexOf(".");                  // determine the end-of-line
				Log.i("eos", "eos");
				if(endOfLineIndex==21)
					Log.i("eos", "121211");

				if (endOfLineIndex > 1) {                                           // make sure there data before ~
					Log.i("debug", "4");
					String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
					Log.i("dataInprint", dataInPrint);
					int dataLength = dataInPrint.length();                          //get length of data received

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
						
						if(key==1)
							dataview.setText("D1: "+sensor0);
						else if(key==2)
							dataview.setText("D2: "+sensor1);
						else if(key==3)
							dataview.setText("D3: "+sensor2);

						Series1.appendData(new GraphViewData(graph2LastXValue,Double.parseDouble(sensor0)),AutoScrollX);
						Series2.appendData(new GraphViewData(graph2LastXValue,Double.parseDouble(sensor1)),AutoScrollX);
						Series3.appendData(new GraphViewData(graph2LastXValue,Double.parseDouble(sensor2)),AutoScrollX);

						//X-axis control
						if (graph2LastXValue >= Xview && Lock == true){
							Series1.resetData(new GraphViewData[] {});
							Series2.resetData(new GraphViewData[] {});
							Series3.resetData(new GraphViewData[] {});
							graph2LastXValue = 0;
						}else graph2LastXValue += 0.2;

						if(Lock == true)
							graphView.setViewPort(0, Xview);
						else
							graphView.setViewPort(graph2LastXValue-Xview, Xview);

						//refresh
						GraphView.removeView(graphView);
						GraphView.addView(graphView);

						if(!"qwertyui".equals(name))
							saveExcelFile(this,name+".xls",sensor0,sensor1,sensor2);
					}
					recDataString.delete(0, recDataString.length());                    //clear all string data
					// strIncom =" ";
					dataInPrint = " ";
					readMessage="";
				}			

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide title
		this.getWindow().setFlags(WindowManager.LayoutParams.
				FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hide Status bar
		setContentView(R.layout.graph_new);
		//set background color
//				LinearLayout background = (LinearLayout)findViewById(R.id.bg);
//				background.setBackgroundColor(Color.BLACK);
		init();
		ButtonInit();
	}

	void init(){
		Bluetooth.gethandler(mHandler);

		//init graphview
		GraphView = (LinearLayout) findViewById(R.id.Graph);
		// init example series data------------------- 
		Series1 = new GraphViewSeries("Device1", 
				new GraphViewStyle(Color.YELLOW, 2),//color and thickness of the line 
				new GraphViewData[] {new GraphViewData(0, 0)});
		Series2 = new GraphViewSeries("Device2", 
				new GraphViewStyle(Color.GREEN, 2),//color and thickness of the line 
				new GraphViewData[] {new GraphViewData(0, 0)});
		Series3 = new GraphViewSeries("Device3", 
				new GraphViewStyle(Color.RED, 2),//color and thickness of the line 
				new GraphViewData[] {new GraphViewData(0, 0)});
		graphView = new LineGraphView(  
				this // context  
				, "Graph" // heading  
				);  		
		graphView.setViewPort(0, Xview);
		graphView.setScrollable(true);
		graphView.setScalable(true);	
		graphView.setShowLegend(true); 
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.setManualYAxis(true);
		graphView.setManualYAxisBounds(500, 0);
		graphView.addSeries(Series1); // data 
		graphView.addSeries(Series2);
		graphView.addSeries(Series3);
		GraphView.addView(graphView); 

	}

	void ButtonInit(){
		d1 = (Button)findViewById(R.id.Button01);
		d1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				graphView.removeSeries(Series2);
				graphView.removeSeries(Series3);
				graphView.removeSeries(Series1);
				graphView.addSeries(Series1); // data 

				key=1;
			}
		});
		d2 = (Button)findViewById(R.id.Button02);	
		d2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				graphView.removeSeries(Series2);
				graphView.removeSeries(Series3);
				graphView.removeSeries(Series1);

				graphView.addSeries(Series2);
//				graph2LastXValue += 0.1;
				key=2;
			}
		});
		d3 = (Button)findViewById(R.id.Button03);
		d3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				graphView.removeSeries(Series2);
				graphView.removeSeries(Series3);
				graphView.removeSeries(Series1);
				graphView.addSeries(Series3);
				key=3;
			}
		});
		d4 = (Button)findViewById(R.id.Button04);	
		d5 = (Button)findViewById(R.id.Button05);	
		d6 = (Button)findViewById(R.id.Button06);	
		d7 = (Button)findViewById(R.id.Button07);	
		d8 = (Button)findViewById(R.id.Button08);	
		allgraph = (Button)findViewById(R.id.all);
		allgraph.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				graphView.removeSeries(Series1);
				graphView.removeSeries(Series2);
				graphView.removeSeries(Series3);
				graphView.addSeries(Series1);
				graphView.addSeries(Series2);
				graphView.addSeries(Series3);
				
			}
		});
		bConnect = (Button)findViewById(R.id.bConnect);
		bConnect.setOnClickListener(this);
		bDisconnect = (Button)findViewById(R.id.bDisconnect);
		bDisconnect.setOnClickListener(this);
		
		dataview = (TextView) findViewById(R.id.devicedata);
		savedata = (Button)findViewById(R.id.Save);
		savedata.setOnClickListener(this);
	
		//X-axis control button
//		bXminus = (Button)findViewById(R.id.bXminus);
//		bXminus.setOnClickListener(this);
//		bXplus = (Button)findViewById(R.id.bXplus);
//		bXplus.setOnClickListener(this);
		//
		//		tbLock = (ToggleButtonk)findViewById(R.id.tbLock);
		//		tbLock.setOnClickListener(this);
		tbScroll = (ToggleButton)findViewById(R.id.tbScroll);
		tbScroll.setOnClickListener(this);
		//		tbStream = (ToggleButton)findViewById(R.id.tbStream);
		//		tbStream.setOnClickListener(this);
		//init toggleButton
		Lock=false;
		AutoScrollX=true;
		//		Stream=true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bConnect:
			startActivity(new Intent("android.intent.action.BT1"));
			break;
		case R.id.bDisconnect:
			Bluetooth.disconnect();
			break;
//		case R.id.bXminus:
//			if (Xview>1) Xview--;
//			break;
//		case R.id.bXplus:
//			if (Xview<30) Xview++;
//			break;
//			//		case R.id.tbLock:
//			//			if (tbLock.isChecked()){
//			//				Lock = true;
//			//			}else{
//			//				Lock = false;
//			//			}
//			//			break;
		case R.id.tbScroll:
			if (tbScroll.isChecked()){
				AutoScrollX = true;
			}else{
				AutoScrollX = false;
			}
			break;
			//		case R.id.tbStream:
			//			if (tbStream.isChecked()){
			//				if (Bluetooth.connectedThread != null)
			//					Bluetooth.connectedThread.write("E");
			//			}else{
			//				if (Bluetooth.connectedThread != null)
			//					Bluetooth.connectedThread.write("Q");
			//			}
			//			break;
		case R.id.Save:
			Intent add_mem = new Intent(this, SaveRecordsGraph.class);
			startActivity(add_mem);
			break;
		}
		
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
			Intent add_mem = new Intent(this, SaveRecordsGraph.class);
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
