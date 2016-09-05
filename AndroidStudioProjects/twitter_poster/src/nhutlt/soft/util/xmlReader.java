package nhutlt.soft.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class xmlReader {
	private Hashtable<String, String> contentTable;
	
	public xmlReader(String xmlString) {
		contentTable = new Hashtable<String, String>();
		XmlPullParserFactory factory;
		XmlPullParser xmlParser;
		int event;
		String key = null, value = null;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlParser = factory.newPullParser();
			xmlParser.setInput(new StringReader(xmlString));
			event = xmlParser.getEventType();
			while(event != XmlPullParser.END_DOCUMENT) {
				if (event == XmlPullParser.START_TAG) {
					key = xmlParser.getName();
				} else if (event == XmlPullParser.TEXT) {
					if (key != null) {
						value = xmlParser.getText();
						contentTable.put(key, value);
						key = null;
						value = null;
					}
				} 
				xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			Log.v("NhutLT", e.getMessage());
			return;
		} catch (IOException e) {
			e.printStackTrace();
			Log.v("NhutLT", e.getMessage());
		}
		
	}
	
	public String getTagContent(String tagName) {
		return contentTable.get(tagName);
	}

}
