/**
 * 
 */
package util;

/**
 * @author ThanhNhut
 *
 */
public class TestData {
	public static final String VARDECLARE = "char testChar; int testInt;" +
											"ushort testUshort; short testShort;" +
											"ulong testUlong; long testLong;" +
											"float testFloat";
	public static final String VARDANDA[] = { 
			 "char testCharA = 255; char testCharB = 0;"
		   + "char testCharC = 256; char testCharD = -1",
			 "int testIntA = 127; int testIntB = -128; "
		   + "int testIntC = 128; int testIntD = -129",
			 "ushort testUshortA = 0; ushort testUshortB = 65535;"
		   + "ushort testUshortC = -1; ushort testUshortD = 65536;",
			 "short testShortA = 32767; short testShortB = -32768;"
		   + "short testShortC = 32768; short testShortD = -32769",
		     "ulong testUlongA = 0; ulong testUlongB = 4294967295;" 
		   + "ulong testUlongC = -1; ulong testUlongD = 4294967296;"
		   + "ulong testUlongE = 100ul; ulong testUlongF = 4294967296ul" ,
		     "long testLongA = -2147483648; long testLongB = 2147483647;"
		   + "long testLongC = -2147483649; long testLongD = 2147483648"
		   + "long testLongE = -100l; long testLongF = 2147483648l",
		     "float testFloatA = -2147483648; float testFloatB = 2147483647;"
		   + "float testFloatC = -2147483649; float testFloatD = 2147483648"
		   + "float testFloatE = -100l; float testFloatF = 2147483648l"};
	public static final String PTRDECLARE = 
			 "char * testCharPtr; int *testIntPtr; ushort *testUshortPtr;" +
			 " short * testShortPtr; ulong *testUlongPtr; " +
			 "long *testLongPtr; float *testFloatPtr";
	public static final String PTRDNA = 
			 "char * testCharPtra = 99; int *testIntPtra = 100; ushort *testUshortPtra = 2000;" +
			 " short * testShortPtra = 1000; ulong *testUlongPtra = 20000; " +
			 "long *testLongPtra = 10000; float *testFloatPtra = 9999";
	public static final String PTRA = "* testCharPtr = 100; *testIntPtr = 99;" +
			"*testUshortPtr = 2000; * testShortPtr = 1000; *testUlongPtr = 100000;" +
			"*testLongPtr = 100000; *testFloatPtr = 999999;"
			+ "* testCharPtra = 100; *testIntPtra = 99;" +
			"*testUshortPtra = 2000; * testShortPtra = 1000; *testUlongPtra = 100000;" +
			"*testLongPtra = 100000; *testFloatPtra = 999999;"; 
//	public static final String VARDECLARE = "int testInt;";
//	public static final String VARDECLARE = "int testInt;";
//	public static final String VARDECLARE = "int testInt;";
	
	public static boolean isValidType(String type) {
		return (other.getSizeOf(type) != 0);
	}
}
