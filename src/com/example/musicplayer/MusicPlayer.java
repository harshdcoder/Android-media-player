package com.example.musicplayer;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MusicPlayer extends Activity implements OnCompletionListener , OnSeekBarChangeListener{

	static Thread t = new Thread() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(mp!=null && mp.getCurrentPosition()<mp.getDuration()){
				curr.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mps1.setMax(mp.getDuration());
						long total = mp.getDuration();
						total = total/1000;
						long min = total/60;
						long sec = total%60;
						String totl="";
						if(sec<10){
							 totl= ""+min+":0"+sec;
						}
						else
						{
						 totl= ""+min+":"+sec;
						}
						tot.setText(totl);
						long cd = mp.getCurrentPosition();
						cd = cd/1000;
						long mn = cd/60;
						long se = cd%60;
						String S ="";
						if(se<10){
							 S= ""+mn+":0"+se;
						}
						else
						{
						 S= ""+mn+":"+se;
						}
						curr.setText(S);
						
					}
				});
			try {
				sleep(100);
				long cd = mp.getCurrentPosition();
				mps1.setProgress((int)cd);
			} catch (InterruptedException e ) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}}
	};
	public void playsong(String s)
	{
		try {
			play.setImageResource(R.drawable.btn_pause);
			SharedPreferences pref = getSharedPreferences("mp3", 0);
			SharedPreferences.Editor e = pref.edit();
			e.putString("cs", s);
			e.commit();
			mp.reset();
			mp.setDataSource(s);
			//Toast.makeText(MusicPlayer.this, ""+length, 1000).show();
			mp.prepare();
			curr_song_name.setText(name[z]);
			long j = mp.getDuration();
			mps1.setMax((int)j);
			long total = mp.getDuration();
			total = total/1000;
			long min = total/60;
			long sec = total%60;
			String totl="";
			if(sec<10){
				 totl= ""+min+":0"+sec;
			}
			else
			{
			 totl= ""+min+":"+sec;
			}
			tot.setText(totl);
			mp.start();
			t.start();
		}  
		catch (Exception e) {
			// TODO Auto-generated catch block
			//Toast.makeText(MusicPlayer.this, "NO ", 1000).show();
		}
	}
	public static int randInt(int min, int max) 
	{
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	 static MediaPlayer mp;
	static SeekBar mps1;
	ImageButton play, ff ,bk,li,shuffle,repeat,forward,backward;
	static TextView curr, tot,curr_song_name;
	static int val_req=10;
	int z,length,rep=0,shuf=0,os=0;
	String arr [],name [];
	String song_path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);
		SharedPreferences pref = getSharedPreferences("mp3", 0);
		song_path = pref.getString("cs", "");
		rep = pref.getInt("y", 0);
		shuf = pref.getInt("sh", 0);
		os = pref.getInt("os", 0);
		if(rep==1)
		{
		Toast.makeText(this,"repeat is on", 1000).show();
		//repeat.setImageResource(R.drawable.btn_repeat_focused);
		}
		if(shuf==1)
		{
		Toast.makeText(this,"shuffle is on", 1000).show();
		//shuffle.setImageResource(R.drawable.btn_shuffle_focused);
		}
		if(mp==null)
		{
		mp = new MediaPlayer();
		mp = MediaPlayer.create(this, R.raw.che);
		}
		if(song_path!="")
		{
			try
			{
			mp.reset();
			mp.setDataSource(song_path);
			mp.prepare();
			}
			catch (Exception e)
			{
			Toast.makeText(this, "Select song", 1000);
			}
		}
		Toast.makeText(this, song_path, 1000).show();
		play = (ImageButton)findViewById(R.id.mpb1);
		//pause = (Button)findViewById(R.id.mpb2);
		curr = (TextView)findViewById(R.id.mptf1);
		tot = (TextView)findViewById(R.id.mptf2);
		ff = (ImageButton)findViewById(R.id.mpb4);
		bk = (ImageButton)findViewById(R.id.mpb5);
		li = (ImageButton)findViewById(R.id.mpb6);
		mps1 = (SeekBar)findViewById(R.id.mpsb);
		repeat = (ImageButton)findViewById(R.id.mpb7);
		shuffle = (ImageButton)findViewById(R.id.mpb8);
		forward = (ImageButton)findViewById(R.id.btnForward);
		backward = (ImageButton)findViewById(R.id.btnBackward);
		curr_song_name = (TextView)findViewById(R.id.textView1);
		mp.setOnCompletionListener(this);
		mps1.setOnSeekBarChangeListener(this);
			
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
					mp.setDataSource(song_path);
				}
					catch (Exception e) 
				{
						// TODO Auto-generated catch block
						//Toast.makeText(MusicPlayer.this, "NO ", 1000).show();
				}
				if(!mp.isPlaying())
				{
					try
					{
					mps1.setMax(mp.getDuration());
					long total = mp.getDuration();
					total = total/1000;
					long min = total/60;
					long sec = total%60;
					String totl="";
					if(sec<10){
						 totl= ""+min+":0"+sec;
					}
					else
					{
					 totl= ""+min+":"+sec;
					}
					tot.setText(totl);
					play.setImageResource(R.drawable.btn_pause);
					mp.start();
					t.start();
					
					}
					catch (Exception e)
					{
						//Toast.makeText(MusicPlayer.this, "ok now", 1000).show();
					}
				}
				else
				{
					mp.pause();
					play.setImageResource(R.drawable.btn_play);
				}
				
				
			}
		});
		forward.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int cd = mp.getCurrentPosition();
				if(cd+5000<=mp.getDuration())
				mp.seekTo(cd+5000);
				else
				{
					playsong(arr[z+1]);
					
				}
				
			}
		});
		backward.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int cd = mp.getCurrentPosition();
				if(cd>=5000)
				mp.seekTo(cd - 5000);
				else
					mp.seekTo(0);
				
				
			}
		});
		/*pause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mp.isPlaying())
				{
					mp.pause();
				}
				
			}
		});*/
		ff.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(z==length-1)
				z=z;
				else
					z++;
				String songPath = arr[z];
				playsong(songPath);
				
			}
		});
		bk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(z!=0)
				z--;
				String songPath = arr[z];
				playsong(songPath);
			}
		});
		li.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = "com.example.musicplayer.MainActivity";
				Class c;
				try
				{
					c= Class.forName(s);
					Intent I = new Intent(MusicPlayer.this,c);
					startActivityForResult(I, val_req);
				}
				catch(ClassNotFoundException e)
				{
					String em = "No list found";
					Toast.makeText(MusicPlayer.this, em, 1000).show();
				}
				
			}
		});
		repeat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(rep==0)
				{
					rep=1;
					repeat.setImageResource(R.drawable.btn_repeat_focused);
					SharedPreferences pref = getSharedPreferences("mp3", 0);
					SharedPreferences.Editor e = pref.edit();
					e.putInt("y", 1);
					e.commit();
				}
				else
				{
					rep=0;
					repeat.setImageResource(R.drawable.btn_repeat);
					SharedPreferences pref = getSharedPreferences("mp3", 0);
					SharedPreferences.Editor e = pref.edit();
					e.putInt("y", 0);
					e.commit();
				}
				
			}
		});
		shuffle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(shuf==0)
				{
					shuf=1;
					shuffle.setImageResource(R.drawable.btn_shuffle_focused);
					SharedPreferences pref = getSharedPreferences("mp3", 0);
					SharedPreferences.Editor e = pref.edit();
					e.putInt("sh", 1);
					e.commit();
				}
				else
				{
					shuf=0;
					shuffle.setImageResource(R.drawable.btn_shuffle);
					SharedPreferences pref = getSharedPreferences("mp3", 0);
					SharedPreferences.Editor e = pref.edit();
					e.putInt("sh", 0);
					e.commit();
				}
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(val_req==10)
		{
				Bundle b = data.getExtras();
				String rs ;//= b.getString("songs_path");
				z = b.getInt("songs_index");
				arr = b.getStringArray("path_detail");
				length = b.getInt("length");
				name = b.getStringArray("name_song");
				rs = arr[z];
				os  = 1;
				play.setImageResource(R.drawable.btn_pause);
				SharedPreferences pref = getSharedPreferences("mp3", 0);
				SharedPreferences.Editor e = pref.edit();
				e.putInt("os", 1);
				e.commit();
				
				//Toast.makeText(this, arr[z], Toast.LENGTH_LONG).show();
				playsong(rs);
		}
				
					
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
			if(fromUser)
			mp.seekTo(mps1.getProgress());
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if(rep==1)
			playsong(arr[z]);
		else if(shuf==1)
		{
			z = randInt(0, length-1);
					playsong(arr[z]);
		}
		else
		{
			playsong(arr[z+1]);
		}
		
	}
}
