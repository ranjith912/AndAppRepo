package com.timereco.app;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceImpl extends Service{
	
	private Timer timer = new Timer();
	private final Calendar time = Calendar.getInstance();
	private Long counter = 0L; 
	private Handler handler = new Handler();
	private double timeInt = 30L;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		/*System.out.println(intent.getStringExtra("Interval"));
		timeInt = Double.parseDouble(intent.getStringExtra("Interval"));
		System.out.println(timeInt);*/
		incrementCounter();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//incrementCounter();
		Toast.makeText(this,"Pop-Pad Started ...", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		timer.cancel();
		Toast.makeText(this, "Pop-pad Stopped ...", Toast.LENGTH_LONG).show();
		
	}
	
	 private void incrementCounter() {
		 	SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		 	timeInt = Integer.parseInt(myPrefs.getString("Time", "10"))*60;
	      
		 	System.out.println("Timer inncrement value:" + (long)timeInt);
	    	timer.scheduleAtFixedRate(new TimerTask(){ public void run() {
	    	handler.post(new Runnable(){
	    		public void run(){
	    			counter= (long) (counter + timeInt);
	    			//Toast.makeText(getApplicationContext(), "Service started b4 "+counter,Toast.LENGTH_LONG);
	    			showMsg();
	    		}
	    	});	
	    	}}, 0,(long) (timeInt*1000));
	    }
	 
	 private void showMsg(){
		 Toast.makeText(this,"Pop-pad will activate every :"+counter/60 +" minutes", Toast.LENGTH_LONG).show();
		 Intent in=new Intent().setClass(ServiceImpl.this,Notepadv2.class);
		 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 startActivity(in);
	 }

	
}
