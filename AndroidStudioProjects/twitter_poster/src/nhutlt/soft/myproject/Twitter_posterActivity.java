package nhutlt.soft.myproject;

import nhutlt.soft.model.OAuth;
import nhutlt.soft.util.HMACSha1Signature;
import nhutlt.soft.util.twitter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Twitter_posterActivity extends Activity {
	private static TextView mText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mText = (TextView) findViewById(R.id.text);
        twitter twitt = new twitter();
        mText.setText(twitt.getTwitterToken());
        
        /*
        OAuth a = new OAuth("POST");
        String Nonce = a.getParams("oauth_nonce");
        String timeStamp = a.getParams("oauth_timestamp");
        Log.v("NhutLT", Nonce + " " + timeStamp);
        String base = "POST&https%3A%2F%2Fapi.twitter.com%2F1%2F&oauth_consumer_key%3DsgyAJvKKZQb0CyBzK8c7cQ%26oauth_nonce%3D" + Nonce +"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D" + timeStamp + "%26oauth_token%3D593548895-vKAIDFSVzo96m3XardTByPdZVlcPIXgzpcPivjrn%26oauth_version%3D1.0";
        String apiSecret = "4B8qdHucrwiZrngzjYeIjjJmCfqcuCsof4WYm3i8";
        String tokenSecret = "cmIRJinLLrxKbfQoxO4GNGxwAoo5DOBFC22oapZNBk0";
        String signture = HMACSha1Signature.getSignature(base, apiSecret, tokenSecret);
        mText.setText(mText.getText() + "\n" + signture);*/
    }
}