package nhutlt.soft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;

import nhutlt.soft.model.OAuth;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class internet {
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
    
    public String request(String uri, String method, String token) {
        JSONObject json = null;
        String result = null, rToken = null, rTokenSecret = null;
        InputStream in = null;
        HttpResponse response = null;
        OAuth oauth = new OAuth(method);
        // declare client to communicate with server
        DefaultHttpClient client = new DefaultHttpClient();
        // use HttpGet to send "get datastream" command from server
        HttpPost httpPost = new HttpPost(uri);
        try {
        	httpPost.addHeader("Authorization", oauth.getParams("authentication_header"));
            // communicate with server to get data from server
            response = client.execute(httpPost);
            rToken = (String) response.getParams().getParameter("oauth_token");
            rTokenSecret = (String) response.getParams().getParameter("oauth_token_secret");
            // get content got from server
            in = response.getEntity().getContent();
            // convert data into human readable format
            result = convertStreamToString(in);
            //xmlReader xr = new xmlReader(result);

            Log.d("NhutLT", result);
        } catch (Exception e) {
            e.printStackTrace();
            //token = "Error : " + e.getMessage();
            result = e.getMessage();
        }
        client.getConnectionManager().shutdown();
        return result;
    }
}
