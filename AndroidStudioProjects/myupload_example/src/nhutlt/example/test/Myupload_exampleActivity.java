package nhutlt.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class Myupload_exampleActivity extends Activity {
    private TextView mMessage;
    private static final String PREFNAME = "MyUpload";
    private static final String TOKEN = "Token";
    private static final int RENEWTOKEN = 0;
    private static final int GETTOKEN = 1;
    private static final long GETTOKENPERIOD = 540000;    
    private Handler mHandler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch(msg.what) {
            case RENEWTOKEN:
                String oldToken = getString(Myupload_exampleActivity.this, TOKEN, null);
                String token = null;
                int i = 0;
                if (oldToken != null) {
                	do {
	                    token = getToken(RENEWTOKEN, oldToken);
	                    if (token != null) {
	                        mMessage.setText(mMessage.getText() + "\n " + token);
	                    }
	                    i++;
	                    Log.v("Try again", i + "times");
                	}
                	while (token.contains("Error") && i < 9999);
                }
                putString(Myupload_exampleActivity.this, TOKEN, token);
                mHandler.sendEmptyMessageDelayed(RENEWTOKEN, GETTOKENPERIOD);
                return true;
            default:
                return true;
            }
        }
    });

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mMessage = (TextView) findViewById(R.id.text);
        String token = null;
        int i = 0;
        // get token
        do {
	        token = getToken(GETTOKEN, null);
	        if (token != null) {
	            mMessage.setText(mMessage.getText() + "\n " + token);
	        } 
	        i++;
	        Log.v("Try again", i + "times");
        } while (token.contains("Error") && i < 9999);
        putString(this, TOKEN, token);
        // renew token
        mHandler.sendEmptyMessageDelayed(RENEWTOKEN, GETTOKENPERIOD);
    }

    public String getString(Context context, String key, String defValue) {
        if(context == null) {
            return defValue;
        }

        SharedPreferences settings = context.getSharedPreferences(
                PREFNAME, /* MODE_PRIVATE */0);
        return settings.getString(key, defValue);
    }
    
    private boolean putString(Context context, String key, String value) {
        if(context == null) {
            return false;
        }

        SharedPreferences pref = context.getSharedPreferences(
                PREFNAME, /* MODE_PRIVATE */0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }
    
    private String signatureGenerator(String password) {
        MessageDigest crypt = null;
        try {
            // declare type of hash function
            crypt = MessageDigest.getInstance("SHA-1");
            // get byte array of password string to hash function to generate hash code
            crypt.update(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // generate hash code
        byte[] hash = crypt.digest();
        // convert byte array of hash code into hexa string format
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    /**
    JSON is built on two structures:
        * A collection of name/value pairs. In various languages, this is realized
        * as an object, record, struct, dictionary, hash table, keyed list, 
        * or associative array.
        * 
        * An ordered list of values. In most languages, this is realized as an 
        * array, vector, list, or sequence.
    
    Syntax of JSON
    Object {name1 : Value1 , name2 : Value2 , name3 : Value3 } -Normal Object Collection
    
    Object [{name1_1 : Value , name1_2 : Value1 , name1_3 : Value2 }
        {name2_1 : Value , name2_2 : Value1 , name2_3 : Value2 } ] - Array Based
    */
    public String getToken(int function, String token) {
        String get_session_token =
                "https://www.mediafire.com/api/user/get_session_token.php";
        String renew_session_token =
                "http://www.mediafire.com/api/user/renew_session_token.php";
        String email = "legendknight99@yahoo.com";
        String pass = "987654321";
        String appID = "5908";
        String resFormat = "json";
        String ver = "2.5";
        String appKey = "lyj4y3d4433l6rz1xp39568buhbn5708xjzft88t";
        String sign = signatureGenerator(email + pass + appID + appKey);
        JSONObject json = null;
        String getToken = null, result = null;
        InputStream in = null;        
        HttpResponse response = null;
        if (function == 1) {
            getToken = get_session_token + "?"
                              + "email=" + email + "&"
                              + "password=" + pass + "&"
                              + "application_id=" + appID + "&"
                              + "signature=" + sign + "&"
                              + "response_format=" + resFormat + "&"
                              + "version=" + ver;
        } else {
            getToken = renew_session_token + "?"
                    + "session_token=" + token + "&"
                    + "response_format=" + resFormat;
        }
        
        // declare client to communicate with server
        DefaultHttpClient client = new DefaultHttpClient();
        // use HttpGet to send "get datastream" command from server
        HttpGet httpGet = new HttpGet(getToken);
        try {
            // communicate with server to get data from server
            response = client.execute(httpGet);
            // get content got from server
            in = response.getEntity().getContent();
            // convert data into human readable format
            result = convertStreamToString(in);
            // put data into json form 
            json = new JSONObject(result);
            // get value of name "response" pair
            json = json.getJSONObject("response");
            // get value of name "session_token"
            token = json.getString("session_token");
        } catch (Exception e) {
            e.printStackTrace();
            token = "Error : " + e.getMessage();
        }
        client.getConnectionManager().shutdown();
        return token;
    }
}