package com.example.helloandroid;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class TwoDeeDraw extends Activity  {
	//private final static int START_DRAGGING = 0;
	//private final static int STOP_DRAGGING = 1;
	//private int status;
	
	private OnClickListener previewListener = new OnClickListener() {       
	    public void onClick(View v) {
	        Log.d("Whatever", "I've been clicked");
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 TwoDeeView aView = new TwoDeeView(this);
		 aView.setClickable(true);
		 //aView.setOnClickListener(previewListener);
	     setContentView(aView);
	     setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	
	/*
	@Override
	public boolean onTouch(View theView, MotionEvent event) {
		Log.d("Whatever", Integer.toString(event.getActionIndex()));
		Log.d("Whatever", Integer.toString(event.getActionMasked()));
		Log.d("Whatever", Integer.toString(event.getAction()));
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			status = START_DRAGGING;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			status = STOP_DRAGGING;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (status == START_DRAGGING) {
				int x = (int) event.getRawX();
				int y = (int) event.getRawY();
			}
		}
		//return false;
		return super.onTouchEvent(event);
	}
	*/
}
