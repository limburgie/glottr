package be.webfactor.polyglot.service.connection.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import be.webfactor.polyglot.service.connection.InternetConnectionTester;

public class InternetConnectionTesterImpl extends AsyncTask<Void, Void, Boolean> implements InternetConnectionTester {

	private static final int TIMEOUT_MILLIS = 1500;
	private static final String GOOGLE_HOME_URL = "http://www.google.com";
	
	private Activity activity;

	public InternetConnectionTesterImpl(Activity activity) {
		this.activity = activity;
	}

	public boolean isActive() {
		if(!isNetworkAvailable()) {
			return false;
		}
		try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(GOOGLE_HOME_URL).openConnection());
            connection.setRequestProperty("User-Agent", "Test");
            connection.setRequestProperty("Connection", "close");
            connection.setConnectTimeout(TIMEOUT_MILLIS); 
            connection.connect();
            return (connection.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
        	return false;
        }
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivityManager.getActiveNetworkInfo() != null;
	}

	protected Boolean doInBackground(Void... params) {
		return isActive();
	}

}
