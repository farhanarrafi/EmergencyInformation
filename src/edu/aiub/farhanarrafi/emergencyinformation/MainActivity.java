package edu.aiub.farhanarrafi.emergencyinformation;

import java.util.ArrayList;

import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseHelperC;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnItemClickListener{
	
	private static final String PREFERNCE_FIRST_RUN = "PREFERENCE";
	Intent intent;
	ArrayList<String> itemList;
	ArrayAdapter<String> adapter;
	
	DatabaseHelperC dbHelper;
	
	SharedPreferences preference;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemList = new ArrayList<String>();
        
        populateItemList();
        
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
		ListView listView = (ListView) findViewById(R.id.ListViewMainActivity);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(this);
		
		preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = preference.getBoolean(PREFERNCE_FIRST_RUN, true);
        if(isFirstRun) {
        	preference.edit().putBoolean(PREFERNCE_FIRST_RUN, false).commit();
        	intent = new Intent(getApplicationContext(), LoadDataActivity.class);
    		startActivity(intent);
        	
        }
		
		
    }
    
    public void populateItemList() {
    	
		itemList.add("NEWSPAPER");
		itemList.add("HOSPITAL");
		itemList.add("PHARMACY");
		itemList.add("DENTAL");
		itemList.add("BLOODBANK");
		itemList.add("NGO");
		itemList.add("RAB");
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView parent, View v, int position, long id) {
		String item = parent.getItemAtPosition(position).toString();
		String tableName = "";
		
		if(item.equals("NEWSPAPER")) {
			tableName = "newspaper";
		} else if(item.equals("HOSPITAL")) {
			tableName = "hospital";
		} else if(item.equals("PHARMACY")) {
			tableName = "pharmacy";
		} else if(item.equals("DENTAL")) {
			tableName = "dental";
		} else if(item.equals("BLOODBANK")) {
			tableName = "bloodbank";
		} else if(item.equals("NGO")) {
			tableName = "ngo";
		} else if(item.equals("RAB")) {
			tableName = "rab";
		}
		intent = new Intent(this, DisplayDataActivity.class);
		intent.putExtra("tableName", tableName);
		startActivity(intent);
	}
	
    
}
