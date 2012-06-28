package com.timereco.app;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
public class TimeRecoAppActivity extends Activity {
    protected static final int ACTIVITY_EDIT = 0;
    private static boolean status=false;
    
   
	private String timeInt = "";
    MediaPlayer mp; 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v("OnCreate", "Inside Create function");
        super.onCreate(savedInstanceState);
        System.out.println("First screen created");
        setContentView(R.layout.main);
        //ImageView homeIconImg = (ImageView) findViewById(R.id.homeIconImg);
        //homeIconImg.setImageResource(R.drawable.ic_launcher);
        //homeIconImg.setBackgroundColor(R.color.black);
        final Button confirmButton = (Button) findViewById(R.id.button1);
     	
        Button endButton= (Button) findViewById(R.id.button2);
        //Reg a context menu for this view
      //  registerForContextMenu(homeIconImg);
       final Intent i = new Intent(this, NoteEdit.class);
       //SharedPreferences myPrefs = TimeRecoAppActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_WRITEABLE);
	   	//String statusval = myPrefs.getString("Status", "NA");
	   	//if (statusval.equalsIgnoreCase("Started") )
       if(TimeRecoAppActivity.status)
	   	{
	   		Log.i("logs", "disabling the button");
	   		confirmButton.setEnabled(false);
	   	}
       //Set onClick listeners to the buttons 
       
       confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	confirmButton.setClickable(false);
            	/*Log.i("After button click", "Button clicked - Intent fired");
            	System.out.println("Button clicked - Intent fired");
            	Intent intentApp = new Intent(TimeRecoAppActivity.this,NoteEdit.class);
            	TimeRecoAppActivity.this.startActivity(intentApp);
                Log.v("TimeRecoApp", "Inside click handler");*/
            	Log.i("SERVICE","Service about to start");
            	Intent svcIntent = new Intent(TimeRecoAppActivity.this,ServiceImpl.class);
            	svcIntent.putExtra("Interval", timeInt);
            	//startService(new Intent(TimeRecoAppActivity.this,ServiceImpl.class));
            	RadioGroup radioGroupTime = (RadioGroup) findViewById(R.id.radioTime);
            	int selected = radioGroupTime.getCheckedRadioButtonId();
            	RadioButton selRadioButton = (RadioButton) findViewById(selected);
            	
            	SharedPreferences myPrefs = TimeRecoAppActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_WRITEABLE);
            	
            	SharedPreferences.Editor prefsEditor = myPrefs.edit();
                prefsEditor.putString("Time",(String) selRadioButton.getText() );
                TimeRecoAppActivity.status=true;
               
                prefsEditor.commit();
            	
            	startService(svcIntent);
            	Log.i("SERVICE","Service started");
               //startActivityForResult(i, ACTIVITY_EDIT);
            	//setContentView(R.layout.note_edit);
            	
            }
                

        });
       
       endButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
           	confirmButton.setClickable(true);
           	/*Log.i("After button click", "Button clicked - Intent fired");
           	System.out.println("Button clicked - Intent fired");
           	Intent intentApp = new Intent(TimeRecoAppActivity.this,NoteEdit.class);
           	TimeRecoAppActivity.this.startActivity(intentApp);
               Log.v("TimeRecoApp", "Inside click handler");*/
           	Log.i("SERVICE","Service about to stop");
           	stopService(new Intent(TimeRecoAppActivity.this,ServiceImpl.class));
           	//SharedPreferences myPrefs = TimeRecoAppActivity.this.getSharedPreferences("myPrefs", MODE_WORLD_WRITEABLE);
           	//SharedPreferences.Editor prefsEditor = myPrefs.edit();
           	//prefsEditor.putString("Status","Stopped" );
           	TimeRecoAppActivity.status=false;
           	confirmButton.setEnabled(true);
           	Log.i("SERVICE","Service stopped");
              //startActivityForResult(i, ACTIVITY_EDIT);
           	//setContentView(R.layout.note_edit);
           	
           }
               

       });
       
       //Set onClick listeners to the Radio buttons
       RadioButton int_15 = (RadioButton)findViewById(R.id.radioTime1);
       RadioButton int_30 = (RadioButton)findViewById(R.id.radioTime2);
       RadioButton int_45 = (RadioButton)findViewById(R.id.radioTime3);
       RadioButton int_60 = (RadioButton)findViewById(R.id.radioTime4);
       int_15.setOnClickListener(radio_listener);
       int_30.setOnClickListener(radio_listener);
       int_45.setOnClickListener(radio_listener);
       int_60.setOnClickListener(radio_listener);
            }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("logss", "I came here");
	}

	/**
        * {@inheritDoc}
        */
       @Override
       public boolean onCreateOptionsMenu(Menu menu) 
       {
         super.onCreateOptionsMenu(menu);
         
         menu.add(Menu.NONE,1,Menu.NONE,"Notes List");
         menu.add(Menu.NONE,2,Menu.NONE,"Exit");
         return true;
       }
       
       /**
           * {@inheritDoc}
           */
          public boolean onOptionsItemSelected(MenuItem item)
          {
        	   
        	//check selected menu item
          	if(item.getItemId() == 2)
          	{
          		//close the Activity
          		this.finish();
          		return true;
          	}
          	
            if (item.getItemId() == 1)
            {
              Intent intent=new Intent(this, Notepadv2.class);
		      try {
		    	  startActivityForResult(intent, 0);
		 	       
		 	  } catch (Exception ex) {
		 	                 System.out.println(ex.getMessage());
		 	  }  
            }
            
            // Consume the selection event.
            return true;
          }
          
          @Override
          public void onCreateContextMenu(ContextMenu menu, View v,
                  ContextMenuInfo menuInfo) {
              super.onCreateContextMenu(menu, v, menuInfo);
              menu.add(0, 0, 0, R.string.menu_delete);
          }
          
          @Override
          protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
              super.onActivityResult(requestCode, resultCode, intent);
          }
          
          OnClickListener radio_listener = new OnClickListener() {
        	    public void onClick(View v) {
        	        // Perform action on clicks
        	        RadioButton rb = (RadioButton) v;
        	        timeInt=(String) rb.getText().subSequence(0, 2);
        	        System.out.println(timeInt);
        	     //   Toast.makeText(HelloFormStuff.this, rb.getText(), Toast.LENGTH_SHORT).show();
        	    }
        	};
}