/**
 * 
 */
package nhutlt.androidapp.constant;

/**
 * @author nhutlt
 *
 */
public class CONSTANTS {
	public static final int one = 1;
	public static final int two = 2;
	public static final int three = 3;
	public static final int four = 4;
	public static final int five = 5;
	public static final int six = 6;
	public static final int seven = 7;
	public static final int eight = 8;
	public static final int nine = 9;
	public static final int zero = 0;
	public static final int dot = 10;
	
	public static final int NOOP = -1;
	public static final int clear = 11;
	public static final int reset = 12;
	public static final int PLUS = 13;
	public static final int MINUS = 14;
	public static final int MULTIPLY = 15;
	public static final int DIVIDE = 16;
	public static final int EQUAL = 17;
	public static final int POWER = 18;
	public static final int ROOT = 19;
	public static final int FACTORIAL = 20;
	public static final int LOG = 21;
	public static final int REMAINDER = 22;
	
	//===Mode selection===//
	public static final int NORMALMODE = 1000;
	public static final int EQUAL2L = 1100;
	public static final int EQUAL2L_S1 = 1101;
	public static final int EQUAL2L_S2 = 1102;
	public static final int EQUAL2L_S3 = 1103;
	public static final int SINCOS = 1200;
	public static final int SINCOS_SIN = 1201;
	public static final int SINCOS_COS = 1202;
	public static final int SINCOS_TG = 1203;
	public static final int SINCOS_COTG = 1204;
	public static final int CHANGEBASE = 1300;
	public static final int CHANGEBASE_2 = 1301;
	public static final int CHANGEBASE_8 = 1302;
	public static final int CHANGEBASE_10 = 1303;
	public static final int CHANGEBASE_16 = 1304;
	
	//===Mode list===//
	public static final int[] MODELIST = {NORMALMODE, EQUAL2L, SINCOS_SIN, 
					SINCOS_COS, SINCOS_TG, SINCOS_COTG, CHANGEBASE_2, CHANGEBASE_8, 
					CHANGEBASE_10, CHANGEBASE_16};
	public static final int MODELISTLENGTH = MODELIST.length;
	
}

