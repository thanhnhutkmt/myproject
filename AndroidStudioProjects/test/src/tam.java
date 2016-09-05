import java.util.ArrayList;
import java.util.Random;

public class tam {
	private static final int TOTAL_ITEM_NUM = 70;
	private static final int DECORATION_TOTAL = 3;
	private static final int SECRET_TOTAL = 20;
	private static final int ITEM_GACHA_ISLAND = 1;
	private static final int ITEM_GACHA_SECRET = 3;
	private static final int ITEM_GACHA_DECORATION = 2;
	private static final int NO_VALUE = -1;
	private static final int totalSecrDeco = 23;
	private static int[] items_flags = new int[TOTAL_ITEM_NUM];
	
	public static int gameResultOutput(int count){
		int countCollection = count;
		// Initialize item flags
		     
		ArrayList<Integer> remainValues = new ArrayList<Integer>(TOTAL_ITEM_NUM);        
		for(int index = 0; index < items_flags.length; index++){
			if(items_flags[index] == 0){
				remainValues.add(new Integer(index));
			}
		}
		int random = NO_VALUE;
		Random generator = new Random(System.currentTimeMillis());		
		if(countCollection >= TOTAL_ITEM_NUM || remainValues.size() == 0){
			random = generator.nextInt(TOTAL_ITEM_NUM);
			return numberToResult(random);
		}else{
			do{ 
				random = generator.nextInt(remainValues.size());
				random = remainValues.get(random);
				if(items_flags[random] != 1) {		
					items_flags[random] = 1;
					random = numberToResult(random);
					// luu so da duoc lay ra?? 
					// PrefUtils.writeIntArray(context, Constants.PREF_SECRET_ITEMS, items_flags);                
				}else {
					// hited, retry to choice random param
					// remove duplicating element before retry to prevent from
					random = NO_VALUE;                
				}        
			}while(random == NO_VALUE);   
			
		}
		return random;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testTranformValue();
		testGameResultOutput();
	}
	
	public static void testTranformValue() {
		int[] output = new int[70];
		for(int i = 0; i < 70; i++){
			output[i] = numberToResult(i);
			System.out.print("input : " + i + "  output : " + output[i] + "\n");
		}		
	}
	
	public static void testGameResultOutput() {
		for(int i = 0; i < 140; i++){
			System.out.print("time " + i + "  output : " + gameResultOutput(i) + "\n");
		}	
	}
	
	public static int numberToResult(int random) {
		random += 1; // cause index result start from 1
		if (random <= SECRET_TOTAL) { // 0 - 19: secret
			random = ITEM_GACHA_SECRET * 100 + random;
		} else if (random <= totalSecrDeco) { // 20 - 22: decoration
			random = random - SECRET_TOTAL;
			random = ITEM_GACHA_DECORATION * 100 + random;
		} else { // 23 - 69: island
			random = random - totalSecrDeco;
			random = ITEM_GACHA_ISLAND * 100 + random;
		}
		return random;
	}
}
