package com.example.repoductor_musica;



import java.io.File;
import java.io.IOException;



import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private boolean mIsBound = false;
	private Button bplay;
	private Button bstop;
	private Button bpause;
	MusicService mServ;
	private ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
	     binder) {
			mServ = ((MusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		};

		void doBindService(){
	 		bindService(new Intent(this,MusicService.class),
					Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}

		public void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
			}
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mServ = new MusicService();
		doBindService();
		Intent music = new Intent();
		music.setClass(this,MusicService.class);
		startService(music);
		setContentView(R.layout.activity_main);
		bplay=(Button)findViewById(R.id.button1);
		bpause=(Button)findViewById(R.id.button3);
		bstop=(Button)findViewById(R.id.button2);
		
		
		bplay.setOnClickListener(new OnClickListener()
		{
            public void onClick(View v) {
            	
            	try{
            		mServ.resumeMusic();
            	}catch(NullPointerException e){
            		//Log.i("EXCEPTION","EXCEPTION E------>"+e.getMessage().toString());
            	}
            	
			}
        });
		bstop.setOnClickListener(new OnClickListener()
		{	
            public void onClick(View v) {
            	try{
            	
            	mServ.stopMusic();
            	//Intent i = new Intent(MainActivity.this,MusicService.class);
            	//stopService(i);
            	}catch(Exception e){
            		Log.i("EXCEPTION","EXCEPTION E------>"+e.getMessage().toString());
            	}
			}
        });
		bpause.setOnClickListener(new OnClickListener()
		{
            public void onClick(View v) {
            	try{
            	mServ.pauseMusic();
            		
            	}catch(NullPointerException e){
            		Log.i("EXCEPTION","EXCEPTION E------>"+e.getMessage().toString());
            	}
			}
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
