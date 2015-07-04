package edu.aiub.farhanarrafi.emergencyinformation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.aiub.farhanarrafi.emergencyinformation.helper.*;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.*;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LoadDataActivity extends ActionBarActivity implements AsycResponseI, OnClickListener {
	
	private static final String PREFERNCE_FIRST_RUN = null;
	
	Button newspaperB, hospitalB, pharmacyB, dentalB, bloodB, ngoB, rabB;
	TextView textView;
	FetchDataC fetch;
	DatabaseHelperC dbHelper;
	ConnectivityManager connManager;
	String currentUrl = "";
	ArrayList<String> resultArray = new ArrayList<String>();
	JSONArray jsonArr;
	ArrayAdapter<String> adapter;
	
	SharedPreferences preference;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        
        newspaperB = (Button) findViewById(R.id.button_newspaper);
        newspaperB.setOnClickListener(this);
        
        hospitalB = (Button) findViewById(R.id.button_hospital);
        hospitalB.setOnClickListener(this);
        
        pharmacyB = (Button) findViewById(R.id.button_pharmacy);
        pharmacyB.setOnClickListener(this);
        
        dentalB = (Button) findViewById(R.id.button_dental);
        dentalB.setOnClickListener(this);
        
        bloodB = (Button) findViewById(R.id.button_blood);
        bloodB.setOnClickListener(this);
        
        ngoB = (Button) findViewById(R.id.button_ngo);
        ngoB.setOnClickListener(this);
        
        rabB = (Button) findViewById(R.id.button_rab);
        rabB.setOnClickListener(this);
        
        textView = (TextView) findViewById(R.id.textViewLoadData);
        
        dbHelper = new DatabaseHelperC(getApplicationContext());
        
        preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = preference.getBoolean(PREFERNCE_FIRST_RUN, true);
        
        
        if(isFirstRun) {
        	loadDatabaseData();
        	preference.edit().putBoolean(PREFERNCE_FIRST_RUN, false).commit();
        }
    }
	
	public void loadDatabaseData() {
		String newUrl,url = "http://eatl-android-farhan.net63.net/output/";
		String[] urlPost = new String[7];
		
		urlPost[0] = "newspaper.php";
		urlPost[1] = "hospital.php";
		urlPost[2] = "pharmacy.php";
		urlPost[3] = "dental.php";
		urlPost[4] = "bloodbank.php";
		urlPost[5] = "ngo.php";
		urlPost[6] = "rab.php";
		for (int i = 0; i < urlPost.length; i++) {
			newUrl = url.concat(urlPost[i]);
			if(networkAvailable()) {
	            fetch = new FetchDataC(this);
	            fetch.response = this;
	            fetch.execute(newUrl);
	        } else {
	        	Toast.makeText(this,"NETWORK ERROR", Toast.LENGTH_SHORT).show();
	        	Log.d("Network Error", "Network not Available!!");
	        }
		}
		 
		
			
	}

	@Override
	public void fetchResult(String result) {
		fetch.cancel(false);
		result = result.split("<")[0];
		boolean flag = false;;
		try {
			flag = parseJSON(result);
		} catch (JSONException e) {
			flag = false;
			Log.d("error", "JSON Parse error");
			e.printStackTrace();
		}
		
		if(flag == true) {
			
			try {
				this.storeDataInDatabase();
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			/*
			setContentView(R.layout.activity_data_display);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultArray);

			ListView listView = (ListView) findViewById(R.id.ListView_Display_Data);
			listView.setAdapter(adapter);*/
		}	
//		textView.setText(result);
	}
	
	public boolean parseJSON(String result) throws JSONException {
		//JSONObject jsonObj = new JSONObject(result);
		jsonArr = new JSONArray(result);

		if(jsonArr.length()>0) {
			String string = "";
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				
				if(json.length()<=2) {
					String name =  json.get("name").toString();
					String address =  json.get("address").toString();
					string = "Name: " + name + "\nAddress: " + address;
				} else {
					String name =  json.get("name").toString();
					String phone =  json.get("phone").toString();
					String address =  json.get("address").toString();
					string = "Name: " + name + "\nPhone: " + phone + "\nAddress: " + address;
				}
				resultArray.add(string);
			}
			if(resultArray.isEmpty()) {
				return false;
			}
			else {
				return true;
			}
		}
		return false;
//		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	}
	
	public void storeDataInDatabase() throws JSONException {
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		
		ContentValues values = new ContentValues();
		
		if(currentUrl.equals("newspaper.php")) {
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(Newspaper.COLUMN_NAME, json.get("name").toString());
				values.put(Newspaper.COLUMN_PHONE, json.get("phone").toString());
				values.put(Newspaper.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(Newspaper.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
			
		
		} else if(currentUrl.equals("hospital.php")) {
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(Hospital.COLUMN_NAME, json.get("name").toString());
				values.put(Hospital.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(Hospital.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
		} else if(currentUrl.equals("bloodbank.php")) {
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(BloodBank.COLUMN_NAME, json.get("name").toString());
				values.put(BloodBank.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(BloodBank.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
		} else if(currentUrl.equals("pharmacy.php")) {
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(Pharmacy.COLUMN_NAME, json.get("name").toString());
				values.put(Pharmacy.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(Pharmacy.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
		} else if(currentUrl.equals("dental.php")){
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(Dental.COLUMN_NAME, json.get("name").toString());
				values.put(Dental.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(Dental.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
		} else if(currentUrl.equals("ngo.php")) {
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(Ngo.COLUMN_NAME, json.get("name").toString());
				values.put(Ngo.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(Ngo.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
		} else if(currentUrl.equals("rab.php")) {
			for (int i = 1; i < jsonArr.length(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				values.put(Rab.COLUMN_NAME, json.get("name").toString());
				values.put(Rab.COLUMN_ADDRESS, json.get("address").toString());
				long newRowID = db.insert(Rab.TABLE_NAME, null, values);
				Log.d("insertid", ""+newRowID);
			}
		} else {
			Log.d("error", "URL MISMATCH ERROR");
		}
	
		
	}
	
	
	public boolean networkAvailable() {
		connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkStatus = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean wifiStatus = networkStatus.isConnected();
		networkStatus = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean internetStatus = networkStatus.isConnected();
		if(wifiStatus || internetStatus) {
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		String url = "http://eatl-android-farhan.net63.net/output/";
		
		if(v.getId() == newspaperB.getId()) {
			currentUrl = "newspaper.php";
		} else if(v.getId() == hospitalB.getId()) {
			currentUrl = "hospital.php";
		} else if(v.getId() == pharmacyB.getId()) {
			currentUrl = "pharmacy.php";
		} else if(v.getId() == dentalB.getId()) {
			currentUrl = "dental.php";
		} else if(v.getId() == bloodB.getId()) {
			currentUrl = "bloodbank.php";
		} else if(v.getId() == ngoB.getId()) {
			currentUrl = "ngo.php";
		} else if(v.getId() == rabB.getId()) {
			currentUrl = "rab.php";
		}
		
		url = url.concat(currentUrl);
		 
		//Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
		if(networkAvailable()) {
            fetch = new FetchDataC(this);
            fetch.response = this;
            fetch.execute(url);
        }
        else {
        	Toast.makeText(this,"NETWORK ERROR", Toast.LENGTH_SHORT).show();
        	Log.d("Network Error", "Network not Available!!");
        }
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search, menu);
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
