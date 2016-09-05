package nhutlt.studyapp.learnnewwords;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nhutlt.studyapp.util.preparer;
import nhutlt.studyapp.wordadder.adder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    private ArrayList<String> word, pronun, wordType, meaning;
    private int[] randomOrder;
    private ListView listAnswer;
    private Context context;
    private int currentWord, itemLayout;
    private Button checkBT;    
    private TextView wordTV;
//    private AssetManager assetmgr;   

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_main);
//		assetmgr = getAssets();
		currentWord = 0;
		checkBT = (Button) findViewById(R.id.confirm);
		wordTV = (TextView) findViewById(R.id.word);
		Typeface font= Typeface.createFromAsset(getAssets(), "ARIAL.TTF");
		wordTV.setTypeface(font);
		String appFolder = getResources().getString(R.string.app_folder);
		if (!preparer.checkAppFolder(appFolder) 
				|| !preparer.checkDataFile(appFolder)) {
			System.err.print("Error");
		}
		adder wordAdder = null;
		try {
			wordAdder = new adder(appFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> data = wordAdder.getData();
		ArrayList<Object> dataLists = preparer.extractData(data);
		randomOrder = preparer.genRandomOrder(((ArrayList<String>)dataLists.get(0)).size());
		listAnswer = (ListView) findViewById(R.id.listanswer);
		itemLayout = R.layout.answeritem;
		word = (ArrayList<String>) dataLists.get(0);
		pronun = (ArrayList<String>) dataLists.get(1);
		wordType = (ArrayList<String>) dataLists.get(2);
		meaning = (ArrayList<String>) dataLists.get(3);
		nextWord();
		
		checkBT.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				String answers[] = ((answerListAdapter)listAnswer.getAdapter()).getAnswer();
				int result = preparer.checkAnswer(answers, pronun, wordType, meaning, randomOrder[currentWord]);
				Bundle myData = new Bundle();
				if (result == 0) {
					String cWord = word.get(randomOrder[currentWord]); 
					String cPronun = pronun.get(randomOrder[currentWord]); 
					String cWT = wordType.get(randomOrder[currentWord]);
					String cMeaning = meaning.get(randomOrder[currentWord]);
					myData.putString("CorrectWord", cWord);
					myData.putString("CorrectPronun", cPronun);
					myData.putString("CorrectWordType", cWT);
					myData.putString("CorrectMeaning", cMeaning);
				}
				myData.putInt("result", result);
				removeDialog(0);
				showDialog(0, myData);
				if (result == 0) {
					currentWord++;
					if (currentWord < word.size()) {					
						nextWord();
					}
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void nextWord() {
		wordTV.setText(word.get(randomOrder[currentWord]));
		List<String> answerChoice = preparer.genAnswerChoices(pronun, wordType, meaning, randomOrder[currentWord]);		
		boolean[] selectItem = new boolean[answerChoice.size()];
		answerListAdapter myAdapter = new answerListAdapter(context, itemLayout, answerChoice, selectItem);		
		listAnswer.setAdapter(myAdapter);
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle myData) {
//		return super.onCreateDialog(id);
		String title, message, buttonLabel;
		buttonLabel = "OK";
		int result = myData.getInt("result");
		switch(result) {
			case 1:
				title = "Wrong type";
				message = "Word Type is wrong. Please check again!";				
				break;
			case 2:
				title = "Wrong pronun";
				message = "Pronun is wrong. Please check again!";
				break;
			case 3:
				title = "Wrong type and pronun";
				message = "Word type and Pronun are wrong. Please check again!";
				break;
			case 4:
				title = "Wrong meaning";
				message = "Meaning is wrong. Please check again!";
				break;
			case 5:
				title = "Wrong word type and meaning";
				message = "Word type and meaning are wrong. Please check again!";
				break;
			case 6:
				title = "Wrong meaning and pronun";
				message = "Word meaning and pronun are wrong. Please check again!";
				break;
			case 7:
				title = "All wrong!!!";
				message = "Please check again!";
				break;				
			default:
				title = "That's right";		
				message = "";
				buttonLabel = "Come on.";
				break;
		}
		AlertDialog.Builder adBuilder = new AlertDialog.Builder(this); 
        adBuilder
        .setTitle(title)            
        .setPositiveButton(buttonLabel, new DialogInterface.OnClickListener() {
                        
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (result != 0) {
        	adBuilder.setMessage(message);
        }
        Dialog myDialog = (Dialog) adBuilder.create();
        
        if (result == 0) {
        	LinearLayout layout = new LinearLayout(this);
	        LinearLayout subLayout = new LinearLayout(this);
	        TextView myMessageTV = new TextView(this);
	        TextView myMessageTV1 = new TextView(this);
	        TextView myMessageTV2 = new TextView(this);        
	        Typeface font1 = Typeface.createFromAsset(context.getAssets(), "ARIAL.TTF");
	        Typeface font2 = Typeface.createFromAsset(context.getAssets(), "PHTIB__.TTF");

	        myMessageTV.setTextColor(Color.BLACK);
	        myMessageTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
	        myMessageTV.setTypeface(font1);
	        myMessageTV.setText(myData.getString("CorrectWord"));              
	        
	        myMessageTV1.setTextColor(Color.BLACK);
	        myMessageTV1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
	        myMessageTV1.setTypeface(font2);
	        myMessageTV1.setPadding(10, 0, 0, 0);
	        myMessageTV1.setText(myData.getString("CorrectPronun"));        
	        
	        myMessageTV2.setTextColor(Color.BLACK);
	        myMessageTV2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
	        myMessageTV2.setTypeface(font1);
	        myMessageTV2.setText(myData.getString("CorrectWordType") + " " + myData.getString("CorrectMeaning"));
	        
	        layout.setOrientation(LinearLayout.VERTICAL);
	        layout.addView(subLayout);
	        layout.addView(myMessageTV2);
	        layout.setPadding(5, 5, 5, 5);
	        subLayout.setOrientation(LinearLayout.HORIZONTAL);
	        subLayout.addView(myMessageTV);
	        subLayout.addView(myMessageTV1);
	        
	        ((AlertDialog)myDialog).setView(layout);	        
        }
        return myDialog;
	}

}
