package nhutlt.soft.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Random;

import android.util.Log;

import nhutlt.soft.util.HMACSha1Signature;

public class OAuth {
	private HashMap<String, String> mParams;
	public static final String RTOKENURL = "https://api.twitter.com/oauth/request_token";
	public static final String ATOKENURL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHENURL = "https://api.twitter.com/oauth/authorize";
	public static final String link = "https://api.twitter.com/1/";
	public static final String METHODPOST = "POST";
	public static final String METHODGET = "GET";
	
	public OAuth(String httpMethod) {
		mParams = new HashMap<String, String>();
		mParams.put("oauth_consumer_key", "sgyAJvKKZQb0CyBzK8c7cQ");
		mParams.put("oauth_consumer_secret_key", "4B8qdHucrwiZrngzjYeIjjJmCfqcuCsof4WYm3i8");
		mParams.put("oauth_token", "593548895-P4GskkuXp4RNlqJyyaEbFqNcM334q6tJtJsGsIxD");
		mParams.put("oauth_token_secret", "ZKYrhXswP2ldhq9j7RG5hiatmoaupXh4CZfXBDkZ7g");
		mParams.put("oauth_realm", "https://api.twitter.com");	
		mParams.put("oauth_signature_method", "HMAC-SHA1");
		mParams.put("oauth_timestamp", generateTimeStamp());
		mParams.put("oauth_nonce", generateOauthNonce());
		mParams.put("oauth_version", "1.0");
		mParams.put("oauth_callback", "");
		mParams.put("oauth_signature", generateSignature(httpMethod));
		try {
			mParams.put("oauth_signature_urlencoded", URLEncoder.encode(mParams.get("oauth_signature"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String authentication_header = "Authorization: OAuth " //realm=" + '"' + "https://api.twitter.com" + '"' 
				+ "oauth_consumer_key="             + '"' + mParams.get("oauth_consumer_key")     + '"'
				+ ", oauth_nonce="                    + '"' + mParams.get("oauth_nonce")            + '"' 
				+ ", oauth_signature="                + '"' + mParams.get("oauth_signature_urlencoded") + '"'//.replace("=", "%3D") + '"'				
				+ ", oauth_signature_method="         + '"' + mParams.get("oauth_signature_method") + '"' 
				+ ", oauth_timestamp="                + '"' + mParams.get("oauth_timestamp")        + '"'
				//+ ", oauth_callback="                 + '"' + mParams.get("oauth_callback")         + '"'
				+ ", oauth_token="                    + '"' + mParams.get("oauth_token")            + '"' 
				+ ", oauth_version="                  + '"' + mParams.get("oauth_version")          + '"';
		mParams.put("authentication_header", authentication_header);
	}	

	public String getParams(String param) {
		return mParams.get(param);
	}
	
	public String generateTimeStamp() {
		return Long.toString(System.currentTimeMillis()/1000);
	}
	
	public String generateOauthNonce() {
		String timeStamp = mParams.get("oauth_timestamp");
		String ranNumber = null;
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < 40; i++) {
			ranNumber += Integer.toString(r.nextInt(10));			
		}
		
        MessageDigest crypt = null;
        try {
            // declare type of hash function
            crypt = MessageDigest.getInstance("MD5");
            // get byte array of password string to hash function to generate hash code
            crypt.update((timeStamp + ranNumber).getBytes("UTF-8"));
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
	
	private String generateSignature(String httpMethod) {
		String baseString = httpMethod.toUpperCase() + "&https%3A%2F%2F"
				+ "api.twitter.com%2Foauth%2Frequest_token&oauth_consumer_key%3DsgyAJvKKZQb0CyBzK8c7cQ%26"
				+ "oauth_nonce%3D" + mParams.get("oauth_nonce") 
				+ "%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D" + mParams.get("oauth_timestamp")
				+ "%26oauth_token%3D593548895-vKAIDFSVzo96m3XardTByPdZVlcPIXgzpcPivjrn%26oauth_version%3D1.0";
		return HMACSha1Signature.getSignature(baseString, mParams.get("oauth_consumer_secret_key"), mParams.get("oauth_token_secret"));
	}

}