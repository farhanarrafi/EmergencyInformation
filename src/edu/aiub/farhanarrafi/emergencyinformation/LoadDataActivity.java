package edu.aiub.farhanarrafi.emergencyinformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;
import android.widget.Toast;
import edu.aiub.farhanarrafi.emergencyinformation.helper.AsycResponseI;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseHelperC;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.BloodBank;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Dental;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Hospital;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Newspaper;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Ngo;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Pharmacy;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Rab;
import edu.aiub.farhanarrafi.emergencyinformation.helper.FetchDataC;

@SuppressWarnings("deprecation")
public class LoadDataActivity extends ActionBarActivity implements AsycResponseI {
	
	Button update_DB;
	
	private static final String PREFERNCE_FIRST_RUN = "FIRST_RUN";
	
	TextView textView;
	FetchDataC fetch;
	DatabaseHelperC dbHelper;
	ConnectivityManager connManager;
	String currentUrl = "";
	JSONArray jsonArr;
	ArrayAdapter<String> adapter;
	SharedPreferences preference;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        
        
        
        dbHelper = new DatabaseHelperC(getApplicationContext());
        
        preference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = preference.getBoolean(PREFERNCE_FIRST_RUN, true);
        if(isFirstRun) {
        	loadDatabaseData();
        	preference.edit().putBoolean(PREFERNCE_FIRST_RUN, false).commit();
        }
        
        update_DB = (Button) findViewById(R.id.update_database);
        update_DB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbHelper = new DatabaseHelperC(getApplicationContext());
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				dbHelper.onUpgrade(db, 2, 1);
				loadDatabaseData();
				preference.edit().putBoolean(PREFERNCE_FIRST_RUN, false).commit();
			}
		});
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
			currentUrl = urlPost[i];
			newUrl = url.concat(currentUrl);
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
		}
	}
	
	public boolean parseJSON(String result) throws JSONException {
		//JSONObject jsonObj = new JSONObject(result);
		jsonArr = new JSONArray(result);

		if(jsonArr.length()>0) {
			return true;
		}
		return false;
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
