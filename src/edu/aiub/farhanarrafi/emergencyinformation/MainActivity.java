package edu.aiub.farhanarrafi.emergencyinformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
	Button newspaperB, hospitalB, pharmacyB, dentalB, bloodB, ngoB, rabB;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
		
		Intent intent = new Intent(this, LoadDataActivity.class);
		intent.putExtra("url", url);
        startActivity(intent);
	}
    
}
