package edu.aiub.farhanarrafi.emergencyinformation;

import android.app.SearchManager;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LoadDataActivity extends ActionBarActivity implements AsycResponseI, OnClickListener {
	Button newspaperB, hospitalB, pharmacyB, dentalB, bloodB, ngoB, rabB;
	TextView textView;
	
	ConnectivityManager connManager;
	
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
        
    }

	@Override
	public void fetchResult(String result) {
		result = result.split("<")[0];

		textView.setText(result);
		
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
