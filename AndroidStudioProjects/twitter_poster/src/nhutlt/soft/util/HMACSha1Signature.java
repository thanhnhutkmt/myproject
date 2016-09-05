package nhutlt.soft.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import nhutlt.soft.util.URLUtils;

/**
 * HMAC-SHA1 implementation of {@SignatureService}
 * 
 * @author Pablo Fernandez
 * 
 */
public class HMACSha1Signature {
    private static final String EMPTY_STRING = "";
    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String UTF8 = "UTF-8";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String METHOD = "HMAC-SHA1";

    /**
     * {@inheritDoc}
     */
    public static String getSignature(String baseString, String apiSecret,
            String tokenSecret) throws IllegalArgumentException {
        generalUtils.checkEmptyString(baseString, "Base string cant be null or empty string");
        generalUtils.checkEmptyString(apiSecret, "Api secret cant be null or empty string");
        return doSign(baseString, URLUtils.percentEncode(apiSecret) + '&'
                + URLUtils.percentEncode(tokenSecret));
    }

    private static String doSign(String toSign, String keyString) {
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes(UTF8),
                    HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(key);
            byte[] bytes = mac.doFinal(toSign.getBytes(UTF8));
            String signature = new String(Base64.encodeBase64(bytes)); 
            return signature;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
