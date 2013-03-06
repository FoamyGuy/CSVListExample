package com.makemyandroidapp.example.csvlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/*
 * Very basic Custom Adapter that takes state name,capital pairs out of a csv
 * file from the assets and uses those values to build a List of State objects.
 * Overrides the default getView() method to return a TextView with the state name.
 * 
 * ArrayAdapter - a type of Adapter that works a lot like ArrayList.
 */
public class CSVAdapter extends ArrayAdapter<State>{
	Context ctx;
	
	//We must accept the textViewResourceId parameter, but it will be unused
	//for the purposes of this example.
	public CSVAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		//Store a reference to the Context so we can use it to load a file from Assets.
		this.ctx = context;
		
		//Load the data.
		loadArrayFromFile();	
	}
	
	
	
	/*
	 * getView() is the method responsible for building a View out of a some data that represents
	 * one row within the ListView. For this example our row will be a single TextView that
	 * gets populated with the state name.
	 * (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int pos, View convertView, final ViewGroup parent){
		/*
		 * Using convertView is important. The system will pass back Views that have been
		 * created but scrolled off of the top (or bottom) of the screen, and thus are no
		 * longer being shown on the screen. Since they are unused, we can "recycle" them
		 * instead of creating a new View object for every row, which would be wasteful, 
		 * and lead to poor performance. The diference may not be noticeable in this
		 * small example. But with larger more complex projects it will make a significant
		 * improvement by recycling Views rather than creating new ones for each row.
		 */
		TextView mView = (TextView)convertView;
		//If convertView was null then we have to create a new TextView.
		//If it was not null then we'll re-use it by setting the appropriate
		//text String to it.
		if(null == mView){ 
			mView = new TextView(parent.getContext());
			mView.setTextSize(28);
		}
		
		//Set the state name as the text.
		mView.setText(getItem(pos).getName());
		
		//We could handle the row clicks from here. But instead
		//we'll use the ListView.OnItemClickListener from inside
		//of MainActivity, which provides some benefits over doing it here.
		
		/*mView.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Toast.makeText(parent.getContext(), getItem(pos).getCapital(), Toast.LENGTH_SHORT).show();
			}
		});*/
		
		return mView;
	}
	
	/*
	 * Helper method that loads the data from the states.csv and builds
	 * each csv row into a State object which then gets added to the Adapter.
	 */
	private void loadArrayFromFile(){
		try {
			// Get input stream and Buffered Reader for our data file.
			InputStream is = ctx.getAssets().open("states.csv"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			
			//Read each line
			while ((line = reader.readLine()) != null) {
				
				//Split to separate the name from the capital
				String[] RowData = line.split(",");
				
				//Create a State object for this row's data.
				State cur = new State();
				cur.setName(RowData[0]);
				cur.setCapital(RowData[1]);
				
				//Add the State object to the ArrayList (in this case we are the ArrayList).
				this.add(cur);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
