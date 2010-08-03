package com.tugraz.android.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.tugraz.android.app.filesystem.MediaFileLoader;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainListViewAdapter extends BaseAdapter{
    private Context mCtx;
    private MediaFileLoader mMediaFileLoader;
    private ListView mMainListView;
    public ArrayList<HashMap<String, String>> mList;

    
	public MainListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> data, ListView listview) {
		mCtx = context;
		mList = data;	
		mMainListView = listview;
		mMediaFileLoader = new MediaFileLoader(mCtx);
		mMediaFileLoader.loadSoundContent();
	}
	
	

	public int getCount() {
		return mList.size();
	}

	public Object getItem(int arg0) {
		
		return mList.get(arg0);
	}

	public long getItemId(int position) {
		//Testfall schreiben
		String type = mList.get(position).get(BrickDefine.BRICK_ID);
		if(type == null)
			return 0;
		else
			return Integer.valueOf(type).intValue();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final HashMap<String, String> brick = mList.get(position);
		//Log.d("View", mList.toString());
		//TODO check convertView
		//TODO Reuse Views
		//Type of the Brick
		String type = mList.get(position).get(BrickDefine.BRICK_TYPE);
		//Inflater to build the views
		LayoutInflater inflater = (LayoutInflater)mCtx.getSystemService(
	      Context.LAYOUT_INFLATER_SERVICE);
		
		//Check the type
		switch(Integer.valueOf(type).intValue()){
		case (BrickDefine.SET_BACKGROUND): 
		{
			RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.brick_set_background, null);
			//text1.setTextColor(Color.BLUE);
			TextView text = (TextView)view.getChildAt(1);
			text.setText("Setze Hintergrund:");
			//text2.setTextColor(Color.BLUE);
			//view.setBackgroundColor(Color.argb(255, 139, 0, 139));

			ImageView imageView = (ImageView)view.getChildAt(0);
			//TODO set correct position
			imageView.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					for(int i = 0; i < mMainListView.getChildCount(); i++){
						if(v.equals(mMainListView.getChildAt(i))){
							Log.d("TEST", i +"");
							mMediaFileLoader.openPictureGallery(i);
						}
					}
					
					
				}
			});
			
//		    LayoutParams params = (LayoutParams) view.getLayoutParams();
//		    params.addRule(RelativeLayout.ALIGN_BOTTOM, parent.getChildAt(size-1).getId());
//		    view.setLayoutParams(params);

			return view;
		}
		case (BrickDefine.PLAY_SOUND): 
		{
			RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.brick_play_sound, null);
			TextView text = (TextView)view.getChildAt(0);
			text.setText("Spiele Klang:");
			
		/*	Spinner spinner = (Spinner)view.getChildAt(1);
         
			final SimpleAdapter adapter = new SimpleAdapter(mCtx, mMediaFileLoader.getSoundContent(), R.layout.sound_spinner,
					new String[] {MediaFileLoader.SOUND_NAME},
	                new int[] {R.id.SoundSpinnerTextView});
			spinner.setAdapter(adapter);
			spinner.setSelection(0);
			OnItemSelectedListener listener = new OnItemSelectedListener(){

				
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
						brick.put(BrickDefine.BRICK_VALUE, ((HashMap<String, String>)adapter.getItem(arg2)).get(MediaFileLoader.SOUND_PATH));
				}

				
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			};
		    spinner.setOnItemSelectedListener(listener);
			//view.setBackgroundColor(Color.BLUE);
			spinner.setSelection(getIndexFromElementSound(adapter, brick.get(BrickDefine.BRICK_VALUE)));
			*/
			return view;
		}
		case (BrickDefine.WAIT): 
		{
			RelativeLayout view =  (RelativeLayout)inflater.inflate(R.layout.brick_wait, null);
			  TextView text = (TextView) view.getChildAt(0);
			  text.setText("Warte ");
			  //text.setTextColor(Color.BLUE);
	          EditText etext = (EditText) view.getChildAt(1);
	          etext.setText("1");
	          
	          etext.setText(brick.get(BrickDefine.BRICK_VALUE));
	          
	          etext.addTextChangedListener(new TextWatcher()
	          {

				
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}

				
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					brick.remove(BrickDefine.BRICK_VALUE);
					brick.put(BrickDefine.BRICK_VALUE, s.toString());
					
				}
	        	  
	        	  
	          });
	          
	         // view.setBackgroundColor(Color.argb(255, 255, 215, 0));
	        
			return view;
			
		}
		case (BrickDefine.HIDE):
		{
			LinearLayout view =  (LinearLayout)inflater.inflate(R.layout.brick_simple_text_view, null);
			TextView text = (TextView) view.getChildAt(0);
			text.setText("Hide");
			//view.setBackgroundColor(Color.argb(255, 255, 215, 100));
			return view;
		}
		case (BrickDefine.SHOW):
		{
			LinearLayout view =  (LinearLayout)inflater.inflate(R.layout.brick_simple_text_view, null);
			TextView text = (TextView) view.getChildAt(0);
			text.setText("Show");
			//view.setBackgroundColor(Color.argb(255, 255, 215, 200));
			return view;
		}
		case (BrickDefine.GO_TO):
		{
			RelativeLayout view =  (RelativeLayout)inflater.inflate(R.layout.brick_goto, null);
			
			TextView text = (TextView) view.getChildAt(3);
			text.setText("GO-TO-XY");
			
			EditText textX = (EditText) view.getChildAt(1);
			textX.setText(brick.get(BrickDefine.BRICK_VALUE));
			textX.addTextChangedListener(new TextWatcher()
	          {
				
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub	
				}
				
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub					
				}
				
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					brick.remove(BrickDefine.BRICK_VALUE);
					brick.put(BrickDefine.BRICK_VALUE, s.toString());					
				} 
	          });
			
			EditText textY = (EditText) view.getChildAt(4);
			textY.setText(brick.get(BrickDefine.BRICK_VALUE_1));
			textY.addTextChangedListener(new TextWatcher()
	          {
				
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub	
				}
				
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub	
				}
				
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					brick.remove(BrickDefine.BRICK_VALUE_1);
					brick.put(BrickDefine.BRICK_VALUE_1, s.toString());	
				} 
	          });
			//view.setBackgroundColor(Color.argb(255, 255, 215, 255));
			return view;
		}
		case (BrickDefine.SET_COSTUME): 
		{
			RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.brick_set_costume, null);
			//text1.setTextColor(Color.BLUE);
			TextView text = (TextView)view.getChildAt(1);
			text.setText("Setze Kost�m:");
			//text2.setTextColor(Color.BLUE);
			//view.setBackgroundColor(Color.argb(255, 139, 0, 50));

			ImageView imageView = (ImageView)view.getChildAt(0);
			

			return view;
		}
		
		
		
		
		case (BrickDefine.NOT_DEFINED):
		{
			return null;
		}
		default: 
		{
			//TODO: Not defined Error
			return null;
	    }
		
		}
	}

  public int  getIndexFromElementSound(SimpleAdapter adapter, String element) {
	  ArrayList<HashMap<String, String>> arrayList = mMediaFileLoader.getSoundContent();
    for(int i = 0; i < adapter.getCount(); i++) {
      String value = arrayList.get(i).get(MediaFileLoader.SOUND_PATH);
	  if(value.equals((element))) {
	    return i;
	    }
	  }
	return 0;
	}
	

}
