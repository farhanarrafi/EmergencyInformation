package edu.aiub.farhanarrafi.emergencyinformation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;


public class MainActivity extends ActionBarActivity {
	Button loadData_B, search_B;
	Intent intent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData_B = (Button) findViewById(R.id.buttonLoadData);
        search_B = (Button) findViewById(R.id.buttonSearchActivity);
        
        loadData_B.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), LoadDataActivity.class);
		        startActivity(intent);
			}
		});
        
        search_B.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(getApplicationContext(), SearchActivity.class);
		        startActivity(intent);
			}
		});
        
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
	
    
}
