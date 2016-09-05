/**
 * 
 */
package nhutlt.androidapp.util;

import nhutlt.androidapp.constant.CONSTANTS;

/**
 * @author nhutlt
 *
 */
public class calculating {
	public static String calculate(float op1, float op2, int operator) {
		String result = "";
		switch(operator) {
			case CONSTANTS.PLUS:
				result = Float.toString(op1 + op2);
				break;
			case CONSTANTS.MINUS:
				result = Float.toString(op1 - op2);
				break;
			case CONSTANTS.MULTIPLY:
				result = Float.toString(op1 * op2);
				break;
			case CONSTANTS.DIVIDE:
				result = Float.toString(op1 / op2);
				break;		
			case CONSTANTS.POWER:
				result = Double.toString(Math.pow(op1, op2));
				break;	
			case CONSTANTS.ROOT:
				result = Double.toString(root(op1, (int)op2));
				break;	
			case CONSTANTS.LOG:
				result = Float.toString(logarit(op1, op2));
				break;	
			case CONSTANTS.REMAINDER:
				result = Float.toString(op1 % op2);
				break;	
		}		
		return result;
	}
	
	public static String factorial(int op) {
		String result = "";
		float temp = 1;
		if (op <= 0) {
			result = "1";
		} else {
			for (int index = 1; index <= op; index++) {
				temp *= index;
			}
			result = Float.toString(temp);
		}
		return result;
	}
	
	private static float logarit(double op1, double op2) {
		String result = "";
		return (float) (Math.log10(op2)/ Math.log10(op1));
	}
	
	private static double root(double op1, int op2) {	
		if (op1 <= 0 || op2 <= 0) {
			return 0;
		}
		
		// find the nearest integer number
		int temp1 = 1;
		while(true) {		
			if (Math.pow(temp1, op2) > op1) {
				temp1--;
				break;
			}
			temp1++;
		}
		
		// find the exactly number with error
		double temp = temp1;
		while(true) {
			if (op1 - Math.pow(temp, op2) < (double)0.1) {
				temp1--;
				break;
			}
			temp += 0.001;
		}
		return temp;
	}
	
	public static String reverse(float op) {
		return Float.toString(1 / op);
	}
	
	public static String oneMinus(float op) {
		return Float.toString(1 - op);
	}
	
	public static String percent(float op) {
		return Float.toString(op * 100);
	}
	
	public static String rootMeanSquare(double op) {
		return Double.toString(Math.sqrt(op));
	}
	
	public static String power3(float op) {
		return Float.toString(op * op * op);
	}
	
	public static String power2(float op) {
		return Float.toString(op * op);
	}
	
	public static String expressionlevel2(float a, float b, float c) {
		double delta = b*b - 4*a*c;						
		double x1 = (-b + Math.sqrt(delta))/(2*a);
		double x2 = (-b - Math.sqrt(delta))/(2*a);
		return String.format("x1 = %.2f x2 = %.2f", x1, x2);
	}
	
	public static String sincostgcotg(double value, int type) {
		double result = 0;
		switch(type) {
			case CONSTANTS.SINCOS_SIN :
				result = Math.sin(value);
				break;
			case CONSTANTS.SINCOS_COS :
				result = Math.cos(value);
				break;
			case CONSTANTS.SINCOS_TG :
				result = Math.tan(value);
				break;
			case CONSTANTS.SINCOS_COTG :
				result = 1 / Math.tan(value);
				break;
		}
		return Double.toString(result);
	}
	
	public static String degreeToRad(float value) {
		return Double.toString((value * Math.PI) / 180);
	}
	
	public static String radToDegree(float value) {
		return Double.toString(value * 180 / Math.PI);
	}
	
	public static String toBinary(String value, int base) {
		return Integer.toBinaryString(Integer.parseInt(value, base));
	}

	public static String toHex(String value, int base) {
		return Integer.toHexString(Integer.parseInt(value, base)).toUpperCase();
	}

	public static String toDec(String value, int base) {
		return Integer.toString(Integer.parseInt(value, base));
	}
	
	public static String toOctal(String value, int base) {
		return Integer.toOctalString(Integer.parseInt(value, base));
	}
}
