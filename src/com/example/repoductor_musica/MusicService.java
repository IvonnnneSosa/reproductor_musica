package com.example.repoductor_musica;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service  implements MediaPlayer.OnErrorListener{
		private final IBinder mBinder = new ServiceBinder(); 
	    
		MediaPlayer mPlayer;
		
	    private int length = 0;

	    public MusicService() { }
	    
	    public class ServiceBinder extends Binder {
	     	public MusicService getService()
	    	 {
	    		return MusicService.this;
	    	 }
	    }
	    
	    @Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return mBinder;
		}
	    @Override
	    public void onCreate (){
		  super.onCreate(); 
	        Log.i("Crean service","create");
	       mPlayer = MediaPlayer.create(this, R.raw.jingle);
	       mPlayer.setOnErrorListener(this);
	        
	       if(mPlayer!= null)
	        {
	        	mPlayer.setLooping(true);
	        	mPlayer.setVolume(100,100);
	        }
	   	

	        mPlayer.setOnErrorListener(new OnErrorListener() {

		  public boolean onError(MediaPlayer mp, int what, int       
	          extra){
			
				onError(mPlayer, what, extra);
				return true;
			}
	    	  });
		}
		
	    @Override
		public int onStartCommand (Intent intent, int flags, int startId)
		{
	         mPlayer.start();
	         return START_STICKY;
		}

		public void pauseMusic()
		{	try{
				Log.i("Entrant","Entrant al pause");
				if(mPlayer.isPlaying())
				{
					mPlayer.pause();
					length=mPlayer.getCurrentPosition();
	
				}
			}catch(NullPointerException e){
				Log.i("Entrant","Entrant al pause i catch"+e.getMessage().toString());
			}
		}	
		public void resumeMusic()
		{	//Log.i("RESUME METHOD","ENTER------>");
			if(mPlayer.isPlaying()==false)
			{	
				Log.i("RESUME METHOD","ENTER IN IF------>");
				mPlayer.seekTo(length);
				mPlayer.start();
			}
		}

		public void stopMusic()
		{
			//try{
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
			onCreate();
			//}catch (Exception e) {
				// TODO: handle exception
				//Log.i("EXCEPTION","EXCEPTION E------>"+e.getMessage().toString());
			//}
		}

		@Override
		public void onDestroy ()
		{
			super.onDestroy();
			if(mPlayer != null)
			{
			try{
			 mPlayer.stop();
			 mPlayer.release();
				}finally {
					mPlayer = null;
				}
			}
		}

		public boolean onError(MediaPlayer mp, int what, int extra) {

			Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
			if(mPlayer != null)
			{
				try{
					mPlayer.stop();
					mPlayer.release();
				}finally {
					mPlayer = null;
				}
			}
			return false;
		}

		
}
