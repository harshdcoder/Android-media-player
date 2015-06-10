package com.example.musicplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
















import android.R.bool;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
class Mp3Filter implements FilenameFilter
{
	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return(name.endsWith(".mp3"));
	}
}

public class MainActivity extends ListActivity
{
	String sour,check;
	int index;
	private static File r = Environment.getExternalStorageDirectory();
	private static File s = r.getParentFile();
	private static File d = s.getParentFile();
	private static String g = s.getAbsolutePath();
	private static final String SD_PATH = new String(g);
	private List<String> songs = new ArrayList<String>();
	private static List<String> paths = new ArrayList<String>();
	 String [] p = new String[2000];
	 String [] name_song = new String[2000];
	int ind =0;
	private MediaPlayer mp = new MediaPlayer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Toast.makeText(this, SD_PATH, 1000).show();
		updatePlayList();
	}
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
			index = position;
			sour = paths.get(position);
		    finish();
		
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		//Toast.makeText(this, p[index], 1000).show();
		Intent I = new Intent();
		I.putExtra("songs_path", sour);
		I.putExtra("songs_index", index);
		I.putExtra("path_detail", p);
		I.putExtra("name_song", name_song);
		I.putExtra("length", ind);
		//Toast.makeText(this, sour, 1000).show();
		setResult(RESULT_OK,I);
		super.finish();
	}
	
  protected void updatePlayList()
	{
		File f=new File(SD_PATH);
		func(f.getAbsolutePath(), f.getName(),f);
		ArrayAdapter<String> songList;
		//Collections.sort(songs);
		songList = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,songs);
		setListAdapter(songList);
		
	}
	public void func(String path,String name,File f)
	   {
		   try{

		   int fm=0,dc=1,fi=0;
		   File files[]=f.listFiles();
		   for(int i=0;i<files.length;i++)
		   {
			   if(files[i].isDirectory())
			   {
				   func(files[i].getAbsolutePath(),files[i].getName(),files[i]);
			   }
			   else if(files[i].isFile())
			   {
				  if(files[i].getName().endsWith(".mp3") || files[i].getName().endsWith(".MP3") )
				  {
					  songs.add(files[i].getName());
				  	  paths.add(files[i].getAbsolutePath());
				  	  p[ind]= files[i].getAbsolutePath();
				  	  name_song[ind] = files[i].getName();
				  	  ind= ind+1;
				  }
			   }
		   }

		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }

	   }
	

}
