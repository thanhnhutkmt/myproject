package nhutlt.androidapp.calculator;

import nhutlt.androidapp.constant.CONSTANTS;
import nhutlt.androidapp.util.calculating;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends Activity implements OnClickListener{
	private TextView screenTV;
	private Button oneBT, twoBT, threeBT, fourBT, fiveBT, sixBT, sevenBT, eightBT, nineBT, zeroBT;
	private Button clearBT, resetBT, plusBT, minusBT, multiplyBT, divideBT, equalBT, powerBT, rootBT, factorialBT, logBT;
	private Button dotBT, reverseBT, oneminusBT, remainderBT, percentBT, modeBT, selectModeBT;
	private Button negativeBT, rootMeanSquareBT, power3BT, power2BT;
	
	private float operand1, operand2;
	private int operator;
	
	private int mode, mode1;
	private float a, b, c;
	
	private int base;
	
	private String valueToConvert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
				
		screenTV = (TextView) findViewById(R.id.mainscreen);
		oneBT = (Button) findViewById(R.id.one);
		oneBT.setOnClickListener(this);
		twoBT = (Button) findViewById(R.id.two);
		twoBT.setOnClickListener(this);
		threeBT = (Button) findViewById(R.id.three);
		threeBT.setOnClickListener(this);
		fourBT = (Button) findViewById(R.id.four);
		fourBT.setOnClickListener(this);
		fiveBT = (Button) findViewById(R.id.five);
		fiveBT.setOnClickListener(this);
		sixBT = (Button) findViewById(R.id.six);
		sixBT.setOnClickListener(this);
		sevenBT = (Button) findViewById(R.id.seven);
		sevenBT.setOnClickListener(this);
		eightBT = (Button) findViewById(R.id.eight);
		eightBT.setOnClickListener(this);
		nineBT = (Button) findViewById(R.id.nine);
		nineBT.setOnClickListener(this);
		zeroBT = (Button) findViewById(R.id.zero);
		zeroBT.setOnClickListener(this);
		clearBT = (Button) findViewById(R.id.clear);
		clearBT.setOnClickListener(this);
		resetBT = (Button) findViewById(R.id.reset);
		resetBT.setOnClickListener(this);
		plusBT = (Button) findViewById(R.id.plus);
		plusBT.setOnClickListener(this);
		minusBT = (Button) findViewById(R.id.minus);
		minusBT.setOnClickListener(this);
		multiplyBT = (Button) findViewById(R.id.multiply);
		multiplyBT.setOnClickListener(this);
		divideBT = (Button) findViewById(R.id.divide);
		divideBT.setOnClickListener(this);
		equalBT = (Button) findViewById(R.id.equal);
		equalBT.setOnClickListener(this);
		powerBT = (Button) findViewById(R.id.power);
		powerBT.setOnClickListener(this);
		rootBT = (Button) findViewById(R.id.root);
		rootBT.setOnClickListener(this);
		factorialBT = (Button) findViewById(R.id.factorial);
		factorialBT.setOnClickListener(this);
		logBT = (Button) findViewById(R.id.log);
		logBT.setOnClickListener(this);
		dotBT = (Button) findViewById(R.id.dot);
		dotBT.setOnClickListener(this);
		reverseBT = (Button) findViewById(R.id.reverse);
		reverseBT.setOnClickListener(this);
		oneminusBT = (Button) findViewById(R.id.oneminus);
		oneminusBT.setOnClickListener(this);
		remainderBT = (Button) findViewById(R.id.remainder);
		remainderBT.setOnClickListener(this);
		percentBT = (Button) findViewById(R.id.percent);
		percentBT.setOnClickListener(this);
		modeBT = (Button) findViewById(R.id.mode);
		modeBT.setOnClickListener(this);
		selectModeBT = (Button) findViewById(R.id.selectmode);
		selectModeBT.setOnClickListener(this);
		negativeBT = (Button) findViewById(R.id.negative);
		negativeBT.setOnClickListener(this);
		rootMeanSquareBT = (Button) findViewById(R.id.rootmeansquare);
		rootMeanSquareBT.setOnClickListener(this);
		power3BT = (Button) findViewById(R.id.power3);
		power3BT.setOnClickListener(this);
		power2BT = (Button) findViewById(R.id.power2);
		power2BT.setOnClickListener(this);
		
		resetCalculator();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int clickedButton = v.getId();
		String temp = screenTV.getText().toString();
		switch (clickedButton) {
		case R.id.one:			
			if (temp.equals("0")) {
				screenTV.setText("1");
			} else {
				screenTV.setText(temp + "1");
			}
			break;
		case R.id.two:			
			if (temp.equals("0")) {
				screenTV.setText("2");
			} else {
				screenTV.setText(temp + "2");
			}
			break;
		case R.id.three:			
			if (temp.equals("0")) {
				screenTV.setText("3");
			} else {
				screenTV.setText(temp + "3");
			}
			break;
		case R.id.four:			
			if (temp.equals("0")) {
				screenTV.setText("4");
			} else {
				screenTV.setText(temp + "4");
			}
			break;
		case R.id.five:			
			if (temp.equals("0")) {
				screenTV.setText("5");
			} else {
				screenTV.setText(temp + "5");
			}
			break;
		case R.id.six:			
			if (temp.equals("0")) {
				screenTV.setText("6");
			} else {
				screenTV.setText(temp + "6");
			}
			break;
		case R.id.seven:			
			if (temp.equals("0")) {
				screenTV.setText("7");
			} else {
				screenTV.setText(temp + "7");
			}
			break;
		case R.id.eight:			
			if (temp.equals("0")) {
				screenTV.setText("8");
			} else {
				screenTV.setText(temp + "8");
			}
			break;
		case R.id.nine:			
			if (temp.equals("0")) {
				screenTV.setText("9");
			} else {
				screenTV.setText(temp + "9");
			}
			break;
		case R.id.zero:			
			if (temp.equals("0")) {
//				screenTV.setText("1");
			} else {
				screenTV.setText(temp + "0");
			}
			break;
		case R.id.dot:			
			screenTV.setText(temp + ".");			
			break;
	//================================================================================//			
		case R.id.clear:
			screenTV.setText("0");
			break;
		case R.id.reset:
			resetCalculator();
			break;
		case R.id.plus:
			setOperator(CONSTANTS.PLUS, temp);		
			break;
		case R.id.minus:
			setOperator(CONSTANTS.MINUS, temp);
			break;
		case R.id.multiply:
			setOperator(CONSTANTS.MULTIPLY, temp);
			break;
		case R.id.divide:
			setOperator(CONSTANTS.DIVIDE, temp);
			break;
		case R.id.equal:
			if (CONSTANTS.MODELIST[mode] != CONSTANTS.NORMALMODE) {
				inputMode(temp);
			} else {
				if (operator == CONSTANTS.NOOP) {
					break;		
				} else {
					operand2 = Float.parseFloat(temp);
					screenTV.setText(calculating.calculate(operand1, operand2, operator));
	//				operator = CONSTANTS.NOOP;
				}
			}
			break;
		case R.id.power:
			setOperator(CONSTANTS.POWER, temp);
			break;
		case R.id.root:
			setOperator(CONSTANTS.ROOT, temp);
			break;
		case R.id.factorial:	
//			operator = CONSTANTS.NOOP;
			screenTV.setText(calculating.factorial(Integer.parseInt(temp)));
			break;
		case R.id.log:
			setOperator(CONSTANTS.LOG, temp);
			break;
		case R.id.reverse:
//			operator = CONSTANTS.NOOP;
			screenTV.setText(calculating.reverse(Float.parseFloat(temp)));
			break;
		case R.id.oneminus:
//			operator = CONSTANTS.NOOP;
			screenTV.setText(calculating.oneMinus(Float.parseFloat(temp)));
			break;
		case R.id.remainder:
			setOperator(CONSTANTS.REMAINDER, temp);
			break;
		case R.id.percent:
//			operator = CONSTANTS.NOOP;
			screenTV.setText(calculating.percent(Float.parseFloat(temp)));
			break;
		case R.id.mode:
			if (valueToConvert == null) {
				valueToConvert = temp;
			}
			mode++;
			if (mode == CONSTANTS.MODELISTLENGTH) {
				mode = 0;
			}
			displayMode(CONSTANTS.MODELIST[mode]);		
			break;
		case R.id.selectmode:
			setMode(CONSTANTS.MODELIST[mode]);
			break;
		case R.id.negative:
			if (temp.equals("0")) {
				break;
			} else if (temp.startsWith("-")){
				screenTV.setText(temp.substring(1));
			} else {
				screenTV.setText("-" + temp);
			}
			break;
		case R.id.rootmeansquare:
			screenTV.setText(calculating.rootMeanSquare((Double.parseDouble(temp))));
			break;
		case R.id.power2:
			if (mode1 == CONSTANTS.SINCOS_SIN || mode1 == CONSTANTS.SINCOS_COS
					|| mode1 == CONSTANTS.SINCOS_TG || mode1 == CONSTANTS.SINCOS_COTG) {
				screenTV.setText(calculating.degreeToRad(Float.parseFloat(temp)));
			} else {
				screenTV.setText(calculating.power2(Float.parseFloat(temp)));
			}
			
			break;
		case R.id.power3:
			if (mode1 == CONSTANTS.SINCOS_SIN || mode1 == CONSTANTS.SINCOS_COS
				|| mode1 == CONSTANTS.SINCOS_TG || mode1 == CONSTANTS.SINCOS_COTG) {
				screenTV.setText(calculating.radToDegree(Float.parseFloat(temp)));
			} else {
				screenTV.setText(calculating.power3(Float.parseFloat(temp)));
			}
			break;
		default:
			break;
		}
	}

	private void setOperator(int selectedOperator, String temp) {
		operand1 = Float.parseFloat(temp);
		operator = selectedOperator;
		screenTV.setText("0");
	}
	
	private void resetCalculator() {
		mode = 0;
		base = 10;
		valueToConvert = null;
		operand1 = 0;
		operand2 = 0;
		operator = CONSTANTS.NOOP;
		screenTV.setText("0");
	}
	
	private void setMode(int mode) {
		mode1 = mode;
		switch(mode) {
			case CONSTANTS.NORMALMODE :
				resetCalculator();
				break;
			case CONSTANTS.EQUAL2L :
				screenTV.setText("0");
//				mode1 = CONSTANTS.EQUAL2L_S1;
				break;
			case CONSTANTS.SINCOS_SIN :
			case CONSTANTS.SINCOS_COS :
			case CONSTANTS.SINCOS_TG :
			case CONSTANTS.SINCOS_COTG :
				power2BT.setText("Rad");
				power3BT.setText("Deg");
				screenTV.setText("0");
//				mode1 = mode;
				break;
			case CONSTANTS.CHANGEBASE_2 :
				screenTV.setText(calculating.toBinary(valueToConvert, base));
				base = 2;
				valueToConvert = screenTV.getText().toString();
				break;
			case CONSTANTS.CHANGEBASE_8 :
				screenTV.setText(calculating.toOctal(valueToConvert, base));
				base = 8;
				valueToConvert = screenTV.getText().toString();
				break;
			case CONSTANTS.CHANGEBASE_10 :
				screenTV.setText(calculating.toDec(valueToConvert, base));
				base = 10;
				valueToConvert = screenTV.getText().toString();
				break;
			case CONSTANTS.CHANGEBASE_16 :
				screenTV.setText(calculating.toHex(valueToConvert, base));
				base = 16;
				valueToConvert = screenTV.getText().toString();
				break;
		}
	}
	
	private void displayMode(int mode) {
		switch(mode) {
			case CONSTANTS.NORMALMODE :
				screenTV.setText("Normal mode");
				break;
			case CONSTANTS.EQUAL2L :
				screenTV.setText("ax^2 + bx + c");
				break;
			case CONSTANTS.SINCOS_SIN :
				screenTV.setText("SIN");
				break;
			case CONSTANTS.SINCOS_COS :
				screenTV.setText("COS");
				break;
			case CONSTANTS.SINCOS_TG :
				screenTV.setText("TG");
				break;
			case CONSTANTS.SINCOS_COTG :
				screenTV.setText("COTG");
				break;
			case CONSTANTS.CHANGEBASE_2 :
				screenTV.setText("Switch to binary");
				break;
			case CONSTANTS.CHANGEBASE_8 :
				screenTV.setText("Switch to otal");
				break;
			case CONSTANTS.CHANGEBASE_10 :
				screenTV.setText("Switch to decimal");
				break;
			case CONSTANTS.CHANGEBASE_16 :
				screenTV.setText("Switch to hexa");
				break;
		}
	}
	
	private void inputMode(String temp) {
		switch(CONSTANTS.MODELIST[mode]) {
			case CONSTANTS.EQUAL2L :
				if (mode1 == CONSTANTS.EQUAL2L_S1) {
					a = Float.parseFloat(temp);
					mode1 = CONSTANTS.EQUAL2L_S2;
					screenTV.setText("0");
				} else if (mode1 == CONSTANTS.EQUAL2L_S2) {
					b = Float.parseFloat(temp);
					mode1 = CONSTANTS.EQUAL2L_S3;
					screenTV.setText("0");
				} else if (mode1 == CONSTANTS.EQUAL2L_S3) {
					c = Float.parseFloat(temp);
					mode1 = CONSTANTS.EQUAL2L_S1;
					String s = calculating.expressionlevel2(a, b, c);
					screenTV.setText(s);						
				}
				break;
			case CONSTANTS.SINCOS_SIN :
			case CONSTANTS.SINCOS_COS :
			case CONSTANTS.SINCOS_TG :
			case CONSTANTS.SINCOS_COTG :
				String s = calculating.sincostgcotg(Double.parseDouble(temp), mode1);
				screenTV.setText(s);
				power2BT.setText(R.string.btarea);
				power3BT.setText(R.string.btcube);
				mode = 0;				
				break;
		}	
	}
}
