package edu.aiub.farhanarrafi.emergencyinformation;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseHelperC;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.BloodBank;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Dental;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Hospital;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Newspaper;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Ngo;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Pharmacy;
import edu.aiub.farhanarrafi.emergencyinformation.helper.DatabaseTablesC.Rab;

public class DisplayDataActivity extends ActionBarActivity {
	
	ArrayList<String> itemList;
	ArrayAdapter<String> adapter;
	DatabaseHelperC dbHelper;
	String tableName;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_data);
		
		dbHelper = new DatabaseHelperC(getApplicationContext());
		tableName = getIntent().getStringExtra("tableName").toString();
		itemList = new ArrayList<String>();
		
		readFromDB();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
		listView = (ListView) findViewById(R.id.ListViewDisplayData);
		listView.setAdapter(adapter);
		
	}
	
	public void setData(Cursor cursor) {
		Toast.makeText(getApplicationContext(), "" + cursor.moveToFirst(), Toast.LENGTH_SHORT).show();
		if(cursor.moveToNext() && cursor != null) { 
			 do{
				 if(cursor.getCount()<=2) {
					String name = cursor.getString(0);
					String address = cursor.getString(1);
					itemList.add("Name: " + name + "\nAddress: " + address);
					Log.d("Output","Name: " + name + "\nAddress: " + address);
				 } else {
					String name = cursor.getString(0);
					String phone = cursor.getString(1);
					String address = cursor.getString(2);
					itemList.add("Name: " + name + "\nPhone: " + phone +"\nAddress: " + address);
					Log.d("Output","Name: " + name + "\nPhone: " + phone +"\nAddress: " + address);
				 }
			}while(cursor.moveToNext());
		}
	}
	
	
	public void readFromDB() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		
		
		
		if(tableName.equals(BloodBank.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {BloodBank.COLUMN_NAME, BloodBank.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		} else if(tableName.equals(Dental.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {Dental.COLUMN_NAME, Dental.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		} else if(tableName.equals(Hospital.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {Hospital.COLUMN_NAME, Hospital.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		} else if(tableName.equals(Newspaper.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {Newspaper.COLUMN_NAME, Newspaper.COLUMN_PHONE, Newspaper.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		} else if(tableName.equals(Pharmacy.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {Pharmacy.COLUMN_NAME, Pharmacy.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		} else if(tableName.equals(Ngo.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {Ngo.COLUMN_NAME, Ngo.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		} else if(tableName.equals(Rab.TABLE_NAME)) {
			Cursor cursor = db.query(tableName, new String[] {Rab.COLUMN_NAME, Rab.COLUMN_ADDRESS},
					null,null,null, null, null, null);
			setData(cursor);
		}
		
//		Toast.makeText(getApplicationContext(), "readFromDB()", Toast.LENGTH_SHORT).show();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_data, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
