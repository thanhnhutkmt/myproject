package nhutlt.soft.util;

import java.util.HashMap;

import nhutlt.soft.model.OAuth;

public class twitter {
    /** token to communicate with twitter */
    private String mToken;
    /** status info : true - authenticated, false - not authenticated */
    private boolean mStatus;

    public String getTwitterToken() {
        String token = null;
        internet netUtil = new internet();
        token = netUtil.request(OAuth.RTOKENURL, OAuth.METHODPOST, null);
        //token = netUtil.request(OAuth.ATOKENURL, OAuth.METHODPOST, token);
        //token = netUtil.request(OAuth.AUTHENURL, OAuth.METHODPOST, token);
        //token = netUtil.requestUrl();
        
        return token;
    }
    
    public boolean authorize() {
        return false;
    }
    
    public boolean postTwitter() {
        return false;
    }
}