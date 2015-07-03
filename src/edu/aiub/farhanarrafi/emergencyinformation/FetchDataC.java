package edu.aiub.farhanarrafi.emergencyinformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class FetchDataC extends AsyncTask<String, Void, String> {
	Context context;
	InputStream iStream;
	AsycResponseI response = null;
	HttpURLConnection conn;
	
	public FetchDataC(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		
		String dataUrl = "http://eatl-android-farhan.net63.net/output/hospital.php";
        dataUrl = params[0];
        try {
        	URL url = new URL(dataUrl);
        	conn = (HttpURLConnection) url.openConnection();
        	conn.setReadTimeout(10000);
        	conn.setRequestMethod("GET");
        	conn.connect();
        	Log.d("", "The response is:" + conn.getResponseMessage());
        	
        	iStream = conn.getInputStream();
        	String content = this.readJson(iStream);
        	//Toast.makeText(context, "DATA READ", Toast.LENGTH_SHORT).show();
        	Log.d("FETCHDATA", "dataloaded");
        	return content;
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("ERROR", e.toString());
		} finally {
			if(iStream != null && conn != null) {
				try {
					conn.disconnect();
					iStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        return null;
		
	}

	@Override
	protected void onPostExecute(String result) {
		response.fetchResult(result);
	}
	
	public String readJson(InputStream stream) throws IOException, UnsupportedEncodingException  {
		Reader reader = new InputStreamReader(stream, "UTF-8");
		BufferedReader buffReader = new BufferedReader(reader);
		StringBuilder result = new StringBuilder();
		String line;
		while( (line = buffReader.readLine()) != null) {
			result.append(line);
		}
//		Reader reader = new InputStreamReader(stream, "UTF-8");
//		char[] buffer = new char[16999];
//		reader.read(buffer);
//		return new String(buffer);
		reader.close();
		return result.toString();
	}

}
