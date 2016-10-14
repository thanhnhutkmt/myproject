package util;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import software.nhut.personalutilitiesforlife.data.CipherData;


/*
* This software is provided 'as-is', without any express or implied
* warranty.  In no event will Google be held liable for any damages
* arising from the use of this software.
*
* Permission is granted to anyone to use this software for any purpose,
* including commercial applications, and to alter it and redistribute it
* freely, as long as the origin is not misrepresented.
* 
* @author: Ricardo Champa
* 
*/

public class MyCipher {
	
	private final static String ALGORITHM = "AES";
	private String mySecret;
	
	public MyCipher(String mySecret){
		this.mySecret = mySecret;
	}

    public static void encryptFile(String key, String encryptedFileNameWithPath, String fileNameWithPath) {
        List<CipherData> lcd = new ArrayList<>();
        lcd.add(new MyCipher(key).encryptUTF8(MyFileIO.readByteFile(new File(fileNameWithPath))));
        MyFileIO.saveData(lcd, encryptedFileNameWithPath);
    }

    public static void decryptFile(String key, String decryptedFileNameWithPath, String encryptedFileNameWithPath) {
        List<CipherData> lcd = (List<CipherData>) MyFileIO.loadData(encryptedFileNameWithPath);
        MyFileIO.writeByteFile(new File(decryptedFileNameWithPath), new MyCipher(key).decryptUTF8(lcd.get(0).getData(), lcd.get(0).getIV()), false);
    }

	public CipherData encryptUTF8(String data){
		try{
			byte[] bytes = data.toString().getBytes("utf-8");
			byte[] bytesBase64 = Base64.encodeBase64(bytes);
			return encrypt(bytesBase64);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
    public CipherData encryptUTF8(byte bytes[]){
        try{
            byte[] bytesBase64 = Base64.encodeBase64(bytes);
            return encrypt(bytesBase64);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
	
	public String decryptUTF8(byte[] encryptedData, IvParameterSpec iv){
		try {
			byte[] decryptedData = decrypt(encryptedData, iv);
			byte[] decodedBytes = Base64.decodeBase64(decryptedData);
			String restored_data = new String(decodedBytes, Charset.forName("UTF8"));
			return restored_data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    public byte[] decryptUTF8(byte[] encryptedData, byte[] iv){
        try {
            byte[] decryptedData = decrypt(encryptedData, new IvParameterSpec(iv));
            return Base64.decodeBase64(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	//AES
	private CipherData encrypt(byte[] raw, byte[] clear) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    //solved using PRNGFixes class
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] data = cipher.doFinal(clear);
	    
	    AlgorithmParameters params = cipher.getParameters();
	    byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
	    return new CipherData(data, iv);
	}

	private byte[] decrypt(byte[] raw, byte[] encrypted, IvParameterSpec iv) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	    byte[] decrypted = cipher.doFinal(encrypted);
	    return decrypted;
	}
	
	private byte[] getKey() throws Exception{
		//a pelo pues porque por ahora no hay mejor solucion
		byte[] keyStart = this.mySecret.getBytes("utf-8");
		KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
		
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
//		if (android.os.Build.VERSION.SDK_INT >= 17) {
//		    sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
//		} else {
//		    sr = SecureRandom.getInstance("SHA1PRNG");
//		}
		
		
		//SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(keyStart);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] key = skey.getEncoded();
		return key;
		
	}
	////////////////////////////////////////////////////////////
	private CipherData encrypt(byte[] data) throws Exception{
		return encrypt(getKey(),data);
	}
	private byte[] decrypt(byte[] encryptedData, IvParameterSpec iv) throws Exception{
		return decrypt(getKey(),encryptedData, iv);
	}
}
