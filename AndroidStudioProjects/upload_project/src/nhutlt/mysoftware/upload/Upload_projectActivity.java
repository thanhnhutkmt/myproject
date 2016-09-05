package nhutlt.mysoftware.upload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Upload_projectActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView result = (TextView) findViewById(R.id.text);      
         
        String email = "legendknight99@yahoo.com";
        String pass  = "987654321";
        String appId = "5908";
        String apiKey = "lyj4y3d4433l6rz1xp39568buhbn5708xjzft88t";
        String sign = getSHA1(email+pass+appId+apiKey);        
        String Uri = "https://www.mediafire.com/api/user/get_login_token.php" +
           	 		 "?email=" + email + 
           	 		 "&password=" + pass + 
           	 		 "&application_id=" + appId + 
           	 		 "&signature=" + sign +
           	 		 "&version=2.5";
        result.setText(result.getText() + "\n " + Uri);
        
    	// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Uri);
        // Execute HTTP Post Request
        try {
			HttpResponse response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setText(result.getText() + "\n " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setText(result.getText() + "\n " + e.getMessage());
		}                      
    }
    
    private String getSHA1(String input)
    {
            MessageDigest cript = null;
			try {
				cript = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            cript.reset();
            try {
				cript.update(input.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return convertToHex(cript.digest());          
    }
    
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
}