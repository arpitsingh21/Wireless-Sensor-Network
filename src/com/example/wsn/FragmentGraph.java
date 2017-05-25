package com.example.wsn;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

public class FragmentGraph extends Fragment {
	
	
	static LinearLayout GraphView;
	static GraphView graphView;
	static GraphViewSeries Series;
	//graph value
	private static double graph2LastXValue = 0;
	private static int Xview=10;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.i("debug", "6");
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);//Hide title
//		getActivity().getWindow().setFlags(WindowManager.LayoutParams.
//				FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//Hide Status bar
		View android = inflater.inflate(R.layout.graph, container, false);
		//set background color
		LinearLayout background = (LinearLayout)android.findViewById(R.id.bg);
		background.setBackgroundColor(Color.BLACK);
		//init graphview
		GraphView = (LinearLayout) android.findViewById(R.id.Graph);
		init();
        return android;
    }
	void init(){

		
		// init example series data------------------- 
		Series = new GraphViewSeries("Signal", 
				new GraphViewStyle(Color.YELLOW, 2),//color and thickness of the line 
				new GraphViewData[] {new GraphViewData(0, 0)});
		graphView = new LineGraphView(  
				getActivity() // context  
				, "Graph" // heading  
				);  		
		graphView.setViewPort(0, Xview);
		graphView.setScrollable(true);
		graphView.setScalable(true);	
		graphView.setShowLegend(true); 
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.setManualYAxis(true);
		graphView.setManualYAxisBounds(5, 0);
		graphView.addSeries(Series); // data   
		GraphView.addView(graphView);  
	}
}
