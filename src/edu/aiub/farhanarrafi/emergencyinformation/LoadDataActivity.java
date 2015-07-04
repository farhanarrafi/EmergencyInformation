package edu.aiub.farhanarrafi.emergencyinformation;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
	Button newspaperB, hospitalB, pharmacyB, dentalB, bloodB, ngoB, rabB;
	TextView textView;
	
	DatabaseHelperC dbHelper;
	ConnectivityManager connManager;
	
	ArrayList<String> resultArray = new ArrayList<String>();
	
	ArrayAdapter<String> adapter;
	
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
    }

	@Override
	public void fetchResult(String result) {
		result = result.split("<")[0];
		boolean flag = false;;
		try {
			flag = parseJSON(result);
		} catch (JSONException e) {
			flag = false;
			Log.d("error", "JSON Parse error");
			e.printStackTrace();
		}
		
//		textView.setText(result);
		
		setContentView(R.layout.activity_data_display);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultArray);
		
		ListView listView = (ListView) findViewById(R.id.ListView_Display_Data);
		listView.setAdapter(adapter);
		
	}
	
	public boolean parseJSON(String result) throws JSONException {
		//JSONObject jsonObj = new JSONObject(result);
		JSONArray jsonArr = new JSONArray(result);
		
		
		
		
		
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
			url = url.concat("newspaper.php");
		} else if(v.getId() == hospitalB.getId()) {
			url = url.concat("hospital.php");
		} else if(v.getId() == pharmacyB.getId()) {
			url = url.concat("pharmacy.php");
		} else if(v.getId() == dentalB.getId()) {
			url = url.concat("dental.php");
		} else if(v.getId() == bloodB.getId()) {
			url = url.concat("bloodbank.php");
		} else if(v.getId() == ngoB.getId()) {
			url = url.concat("ngo.php");
		} else if(v.getId() == rabB.getId()) {
			url = url.concat("rab.php");
		}
		 
		//Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
		if(networkAvailable()) {
            FetchDataC fetch = new FetchDataC(this);
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
