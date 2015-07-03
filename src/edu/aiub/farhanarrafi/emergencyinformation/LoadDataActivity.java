package edu.aiub.farhanarrafi.emergencyinformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LoadDataActivity extends Activity implements AsycResponseI {
	
	TextView textView;
	Intent intent;
	ConnectivityManager connManager;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        
        if(networkAvailable()) {
        	intent = getIntent();
            String dataUrl = intent.getStringExtra("url");
            textView = (TextView) findViewById(R.id.textView_loadData);
//            String dataUrl = "http://eatl-android-farhan.net63.net/output/pharmacy.php";
            FetchDataC fetch = new FetchDataC(this);
            fetch.response = this;
            fetch.execute(dataUrl);
        }
        else {
        	Log.d("Network Error", "Network not Available!!");
        	finish();
        }
        
        
        
    }

	@Override
	public void fetchResult(String result) {
		result = result.split("<")[0];
		
//		JSONObject jsonObj;
//		try {
//			jsonObj = new JSONObject(result);
//			textView.setText("NAME");
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
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
	
	
}
