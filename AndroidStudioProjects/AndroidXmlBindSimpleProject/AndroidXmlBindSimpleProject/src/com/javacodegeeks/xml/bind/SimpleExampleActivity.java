package com.javacodegeeks.xml.bind;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.javacodegeeks.xml.bind.model.OpenSearchDescription;

public class SimpleExampleActivity extends Activity {
	
	private static final String url = 
		"http://dl.dropbox.com/u/7215751/JavaCodeGeeks/AndroidFullAppTutorialPart03/Transformers+2007.xml";
	
	private DefaultHttpClient client = new DefaultHttpClient();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
        	
			String xmlData = retrieve(url);
			Serializer serializer = new Persister();        
			
			Reader reader = new StringReader(xmlData);
			OpenSearchDescription osd = serializer.read(OpenSearchDescription.class, reader, false);
			
			Log.d(SimpleExampleActivity.class.getSimpleName(), osd.toString());
			
		} 
        catch (Exception e) {
        	Toast.makeText(this, "Error Occured", Toast.LENGTH_LONG).show();
		}
        
    }
    
	public String retrieve(String url) {

		HttpGet getRequest = new HttpGet(url);

		try {

			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			HttpEntity getResponseEntity = getResponse.getEntity();

			if (getResponseEntity != null) {
				return EntityUtils.toString(getResponseEntity);
			}

		} 
		catch (IOException e) {
			getRequest.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

		return null;

	}
    
}