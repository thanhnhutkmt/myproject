package software.nhut.personalutilitiesforlife.data;

import java.io.Serializable;

public class CipherData implements Serializable{

	private byte[] data;
	private byte[] iv;
	String pathWithName;
	
	public CipherData(byte[] data, byte[] iv) {
		this.data = data;
		this.iv = iv;
	}
	public byte[] getData() {
		return data;
	}
	public byte[] getIV() {
		return iv;
	}
	public String getPathWithName() {return pathWithName;}
    public void setPathWithName(String pathWithName) {this.pathWithName = pathWithName;}
}
