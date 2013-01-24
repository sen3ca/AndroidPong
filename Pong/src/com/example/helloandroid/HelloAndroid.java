package com.example.helloandroid;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;

public class HelloAndroid extends Activity {
    private AudioManager audio;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView tv = new TextView(this);
        //tv.setText("Hello, Android");
        //setContentView(tv);
        setContentView(R.layout.main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
}